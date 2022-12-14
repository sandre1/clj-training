(ns nas.htmx-demo.htmx-examples.bulk-update
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
            [nas.htmx-demo.htmx-examples.data :as local-db]))

(def bulk-update-state (atom local-db/persons))

(defn row-structure [person]
  (let [name (:name person)
        email (:email person)
        status (:status person)
        index (:index person)
        active? (= status "active")]
    [:tr {:class (if active? "activate" "deactivate")}
     [:td [:input {:type "checkbox" :name "ids" :value index}]]
     [:td name]
     [:td email]
     [:td (if active? "Active" "Inactive")]]))

(defn generate-rows [persons]
  (map row-structure persons))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:form {:id "checked-contacts"}
     [:table
      [:thead [:tr [:th]
               [:th "Name"]
               [:th "Email"]
               [:th "Status"]]
       [:tbody {:id "tbody"}
        (generate-rows @bulk-update-state)]]]]
    [:div {:hx-include "#checked-contacts" :hx-target "#tbody"}
     [:a {:class "btn" :hx-put "/bulk-update/activate"} "Activate"]
     [:a {:class "btn" :hx-put "/bulk-update/deactivate"} "Deactivate"]]]
   ))

(defn create-activation-data [state request-params]
    (let [request-ids (vals request-params)
          ids (flatten (list request-ids))]
      (swap! state 
           (fn [persons]
             (map (fn [person]
                    (if (some #(= (:index person) %) ids)
                      (assoc person :status "active")
                      person))
                  persons)))))

(defn create-deactivation-data [state request-params]
    (let [request-ids (vals request-params)
          ids (flatten (list request-ids))]
      (swap! state 
           (fn [persons]
             (map (fn [person]
                    (if (some #(= (:index person) %) ids)
                      (assoc person :status "inactive")
                      person))
                  persons)))))

(defn activate [request]
  (let [req-params (:form-params request)
        activation-data (create-activation-data bulk-update-state req-params)]
    (ui
     (generate-rows activation-data))))

(defn deactivate [request]
  (let [req-params (:form-params request)
        deactivation-data (create-deactivation-data bulk-update-state req-params)]
    (ui
     (generate-rows deactivation-data))))