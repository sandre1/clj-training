(ns nas.htmx-demo.htmx-examples.sortable
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:script {:src "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.js" :defer true}]
    #_[:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]
    [:script "htmx.onLoad(function(content) {
    var sortables = content.querySelectorAll(\".sortable\");
    for (var i = 0; i < sortables.length; i++) {
      var sortable = sortables[i];
      new Sortable(sortable, {
          animation: 150,
          ghostClass: 'blue-background-class'
      });
    }
})"]]
   [:body
    ;;TODO @Andrei - nu apeleaza post/items
    [:form {:class "sortable"
            :hx-post "/sortable/items"
            :hx-trigger "end"}
     [:div {:class "htmx-indicator"} "Updating..."]
     [:div [:input {:type "hidden"
                     :name "item"
                     :value "1"}] "Item 1"]
     [:div [:input {:type "hidden"
                     :name "item"
                     :value "2"}] "Item 2"]
     [:div
      [:input {:type "hidden"
               :value "3"}] "Item 3"]
     [:div [:input {:type "hidden"
                     :name "item"
                     :value "4"}] "Item 4"]
     [:div [:input {:type "hidden"
                     :name "item"
                     :value "5"}] "Item 5"]]]))

(defn items [request]
  (ui [:form {:class "sortable"
              :hx-post "sortable/items"
              :hx-trigger "end"}
       [:div {:class "htmx-indicator"} "Updating..."]
       [:div [:input {:type "hidden"
                      :name "item"
                      :value "1"}] "Item 10"]
       [:div [:input {:type "hidden"
                      :name "item"
                      :value "2"}] "Item 6"]
       [:div [:input {:type "hidden"
                      :name "item"
                      :value "3"}] "Item 7"]
       [:div [:input {:type "hidden"
                      :name "item"
                      :value "4"}] "Item 8"]
       [:div [:input {:type "hidden"
                      :name "item"
                      :value "5"}] "Item 9"]]))






