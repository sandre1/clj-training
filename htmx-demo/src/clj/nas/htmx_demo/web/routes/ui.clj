(ns nas.htmx-demo.web.routes.ui
  (:require
   [nas.htmx-demo.web.middleware.exception :as exception]
   [nas.htmx-demo.web.routes.utils :as utils]
   [nas.htmx-demo.web.pages.layout :as layout]
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
   [integrant.core :as ig]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]]
   [:body
    [:div {:hx-target "this" :hx-swap "outerHTML"}
     [:div [:label "First Name"] "Joe"]
     [:div [:label "Last Name"] "Dark"]
     [:div [:label "Email"] "joe@dark.com"]
     [:button {:hx-get "/edit" :class "btn btn-primary"} "Click to edit"]]
    ]))

;; (defn home [request]
;;   (layout/render request "base.html" {}))

(defn clicked [request]
  (ui
   [:div "Congratulations! You just clicked the button!"]))

(defn edit [request]
  (ui
   [:form {:hx-put "/"
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
    [:button {:class "btn" :hx-get "/"} "Cancel"]]))

;; Routes
(defn ui-routes [_opts]
  [["/" {:get home}]
   ["/clicked" {:post clicked}]
   ["/edit" {:get edit
             :put home}]])

(defn route-data [opts]
  (merge
   opts
   {:middleware 
    [;; Default middleware for ui
     ;; query-params & form-params
     parameters/parameters-middleware
     ;; encoding response body
     muuntaja/format-response-middleware
     ;; exception handling
     exception/wrap-exception]}))

(derive :reitit.routes/ui :reitit/routes)

(defmethod ig/init-key :reitit.routes/ui
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  [base-path (route-data opts) (ui-routes opts)])
