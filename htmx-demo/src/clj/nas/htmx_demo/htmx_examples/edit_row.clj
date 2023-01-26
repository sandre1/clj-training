(ns nas.htmx-demo.htmx-examples.edit-row
  (:require
   [nas.htmx-demo.htmx-examples.data :as local-db]
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(def users (atom local-db/persons))

(defn row [user]
  (let [i (:index user)
        url (str "/edit-row/" i "/edit")]
    [:tr
     [:td (:name user)]
     [:td (:email user)]
     [:td [:button {:class "btn btn-danger"
                    :hx-get url
                    :hx-trigger "edit"
                    :_ "on click
                     if .editing is not empty
                       Swal.fire({title: 'Already Editing',
                                  showCancelButton: true,
                                  confirmButtonText: 'Yep, Edit This Row!',
                                  text:'Hey!  You are already editing a row!  Do you want to cancel that edit and continue?'})
                       if the result's isConfirmed is false
                         halt
                       end
                       send cancel to .editing
                     end
                     trigger edit"} "Edit"]]]))

(defn make-rows [users]
  (map row users))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.7"}]
    [:script {:src "//cdn.jsdelivr.net/npm/sweetalert2@11"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}

      [:h2 "Edit Row"]
      [:p "This example shows how to implement editable rows. First let's look at the table body:"]

      [:pre [:code {:class "language-html"}
             "   [:table {:class \"table delete-row-example\"}
       [:thead [:tr [:th \"Name\"]
                [:th \"Email\"]
                [:th]]]
       [:tbody {:hx-target \"closest tr\"
                :hx-swap \"outerHTML\"}]]"]]

      [:p "This will tell the requests from within the table to target the closest enclosing row that the request is triggered on and to replace the entire row."]
      [:p "Here is the HTML for a row:"]

      [:pre [:code {:class "language-html"}
             "   [:tr
       [:td (:name user)]
       [:td (:email user)]
       [:td [:button {:class \"btn btn-danger\"
                      :hx-get url
                      :hx-trigger \"edit\"
                      :_ \"on click
                     if .editing is not empty
                       Swal.fire({title: 'Already Editing',
                                  showCancelButton: true,
                                  confirmButtonText: 'Yep, Edit This Row!',
                                  text:'Hey!  You are already editing a row!  Do you want to cancel that edit and continue?'})
                       if the result's isConfirmed is false
                         halt
                       end
                       send cancel to .editing
                     end
                     trigger edit\"} \"Edit\"]]]"]]

      [:p "Here we are getting a bit fancy and only allowing one row at a time to be edited, using " [:code "hyperscript"] ". We check to see if there is a row with the " [:code ".editing"]  " class on it and confirm that the user wants to edit this row and dismiss the other one. If so, we send a cancel event to the other row so it will issue a request to go back to its initial state."]
      [:p "We then trigger the " [:code "edit"] " event on the current element, which triggers the htmx request to get the editable version of the row."]
      [:p "Note that if you didn't care if a user was editing multiple rows, you could omit the hyperscript and custom " [:code "hx-trigger"] ", and just let the normal click handling work with htmx. You could also implement mutual exclusivity by simply targeting the entire table when the Edit button was clicked. Here we wanted to show how to integrate htmx and hyperscript to solve the problem and narrow down the server interactions a bit, plus we get to use a nice SweetAlert confirm dialog."]
      [:p "Finally, here is what the row looks like when the data is being edited:"]
      
      [:pre [:code {:class "language-html"}
             "   [:tr {:hx-trigger \"cancel\"
            :class \"editing\"
            :hx-get \"/edit-row/contact-id\"}
       [:td [:input {:name \"name\" :value \"(:name contact)\"}]]
       [:td [:input {:name \"email\" :value (:email contact)}]]
       [:td
        [:button {:class \"btn btn-danger\"
                  :hx-get \"/edit-row/contact-id\" } \"Cancel\"]
        [:button {:class \"btn btn-danger\"
                  :hx-put \"/edit-row/contact-id\"
                  :hx-include \"closest tr\"} \"Save\"]]]"]]
      
      [:p "Here we have a few things going on: First off the row itself can respond to the " [:code "cancel"] " event, which will bring back the read-only version of the row. This is used by the hyperscript above. There is a cancel button that allows cancelling the current edit. Finally, there is a save button that issues a " [:code "PUT"] " to update the contact. Note that there is an " [:code "hx-include"] " that includes all the inputs in the closest row. Tables rows are notoriously difficult to use with forms due to HTML constraints (you can't put a " [:code "form"] " directly inside a " [:code "tr"] ") so this makes things a bit nicer to deal with."]
      
      [:h2 "Demo"]

      [:table {:class "table delete-row-example"}
       [:thead [:tr [:th "Name"]
                [:th "Email"]
                [:th]]]
       [:tbody {:hx-target "closest tr"
                :hx-swap "outerHTML"}
        (make-rows @users)]]]]]))

(defn edit [request]
  (ui (let [params (:path-params request)
            id (:id params)
            num-id (Integer/parseInt id)
            edited-user (first (filter #(= (Integer/parseInt (:index %)) num-id) @users))]
        [:tr {:hx-trigger "cancel"
              :class "editing"
              :hx-get (str "/edit-row/" id)}
         [:td [:input {:name "name" :value (:name edited-user)}]]
         [:td [:input {:name "email" :value (:email edited-user)}]]
         [:td
          [:button {:class "btn btn-danger"
                    :hx-get (str "/edit-row/" id)} "Cancel"]
          [:button {:class "btn btn-danger"
                    :hx-put (str "/edit-row/" id)
                    :hx-include "closest tr"} "Save"]]])))

(defn cancel-edit [request]
  (ui (let [params (:path-params request)
            id (:id params)
            num-idx (Integer/parseInt id)
            user (nth @users num-idx)]
        (row user))))

(defn save [request]
  (ui (let [path-par (:path-params request)
            params (:params request)
            name (:name params)
            email (:email params)
            id (:id path-par)
            num-idx (Integer/parseInt id)]
        (do (swap! users assoc-in [num-idx :name] name)
            (swap! users assoc-in [num-idx :email] email)
            (row (first (filter #(= (:index %) id) @users)))))))

(comment
  (filter #(= (Integer/parseInt (:index %)) 0) @users)
  (deref users)

  0)