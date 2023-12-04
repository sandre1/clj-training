(ns concurrency.sync-thread-interference
  (:import java.lang.Thread))

(defn increment [n]
  (inc n))

(defn decrement [n]
  (- n 1))

(defn counter []
  (let [n 0]
    (increment n)
    (decrement n)
    n))

(counter)



