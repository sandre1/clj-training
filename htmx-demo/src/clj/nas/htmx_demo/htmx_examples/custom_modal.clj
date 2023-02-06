(ns nas.htmx-demo.htmx-examples.custom-modal
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home
  [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      [:h2 "Custom Modal Dialogs"]
      [:p "While htmx works great with dialogs built into CSS frameworks (like Bootstrap and UIKit), htmx also makes it easy to build modal dialogs from scratch. Here is a quick example of one way to build them."] ;;TODO add links to bootstrap and uikit
      [:p "Click here to see a demo of the final result:"]

      [:div
       [:button {:hx-get "custom-modal/modal"
                 :hx-target "body"
                 :hx-trigger "click"
                 :hx-swap "beforeend"}
        "Open a Modal"]]

      [:h3 "High Level Plan"]
      [:p "We're going to make a button that loads remote content from the server, then displays it in a modal dialog. The modal content will be added to the end of the " [:code "&lt;body&gt;"] " element, in a div named " [:code "#modal"] "."]
      [:p "We'll define some nice animations in CSS, and use some Hyperscript events (or alternatively, Javascript) to remove the modals from the DOM when the user is done. This requires you to add a minimal amount of extra markup around your modal HTML."]
      [:h3 "Main Page HTML"]

      [:pre
       [:code {:class "language-html"}
        "[:button {:hx-get \"custom-modal/modal\"
                 :hx-target \"body\"
                 :hx-trigger \"click\"
                 :hx-swap \"beforeend\"}
        \"Open a Modal\"]
"]]
      [:h3 "Modal HTML Fragment"]

      [:pre
       [:code {:class "language-html"}
        "[:div {:id \"modal\"
          :_ \"on closeModal add .closing then wait for animationend then remove me\"}
    [:div {:class \"modal-underlay\"
           :_ \"on click trigger closeModal\"}]
    [:div {:class \"modal-content\"}
     [:h1 \"Modal dialog\"]
     \"This is the modal content.
      You can put anything here, like text, or a form, or an image.\"
     [:br]
     [:br]
     [:button {:_ \"on click trigger closeModal\"} \"Close\"]]]
"]]

      [:h3 "Custom Stylesheet"]

      [:pre
       [:code {:class "language-html"}
        "[:style
    \"/***** MODAL DIALOG ****/
#modal {
	/* Underlay covers entire screen. */
	position: fixed;
	top:0px;
	bottom: 0px;
	left:0px;
	right:0px;
	background-color:rgba(0,0,0,0.5);
	z-index:1000;

	/* Flexbox centers the .modal-content vertically and horizontally */
	display:flex;
	flex-direction:column;
	align-items:center;

	/* Animate when opening */
	animation-name: fadeIn;
	animation-duration:150ms;
	animation-timing-function: ease;
}

#modal > .modal-underlay {
	/* underlay takes up the entire viewport. This is only
	required if you want to click to dismiss the popup */
	position: absolute;
	z-index: -1;
	top:0px;
	bottom:0px;
	left: 0px;
	right: 0px;
}

#modal > .modal-content {
	/* Position visible dialog near the top of the window */
	margin-top:10vh;

	/* Sizing for visible dialog */
	width:80%;
	max-width:600px;

	/* Display properties for visible dialog*/
	border:solid 1px #999;
	border-radius:8px;
	box-shadow: 0px 0px 20px 0px rgba(0,0,0,0.3);
	background-color:white;
	padding:20px;

	/* Animate when opening */
	animation-name:zoomIn;
	animation-duration:150ms;
	animation-timing-function: ease;
}

#modal.closing {
	/* Animate when closing */
	animation-name: fadeOut;
	animation-duration:150ms;
	animation-timing-function: ease;
}

#modal.closing > .modal-content {
	/* Aniate when closing */
	animation-name: zoomOut;
	animation-duration:150ms;
	animation-timing-function: ease;
}

@keyframes fadeIn {
	0% {opacity: 0;}
	100% {opacity: 1;}
} 

@keyframes fadeOut {
	0% {opacity: 1;}
	100% {opacity: 0;}
} 

@keyframes zoomIn {
	0% {transform: scale(0.9);}
	100% {transform: scale(1);}
} 

@keyframes zoomOut {
	0% {transform: scale(1);}
	100% {transform: scale(0.9);}
} 

"]]]]]))

(defn modal [_request]
  (ui
   [:div {:id "modal"
          :_ "on closeModal add .closing then wait for animationend then remove me"}
    [:div {:class "modal-underlay"
           :_ "on click trigger closeModal"}]
    [:div {:class "modal-content"}
     [:h1 "Modal dialog"]
     "This is the modal content.
      You can put anything here, like text, or a form, or an image."
     [:br]
     [:br]
     [:button {:_ "on click trigger closeModal"} "Close"]]]))


