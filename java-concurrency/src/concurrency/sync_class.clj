(ns concurrency.sync-class
(:import java.lang.IllegalArgumentException))

(def a (Object.))


(defn check [red green blue]
  (when (or (< red 0)
            (> red 255)
            (< green 0)
            (> green 255)
            (< blue 0)
            (> blue 255))
    (throw IllegalArgumentException)))

(defn syncronized-RGB [red green blue name atm]
  (check red green blue)
  (swap! atm assoc :red red)
  (swap! atm assoc :green green)
  (swap! atm assoc :blue blue)
  (swap! atm assoc :name name))

(defn set-rgb [red green blue name atm]
  (check red green blue)
  (locking a
    (swap! atm assoc :red red)
    (swap! atm assoc :green green)
    (swap! atm assoc :blue blue)
    (swap! atm assoc :name name)))

(defn getRGB [atm]
  (locking a
    (let [{:keys [red green blue]} @atm]
      (bit-or (bit-shift-left red 16)
              (bit-shift-left green 8)
              blue))))

(defn getName [atm]
  (locking a
    (:name @atm)))

(defn invert [atm]
  (locking a
    (let [{:keys [red green blue name]} @atm
          r (- 255 red)
          g (- 255 green)
          b (- 255 blue)
          n (str "Inverse of " name)]
      (swap! atm assoc :red r)
      (swap! atm assoc :green g)
      (swap! atm assoc :blue b)
      (swap! atm assoc :name n))))

(defn -main [& args]
  (let [x (atom {})
        t1 (Thread. (fn []
                      (syncronized-RGB 0 0 0 "Pitch-Black" x)
                      (locking a 
                        (println (getRGB x))
                        (Thread/sleep 5000)
                        (println (getName x)))))
        t2 (Thread. #(set-rgb 10 10 10 "test test" x))]
    (.start t1)
    (.start t2)
    (.join t1)
    (.join t2)
    @x))







(comment
  (-main)
  0)