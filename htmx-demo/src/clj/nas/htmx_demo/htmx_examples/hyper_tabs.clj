(ns nas.htmx-demo.htmx-examples.hyper-tabs
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js"}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5"}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div {:id "tabs"
           :hx-target "#tab-contents"
           :_ "on htmx:afterOnLoad take .selected for event.target"}
     [:a {:hx-get "hyper-tabs/tab1"
          :class "selected"} "Tab 1"]
     [:a {:hx-get "hyper-tabs/tab2"} "Tab 2"]
     [:a {:hx-get "hyper-tabs/tab3"} "Tab 3"]]
    [:div {:id "tab-contents"
           :hx-get "/tab1"
           :hx-trigger "load"}]]))

(defn tab1 [_request]
  (ui
   [:p "Commodo normcore truffaut VHS duis gluten-free keffiyeh iPhone taxidermy godard ramps anim pour-over. 
	Pitchfork vegan mollit umami quinoa aute aliquip kinfolk eiusmod live-edge cardigan ipsum locavore. 
	Polaroid duis occaecat narwhal small batch food truck. 
	PBR&B venmo shaman small batch you probably haven't heard of them hot chicken readymade. 
	Enim tousled cliche woke, typewriter single-origin coffee hella culpa. 
	Art party readymade 90's, asymmetrical hell of fingerstache ipsum."]))

(defn tab2 [_request]
  (ui
   [:p "Kitsch fanny pack yr, farm-to-table cardigan cillum commodo reprehenderit plaid dolore cronut meditation. Tattooed polaroid veniam, anim id cornhole hashtag sed forage. Microdosing pug kitsch enim, kombucha pour-over sed irony forage live-edge. Vexillologist eu nulla trust fund, street art blue bottle selvage raw denim. Dolore nulla do readymade, est subway tile affogato hammock 8-bit. Godard elit offal pariatur you probably haven't heard of them post-ironic. Prism street art cray salvia."]))

(defn tab3 [_request]
  (ui
   [:p "Aute chia marfa echo park tote bag hammock mollit artisan listicle direct trade. Raw denim flexitarian eu godard etsy. Poke tbh la croix put a bird on it fixie polaroid aute cred air plant four loko gastropub swag non brunch. Iceland fanny pack tumeric magna activated charcoal bitters palo santo laboris quis consectetur cupidatat portland aliquip venmo."]))
