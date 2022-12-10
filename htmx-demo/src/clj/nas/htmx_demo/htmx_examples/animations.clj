(ns nas.htmx-demo.htmx-examples.animations
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
    [:div {:id "color-demo"
           :class "smooth"
           :style "color:red"
           :hx-get "/animations/colors"
           :hx-swap "outerHTML"
           :hx-trigger "every 1s"}
     "Color Swap Demo"]
    [:button {:class "fade-me-out"
              :hx-delete "animations/fade-out-demo"
              :hx-swap "outerHTML swap:1s"}
     "Fade me out"]]))

#_(defn colors [_request]
  (ui
   [:div {:id "color-demo"
          :class "smooth"
          :style "color:blue"
          :hx-get "/animations"
          :hx-swap "outerHTML"
          :hx-trigger "every 1s"}
    "Color Swap Demo"])) 

(defn colors [_request]
  (ui 
   (let [nr1 (rand-int 256)
         nr2 (rand-int 256)
         nr3 (rand-int 256)]
    [:div {:id "color-demo"
           :class "smooth"
           :style (str "color:rgb(" nr1 "," nr2 "," nr3 ")")
           :hx-get "/animations/colors"
           :hx-swap "outerHTML"
           :hx-trigger "every 1s"}
     "Color Swap Demo"])))

(defn fade-out-demo [_request]
  (ui 
   nil))

