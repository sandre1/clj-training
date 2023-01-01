(ns nas.htmx-demo.htmx-examples.confirm
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    #_[:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:script {:src "https://cdn.jsdelivr.net/npm/sweetalert2@11" :defer true}]
    [:div {:_ "on click call alert('You clicked me!')"} "click me!"]
    [:button {:hx-trigger "confirmed"
              :hx-get "confirmation-dialog/confirmed"
              :_ "on click
                           call Swal.fire({title: 'Confirm', text:'Do you want to continue?'})
                           if result.isConfirmed trigger confirmed"} "click meee"]]))

(defn confirmed
  [request]
  (ui
   "Confirmed"))










