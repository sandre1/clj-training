(ns nas.htmx-demo.htmx-examples.custom-modal
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home
  [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:div
       [:button {:hx-get "custom-modal/modal"
                 :hx-target "body"
                 :hx-trigger "click"
                 :hx-swap "beforeend"}
        "Open a Modal"]]]]
    ]))

(defn modal [_request]
  (ui
   [:div {:id "modal"
          :_ "on closeModal add .closing then wait for animationend then remove me"}
    [:div {:class "modal-underlay"
           :_ "on click trigger closeModal"}]
    [:div {:class "modal-content"}
     [:h1 "Modal dialog"]
     "This is the modal content.
      You can put anything here, like text, or a form, or an image."
     [:br]
     [:br]
     [:button {:_ "on click trigger closeModal"} "Close"]]]))


