(ns nas.htmx-demo.htmx-examples.tabs
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home
  [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"  }]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"  }]]
   [:body
    [:div {:class "container"}
     [:div {:class "example-wrapper"}
      
      [:h2 "Tabs (Using HATEOAS)"]
      [:p "This example shows how easy it is to implement tabs using htmx. Following the principle of " [:a {:href "https://en.wikipedia.org/wiki/HATEOAS"}"Hypertext As The Engine Of Application State"] ", the selected tab is a part of the application state. Therefore, to display and select tabs in your application, simply include the tab markup in the returned HTML. If this does not suit your application server design, you can also use a little bit of " [:a {:href "/hyper-tabs"} "Hyperscript to select tabs instead"] "."]
      
      [:h3 "Example Code (Main Page)"]
      [:p "The main page simply includes the following HTML to load the initial tab into the DOM."]
      
      [:pre
       [:code {:class "language-html"}
        "[:div {:id \"tabs\"
             :hx-get \"tabs/tab1\"
             :hx-trigger \"load delay:100ms\"
             :hx-target \"#tabs\"}]"]]
      
      [:h3 "Example Code (Each Tab)"]
      [:p "Subsequent tab pages display all tabs and highlight the selected one accordingly."]
      
      [:pre
       [:code {:class "language-html"}
        "[:div {:class \"tab-list\"}
    [:a {:hx-get \"tabs/tab1\"
         :class \"selected\"} \"Tab 1\"]
    [:a {:hx-get \"tabs/tab2\"} \"Tab 2\"]
    [:a {:hx-get \"tabs/tab3\"} \"Tab 3\"]]
  [:div {:class \"tab-content\"}
    \"Commodo normcore truffaut VHS duis gluten-free keffiyeh iPhone taxidermy godard ramps anim pour-over. 
	Pitchfork vegan mollit umami quinoa aute aliquip kinfolk eiusmod live-edge cardigan ipsum locavore. 
	Polaroid duis occaecat narwhal small batch food truck. 
	PBR&B venmo shaman small batch you probably haven't heard of them hot chicken readymade. 
	Enim tousled cliche woke, typewriter single-origin coffee hella culpa. 
	Art party readymade 90's, asymmetrical hell of fingerstache ipsum.\"]"]]
      
      [:h2 "Demo"]
      
      [:div {:id "tabs"
             :hx-get "tabs/tab1"
             :hx-trigger "load delay:100ms"
             :hx-target "#tabs"}]]]]))

(defn tab1 [_request]
  (ui
   [:div {:class "tab-list"}
    [:a {:hx-get "tabs/tab1"
         :class "selected"} "Tab 1"]
    [:a {:hx-get "tabs/tab2"} "Tab 2"]
    [:a {:hx-get "tabs/tab3"} "Tab 3"]]
   [:div {:class "tab-content"}
    "Commodo normcore truffaut VHS duis gluten-free keffiyeh iPhone taxidermy godard ramps anim pour-over. 
	Pitchfork vegan mollit umami quinoa aute aliquip kinfolk eiusmod live-edge cardigan ipsum locavore. 
	Polaroid duis occaecat narwhal small batch food truck. 
	PBR&B venmo shaman small batch you probably haven't heard of them hot chicken readymade. 
	Enim tousled cliche woke, typewriter single-origin coffee hella culpa. 
	Art party readymade 90's, asymmetrical hell of fingerstache ipsum."]))

(defn tab2 [_request]
  (ui
   [:div {:class "tab-list"}
    [:a {:hx-get "tabs/tab1"} "Tab 1"]
    [:a {:hx-get "tabs/tab2"
         :class "selected"} "Tab 2"]
    [:a {:hx-get "tabs/tab3"} "Tab 3"]]
   [:div {:class "tab-content"}
    "Kitsch fanny pack yr, farm-to-table cardigan cillum commodo reprehenderit plaid dolore cronut meditation. Tattooed polaroid veniam, anim id cornhole hashtag sed forage. Microdosing pug kitsch enim, kombucha pour-over sed irony forage live-edge. Vexillologist eu nulla trust fund, street art blue bottle selvage raw denim. Dolore nulla do readymade, est subway tile affogato hammock 8-bit. Godard elit offal pariatur you probably haven't heard of them post-ironic. Prism street art cray salvia."]))

(defn tab3 [_request]
  (ui
   [:div {:class "tab-list"}
    [:a {:hx-get "tabs/tab1"} "Tab 1"]
    [:a {:hx-get "tabs/tab2"} "Tab 2"]
    [:a {:hx-get "tabs/tab3"
         :class "selected"} "Tab 3"]]
   [:div {:class "tab-content"}
    "Aute chia marfa echo park tote bag hammock mollit artisan listicle direct trade. Raw denim flexitarian eu godard etsy. Poke tbh la croix put a bird on it fixie polaroid aute cred air plant four loko gastropub swag non brunch. Iceland fanny pack tumeric magna activated charcoal bitters palo santo laboris quis consectetur cupidatat portland aliquip venmo."]))