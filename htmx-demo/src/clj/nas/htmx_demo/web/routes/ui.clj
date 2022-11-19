(ns nas.htmx-demo.web.routes.ui
  (:require
   [nas.htmx-demo.web.middleware.exception :as exception]
   [nas.htmx-demo.web.routes.utils :as utils]
   [nas.htmx-demo.web.pages.layout :as layout]
   [nas.htmx-demo.htmx-examples.click-to-load :as click-to-load]
   [nas.htmx-demo.htmx-examples.delete-row :as delete-row]
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
   [integrant.core :as ig]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [portal.api :as p]))

(def htmx-state (atom [{:name "Stan Andrei" :email "stan.andrei@gmail.com" :status "Inactive" :index "0"} {:name "Joe Smith" :email "joe@smith.org" :status "Inactive" :index "1"} {:name "Angie MacDowell" :email "	angie@macdowell.org" :status "Inactive" :index "2"} {:name "Fuqua Tarkenton" :email "fuqua@tarkenton.org" :status "Inactive" :index "3"} {:name "Kim Yee" :email "	kim@yee.org" :status "Inactive" :index "4"}]))

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
    [:div "Htmx examples"
     [:a {:href "/click-to-load"} "Click-to-load"]
     [:a {:href "/delete-row"} "Delete-row"]]
    ]))

;; (defn home [request]
;;   (layout/render request "base.html" {}))

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

(def persons [{:name "Stan Andrei" :email "stan.andrei@gmail.com" :status "inactive" :index "0"} {:name "Joe Smith" :email "joe@smith.org" :status "inactive" :index "1"} {:name "Angie MacDowell" :email "	angie@macdowell.org" :status "inactive" :index "2"} {:name "Fuqua Tarkenton" :email "fuqua@tarkenton.org" :status "inactive" :index "3"} {:name "Kim Yee" :email "	kim@yee.org" :status "inactive" :index "4"}])

(def bulk-update-state (atom persons))

(defn row-structure [person]
  (let [name (:name person)
        email (:email person)
        status (:status person)
        index (:index person)
        active? (= status "active")]
    [:tr {:class (if active? "activate" "deactivate")}
     [:td [:input {:type "checkbox" :name "ids" :value index}]]
     [:td name]
     [:td email]
     [:td (if active? "Active" "Inactive")]]))

(defn generate-rows [persons]
  (map row-structure persons))

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
        (generate-rows @bulk-update-state)]]]]
    [:div {:hx-include "#checked-contacts" :hx-target "#tbody"}
     [:a {:class "btn" :hx-put "/bulk-update/activate"} "Activate"]
     [:a {:class "btn" :hx-put "/bulk-update/deactivate"} "Deactivate"]]]
   ))

(defn create-activation-data [state request-params]
    (let [request-ids (vals request-params)
          ids (flatten (list request-ids))]
      (swap! state 
           (fn [persons]
             (map (fn [person]
                    (if (some #(= (:index person) %) ids)
                      (assoc person :status "active")
                      person))
                  persons)))))

(defn create-deactivation-data [state request-params]
    (let [request-ids (vals request-params)
          ids (flatten (list request-ids))]
      (swap! state 
           (fn [persons]
             (map (fn [person]
                    (if (some #(= (:index person) %) ids)
                      (assoc person :status "inactive")
                      person))
                  persons)))))

(defn activate [request]
  (let [req-params (:form-params request)
        activation-data (create-activation-data bulk-update-state req-params)]
    (ui
     (generate-rows activation-data))))

(defn deactivate [request]
  (let [req-params (:form-params request)
        deactivation-data (create-deactivation-data bulk-update-state req-params)]
    (ui
     (generate-rows deactivation-data))))

;; Routes
(defn ui-routes [_opts]
  [["/" home]
   ["/edit" {:get edit
             :post post-edit}]
   ["/bulk-update" {:get bulk-update}]
   ["/bulk-update/activate" {:put activate}]
   ["/bulk-update/deactivate" {:put deactivate}]
   ["/click-to-load" {:get click-to-load/home}]
   ["/click-to-load/load-more" {:get click-to-load/load-more}]
   ["/delete-row" {:get delete-row/home}]
   ["/delete-row/delete-user" {:delete delete-row/delete-user}]])

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
  (into [] ["1" "2"])
  (some #(= "1" %) '("0" "3"))
  (Integer/parseInt "0")
  (set "2")
  (let [nums (map #(Integer/parseInt %) ["0" "3"])]
    (type nums))
  (contains? (into [] ["0" "1" "2"]) (Integer/parseInt "3"))
  (type (doall (lazy-seq '(1 2 3))))
  (flatten '(["0" "1"]))
  (list "0")
  (some #(= "1" %) '("0" "1"))
  (prn @htmx-state)
  0)
