(ns nas.htmx-demo.htmx-examples.confirm
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" }]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:h2 "A Customized Confirmation UI"]
      [:p "htmx supports the " [:a {:href "https://htmx.org/attributes/hx-confirm/"} [:code "hx-post"]] " attribute to provide a simple mechanism for confirming a user action. This uses the default " [:code "confirm()"] " function in javascript which, while trusty, may not be consistent with your applications UX."]
      [:p "In this example we will see how to use " [:a {:href "https://sweetalert2.github.io/"} "sweetalert2"] " and the " [:a {:href "https://htmx.org/events/#htmx:confirm"} [:code "htmx:confirm"]] " event to implement a custom confirmation dialog."]
      
      [:pre
       [:code {:class "language-html"}
        "[:script {:src \"https://cdn.jsdelivr.net/npm/sweetalert2@11\"}]
[:button {:hx-trigger \"confirmed\"
          :hx-get \"confirmation-dialog/confirmed\"
          :_ \"on click
              call Swal.fire({title: 'Confirm', text:'Do you want to continue?'})
              if result.isConfirmed trigger confirmed\"} \"click me\"]"]]
      
      [:p "We add some hyperscript to invoke Sweet Alert 2 on a click, asking for confirmation. If the user confirms the dialog, we trigger the request by invoking the " [:code "issueRequest()"] " function, which was destructured from the event detail object."]
      [:p "Note that we are taking advantage of the fact that hyperscript is " [:a {:href "https://hyperscript.org/docs/#async"} "async-transparent"] " and automatically resolves the Promise returned by " [:code "Swal.fire()"] "."]
      [:p "A VanillaJS implementation is left as an exercise for the reader. :)"]
      
      
      [:h2 "Demo"]
      [:script {:src "https://cdn.jsdelivr.net/npm/sweetalert2@11"}]
      [:button {:hx-trigger "confirmed"
                :hx-get "confirmation-dialog/confirmed"
                :_ "on click
                           call Swal.fire({title: 'Confirm', text:'Do you want to continue?'})
                           if result.isConfirmed trigger confirmed"} "click me"]]]
    ]))

(defn confirmed
  [request]
  (ui
   "Confirmed"))










