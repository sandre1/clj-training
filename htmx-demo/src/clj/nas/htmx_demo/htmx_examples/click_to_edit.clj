(ns nas.htmx-demo.htmx-examples.click-to-edit
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" }]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:div {:hx-target "this" :hx-swap "outerHTML"}
       [:div [:label "First Name"] "Joe"]
       [:div [:label "Last Name"] "Dark"]
       [:div [:label "Email"] "joe@dark.com"]
       [:button {:hx-get "click-to-edit/edit" :class "btn btn-primary"} "Click to edit"]]]]
    ]))

(defn edit [request]
  #_(let [b (slurp (:body request))]
    (tap> b))
  ;; (tap> request)
  (let [{:keys [form-params]} request]
    (tap> form-params))
  (ui
   [:form {:hx-post "click-to-edit/edit"
           :hx-target "this"
           :hx-swap "outerHTML"}
    [:div [:label "First Name"]
     [:input {:type "text" :name "firstName" :value "Joe"}]]
    [:div {:class "form-group"}
     [:label "Last Name"]
     [:input {:type "text" :name "lastName" :value "Dark"}]]
    [:div {:class "form-group"}
     [:label "Email Address"]
     [:input {:type "email" :name "email" :value "joe@dark.com"}]]
    [:button {:class "btn"} "Submit"]
    [:button {:class "btn" :hx-get "/click-to-edit"} "Cancel"]]))

(defn post-edit [request]
  (let [{:keys [form-params]} request
        {:strs [firstName lastName email]} form-params]
    (tap> form-params)
    (ui
     [:div {:hx-target "this" :hx-swap "outerHTML"}
     [:div [:label "First Name"] firstName]
     [:div [:label "Last Name"] lastName]
     [:div [:label "Email"] email]
     [:button {:hx-get "click-to-edit/edit" :class "btn btn-primary"} "Click to edit"]])))