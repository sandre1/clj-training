(ns nas.htmx-demo.htmx-examples.click-to-load
  (:require
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
   [nano-id.core :refer [nano-id]]))

(defn create-data [i]
  (let [entries (range 0 i)
        create-agents (map (fn [n]
                             [{:name "Agent Smith"
                               :email (str "void" n "@null.org")
                               :id (nano-id 15)}]) entries)] create-agents))

(def agents (create-data 100))

(def team (atom agents))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:table
     [:thead [:tr
              [:th "Name"]
              [:th "Email"]
              [:th "ID"]]
      [:tbody {:id "tbody"}]
      [:tr {:id "replaceMe"}
       [:td {:colspan 3}
        [:button {:class "btn"
                  :hx-get "/click-to-load/load-more?page=2"
                  :hx-target "#replaceMe"
                  :hx-swap "outerHTML"} "Load more agents..."]]]]]]))

(defn load-more [request]
  (ui 
   (let [params (:query-params request)]
     [:div (str params)])))

(comment
  (create-data 10)
  (deref team)
  0)