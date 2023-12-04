(ns concurrency.sync-class
(:import java.lang.IllegalArgumentException))

(defn check [red green blue]
  (let []
    (when (or (< red 0)
            (> red 255)
            (< green 0)
            (> green 255)
            (< blue 0)
            (> blue 255))
      (throw IllegalArgumentException))))

(defn syncronized-RGB [red green blue name]
  (check red green blue))

(defn set-rgb [red green blue name]
  (check red green blue))