(ns nas.htmx-demo.htmx-examples.inline-validation
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
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
      [:h3 "Signup Form"]
      [:form {:hx-post "inline-validation/contact"}
       [:div {:hx-target "this"
              :hx-swap "outerHTML"}
        [:label "Email Address"]
        [:input {:name "email"
                 :type "email"
                 :hx-post "/inline-validation/contact/email"
                 :hx-indicator "#ind"}]
        [:img {:id "ind"
               :src "/img/bars.svg"
               :class "htmx-indicator"}]]
       [:div {:class "form-group"}
        [:label "First Name"]
        [:input {:type "text"
                 :class "form-control"
                 :name "firstName"}]]
       [:div {:class "form-group"}
        [:label "Last Name"]
        [:input {:type "text"
                 :class "form-control"
                 :name "lastName"}]]
       [:button {:class "btn btn-default"} "Submit"]]]]]))

(defn email? [request]
  (ui (let [p (:params request)
            email-value (:email p)
            valid? (= "test@test.com" email-value)]
        [:div {:hx-target "this"
             :hx-swap "outerHTML"
             :class (if valid? "valid" "error")}
       [:label "Email Address"]
       [:input {:name "email"
                :hx-post "/inline-validation/contact/email"
                :hx-indicator "#ind"
                :value email-value}]
       [:img {:id "ind"
              :src "/img/bars.svg"
              :class "htmx-indicator" }]
       (if valid? nil [:div {:class "error-message"} "That email is already taken.  Please enter another email."])])))

#_(defn contact [request] ;;TODO @andrei: no need for this method ???
  (ui (let [p (:params request)
            email (:email p)
            first (:firstName p)
            second (:secondName p)]
        [:h3 "Signup Form"]
        [:form {:hx-post "inline-validation/contact"}
         [:div {:hx-target "this"
                :hx-swap "outerHTML"}
          [:label "Email Address"]
          [:input {:name "email"
                   :type "email"
                   :hx-post "/inline-validation/contact/email"
                   :hx-indicator "#ind"}]
          [:img {:id "ind"
                 :src "/img/bars.svg"
                 :class "htmx-indicator"}]]
         [:div {:class "form-group"}
          [:label "First Name"]
          [:input {:type "text"
                   :class "form-control"
                   :name "firstName"}]]
         [:div {:class "form-group"}
          [:label "Last Name"]
          [:input {:type "text"
                   :class "form-control"
                   :name "lastName"}]]
         [:button {:class "btn btn-default"} "Submit"]])))


(comment 
  0)