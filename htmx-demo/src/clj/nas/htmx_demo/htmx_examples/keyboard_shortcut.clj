(ns nas.htmx-demo.htmx-examples.keyboard-shortcut
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:button {:hx-trigger "click, keyup[altKey&&shiftKey&&key=='P'] from:body"
              :hx-post "keyboard-shortcut/doit"}
     "Do it! (alt-shift-P)"]
    ]))

(defn doit [_request]
  (ui (str "Dit it!")))






