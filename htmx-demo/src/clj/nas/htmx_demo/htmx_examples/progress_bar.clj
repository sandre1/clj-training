(ns nas.htmx-demo.htmx-examples.progress-bar
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(def pb-width (atom 0))

(defn home [request]
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

      [:h2 "Progress Bar"]
      [:p "This example shows how to implement a smoothly scrolling progress bar."]
      [:p "We start with an initial state with a button that issues a " [:code "POST"] " to " [:code "progress-bar/start"] " to begin the job:"]

      [:pre
       [:code {:class "language-html"}
        "  [:div {:hx-target \"this\"
             :hx-swap \"outerHTML\"}
       [:h3 \"Start Progress\"]
       [:button {:class \"btn\"
                 :hx-post \"progress-bar/start\"}
        \"Start Job\"]]"]]

      [:p "This div is then replaced with a new div that reloads itself every 600ms:"]

      [:pre
       [:code {:class "language-html"}
        "  [:div {:hx-target \"this\"
                  :hx-get \"progress-bar/job\"
                  :hx-trigger \"load\"
                  :hx-swap \"outerHTML\"}
            [:h3 \"Running\"]
            [:div {:class \"progress\"}
             [:div {:id \"pb\"
                    :class \"progress-bar\"
                    :style \"0%\"}]]]"]]

      [:p "This HTML is rerendered every 600 milliseconds, with the \"width\" style attribute on the progress bar being updated. Because there is an id on the progress bar div, htmx will smoothly transition between requests by settling the style attribute into its new value. This, when coupled with CSS transitions, make the visual transition continuous rather than jumpy."]
      [:p "Finally, when the process is complete, a restart button is added to the UI (we are using the " [:code "class-tools"] " extension in this example):"]

      [:pre
       [:code {:class "language-html"}
        "[:div
 {:hx-target \"this\"
  :hx-get \"progress-bar/job\"
  :hx-trigger \"none\"
  :hx-swap \"outerHTML\"}
  [:h3 \"Complete\"]
  [:div {:class \"progress\"}
  [:div {:id \"pb\"
         :class \"progress-bar\"
         :style \"100%\"}]]
  [:button {:hx-ext \"class-tools\"
            :id \"restart-btn\"
            :class \"btn\"
            :hx-post \"progress-bar/start\"
            :classes \"add show:600ms\"}
  \"Restart Job\"]]"]]

      [:p "This example uses styling cribbed from the bootstrap progress bar:"]

      [:pre
       [:code {:class "language-html"}
        "[:style 
\".progress {
    height: 20px;
    margin-bottom: 20px;
    overflow: hidden;
    background-color: #f5f5f5;
    border-radius: 4px;
    box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
}
.progress-bar {
    float: left;
    width: 0%;
    height: 100%;
    font-size: 12px;
    line-height: 20px;
    color: #fff;
    text-align: center;
    background-color: #337ab7;
    -webkit-box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
    box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
    -webkit-transition: width .6s ease;
    -o-transition: width .6s ease;
    transition: width .6s ease;
}\"]"]]
      
      [:h2 "Demo"]

      [:div {:class "demo-wrapper"
             :hx-target "this"
             :hx-swap "outerHTML"}
       [:h3 "Start Progress"]
       [:button {:class "btn"
                 :hx-post "progress-bar/start"}
        "Start Job"]]]]]))

(defn start [_request]
  (ui
   (let [width (str "width:" @pb-width "%")]
     (list [:div {:hx-target "this"
                  :hx-get "progress-bar/job"
                  :hx-trigger "load"
                  :hx-swap "outerHTML"}
            [:h3 "Running"]
            [:div {:class "progress"}
             [:div {:id "pb"
                    :class "progress-bar"
                    :style width}]]]))))

(defn job [_request]
  (ui
   (let [s @pb-width
         not-loaded? (< s 100)]
     (swap! pb-width + (rand-int 30))
     (list [:div
            {:hx-target "this"
             :hx-get "progress-bar/job"
             :hx-trigger (if not-loaded? "load  delay:600ms" "none")
             :hx-swap "outerHTML"}
            [:h3 (if not-loaded? "Running " "Complete")]
            [:div {:class "progress"}
             (let [width (str "width:" @pb-width "%")]
               [:div {:id "pb"
                      :class "progress-bar"
                      :style width}])]
            (if not-loaded? nil (do (reset! pb-width 0)
                                    [:button {:hx-ext "class-tools"
                                              :id "restart-btn"
                                              :class "btn"
                                              :hx-post "progress-bar/start"
                                              :classes "add show:600ms"} "Restart Job"]))]))))

(comment
  (rand-int (range 0 100))
  0)