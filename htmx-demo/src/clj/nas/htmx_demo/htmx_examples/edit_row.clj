(ns nas.htmx-demo.htmx-examples.edit-row
  (:require
   [nas.htmx-demo.htmx-examples.data :as local-db]
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(def users (atom local-db/persons))

(defn row [user]
  (let [i (:index user)
        url (str "/edit-row/" i "/edit")]
    [:tr
     [:td (:name user)]
     [:td (:email user)]
     [:td [:button {:class "btn btn-danger"
                    :hx-get url} "Edit"]]]))

(defn make-rows [users]
  (map row users))

(defn home [request]
  (page
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:table {:class "table delete-row-example"}
     [:thead [:tr [:th "Name"]
              [:th "Email"]
              [:th]]]
     [:tbody {:hx-target "closest tr"
              :hx-swap "outerHTML"}
      (make-rows @users)]]]))

(defn edit [request]
(ui (let [params (:path-params request)
          id (:id params)
          num-id (Integer/parseInt id)
          edited-user (first (filter #(= (Integer/parseInt (:index %)) num-id) @users))]
      [:tr
     [:td [:input {:name "name" :value (:name edited-user)}]]
     [:td [:input {:name "email" :value (:email edited-user)}]]
     [:td
      [:button {:class "btn btn-danger"
                :hx-get (str "/edit-row/" id)} "Cancel"]
      [:button {:class "btn btn-danger"
                :hx-put (str "/edit-row/" id)
                :hx-include "closest tr"} "Save"]]])))

(defn cancel-edit [request]
  (ui (let [params (:path-params request)
        id (:id params)
        num-idx (Integer/parseInt id)
        user (nth @users num-idx)]
    (row user))))

(defn save [request]
  (ui (let [path-par (:path-params request)
            params (:params request)
            name (:name params)
            email (:email params)
            id (:id path-par)
            num-idx (Integer/parseInt id)]
        (do (swap! users assoc-in [num-idx :name] name)
            (swap! users assoc-in [num-idx :email] email)
            (row (first (filter #(= (:index %) id) @users)))))))

(comment 
  (filter #(= (Integer/parseInt (:index %)) 0) @users)
  (deref users)
  
  0)