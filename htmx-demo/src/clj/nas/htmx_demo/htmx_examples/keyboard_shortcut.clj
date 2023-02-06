(ns nas.htmx-demo.htmx-examples.keyboard-shortcut
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [_request]
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
      
      [:h2 "Keyboard Shortcut"]
      [:p "In this example we show how to create a keyboard shortcut for an action."]
      [:p "We start with a simple button that loads some content from the server:"]
      
      [:pre
       [:code {:class "language-html"}
        "[:button {:hx-trigger \"click, keyup[altKey&&shiftKey&&key=='P'] from:body\"
          :hx-post \"keyboard-shortcut/doit\"}
 \"Do it! (alt-shift-P)\"]"]]
      
      [:p "Note that the button responds to both the " [:code "click"] " event (as usual) and also the keyup event when " [:code "alt-shift-P"] " is pressed. The " [:code "from:"] " modifier is used to listen for the keyup event on the " [:code "body"] " element, thus making it a \"global\" keyboard shortcut."]
      [:p "You can trigger the demo below by either clicking on the button, or by hitting alt-shift-P."]
      [:p "You can find out the conditions needed for a given keyboard shortcut here:"]
      [:p "https://javascript.info/keyboard-events"]
      
      [:h2 "Demo"]
      [:button {:hx-trigger "click, keyup[altKey&&shiftKey&&key=='P'] from:body"
                :hx-post "keyboard-shortcut/doit"}
       "Do it! (alt-shift-P)"]]]
    ]))

(defn doit [_request]
  (ui (str "Dit it!")))






