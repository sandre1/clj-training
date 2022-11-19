(ns nas.htmx-demo.htmx-examples.delete-row
  (:require 
   [nas.htmx-demo.htmx-examples.data :as local-db]
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(def users local-db/persons)

#_(def app-state (atom users))

#_(defn delete-user-by-index [i]
  (let [coll @app-state]
    (filter #(not= (Integer/parseInt (:index %)) i) coll)))

#_(defn delete-handler [v]
  (let [set1 (set v)
        set2 (set @app-state)]
    (vec (clojure.set/intersection set1 set2))))

(defn delete-user [request]
  (ui nil))

(defn row [p]
  (let [i (:index p)
        url (str "/delete-row/delete-user?user-index=" i)]
    [:tr [:td (:name p)]
   [:td (:email p)]
   [:td (:status p)]
   [:td [:button {:class "btn btn-danger" :hx-delete url} "Delete"]]]))

(defn make-rows [persons]
  (map row persons))

(defn home [request]
  (page 
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:table {:class "table delete-row-example"}
     [:thead [:tr [:th "Name"]
              [:th "Email"]
              [:th "Status"]
              [:th]]]
     [:tbody {:hx-confirm "Are you Sure?"
              :hx-target "closest tr"
              :hx-swap "outerHTML swap:1s"}
      (make-rows users)]]]
   ))

(comment 
  
  0)

