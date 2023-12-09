(ns concurrency.records-and-protocols)

;; record = MAP with PREDEFINED KEYS

(def jeep-wrangler {:make "Jeep"
                    :model "Wrangler"})

(defrecord CarModel [make model])

;; after evaluation, clojure created 2 fn's behind the scenes:

(def fiat-500 (->CarModel "Fiat" "500"))

(def ford-focus (map->CarModel {:make "Ford" :model "Focus"}))

(:make jeep-wrangler)

(type fiat-500)

;; protocols allow to attach functionality to records

(defprotocol Display
  (title [this])
  (description [this description]))

(defrecord HandgunModel [name model]
  Display
  (title [this] (str "this is a <" name "> handgun, model <"
                     model ">"))
  (description [this descr] (str "The " name " " model " is " descr)))

(defrecord Product [name])
;; (->Product)
;; (map->Product)

(def toaster (->Product "Toaster"))

(extend-protocol Display
  Product
  (title [p] (str "This product is a " (:name p)))
  (description [p descr] (str "The " (:name p)  " is " descr)))
(title toaster)

(description toaster "stricat")

(def left-hand (->HandgunModel "Glock" "17"))
left-hand

(title left-hand)


