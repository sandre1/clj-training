(ns nas.htmx-demo.htmx-examples.dialogs-bootstrap
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home
  [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:link {:href "/css/htmx-bootstrap-styles.css" :rel "stylesheet" :type "text/css"}]
    [:link {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" :rel "stylesheet" :type "text/css"}]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]]
   [:body
    [:div {:class "container-custom"}
     [:div {:class "example-wrapper"}
      [:h2 "Modal Dialogs in Bootstrap"]
      [:p "Many CSS toolkits include styles (and Javascript) for creating modal dialog boxes. This example shows how to use HTMX to display dynamic dialog using Bootstrap, and how to trigger its animation styles in Javascript."]
      [:p "We start with a button that triggers the dialog, along with a DIV at the bottom of your markup where the dialog will be loaded:"]
      
      [:pre
       [:code {:class "language-html"}
        "[:button {:hx-get \"dialogs-bootstrap/modal\"
                   :class \"btn btn-primary\"
                   :hx-trigger \"click\"
                   :_ \"on htmx:afterOnLoad wait 3000ms then add 
                    .show to #modal then add .show to #modal-backdrop\"
                   :hx-target \"#modals-here\"}
        \"Open Modal\"]
       [:div {:id \"modals-here\"}]"]]
      
      [:p "This button uses a " [:code "GET"] " request to " [:code "dialogs-bootstrap/"] " when this button is clicked. The contents of this file will be added to the DOM underneath the " [:code "#modals-here"] " DIV."]
      [:p "We're replacing Bootstrap's javascript widgets with a small bit of Hyperscript to provide smooth animations when the dialog opens and closes."]
      [:p "Finally, the server responds with a slightly modified version of Bootstrap's standard modal"]
      
      [:pre
       [:code {:class "language-html"}
        "[:div {:id \"modal-backdrop\"
             :class \"modal-backdrop fade show\"
             :style \"display:block;\"}]
[:div {:id \"modal\"
       :class \"modal fade show\"
       :tabindex \"-1\"
       :style \"display:block;\"}
 [:div {:class \"modal-dialog modal-dialog-centered\"}
  [:div {:class \"modal-content\"}
   [:div {:class \"modal-header\"}
    [:h5 {:class \"modal-title\"} \"Modal title\"]]
   [:div {:class \"modal-body\"}
    [:p \"modal body text goes here\"]]
   [:div {:class \"modal-footer\"}
    [:button {:type \"button\"
              :class \"btn btn-secondary\"
              :onclick \"closeModal()\"}
     \"Close\"]]]]]
"]]
      
      [:p "We're replacing the standard Bootstrap Javascript library with a little bit of Javascript, which triggers Bootstrap's smooth animations."]
      
      [:pre
       [:code {:class "language-html"}
        "[:script {:type \"text/javascript\"}
    \"function closeModal() {
	var container = document.getElementById(\"modals-here\")
	var backdrop = document.getElementById(\"modal-backdrop\")
	var modal = document.getElementById(\"modal\")

	modal.classList.remove(\"show\")
	backdrop.classList.remove(\"show\")

	setTimeout(function() {
		container.removeChild(backdrop)
		container.removeChild(modal)
	}, 200)
}\"]
"]]
      
      [:h2 "Demo"]
      
      [:div
       [:button {:hx-get "dialogs-bootstrap/modal"
                 :class "btn btn-primary"
                 :hx-trigger "click"
                 :_ "on htmx:afterOnLoad wait 3000ms then add .show to #modal then add .show to #modal-backdrop"
                 :hx-target "#modals-here"}
        "Open Modal"]
       [:div {:id "modals-here"}]]]]]))

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


