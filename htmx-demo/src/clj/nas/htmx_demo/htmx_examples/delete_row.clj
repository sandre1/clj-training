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
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      
      [:h2 "Delete Row"]
      
      [:p "This example shows how to implement a delete button that removes a table row upon completion. First let's look at the table body:"] 
      
      [:pre [:code {:class "language-html"}
             "[:table {:class \"table delete-row-example\"}
       [:thead [:tr [:th \"Name\"]
                [:th \"Email\"]
                [:th \"Status\"]
                [:th]]]
       [:tbody {:hx-confirm \"Are you Sure?\"
                :hx-target \"closest tr\"
                :hx-swap \"outerHTML swap:1s\"}
              .....
        ]]
         "]]
      
      [:p "The table body has a " [:code "hx-confirm"] " attribute to confirm the delete action. It also set the target to be the " [:code "closest tr"]  " that is, the closest table row, for all the buttons (" [:code "hx-target"] " is inherited from parents in the DOM.) The swap specification in " [:code "hx-swap"] " says to swap the entire target out and to wait 1 second after receiving a response. This last bit is so that we can use the following CSS:"]
      
      [:pre [:code {:class "language-html"}
             "         [:style  \"tr.htmx-swapping td {opacity: 0; transition: opacity 1s ease-out;}\"]
         "]]
      [:p "To fade the row out before it is swapped/removed."]
      [:p "Each row has a button with a " [:code "hx-delete"] " attribute containing the url on which to issue a " [:code "DELETE"]  " request to delete the row from the server. This request responds with empty content, indicating that the row should be replaced with nothing."]
      
      [:pre [:code {:class "language-html"}
             "   [:tr 
      [:td \"Angie MacDowell\"]
      [:td \"angie@macdowell.org\"]
      [:td \"Active\"]
      [:td
        [:button {:class \"btn btn-danger\"
                  :hx-delete \"/delete-row/contact/1} 
        \"Delete\"]]]"]]
            
      [:h2 "Demo"]
      
      [:table {:class "table delete-row-example"}
       [:thead [:tr [:th "Name"]
                [:th "Email"]
                [:th "Status"]
                [:th]]]
       [:tbody {:hx-confirm "Are you Sure?"
                :hx-target "closest tr"
                :hx-swap "outerHTML swap:1s"}
        (make-rows users)]]]]]
   ))

(comment 
  
  0)

