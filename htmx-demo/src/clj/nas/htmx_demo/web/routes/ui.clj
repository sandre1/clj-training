(ns nas.htmx-demo.web.routes.ui
  (:require
   [nas.htmx-demo.web.middleware.exception :as exception]
   [nas.htmx-demo.web.routes.utils :as utils]
   [nas.htmx-demo.web.pages.layout :as layout]
   [nas.htmx-demo.htmx-examples.data :as local-db]
   [nas.htmx-demo.htmx-examples.click-to-edit :as click-to-edit]
   [nas.htmx-demo.htmx-examples.click-to-load :as click-to-load]
   [nas.htmx-demo.htmx-examples.delete-row :as delete-row]
   [nas.htmx-demo.htmx-examples.edit-row :as edit-row]
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
   [integrant.core :as ig]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [portal.api :as p]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div "Htmx examples"
     [:ul
      [:li [:a {:href "/click-to-edit"} "Click-to-edit"]]
      [:li [:a {:href "/click-to-load"} "Click-to-load"]]
      [:li [:a {:href "/delete-row"} "Delete-row"]]
      [:li [:a {:href "/edit-row"} "Edit-row"]]]]
    ]))

;; (defn home [request]
;;   (layout/render request "base.html" {}))

(def bulk-update-state (atom (atom local-db/persons)))

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
   ["/click-to-edit" {:get click-to-edit/home}]
   ["/click-to-edit/edit" {:get click-to-edit/edit
                          :post click-to-edit/post-edit}]
   ["/bulk-update" {:get bulk-update}]
   ["/bulk-update/activate" {:put activate}]
   ["/bulk-update/deactivate" {:put deactivate}]
   ["/click-to-load" {:get click-to-load/home}]
   ["/click-to-load/load-more" {:get click-to-load/load-more}]
   ["/delete-row" {:get delete-row/home}]
   ["/delete-row/delete-user" {:delete delete-row/delete-user}]
   ["/edit-row" {:get edit-row/home}]
   ["/edit-row/:id" {:get edit-row/cancel-edit
                     :put edit-row/save}]
   ["/edit-row/:id/edit" {:get edit-row/edit}]])

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
  0)
