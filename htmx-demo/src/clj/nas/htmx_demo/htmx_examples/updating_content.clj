(ns nas.htmx-demo.htmx-examples.updating-content
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(def state (atom ()))

(defn make-contact [s]
  (map (fn [p] (let [name (:name p)
                     email (:email p)]
                 [:tr [:td name]
                  [:td email]
                  [:td]])) s))

(defn home [request]
  (page
   (do (reset! state [])
       (list [:head
              [:meta {:charset "UTF-8"}]
              [:title "Htmx + Kit"]
              [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
              [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
              [:script {:src "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.js"}]
              [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
             [:body
              [:div {:class "container"}
               [:div {:class "example-wrapper"}

                [:h2 "Updating Other Content"]
                [:p "A question that often comes up when people are first working with htmx is:"]
                [:blockquote [:p "&quot;I need to update other content on the screen.  How do I do this?&quot;"]]
                [:p "There are multiple ways to do so, and in this example will walk you through some of them."]
                [:p "We'll use the following basic UI to discuss this concept: a simple table of contacts, and a form below it to add new contacts to the table using " [:a {:href "https://htmx.org/attributes/hx-post/"} "hx-post"] "."]

                [:pre
                 [:code {:class "language-html"}
                  "[:div {:id \"table-and-form\"}
 [:h2 \"Contacts\"]
 [:table {:class \"table\"}
  [:thead
   [:tr
    [:th \"Name\"]
    [:th \"Email\"]
    [:th]]]
  [:tbody {:id \"contacts-table\"}
   \"...\"]]
[:h2 \"Add a contact\"]
[:form {:hx-post \"updating-content/contacts\"}
 [:label \"Name\"]
 [:input {:name \"name\"
          :type \"text\"}]
 [:label \"Email\"]
 [:input {:name \"email\"
          :type \"email\"}]]]"]]

                [:p "The problem here is that when you submit a new contact in the form, you want the contact table above to refresh and include the contact that was just added by the form."]
                [:p "What solutions to we have?"]
                [:h3
                 [:a {:name "expand"}]
                 [:a {:href "#expand"} "Solution 1: Expand the Target"]]
                [:p "The easiest solution here is to \"expand the target\" of the form to enclose both the table " [:i "and"] " the form. For example, you could wrap the whole thing in a " [:code "div"] " and then target that " [:code "div"] " in the form:"]

                [:pre
                 [:code {:class "language-html"}
                  "[:div {:id \"table-and-form\"}
 [:h2 \"Contacts\"]
 [:table {:class \"table\"}
  [:thead
   [:tr
    [:th \"Name\"]
    [:th \"Email\"]
    [:th]]]
  [:tbody {:id \"contacts-table\"}
   \"...\"]]
  [:h2 \"Add a contact\"]
  [:form {:hx-post \"updating-content/contacts\"
          :hx-target \"#table-and-form\"}
   [:label \"Name\"]
   [:input {:name \"name\"
            :type \"text\"}]
   [:label \"Email\"]
   [:input {:name \"email\"
            :type \"email\"}]]]"]]

                [:p "Note that we are targeting the enclosing div using the " [:a {:href "https://htmx.org/attributes/hx-target/"} "target"] " attribute. You would need to render both the table and the form in the response to the " [:code "POST"] " to " [:code "updating-content/contacts"] "."]
                [:p "This is a simple and reliable approach, although it might not feel particularly elegant."]
                
                [:div {:id "table-and-form"}
                 [:h2 "Contacts"]
                 [:table {:class "table"}
                  [:thead
                   [:tr
                    [:th "Name"]
                    [:th "Email"]
                    [:th]]]
                  [:tbody {:id "contacts-table"}]]
                 [:h2 "Add a contact"]
                 [:form {:hx-post "updating-content/contacts"
                         :hx-target "#table-and-form"}
                  [:label "Name"]
                  [:input {:name "name"
                           :type "text"}]
                  [:label "Email"]
                  [:input {:name "email"
                           :type "email"}]
                  [:button "Submit"]]]

                #_[:h3
                 [:a {:name "oob"}]
                 [:a {:href "#oob"} "Solution 2: Out of Band Responses"]]
                #_[:p "A more sophisticated approach to this problem would use " [:a {:href "https://htmx.org/attributes/hx-swap-oob/"} "out of band swaps"] " to swap in updated content to the DOM."]
                #_[:p "Using this approach, the HTML doesn't need to change from the original setup at all:"]

                #_[:pre
                 [:code {:class "language-html"}
                  "[:h2 \"Contacts\"]
[:table {:class \"table\"}
 [:thead
  [:tr
   [:th \"Name\"]
   [:th \"Email\"]
   [:th]]]
 [:tbody {:id \"contacts-table\"}
  \"...\"]]
[:h2 \"Add a contact\"]
[:form {:hx-post \"updating-content/contacts-solution-two\"}
 [:label \"Name\"]
 [:input {:name \"name\"
          :type \"text\"}]
 [:label \"Email\"]
 [:input {:name \"email\"
          :type \"email\"}]]"]]
                #_[:p "Instead of modifying something on the front end, in your response to the " [:code "POST"] " to " [:code "updating-content/contacts-solution-two"] " you would include some additional content:"]

                #_[:pre
                 [:code {:class "language-html"}
                  "[:tr {:hx-swap-oob \"beforeend:#contacts-table2\"}
  [:td \"Joe Smith\"]
  [:td \"joe@smith.com\"]]
[:form {:hx-post \"updating-content/contacts-sol-two\"}
  [:label \"Name\"
    [:input {:name \"name\"
             :type \"text\"}]]
  [:label \"Email\"
    [:input {:name \"email\"
             :type \"email\"}]]]"]]

                #_[:div {:id "solution-two"}
                 [:h2 "Contacts solution two"]
                 [:table {:class "table"}
                  [:thead
                   [:tr
                    [:th "Name"]
                    [:th "Email"]
                    [:th]]]
                  [:tbody {:id "contacts-table2"}]]
                 [:h2 "Add a contact"]
                 [:form {:hx-post "updating-content/contacts-sol-two"}
                  [:label "Name"
                   [:input {:name "name"
                            :type "text"}]]
                  [:label "Email"
                   [:input {:name "email"
                            :type "email"}]]
                  [:button "Submit"]]]]]]))))

(defn add-contact [c]
  (let []
    (swap! state (fn [s] (conj s c)))))

(defn contacts [request]
  (ui (let [p (:params request)]
        (do (add-contact p)
            (list [:div {:id "table-and-form"}
                   [:h2 "Contacts"]
                   [:table {:class "table"}
                    [:thead
                     [:tr
                      [:th "Name"]
                      [:th "Email"]
                      [:th]]]
                    [:tbody {:id "contacts-table"}
                     (make-contact @state)]]
                   [:h2 "Add a contact"]
                   [:form {:hx-post "updating-content/contacts"
                           :hx-target "#table-and-form"}
                    [:label "Name"]
                    [:input {:name "name"
                             :type "text"}]
                    [:label "Email"]
                    [:input {:name "email"
                             :type "email"}]
                    [:button "Submit"]]])))))

(defn contacts-sol-two [request]
  (ui (let [p (:params request)
            name (:name p)
            email (:email p)]
        [:tr {:hx-swap-oob "beforeend:#contacts-table2"}
         [:td name]
         [:td email]])))


(comment

  0)







