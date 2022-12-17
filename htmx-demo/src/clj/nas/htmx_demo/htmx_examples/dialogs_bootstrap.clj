(ns nas.htmx-demo.htmx-examples.dialogs-bootstrap
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home
  [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:link {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" :rel "stylesheet" :type "text/css"}]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.7" :defer true}]]
   [:body
    [:div
     [:button {:hx-get "dialogs-bootstrap/modal"
               :class "btn btn-primary"
               :hx-trigger "click"
               :_ "on htmx:afterOnLoad wait 10ms then add .show to #modal then add .show to #modal-backdrop"         
               :hx-target "#modals-here"}
      "Open Modal"]
     [:div {:id "modals-here"}]]]))

(defn modal [request]
  (ui
   [:div {:id "modal-backdrop"
          :class "modal-backdrop fade show"
          :style "display:block;"}]
   [:div {:id "modal"
          :class "modal fade show"
          :tabindex "-1"
          :style "display:block;"}
    [:div {:class "modal-dialog modal-dialog-centered"}
     [:div {:class "modal-content"}
      [:div {:class "modal-header"}
       [:h5 {:class "modal-title"} "Modal title"]]
      [:div {:class "modal-body"}
       [:p "modal body text goes here"]]
      [:div {:class "modal-footer"}
       [:button {:type "button"
                 :class "btn btn-secondary"
                 :onclick "closeModal()"}
        "Close"]]]]]
   [:script {:type "text/javascript"}
    "function closeModal() {
	var container = document.getElementById(\"modals-here\")
	var backdrop = document.getElementById(\"modal-backdrop\")
	var modal = document.getElementById(\"modal\")

	modal.classList.remove(\"show\")
	backdrop.classList.remove(\"show\")

	setTimeout(function() {
		container.removeChild(backdrop)
		container.removeChild(modal)
	}, 200)
}
"]))


