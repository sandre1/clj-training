(ns nas.htmx-demo.htmx-examples.animations
  (:require
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))


(defn home
  [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:script {:src "https://unpkg.com/htmx.org@1.8.4/dist/ext/class-tools.js"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:h2 "Animations"]
      [:p "Htmx is designed to allow you to use " [:a {:href "https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Transitions/Using_CSS_transitions"} "CSS Transitions"] " to add smooth animations and transitions to your web page using only CSS and HTML.  Below are a few examples of various animation techniques."]
      [:h3
       [:a {:name "basic"}]
       [:a {:href "#basic"} "Basic CSS Animations"]]
      [:h4 "Color Throb"]

      [:p "The simplest animation technique in htmx is to keep the " [:code " id"] " of an element stable across a content swap. If the "
       [:code "id"] " of an element is kept stable htmx will swap it in such a way that CSS transitions can be written between the old version of the element and the new one."]
      [:p "Consider this div:"]
      [:pre
       [:code {:class "language-html"}
        "         [:style \".smooth {transition: all 1s ease-in;}\"]
         [:div {:id \"color-demo\" 
                :class \"smooth\" 
                :style \"color:red\" 
                :hx-get \"/animations/colors\" 
                :hx-swap \"outerHTML\" 
                :hx-trigger \"every 1s\"} 
           \"Color Swap Demo\"]"]]

      [:p "This div will poll every second and will get replaced with new content which changes the" [:code "color"] " style to a new value (e.g. " [:code "blue"] ")."]
      [:p "Because the div has a stable id, " [:code "color-demo"] ", htmx will structure the swap such that a CSS transition, defined on the " [:code ".smooth"] " class, applies to the style update from " [:code "red"] " to " [:code "blue"] " and smoothly transitions between them."]

      [:h5 "Demo"]

      [:div {:id "color-demo"
             :class "smooth"
             :style "color:red"
             :hx-get "/animations/colors"
             :hx-swap "outerHTML"
             :hx-trigger "every 1s"}
       "Color Swap Demo"]

      [:h4 " Smooth Progress Bar"]
      [:p "The " [:a {:href "/progress-bar"} "Progress Bar"] " demo uses this basic CSS animation technique as well by updating the " [:code "length"] " property of a progress bar element allowing for a smooth animation."]

      [:h3 [:a {:name "swapping"}] [:a {:href "#swapping"} "Swap Transitions"]]
      [:h4 "Fade Out On Swap"]
      [:p "If you want to fade out an element that is going to be removed when the request ends, you want to take advantage of the " [:code "htmx-swapping"] " class with some CSS and extend the swap phase to be long enough for your animation to complete. This can be done like so:"]

      [:pre [:code {:class "language-html"}
             "         [:style \".fade-me-out.htmx-swapping {opacity: 0; transition: all 1s ease-out;}\"]
         [:button {:class \"fade-me-out\"
                   :hx-delete \"animations/fade-out-demo\"
                   :hx-swap \"outerHTML swap:1s\"}
         \"Fade me out\"]"]]

      [:h5 "Demo"]
      [:button {:class "fade-me-out"
                :hx-delete "animations/fade-out-demo"
                :hx-swap "outerHTML swap:1s"}
       "Delete me"]

      [:h3 [:a {:name "settling"}] [:a {:href "#settling"} "Settling Transitions"]]
      [:h4 "Fade In On Addition"]
      [:p "Building on the last example, we can fade in the new content by using the " [:code "htmx-added"] " class during the settle phase. You can also write CSS transitions against the target, rather than the new content, by using the " [:code "htmx-settling"] " class."]

      [:pre [:code {:class "language-html"}
             "         [:style \"#fade-me-in.htmx-added {opacity: 0;}
                  #fade-me-in {opacity: 1; transition: opacity 1s ease-out;}\"]
         [:button {:id \"fade-me-in\"
                :hx-post \"animations/fade-in-demo\"
                :hx-swap \"outerHTML settle:1s\"}
         \"Fade me in\"]"]]

      [:h5 "Demo"]
      [:button {:id "fade-me-in"
                :hx-post "animations/fade-in-demo"
                :hx-swap "outerHTML settle:1s"}
       "Fade me in"]

      [:h3 [:a {:name "request"}] [:a {:href "#request"} "Request In Flight Animation"]]
      [:p "You can also take advantage of the " [:code "htmx-request"] " class, which is applied to the element that triggers a request. Below is a form that on submit will change its look to indicate that a request is being processed:"]

      [:pre [:code {:class "language-html"}
             "         [:style \"form.htmx-request {opacity: 0.5;
             transition: opacity 300ms linear;}\"]
         [:form {:hx-post \"animations/name\"}
              [:label \"Name\"]
              [:input {:name \"name\"}][:br]
              [:button \"Submit\"]]"]]

      [:h5 "Demo"]

      [:form {:hx-post "animations/name"}
       [:label "Name"]
       [:input {:name "name"}] [:br]
       [:button "Submit"]]

      [:h3 "Using the HTMX " [:code "class-tools"] " Extensions"]
      [:p "Many interesting animations can be created by using the " [:code "class-tools"] " extension."]
      [:p "Here is an example that toggles the opacity of a div. Note that we set the toggle time to be a bit longer than the transition time. This avoids flickering that can happen if the transition is interrupted by a class change."]

      [:pre [:code {:class "language-html"}
             "         [:style \".demo.faded {opacity: 0.3;}
                  .demo {opacity: 1; transition: opacity ease-in 900ms;}\"]
         [:div {:hx-ext \"class-tools\"
                :class \"demo\"
                :classes \"toggle faded:1s\"}
          \"Toggle Demo\"]"]]

      [:h5 "Demo"]

      [:div {:hx-ext "class-tools"
             :class "demo"
             :classes "toggle faded:1s"} "Toggle Demo"]

      [:h5 "Conclusion"]
      [:p "You can use the techniques above to create quite a few interesting and pleasing effects with plain old HTML while using htmx."]]]]))



#_(defn colors [_request]
    (ui
     [:div {:id "color-demo"
            :class "smooth"
            :style "color:blue"
            :hx-get "/animations"
            :hx-swap "outerHTML"
            :hx-trigger "every 1s"}
      "Color Swap Demo"]))

(defn colors
  [_request]
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


(defn fade-out-demo
  [_request]
  (ui
   nil))

(defn fade-in-demo
  [_request]
  (ui
   [:button {:id "fade-me-in"
             :hx-post "animations/fade-in-demo"
             :hx-swap "outerHTML settle:1s"}
    "Fade me in"]))

(defn name
  [_request]
  (ui
   [:div "Submitted!"]))

