(ns nas.htmx-demo.htmx-examples.click-to-load
  (:require
   [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]
   [nano-id.core :refer [nano-id]]
   [clojure.set :refer [rename-keys]]))

(defn create-data [i]
  (let [entries (range 0 i)
        create-agents (map (fn [n]
                             {:name "Agent Smith"
                              :email (str "void" n "@null.org")
                              :id (nano-id 15)}) entries)]
    (vec create-agents)))

#_(def agents (create-data 100))

(def agents [{:name "Agent Smith", :email "void0@null.org", :id "TzmuvJyRCYOkvpj"} {:name "Agent Smith", :email "void1@null.org", :id "9HCIxs_dHKLkgFI"} {:name "Agent Smith", :email "void2@null.org", :id "dXKOQQQcXqRGEC1"} {:name "Agent Smith", :email "void3@null.org", :id "qdyISxdVNProFV7"} {:name "Agent Smith", :email "void4@null.org", :id "oSAqPFSvQ6bV5c6"} {:name "Agent Smith", :email "void5@null.org", :id "5OdAXZiMPfgbv75"} {:name "Agent Smith", :email "void6@null.org", :id "sMqutTMVcoolJFu"} {:name "Agent Smith", :email "void7@null.org", :id "6EoFG04CZ1CzSzF"} {:name "Agent Smith", :email "void8@null.org", :id "4pg6v0ZEnOcsEoP"} {:name "Agent Smith", :email "void9@null.org", :id "LPVFTNGMudzA-UF"} {:name "Agent Smith", :email "void10@null.org", :id "e__5qt5ZQdWlJlH"} {:name "Agent Smith", :email "void11@null.org", :id "0viTNg5REmM3ljy"} {:name "Agent Smith", :email "void12@null.org", :id "Dq8u5bDGUe07Mcz"} {:name "Agent Smith", :email "void13@null.org", :id "4TnNFTN7NrkOfZA"} {:name "Agent Smith", :email "void14@null.org", :id "QhPJUCiGecIAc-j"} {:name "Agent Smith", :email "void15@null.org", :id "zOlrBsuxNdMbvpt"} {:name "Agent Smith", :email "void16@null.org", :id "QAVY85WybanoQEK"} {:name "Agent Smith", :email "void17@null.org", :id "DKc9b3f55mhdeZS"} {:name "Agent Smith", :email "void18@null.org", :id "vnAT1XBpFkDVBU9"} {:name "Agent Smith", :email "void19@null.org", :id "nh-YsrkQi5ydttF"} {:name "Agent Smith", :email "void20@null.org", :id "ROmkT4DZjd2g4hG"} {:name "Agent Smith", :email "void21@null.org", :id "CftxxSCjcO-jMrI"} {:name "Agent Smith", :email "void22@null.org", :id "-KYmV5mgVMvUqht"} {:name "Agent Smith", :email "void23@null.org", :id "k-ZIHfL5z1LwJJk"} {:name "Agent Smith", :email "void24@null.org", :id "Ruh1yR2tD_NfFTh"} {:name "Agent Smith", :email "void25@null.org", :id "5_l9rIni_6NE02D"} {:name "Agent Smith", :email "void26@null.org", :id "2sv38J9wRCI6RkH"} {:name "Agent Smith", :email "void27@null.org", :id "5n8OTr7LaDIJrwO"} {:name "Agent Smith", :email "void28@null.org", :id "FihsZdX-FykIxDG"} {:name "Agent Smith", :email "void29@null.org", :id "ub64Pm91MX3Mtvt"} {:name "Agent Smith", :email "void30@null.org", :id "PzAh-xjSkMmNZsc"} {:name "Agent Smith", :email "void31@null.org", :id "5NT9Bl8VDSCeC0M"} {:name "Agent Smith", :email "void32@null.org", :id "b71Ba9Ndq3NN8K8"} {:name "Agent Smith", :email "void33@null.org", :id "OvHFD3y9O4IWYG1"} {:name "Agent Smith", :email "void34@null.org", :id "ZFNYAJ7OV68yllT"} {:name "Agent Smith", :email "void35@null.org", :id "8b2qww7npnAjL5A"} {:name "Agent Smith", :email "void36@null.org", :id "fo4O52G5RhPza0R"} {:name "Agent Smith", :email "void37@null.org", :id "t8FcJXuqpyDrLhu"} {:name "Agent Smith", :email "void38@null.org", :id "04PZZ2CiwHVsVB1"} {:name "Agent Smith", :email "void39@null.org", :id "g3E70u6O9faqXuU"} {:name "Agent Smith", :email "void40@null.org", :id "_rua2R-q3Wgo33T"} {:name "Agent Smith", :email "void41@null.org", :id "frFl5m28KtwZfAv"} {:name "Agent Smith", :email "void42@null.org", :id "vFw-8ymykuz9G7W"} {:name "Agent Smith", :email "void43@null.org", :id "2Tbb9nziQOsYQa5"} {:name "Agent Smith", :email "void44@null.org", :id "rjvEYBolej3aHgb"} {:name "Agent Smith", :email "void45@null.org", :id "d_7sqK-wzDjrYPk"} {:name "Agent Smith", :email "void46@null.org", :id "KoDO_GrmBRn94Jd"} {:name "Agent Smith", :email "void47@null.org", :id "Kw1rFGUOVj-IpZe"} {:name "Agent Smith", :email "void48@null.org", :id "qlUb7Wt1H9CKQGM"} {:name "Agent Smith", :email "void49@null.org", :id "quVmonopsTI57Tb"} {:name "Agent Smith", :email "void50@null.org", :id "6gaDZI4tBcI4UHA"} {:name "Agent Smith", :email "void51@null.org", :id "sQZ7UoilStNTdHL"} {:name "Agent Smith", :email "void52@null.org", :id "f3f4E27-NWtCLvV"} {:name "Agent Smith", :email "void53@null.org", :id "FYzstoWqQCeIsOo"} {:name "Agent Smith", :email "void54@null.org", :id "g7Lmo1pT78W8NQl"} {:name "Agent Smith", :email "void55@null.org", :id "6udj9JWGeTqY0Hs"} {:name "Agent Smith", :email "void56@null.org", :id "Tt5ViNKC9zF_FAW"} {:name "Agent Smith", :email "void57@null.org", :id "fEU3FZdHMQOQAlw"} {:name "Agent Smith", :email "void58@null.org", :id "BI1VgEDZVcoJypW"} {:name "Agent Smith", :email "void59@null.org", :id "v3BsTFk-KVk1th2"} {:name "Agent Smith", :email "void60@null.org", :id "9YuGwv6z-5OlQSh"} {:name "Agent Smith", :email "void61@null.org", :id "eTET1YiX8EInvwg"} {:name "Agent Smith", :email "void62@null.org", :id "GWfnogNMxsZ67SY"} {:name "Agent Smith", :email "void63@null.org", :id "yjhxyT1IZEUz4lm"} {:name "Agent Smith", :email "void64@null.org", :id "73YpP2WZgzJvD7I"} {:name "Agent Smith", :email "void65@null.org", :id "XlCTfuiYZ_HW7Bc"} {:name "Agent Smith", :email "void66@null.org", :id "1f4WHxIeZfRaVIi"} {:name "Agent Smith", :email "void67@null.org", :id "zfS1_uvDWsO70eO"} {:name "Agent Smith", :email "void68@null.org", :id "Wy53DRwd3UPCmwZ"} {:name "Agent Smith", :email "void69@null.org", :id "Mk4V7EHtGpIDsE9"} {:name "Agent Smith", :email "void70@null.org", :id "LwTtXamU0KHO6AC"} {:name "Agent Smith", :email "void71@null.org", :id "GNjiCIPrzejANg2"} {:name "Agent Smith", :email "void72@null.org", :id "x5Y7Wr78o1qhufH"} {:name "Agent Smith", :email "void73@null.org", :id "k62aX1zmImiu9DU"} {:name "Agent Smith", :email "void74@null.org", :id "saz1AQtptAGstDu"} {:name "Agent Smith", :email "void75@null.org", :id "0dnzlBX8K8HTsdT"} {:name "Agent Smith", :email "void76@null.org", :id "_nSb-CRbVG5eg9u"} {:name "Agent Smith", :email "void77@null.org", :id "Oh2rpLqqPrkMYA9"} {:name "Agent Smith", :email "void78@null.org", :id "86A3pqK3liqY8Ev"} {:name "Agent Smith", :email "void79@null.org", :id "1BInULumg7nOso5"} {:name "Agent Smith", :email "void80@null.org", :id "eUEwbg7rIVcwPoT"} {:name "Agent Smith", :email "void81@null.org", :id "rJKqRrL8hnwrZP8"} {:name "Agent Smith", :email "void82@null.org", :id "Kg0o93LQOGgx8FB"} {:name "Agent Smith", :email "void83@null.org", :id "mh2LJd8_0SqS0n4"} {:name "Agent Smith", :email "void84@null.org", :id "Of144puNqernAv-"} {:name "Agent Smith", :email "void85@null.org", :id "nBNmifexRSrtWIB"} {:name "Agent Smith", :email "void86@null.org", :id "7Hao1fEuEd2M7QN"} {:name "Agent Smith", :email "void87@null.org", :id "kQ0OsTyFpqtAmBq"} {:name "Agent Smith", :email "void88@null.org", :id "0wh3gtOwmODdyXd"} {:name "Agent Smith", :email "void89@null.org", :id "GdrB3AL6KbBiw40"} {:name "Agent Smith", :email "void90@null.org", :id "6LA-5I2rQHMPoBu"} {:name "Agent Smith", :email "void91@null.org", :id "9oVURwsyWYy4C_W"} {:name "Agent Smith", :email "void92@null.org", :id "V1fuvm_cEUAi7ym"} {:name "Agent Smith", :email "void93@null.org", :id "iBKkWeZ3vdZFWBk"} {:name "Agent Smith", :email "void94@null.org", :id "dtclg2fcmT4ogrz"} {:name "Agent Smith", :email "void95@null.org", :id "WlpHtKQQfYJb2iN"} {:name "Agent Smith", :email "void96@null.org", :id "LkzDPAS_pXEpaEI"} {:name "Agent Smith", :email "void97@null.org", :id "j8k1YTPihrCcm4r"} {:name "Agent Smith", :email "void98@null.org", :id "YkTh3L_rI54WEdy"} {:name "Agent Smith", :email "void99@null.org", :id "Up6ERDP_u2seh4n"}])

(def team (atom ()))

(defn data-row [a]
  (let [name (:name a)
        email (:email a)
        id (:id a)]
    [:tr [:td name]
     [:td email]
     [:td id]]))

(defn data-rows [agents]
  (map data-row agents))

(defn home [request]
  (page
   (do (reset! team [])
       (list [:head
    [:meta {:charset "UTF-8"}]
    [:title "Htmx + Kit"]
    [:script {:src "https://unpkg.com/htmx.org@1.7.0/dist/htmx.min.js" :defer true}]
    [:script {:src "https://unpkg.com/hyperscript.org@0.9.5" :defer true}]
    [:link {:href "/css/htmx-styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:table
     [:thead [:tr
              [:th "Name"]
              [:th "Email"]
              [:th "ID"]]
      [:tbody {:id "tbody"}
       (data-rows (take 10 agents))
       [:tr {:id "replaceMe"}
        [:td {:colspan 3}
         [:button {:class "btn"
                   :hx-get "/click-to-load/load-more?page=2"
                   :hx-target "#replaceMe"
                   :hx-swap "outerHTML"} "Load more agents..."]]]]]]]))))

(defn construct-tr [state]
  (for [i @state]
    [:tr [:td (:name i)]
     [:td (:email i)]
     [:td (:id i)]]))

(defn add-more-agents [list-of-agents n]
  (let [min-n (dec n) ;; exclude first 10 rows already loaded
        start (* min-n 10)
        finish (* n 10)]
    (subvec list-of-agents start finish)))


(defn load-more [request]
  (ui
   (let [params (:query-params request)
         parsed-params (rename-keys params {"page" :page})
         page (:page parsed-params)
         page-number (Integer/parseInt page)
         new-page (inc page-number)
         url (str "/click-to-load/load-more?page=" new-page)]
     (do (reset! team [])
       (swap! team (fn [team] (vec (distinct (concat team (add-more-agents agents page-number))))))
       (list
        (construct-tr team)
        [:tr {:id "replaceMe"}
         [:td {:colspan 3}
          [:button {:class "btn"
                    :hx-get url
                    :hx-target "#replaceMe"
                    :hx-swap "outerHTML"} "Load more agents..."]]])))))

(comment
  (let [page 2
        new-page (inc page)]
    (swap! team (fn [team] (distinct (concat team (take (* new-page 10) agents))))))
  (let [p (atom {:name "nas" :age "34"})]
    [:td (:name @p)])
  (take 20 agents)
  (add-more-agents agents 2)
  0
  )