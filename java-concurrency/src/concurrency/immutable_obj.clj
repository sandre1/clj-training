(ns concurrency.immutable-obj
  (:import java.lang.IllegalArgumentException))


(def a (atom {}))

(defprotocol ImmutableRGB
  (check [this])
  (immutable-rgb [this])
  (getRGB [this])
  (getName [this])
  (invert [this]))

(defrecord newImmutableRGB [red green blue name]
  ImmutableRGB
  (check [this]
         (let [{:keys [red green blue]} this]
           (when (or (< red 0)
                     (> red 255)
                     (< green 0)
                     (> green 255)
                     (< blue 0)
                     (> blue 255))
             (throw (IllegalArgumentException. "Something went wrong") ))))
  (immutable-rgb [this]
                 (let [{:keys [red green blue name]} this
                       l [{:red red} 
                          {:green green} 
                          {:blue blue}
                          {:name name}]]
                   (check this)
                   (apply merge l)))
  (getRGB [this]
          (let [{:keys [red green blue]} this]
            (bit-or (bit-shift-left red 16)
                    (bit-shift-left green 8)
                    blue)))
  (getName [this]
           (:name this))
  (invert [this]
          (let [{:keys [red green blue name]} this
                r (- 255 red)
                g (- 255 green)
                b (- 255 blue)
                n (str "Inverse of " name)
                inverted (assoc this :red r :blue b :green g :name n)]
            (immutable-rgb inverted))))

(def rgb (->newImmutableRGB 1 9 0 "corn-blue"))
(def rgb1 (->newImmutableRGB -1 9 0 "not a color"))

(invert rgb)




