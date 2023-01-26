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
      [:h2 "Lazy Loading"]
      [:p "This example shows how to lazily load an element on a page. We start with an initial state that looks like this:"]
      
      [:pre [:code {:class "language-html"}
             "   [:div {:hx-get \"lazy-loading/graph\"
             :hx-trigger \"load\"}
       [:img {:alt \"Result loading...\"
              :class \"htmx-indicator\"
              :width \"150\"
              :src \"/img/bars.svg\"}]]"]]
      
      [:p "Which shows a progress indicator as we are loading the graph. The graph is then loaded and faded gently into view via a settling CSS transition:"]
      
      [:pre [:code {:class "language-html"}
             "   [:style \".htmx-settling img {opacity: 0;}
            img {transition: opacity 300ms ease-in;}\"]"]]
      
      [:h2 "Demo"]
      
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