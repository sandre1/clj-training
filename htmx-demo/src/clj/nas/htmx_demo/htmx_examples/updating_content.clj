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
              [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"  }]
              [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"  }]
              [:script {:src "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.js"  }]
              [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
             [:body
              [:div {:class "container"}
               [:div {:class "example-wrapper"}
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
                [:div {:id "solution 2"}
                 [:h2 "Contacts solution 2"]
                 [:table {:class "table"}
                  [:thead
                   [:tr
                    [:th "Name"]
                    [:th "Email"]
                    [:th]]]
                  [:tbody {:id "contacts-table2"}]]
                 [:h2 "Add a contact"]
                 [:form {:hx-post "updating-content/contacts-sol2"}
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

(defn contacts-sol2 [request]
  (ui (let [p (:params request)
            name (:name p)
            email (:email p)]
        [:tr {:hx-swap-oob "beforeend:#contacts-table2"}
         [:td name]
         [:td email]])))


(comment
  
  0
  )







