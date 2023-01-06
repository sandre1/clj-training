(ns nas.htmx-demo.htmx-examples.sortable
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn make-items [items-list]
  (map (fn [i] [:div [:input {:type "hidden"
                        :name "item"
                        :value i}] (str "Item " i)]) items-list))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.8.4/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    #_[:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    ;;TODO @Andrei - nu apeleaza post/items
    [:form {:class "sortable"
            :hx-post "sortable/items"
            :hx-trigger "end"}
     [:div {:class "htmx-indicator"} "Updating..."]
     (make-items (vec (range 1 6)))]
    [:script {:src "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.js" :defer true}]
    [:script {:src "/js/scripts.js"}]
    ]))

(defn items [request]
  (ui 
   (let [p (:params request)
         items (:item p)]
     (list 
      [:div {:class "htmx-indicator"} "Updating..."]
      (make-items items)
      [:script {:src "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.js" :defer true}]
       [:script {:src "/js/scripts.js"}]))))



