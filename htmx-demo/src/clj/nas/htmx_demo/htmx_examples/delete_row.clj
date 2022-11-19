(ns nas.htmx-demo.htmx-examples.delete-row
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page 
   [:table {:class "table delete-row-example"}
    [:thead [:tr [:th "Name"]
             [:th "Email"]
             [:th "Status"]
             [:th]]]
    [:tbody {:hx-confirm "Are you Sure?"
             :hx-target "closest tr"
             :hx-swap "outerHTML swap:1s"}
     [:tr [:td "Joe Smith"]
      [:td "joe@smith.org"]
      [:td "Active"]
      [:td [:button {:class "btn btn-danger" :hx-delete "/contact/1"} "Delete"]]]
     [:tr [:td "Angie McDowel"]
      [:td "angie@mcdowel.com"]
      [:td "Active"]
      [:td [:button {:class "btn btn-danger" :hx-delete "/contact/1"} "Delete"]]]
     [:tr [:td "Fuqua Tarkenton"]
      [:td "fuqua@tarkenton.org"]
      [:td "Active"]
      [:td [:button {:class "btn btn-danger" :hx-delete "/contact/1"} "Delete"]]]
     [:tr [:td "Kim Yee"]
      [:td "kim@yee.com"]
      [:td "Inctive"]
      [:td [:button {:class "btn btn-danger" :hx-delete "/contact/1"} "Delete"]]]]]))

