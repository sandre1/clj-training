(ns concurrency.defining-starting-thread
  (:import java.lang.Thread
          java.lang.Runnable))

;; Provide a Runnable object - Method 1
(defn create-runnable []
  (proxy [Runnable] []
    (run [] (println "Hello from a Thread!"))))

(defn create-runnable-2 []
  (reify Runnable 
    (run [_]
      (println "hello from a thread, using reify"))))

(comment
  (let [runnable (create-runnable-2)
        thread (Thread. runnable)]
    (.start thread))
  )


;; Provide a Runnable object - Method 2
;; NOTE: - clojure functions implement Runnable.
;; TODO: check reify
(defn hello-runnable []
  (Thread. (fn [] (println "Hello from a Thread!"))))

(comment
  (.start (hello-runnable))
  )

;; Subclass Thread
(defn hello-thread []
  (proxy [Thread] []
    (run [] (println "Hello from a Thread!"))))

(comment
  (.start (hello-thread))
  )







