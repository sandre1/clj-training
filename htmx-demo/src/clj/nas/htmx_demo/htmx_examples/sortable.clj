(ns nas.htmx-demo.htmx-examples.sortable
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn make-items [items-list]
  (map (fn [i] [:div {:class "drag-item"}
                [:input {:type "hidden"
                        :name "item"
                        :value i}] (str "Item " i)]) items-list))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.8.4/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      
      [:h2 "Sortable"]
      [:p "In this example we show how to integrate the " [:a {:href "https://sortablejs.github.io/Sortable/"} "Sortable"] " javascript library with htmx."]
      [:p "To begin we intialize the " [:code ".sortable"] " class with the " [:code "Sortable"] " javascript library:"]
      
      [:pre
       [:code {:class "language-html"}
        "htmx.onLoad(function(content) {
    var sortables = content.querySelectorAll(\".sortable\");
    for (var i = 0; i < sortables.length; i++) {
      var sortable = sortables[i];
      new Sortable(sortable, {
          animation: 150,
          ghostClass: 'blue-background-class'
      });
    }})"]]
      
      [:p "Next, we create a form that has some sortable divs within it, and we trigger an ajax request on the " [:code "end"] " event, fired by Sortable.js:"]
      [:pre
       [:code {:class "language-html"}
        "[:form {:class \"sortable\"
              :hx-post \"sortable/items\"
              :hx-trigger \"end\"}
       [:div {:class \"htmx-indicator\"} \"Updating...\"]
       [:div [:input {:type \"hidden\"
                        :name \"item\"
                        :value " 1 "}] \"Item 1\"]
       [:div [:input {:type \"hidden\"
                        :name \"item\"
                        :value " 2 "}] \"Item 2\"]
       [:div [:input {:type \"hidden\"
                        :name \"item\"
                        :value " 3 "}] \"Item 3\"]
       [:div [:input {:type \"hidden\"
                        :name \"item\"
                        :value " 4 "}] \"Item 4\"]
       [:div [:input {:type \"hidden\"
                        :name \"item\"
                        :value " 5 "}] \"Item 5\"]]"]]
      
      [:p "Note that each div has a hidden input inside of it that specifies the item id for that row."]
      [:p "When the list is reordered via the Sortable.js drag-and-drop, the " [:code "end"] "  event will be fired. htmx will then post the item ids in the new order to " [:code "sortable/items"] ", to be persisted by the server."]
      [:p "That's it!"]
      
      [:h2 "Demo"]
      [:form {:class "sortable"
              :hx-post "sortable/items"
              :hx-trigger "end"}
       [:div {:class "htmx-indicator"} "Updating..."]
       (make-items (vec (range 1 6)))]
      [:script {:src "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.js"}]
      [:script {:src "/js/scripts.js"}]]]]))

(defn items [request]
  (ui 
   (let [p (:params request)
         items (:item p)]
     (list 
      [:div {:class "htmx-indicator"} "Updating..."]
      (make-items items)
      [:script {:src "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.js"  }]
       [:script {:src "/js/scripts.js"}]))))



