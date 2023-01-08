(ns nas.htmx-demo.htmx-examples.lazy-loading
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:div {:hx-get "lazy-loading/graph"
             :hx-trigger "load"}
       [:img {:alt "Result loading..."
              :class "htmx-indicator"
              :width "150"
              :src "/img/bars.svg"}]]]]]))

(defn graph [request]
  (ui [:img {:alt "some graph"
             :src "/img/tokyo.png"}]))

(comment 
  (graph {})
  0)