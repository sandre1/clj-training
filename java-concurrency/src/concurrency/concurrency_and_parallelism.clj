(ns concurrency.concurrency-and-parallelism)

;; read more at https://clojure-doc.org/articles/language/concurrency_and_parallelism/

;; ===ATOMS===

(def currently-connected (atom []))

(let [xs (atom [])]
  @xs)

(swap! currently-connected conj "mere")

(reset! currently-connected [])

(let [a (atom 0)
      q (mapv #(future (swap! a (fn [n] (println %) (inc n))))
              (range 10))]
  (mapv deref q))

;This will return a vector of 10 values, 1 to 10, in some arbitrary order, and it will print the numbers 0 to 9 at least once each -- but probably print several of them more than once, indicating that swap! was retried.

;;Note:  swap! is an impure function, it can lead to contention => it retries


;; ===AGENTS===

(def errors-counter (agent 0))

(send errors-counter inc)
@errors-counter
