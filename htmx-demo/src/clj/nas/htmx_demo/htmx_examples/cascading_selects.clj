(ns nas.htmx-demo.htmx-examples.cascading-selects
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
            [nas.htmx-demo.htmx-examples.data :as local-db]))

(def cars local-db/csv-cars)

(defn make-options [c]
  (let [makes (group-by :make c)]
    (map (fn [[k _]] [:option {:value k} k]) makes)))

(defn model-options [c opt]
  (let [makes (group-by :make c)
        select-values (comp vals select-keys)
        models (select-values makes [opt])]
    (first models)))

(defn home [_request]
  (page 
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" }]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div 
     [:label "Make"]
     [:select {:name "make"
               :hx-get "/cascading-selects/models"
               :hx-target "#models"
               :hx-indicator ".htmx-indicator"}
      (make-options cars)]]
    [:div [:label "Model"]
     [:select {:id "models"
               :name "model"}
      [:option {:value "a1"} "A1"]
      [:option {:value "a3"} "A3"]]]]))

(defn models [request]
  (ui 
   (let [params (:params request)
         model (:make params)
         car-models (model-options cars model)]
     (map (fn [cm] [:option {:value (:model cm)} (:model cm)]) car-models)
     )))

(comment 
  (list cars)
  (group-by :make cars)
  (let [cars (group-by :make cars)]
    (select-keys cars ["Acura"]))
  (let [c (group-by :make cars)]
    (map (fn [[k v]] (str "make is: " k)) c))
  
  0)

