(ns nas.htmx-demo.htmx-examples.active-search
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
            [nas.htmx-demo.htmx-examples.data :as local-db]
            [clojure.string :as str]))

(def persons (take 100 local-db/csv-persons))

(defn parse-person [person]
  (let [fname (:first_name person)
        lname (:last_name person)]
    {:firstName fname
     :lastName lname
     :email (:email person)}))

(defn persons-list [data]
  (map parse-person data))

(defn make-row [p]
  [:tr 
   [:td (:firstName p)]
   [:td (:lastName p)]
   [:td (:email p)]])

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
      [:h2 "Active Search"]
      [:p "This example actively searches a contacts database as the user enters text."]
      [:p "We start with a search input and an empty table:"]
      [:p "The input issues a " [:code " POST"] " to " [:code "/search"] " on the " [:code "keyup"] " event and sets the body of the table to be the resulting content."]
      [:p "We add the " [:code "delay:500ms"] " modifier to the trigger to delay sending the query until the user stops typing.  Additionally, we add the " [:code "changed"] " modifier to the trigger to ensure we don't send new queries when the user doesn't change the value of the input (e.g. they hit an arrow key)"]
      [:p "Since we use a " [:code "search"] " type input we will get an " [:code "x"] " in the input field to clear the input. To make this trigger a new " [:code "POST"] " we have to specify another trigger. We specify another trigger by using a comma to separate them. The " [:code "search"] " trigger will be run when the field is cleared but it also makes it possible to override the 500 ms delay on " [:code "keyup"] " by just pressing enter."]
      [:p "Finally, we show an indicator when the search is in flight with the " [:code "hx-indicator"] " attribute."]
      [:h2 "Demo"]
      [:div {:class "demo-wrapper"}
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
 [:tbody {:id "search-results"}]]]]]]))

(defn contains-string? [person search-str]
  (let [low-fname (str/lower-case (:firstName person))
        low-lname (str/lower-case (:lastName person))]
    (or (str/includes? low-fname search-str)
        (str/includes? low-lname search-str)
        (str/includes? (:email person) search-str))))

(defn search [request]
  (ui
   (let [params (:params request)
         p (:search params)
         low-p (str/lower-case p)
         users (persons-list persons)
         results (filter #(contains-string? % low-p) users)]
     (map make-row results))))





(comment
  (str/includes? "abvc ss " "m")
  
  (let [p "jam"
        users (persons-list persons)
        results (filter (fn [person] (or (str/includes? (:firstName person) p)
                                         (str/includes? (:lastName person) p)
                                         (str/includes? (:email person) p))) users)]
     results)
  (str/includes? "Jamal" "J")
  
  0
  )