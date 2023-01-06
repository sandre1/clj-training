(ns nas.htmx-demo.web.routes.ui
  (:require
   [nas.htmx-demo.web.middleware.exception :as exception]
   [nas.htmx-demo.web.routes.utils :as utils]
   [nas.htmx-demo.web.pages.layout :as layout]
   [nas.htmx-demo.htmx-examples.click-to-edit :as click-to-edit]
   [nas.htmx-demo.htmx-examples.bulk-update :as bulk-update]
   [nas.htmx-demo.htmx-examples.click-to-load :as click-to-load]
   [nas.htmx-demo.htmx-examples.delete-row :as delete-row]
   [nas.htmx-demo.htmx-examples.edit-row :as edit-row]
   [nas.htmx-demo.htmx-examples.lazy-loading :as ll]
   [nas.htmx-demo.htmx-examples.inline-validation :as ivalidation]
   [nas.htmx-demo.htmx-examples.infinite-scroll :as iscroll]
   [nas.htmx-demo.htmx-examples.active-search :as asearch]
   [nas.htmx-demo.htmx-examples.progress-bar :as prog-bar]
   [nas.htmx-demo.htmx-examples.cascading-selects :as cselects]
   [nas.htmx-demo.htmx-examples.animations :as animations]
   [nas.htmx-demo.htmx-examples.file-upload :as fu]
   [nas.htmx-demo.htmx-examples.dialogs :as dialogs]
   [nas.htmx-demo.htmx-examples.dialogs-bootstrap :as dialogb]
   [nas.htmx-demo.htmx-examples.custom-modal :as cmodal]
   [nas.htmx-demo.htmx-examples.tabs :as tabs]
   [nas.htmx-demo.htmx-examples.hyper-tabs :as hypertabs]
   [nas.htmx-demo.htmx-examples.keyboard-shortcut :as ks]
   [nas.htmx-demo.htmx-examples.sortable :as sortable]
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
   [nas.htmx-demo.htmx-examples.updating-content :as uc]
   [nas.htmx-demo.htmx-examples.confirm :as confirm]
   [integrant.core :as ig]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [portal.api :as p]))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"  }]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"  }]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div "Htmx examples"
     [:ul
      [:li [:a {:href "/click-to-edit"} "Click-to-edit"]]
      [:li [:a {:href "/bulk-update"} "Bulk-update"]]
      [:li [:a {:href "/click-to-load"} "Click-to-load"]]
      [:li [:a {:href "/delete-row"} "Delete-row"]]
      [:li [:a {:href "/edit-row"} "Edit-row"]]
      [:li [:a {:href "/lazy-loading"} "Lazy-loading"]]
      [:li [:a {:href "/inline-validation"} "Inline-validation"]]
      [:li [:a {:href "/infinite-scroll"} "Infinite-scroll"]]
      [:li [:a {:href "/active-search"} "Active-search"]]
      [:li [:a {:href "/progress-bar"} "Progress-bar"]]
      [:li [:a {:href "/cascading-selects"} "Cascading selects"]] \
      [:li [:a {:href "/animations"} "Animations"]]
      [:li [:a {:href "/file-upload"} "File Upload"]]
      [:li [:a {:href "/dialogs"} "Dialogs - Browser"]]
      [:li [:a {:href "/dialogs-bootstrap"} "Dialogs - Bootstrap"]]
      [:li [:a {:href "/custom-modal"} "Custom Modal Dialog"]]
      [:li [:a {:href "/tabs"} "Tabs"]]
      [:li [:a {:href "/hyper-tabs"} "Tabs - hyperscript"]]
      [:li [:a {:href "/keyboard-shortcut"} "Keyboard Shortcut"]]
      [:li [:a {:href "/sortable"} "Sortable"]]
      [:li [:a {:href "/updating-content"} "Updating content"]]
      [:li [:a {:href "/confirmation-dialog"} "Confirmation dialog"]]]]]))

;; (defn home [request]
;;   (layout/render request "base.html" {}))


;; Routes
(defn ui-routes [_opts]
  [["/" home]
   ["/click-to-edit" {:get click-to-edit/home}]
   ["/click-to-edit/edit" {:get click-to-edit/edit
                           :post click-to-edit/post-edit}]
   ["/bulk-update" {:get bulk-update/home}]
   ["/bulk-update/activate" {:put bulk-update/activate}]
   ["/bulk-update/deactivate" {:put bulk-update/deactivate}]
   ["/click-to-load" {:get click-to-load/home}]
   ["/click-to-load/load-more" {:get click-to-load/load-more}]
   ["/delete-row" {:get delete-row/home}]
   ["/delete-row/delete-user" {:delete delete-row/delete-user}]
   ["/edit-row" {:get edit-row/home}]
   ["/edit-row/:id" {:get edit-row/cancel-edit
                     :put edit-row/save}]
   ["/edit-row/:id/edit" {:get edit-row/edit}]
   ["/lazy-loading" {:get ll/home}]
   ["/lazy-loading/graph" {:get ll/graph}]
   ["/inline-validation" {:get ivalidation/home}]
   ["/inline-validation/contact/email" {:post ivalidation/email?}]
   ["/infinite-scroll" {:get iscroll/home}]
   ["/infinite-scroll/contacts/" {:get iscroll/contacts}]
   ["/active-search" {:get asearch/home}]
   ["/active-search/search" {:post asearch/search}]
   ["/progress-bar" {:get prog-bar/home}]
   ["/progress-bar/start" {:post prog-bar/start}]
   ["/progress-bar/job" {:get prog-bar/job}]
   ["/cascading-selects" {:get cselects/home}]
   ["/cascading-selects/models" {:get cselects/models}]
   ["/animations" {:get animations/home}]
   ["/animations/colors" {:get animations/colors}]
   ["/animations/fade-out-demo" {:delete animations/fade-out-demo}]
   ["/animations/fade-in-demo" {:post animations/fade-in-demo}]
   ["/animations/name" {:post animations/name}]
   ["/file-upload" {:get fu/home}]
   ["/file-upload/upload" {:post fu/upload}]
   ["/dialogs" {:get dialogs/home}]
   ["/dialogs/submit" {:post dialogs/submit}]
   ["/dialogs-bootstrap" {:get dialogb/home}]
   ["/dialogs-bootstrap/modal" {:get dialogb/modal}]
   ["/custom-modal" {:get cmodal/home}]
   ["/custom-modal/modal" {:get cmodal/modal}]
   ["/tabs" {:get tabs/home}]
   ["/tabs/tab1" {:get tabs/tab1}]
   ["/tabs/tab2" {:get tabs/tab2}]
   ["/tabs/tab3" {:get tabs/tab3}]
   ["/hyper-tabs" {:get hypertabs/home}]
   ["/hyper-tabs/tab1" {:get hypertabs/tab1}]
   ["/hyper-tabs/tab2" {:get hypertabs/tab2}]
   ["/hyper-tabs/tab3" {:get hypertabs/tab3}]
   ["/keyboard-shortcut" {:get ks/home}]
   ["/keyboard-shortcut/doit" {:post ks/doit}]
   ["/sortable" {:get sortable/home}]
   ["/sortable/items" {:post sortable/items}]
   ["/updating-content" {:get uc/home}]
   ["/updating-content/contacts" {:post uc/contacts}]
   ["/updating-content/contacts-sol2" {:post uc/contacts-sol2}]
   ["/confirmation-dialog" {:get confirm/home}]
   ["/confirmation-dialog/confirmed" {:get confirm/confirmed}]])


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
