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
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
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
  #_(let [b (slurp (:body request))]
    (tap> b))
  ;; (tap> request)
  (let [{:keys [form-params]} request]
    (tap> form-params))
  (ui
   [:form {:hx-post "/edit"
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

(defn post-edit [request]
  (let [{:keys [form-params]} request
        {:strs [firstName lastName email]} form-params]
    (tap> form-params)
    (ui
     [:div {:hx-target "this" :hx-swap "outerHTML"}
     [:div [:label "First Name"] firstName]
     [:div [:label "Last Name"] lastName]
     [:div [:label "Email"] email]
     [:button {:hx-get "/edit" :class "btn btn-primary"} "Click to edit"]])))

(def bulk-data [{:name "Stan Andrei" :email "stan.andrei@gmail.com" :status "Active" :index "0"}{:name "Joe Smith" :email "joe@smith.org" :status "Active" :index "1"} {:name "Angie MacDowell" :email "	angie@macdowell.org" :status "Active" :index "2"} {:name "Fuqua Tarkenton" :email "fuqua@tarkenton.org" :status "Active" :index "3"} {:name "Kim Yee" :email "	kim@yee.org" :status "Inactive" :index "4"}])

(defn in?
      "true if coll contains elm"
      [coll elm]
      (let [v (into [] coll)]
        (some #(= elm %) coll)))

(defn bulk-row [person]
  (let [name (:name person)
        email (:email person)
        status (:status person)
        index (:index person)]
    [:tr {:class ""}
     [:td [:input {:type "checkbox" :name "ids" :value index}]]
     [:td name]
     [:td email]
     [:td status]]))

(defn bulk-activate-row [person ids]
  (let [name (:name person)
        email (:email person)
        status (:status person)
        index (:index person)
        active? (in? ids index)]
    [:tr {:class (if active? "activate" "")}
         [:td [:input {:type "checkbox" :name "ids" :value index}]]
         [:td name]
         [:td email]
         [:td (if active? "Active" status)]]))

(defn bulk-rows [persons]
  (map bulk-row persons))

(defn bulk-activate-rows [persons ids]
  (map #(bulk-activate-row % ids) persons))

(defn bulk-update [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:form {:id "checked-contacts"}
     [:table
      [:thead [:tr [:th]
               [:th "Name"]
               [:th "Email"]
               [:th "Status"]]
       [:tbody {:id "tbody"}
        (bulk-rows bulk-data)]]]]
    [:div {:hx-include "#checked-contacts" :hx-target "#tbody"}
     [:a {:class "btn" :hx-put "/bulk-update/activate"} "Activate"]
     [:a {:class "btn" :hx-put "/bulk-update/deactivate"} "Deactivate"]]]
   ))

(defn activate [request]
  (let [ids (:form-params request)]
    (ui
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:form {:id "checked-contacts"}
     [:table
      [:thead [:tr [:th]
               [:th "Name"]
               [:th "Email"]
               [:th "Status"]]
       [:tbody {:id "tbody"}
        (bulk-activate-rows bulk-data ids)]]]]])))

;; (defn activate [request]
;;   [:div "bulk activate"])
(defn deactivate [request]
  [:div "bulk deactivate"])

;; Routes
(defn ui-routes [_opts]
  [["/" home]
   ["/clicked" {:post clicked}]
   ["/edit" {:get edit
             :post post-edit}]
   ["/bulk-update" {:get bulk-update}]
   ["/bulk-update/activate" {:put activate}]
   ["/bulk-update/deactivate" {:put deactivate}]])

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

(comment 
  (let [ids ["0" "1"]]
    (contains? ids 1))
  (in? '("1") "1")
  (into [] ["1" "2"])
  (bulk-rows bulk-data)
  (activate {:form-params "4"})
  0)
