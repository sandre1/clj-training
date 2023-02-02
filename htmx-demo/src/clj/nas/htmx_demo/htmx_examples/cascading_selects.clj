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
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:h2 "Cascading Selects"]
      [:p "In this example we show how to make the values in one " [:code "select"] " depend on the value selected in another " [:code "select"] "."]
      [:p "To begin we start with a default value for the  " [:code "make"] " select: Audi. We render the " [:code "model"] " select for this make. We then have the " [:code "make"] " select trigger a " [:code "GET"] " to " [:code "/models"] " to retrieve the models options and target the " [:code "models"] " select."]
      [:p "Here is the code:"]
      
      [:pre
       [:code {:class "language-html"}
        "[:div
  [:label \"Make\"]
  [:select {:name \"make\"
            :hx-get \"/value-select/models\"
            :hx-target \"#models\"
            :hx-indicator \".htmx-indicator\"}
  [:option {:value \"acura\"} \"Acura\"]
  [:option {:value \"alfa-romeo\"} \"Alfa Romeo\"]
  [:option {:value \"aston-martin\"} \"Aston Martin\"]]]
  [:div [:label \"Model\"]
   [:select {:id \"models\"
             :name \"model\"}
     [:option {:value \"integra\"} \"Integra\"]
         ...
  ]]"]]
      
      [:p "When a request is made to the " [:code "/value-select/models"] " end point, we return the models for that make, and they become available in the " [:code "model"] " select."]
      
      
      [:div
       [:label "Make"]
       [:select {:name "make"
                 :hx-get "/value-select/models"
                 :hx-target "#models"
                 :hx-indicator ".htmx-indicator"}
        (make-options cars)]]
      [:div [:label "Model"]
       [:select {:id "models"
                 :name "model"}
        [:option {:value "integra"} "Integra"]
        [:option {:value "tlx"} "TLX"]
        [:option {:value ""} "..."]]]]]]))

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
    (map (fn [[k v]] (str "make is: " v)) c))
  
  0)

