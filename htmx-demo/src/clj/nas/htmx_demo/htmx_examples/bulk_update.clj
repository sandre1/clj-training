(ns nas.htmx-demo.htmx-examples.bulk-update
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
            [nas.htmx-demo.htmx-examples.data :as local-db]))

(def bulk-update-state (atom local-db/persons))

(defn row-structure [person]
  (let [name (:name person)
        email (:email person)
        status (:status person)
        index (:index person)
        active? (= status "active")]
    [:tr {:class (if active? "activate" "deactivate")}
     [:td [:input {:type "checkbox" :name "ids" :value index}]]
     [:td name]
     [:td email]
     [:td (if active? "Active" "Inactive")]]))

(defn generate-rows [persons]
  (map row-structure persons))

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

      [:h2 "Bulk Update"]
      [:p "This demo shows how to implement a common pattern where rows are selected and then bulk updated.  This is accomplished by putting a form around a table, with checkboxes in the table, and then including the checked values in " [:code "PUT"] "'s to two different endpoints: " [:code "activate"] " and " [:code "deactivate"] ":"]
      
      [:pre [:code {:class "language-html"}
             "[:form {:id \"checked-contacts\"}
       [:table
        [:thead 
         [:tr 
          [:th]
          [:th \"Name\"]
          [:th \"Email\"]
          [:th \"Status\"]]
        [:tbody {:id \"tbody\"}
         [:tr {:class \"\"}
          [:td [:input {:type 'checkbox' :name 'ids' :value '0'}]]
          [:td \"Joe Smith\"]
          [:td \"joe@smith.com\"]
          [:td \"Active\"]]
          ]
           .....    
        ]]]         
[:div {:hx-include \"#checked-contacts\"
       :hx-target \"#body\"}
[:a {:class \"btn\"
     :hx-put \"/bulk-update/activate\"} \"Activate\"]
[:a {:class \"btn\"
     :hx-put \"/bulk-update/deactivate\"} \"Deactivate\"]]"]]
      
      [:p "The server will either activate or deactivate the checked users and then rerender the " [:code "tbody"] " tag with updated rows. It will apply the class " [:code "activate"] " or " [:code "deactivate"] " to rows that have been mutated. This allows us to use a bit of CSS to flash a color helping the user see what happened:"]

      [:pre [:code {:class "language-html"}
             "         [:style  \".htmx-settling tr.deactivate td {background: lightcoral;}
                   .htmx-settling tr.activate td {background: darkseagreen;}
                   tr td {transition: all 1.2s;}\"]
         "]]

      [:p "You can see a working example of this code below."]

      [:h2 "Demo"]
      [:h3 "Select Rows And Activate Or Deactivate Below"]
      [:form {:id "checked-contacts"}
       [:table
        [:thead [:tr [:th]
                 [:th "Name"]
                 [:th "Email"]
                 [:th "Status"]]
         [:tbody {:id "tbody"}
          (generate-rows @bulk-update-state)]]]]
      [:div {:hx-include "#checked-contacts" :hx-target "#tbody"}
       [:a {:class "btn" :hx-put "/bulk-update/activate"} "Activate"]
       [:a {:class "btn" :hx-put "/bulk-update/deactivate"} "Deactivate"]]]]]))

(defn create-activation-data [state request-params]
  (let [request-ids (vals request-params)
        ids (flatten (list request-ids))]
    (swap! state
           (fn [persons]
             (map (fn [person]
                    (if (some #(= (:index person) %) ids)
                      (assoc person :status "active")
                      person))
                  persons)))))

(defn create-deactivation-data [state request-params]
  (let [request-ids (vals request-params)
        ids (flatten (list request-ids))]
    (swap! state
           (fn [persons]
             (map (fn [person]
                    (if (some #(= (:index person) %) ids)
                      (assoc person :status "inactive")
                      person))
                  persons)))))

(defn activate [request]
  (let [req-params (:form-params request)
        activation-data (create-activation-data bulk-update-state req-params)]
    (ui
     (generate-rows activation-data))))

(defn deactivate [request]
  (let [req-params (:form-params request)
        deactivation-data (create-deactivation-data bulk-update-state req-params)]
    (ui
     (generate-rows deactivation-data))))