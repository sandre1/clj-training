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
    #_[:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
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
     [:div prompt-text])))