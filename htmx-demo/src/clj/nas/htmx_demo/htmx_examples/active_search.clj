(ns nas.htmx-demo.htmx-examples.active-search
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
            [nas.htmx-demo.htmx-examples.data :as local-db]))

(defn home [request]
  (page 
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:h3 "Search contacts"
     [:span {:class "htmx-indicator"}
      [:img {:src "/img/bars.svg"}] "Searching..."]]
    [:input {:class "form-control"
             :type "search"
             :name "search"
             :placeholder "Begin Typing To Search Users..."
             :hx-post "active-search/search"
             :hx-trigger "keyup changed delay:500ms, search"
             :hx-target "#search-results"
             :hx-indicator "htmx-indicator"}]
    [:table {:class "table"}
     [:thead [:tr
              [:th "First Name"]
              [:th "Last Name"]
              [:th "Email"]]]
     [:tbody {:id "search-results"}]]]))

(defn search [request]
  (ui
   [:tr
    [:td "nas"]
    [:td "andrei"]
    [:td "nas@office.com"]]))

(comment 
 
 0)