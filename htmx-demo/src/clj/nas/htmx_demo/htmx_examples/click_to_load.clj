(ns nas.htmx-demo.htmx-examples.click-to-load
  (:require
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
   [nas.htmx-demo.htmx-examples.data :as local-db]
   [nano-id.core :refer [nano-id]]
   [clojure.set :refer [rename-keys]]))

(defn create-data [i]
  (let [entries (range 0 i)
        create-agents (map (fn [n]
                             {:name "Agent Smith"
                              :email (str "void" n "@null.org")
                              :id (nano-id 15)}) entries)]
    (vec create-agents)))

#_(def agents (create-data 100))

(def agents local-db/agents)

(def team (atom ()))

(defn data-row [a]
  (let [name (:name a)
        email (:email a)
        id (:id a)]
    [:tr [:td name]
     [:td email]
     [:td id]]))

(defn data-rows [agents]
  (map data-row agents))

(defn home [request]
  (page
   (do (reset! team [])
       (list [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" }]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:table
     [:thead [:tr
              [:th "Name"]
              [:th "Email"]
              [:th "ID"]]]
     [:tbody {:id "tbody"}
       (data-rows (take 10 agents))
       [:tr {:id "replaceMe"}
        [:td {:colspan 3}
         [:button {:class "btn"
                   :hx-get "/click-to-load/load-more?page=2"
                   :hx-target "#replaceMe"
                   :hx-swap "outerHTML"} "Load more agents..."]]]]]]))))

(defn construct-tr [state]
  (for [i @state]
    [:tr [:td (:name i)]
     [:td (:email i)]
     [:td (:id i)]]))

(defn add-more-agents [list-of-agents n]
  (let [min-n (dec n) ;; exclude first 10 rows already loaded
        start (* min-n 10)
        finish (* n 10)]
    (subvec list-of-agents start finish)))


(defn load-more [request]
  (ui
   (let [params (:query-params request)
         parsed-params (rename-keys params {"page" :page})
         page (:page parsed-params)
         page-number (Integer/parseInt page)
         new-page (inc page-number)
         url (str "/click-to-load/load-more?page=" new-page)]
     (do (reset! team [])
       (swap! team (fn [team] (vec (distinct (concat team (add-more-agents agents page-number))))))
       (list
        (construct-tr team)
        [:tr {:id "replaceMe"}
         [:td {:colspan 3}
          [:button {:class "btn"
                    :hx-get url
                    :hx-target "#replaceMe"
                    :hx-swap "outerHTML"} "Load more agents..."]]])))))

(comment
  (let [page 2
        new-page (inc page)]
    (swap! team (fn [team] (distinct (concat team (take (* new-page 10) agents))))))
  (let [p (atom {:name "nas" :age "34"})]
    [:td (:name @p)])
  (take 20 agents)
  (add-more-agents agents 2)
  0
  )