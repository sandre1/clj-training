(ns nas.htmx-demo.htmx-examples.file-upload
  (:require
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home
  [_request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:form {:id "form"
            :hx-encoding "multipart/form-data"
            :hx-post "/file-upload/upload"}
     [:input {:type "file"
              :name "file"}
      [:button "Upload"]
      [:progress {:id "progress"
                  :value 0
                  :max 100}]]]
    [:script
     "htmx.on('#form', 'htmx:xhr:progress', function(evt) {
          htmx.find('#progress').setAttribute('value', evt.detail.loaded/evt.detail.total * 100)
        });"]]))

(defn upload
  [request]
  (ui
   (let [params (:params request)]
   (list 
    [:form {:id "form"
            :hx-encoding "multipart/form-data"
            :hx-post "/file-upload/upload"}
     [:input {:type "file"
              :name "file"}
      [:button "Upload"]
      [:progress {:id "progress"
                  :value 50
                  :max 100}]]]
[:div (str request)]
[:script
 "htmx.on('#form', 'htmx:xhr:progress', function(evt) {
          htmx.find('#progress').setAttribute('value', evt.detail.loaded/evt.detail.total * 100)
        });"]))))