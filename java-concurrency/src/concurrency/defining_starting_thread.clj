(ns concurrency.defining-starting-thread
  (:import java.lang.Thread
          java.lang.Runnable))

;; Method 1
(defn create-runnable []
  (proxy [Runnable] []
    (run [] (println "Hello from a Thread!"))))

(let [runnable (create-runnable)
      thread (Thread. runnable)]
  (.start thread))


;; Method 2 - clojure functions implement Runnable.
(defn hello-runnable []
  (Thread. (fn [] (println "Hello from a Thread!"))))

(.start (hello-runnable))






