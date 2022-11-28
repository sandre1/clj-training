(ns nas.htmx-demo.htmx-examples.infinite-scroll
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
            [nas.htmx-demo.htmx-examples.data :as local-db]))

(def agents local-db/agents)

(def agents-team (atom (take 30 agents)))

(defn agent-row [a]
  (let [name (:name a)
        email (:email a)
        id (:id a)]
    [:tr [:td name]
     [:td email]
     [:td id]]))

(defn last-agent-row [a]
  (let [name (:name a)
        email (:email a)
        id (:id a)
        page-nr 1
        url (str "/infinite-scroll/contacts/?page=" (inc page-nr))]
    [:tr {:hx-get url
          :hx-trigger "revealed"
          :hx-swap "afterend"}
     [:td name]
     [:td email]
     [:td id]]))

#_[:tr {:hx-get "/infinite-loading/contacts/?page=2"
          :hx-trigger "revealed"
          :hx-swap "afterend"}
     ]

#_(map #(if (= % (last (take 30 agents))) (last-agent-row %) (agent-row %)) (take 30 agents))

(defn home [request]
  (page
   (do (reset! agents-team (vec (take 30 agents)))
     (list [:head
            [:meta {:charset "UTF-8"}]
            [:title "Htmx + Kit"]
            [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
            [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
            [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
           [:body
            (list [:table [:thead
                     [:th "Name"]
                     [:th "Email"]
                     [:th "ID"]]
             [:tbody (map #(if (= % (last @agents-team)) (last-agent-row %) (agent-row %)) @agents-team)]]
                  [:div 
                   [:img {:alt "Result loading..."
           :class ""
           :width "150"
           :src "/img/bars.svg"}]])]))))

(defn add-more-agents [list-of-agents]
  (let [loaded-agents-nr (count @agents-team)
        how-many? 20
        start (+ loaded-agents-nr 1)
        finish (+ loaded-agents-nr how-many?)]
    (subvec list-of-agents start finish)))

(defn contacts [request]
  (ui 
(do (swap! agents-team (comp vec flatten conj) (add-more-agents agents))
    (Thread/sleep 1000) 
    (map #(if (= % (last @agents-team)) (last-agent-row %) (agent-row %)) (subvec @agents-team 31)))))

(comment 
  (let [l (take 10 local-db/agents)]
    (map #(if (= % (last l)) (println "BINGO") (println "not true")) l))
  (last (take 10 local-db/agents))
  (= {:name "Agent Smith", :email "void9@null.org", :id "LPVFTNGMudzA-UF"} {:name "Agent Smith", :email "void9@null.org", :id "LPVFTNGMudzA-UF"})
  (last (take 10 local-db/agents))

  (map #(if (= % (last (take 20 agents))) (last-agent-row %) (agent-row %)) (take 20 agents))
  
  (take 1 @agents-team)
  (type (reset! agents-team (atom (take 30 agents))))
  (list 1 2)
  (deref agents-team)
  (reset! agents-team (vec (take 30 agents)))
  (add-more-agents agents)
  (swap! agents-team (comp vec flatten conj) [{:name "Agent Smith", :email "void31@null.org", :id "5NT9Bl8VDSCeC0M"} {:name "Agent Smith", :email "void32@null.org", :id "b71Ba9Ndq3NN8K8"} {:name "Agent Smith", :email "void33@null.org", :id "OvHFD3y9O4IWYG1"} {:name "Agent Smith", :email "void34@null.org", :id "ZFNYAJ7OV68yllT"} {:name "Agent Smith", :email "void35@null.org", :id "8b2qww7npnAjL5A"} {:name "Agent Smith", :email "void36@null.org", :id "fo4O52G5RhPza0R"} {:name "Agent Smith", :email "void37@null.org", :id "t8FcJXuqpyDrLhu"} {:name "Agent Smith", :email "void38@null.org", :id "04PZZ2CiwHVsVB1"} {:name "Agent Smith", :email "void39@null.org", :id "g3E70u6O9faqXuU"} {:name "Agent Smith", :email "void40@null.org", :id "_rua2R-q3Wgo33T"} {:name "Agent Smith", :email "void41@null.org", :id "frFl5m28KtwZfAv"} {:name "Agent Smith", :email "void42@null.org", :id "vFw-8ymykuz9G7W"} {:name "Agent Smith", :email "void43@null.org", :id "2Tbb9nziQOsYQa5"} {:name "Agent Smith", :email "void44@null.org", :id "rjvEYBolej3aHgb"} {:name "Agent Smith", :email "void45@null.org", :id "d_7sqK-wzDjrYPk"} {:name "Agent Smith", :email "void46@null.org", :id "KoDO_GrmBRn94Jd"} {:name "Agent Smith", :email "void47@null.org", :id "Kw1rFGUOVj-IpZe"} {:name "Agent Smith", :email "void48@null.org", :id "qlUb7Wt1H9CKQGM"} {:name "Agent Smith", :email "void49@null.org", :id "quVmonopsTI57Tb"}])
  ((comp vec flatten conj) [{:name "Agent Smith", :email "void29@null.org", :id "ub64Pm91MX3Mtvt"} {:name "Agent Smith", :email "void30@null.org", :id "ub64Pm91MX3Mttt"}] [{:name "Agent Smith2", :email "void29@null.org", :id "ub64Pm91MX3Mtvt"} {:name "Agent Smith2", :email "void30@null.org", :id "ub64Pm91MX3Mttt"}])
  0)
