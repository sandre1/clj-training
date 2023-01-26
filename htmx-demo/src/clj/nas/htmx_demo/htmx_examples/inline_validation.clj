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
      [:h2 "Inline Validation"]
      [:p "This example shows how to do inline field validation, in this case of an email address. To do this we need to create a form with an input that" [:code "POST"] "s back to the server with the value to be validated and updates the DOM with the validation results."]
      
      [:pre [:code {:class "language-html"}
             "   [:h3 \"Signup Form\"]
      [:form {:hx-post \"inline-validation/contact\"}
       [:div {:hx-target \"this\"
              :hx-swap \"outerHTML\"}
        [:label \"Email Address\"]
        [:input {:name \"email\"
                 :type \"email\"
                 :hx-post \"/inline-validation/contact/email\"
                 :hx-indicator \"#ind\"}]
        [:img {:id \"ind\"
               :src \"/img/bars.svg\"
               :class \"htmx-indicator\"}]]
       [:div {:class \"form-group\"}
        [:label \"First Name\"]
        [:input {:type \"text\"
                 :class \"form-control\"
                 :name \"firstName\"}]]
       [:div {:class \"form-group\"}
        [:label \"Last Name\"]
        [:input {:type \"text\"
                 :class \"form-control\"
                 :name \"lastName\"}]]
       [:button {:class \"btn btn-default\"} \"Submit\"]]"]]
      
      [:p "Note that the first div in the form has set itself as the target of the request and specified the " [:code "outerHTML"] "  swap strategy, so it will be replaced entirely by the response. The input then specifies that it will " [:code "POST"] " to " [:code "\"inline-validation/contact\""] " for validation, when the " [:code "changed"] " event occurs (this is the default for inputs). It also specifies an indicator for the request."]
      [:p "When a request occurs, it will return a partial to replace the outer div. It might look like this:"]
      
      [:pre [:code {:class "language-html"}
             "   [:div {:hx-target \"this\"
             :hx-swap \"outerHTML\"
             :class \"error\"}
       [:label \"Email Address\"]
       [:input {:name \"email\"
                :hx-post \"/inline-validation/contact/email\"
                :hx-indicator \"#ind\"
                :value \"test@foo.com\"}]
       [:img {:id \"ind\"
              :src \"/img/bars.svg\"
              :class \"htmx-indicator\"}]
       [:div {:class \"error-message\"} \"That email is already taken.  Please enter another email.\"]]"]]
      
      [:p "Note that this div is annotated with the" [:code "error"] " class and includes an error message element."]
      [:p "This form can be lightly styled with this CSS:"]
      
      [:pre [:code {:class "language-html"}
             "   [:style \".error-message {color:red;}
            .error input {box-shadow: 0 0 3px #CC0000;}
            .valid input {box-shadow: 0 0 3px #36cc00;}\"]"]]
      
      [:p "To give better visual feedback, below is a working demo of this example.  The only email that will be accepted is " [:code "test@test.com"] "."]
      
      [:h2 "Demo"]
      
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