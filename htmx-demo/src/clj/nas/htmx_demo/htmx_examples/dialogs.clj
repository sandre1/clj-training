(ns nas.htmx-demo.htmx-examples.dialogs
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home
  [_request]
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
      [:h2 "Dialogs"]
      [:p "Dialogs can be triggered with the " [:code "hx-prompt"] " and " [:code "hx-confirm"] " attributes. These are triggered by the user interaction that would trigger the AJAX request, but the request is only sent if the dialog is accepted."]
      
      [:pre
       [:code {:class "language-html"}
        "[:div
 [:button {:class \"btn\"
           :hx-post \"/dialogs/submit\"
           :hx-prompt \"Enter a string\"
           :hx-confirm \"Are you sure?\"
           :hx-target \"#response\"}
  \"Prompt Submission\"]
 [:div {:id \"response\"}]]"]]
      
      [:p "The value provided by the user to the prompt dialog is sent to the server in a " [:code "HX-Prompt"] " header. In this case, the server simply echos the user input back."]
      
      [:pre [:code {:class "language-html"}
             "[:div \"User entered\" [:i prompt-text]]"]]
      
      [:h2 "Demo"]
      
      [:div
       [:button {:class "btn"
                 :hx-post "/dialogs/submit"
                 :hx-prompt "Enter a string"
                 :hx-confirm "Are you sure?"
                 :hx-target "#response"}
        "Prompt Submission"]
       [:div {:id "response"}]]]]]))

(defn submit
  [request]
  (ui
   (let [headers (:headers request)
         prompt-text (get headers "hx-prompt")]
     [:div "User entered " [:i prompt-text]])))