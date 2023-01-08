(ns nas.htmx-demo.htmx-examples.progress-bar
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(def pb-width (atom 0))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"  }]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"  }]
    [:script {:src "https://unpkg.com/htmx.org@1.8.4/dist/ext/class-tools.js"  }]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:div {:hx-target "this"
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