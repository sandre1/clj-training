(ns nas.htmx-demo.htmx-examples.confirm
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" }]
    #_[:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body

    [:div {:class "container"}
     [:div {:class "example-wrapper"}
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










