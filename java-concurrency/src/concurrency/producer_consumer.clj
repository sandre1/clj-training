(ns concurrency.producer-consumer
  (:import java.lang.Thread
           java.lang.InterruptedException))

(def empty-a (atom true))

(def message-global (atom ""))

(def a (Object.))

(defn take-fn []
  (locking a
    (while @empty-a
      (try
        (.wait a)
        (catch InterruptedException e (.getMessage e))))
    (swap! empty-a (constantly true))
    (.notifyAll a)
    @message-global))


(defn put [message]
  (locking a
    (println "put message: " message)
    (while (= false @empty-a)
      (try (.wait a)
           (catch InterruptedException e (.getMessage e))))
    (swap! empty-a (constantly false))
    (swap! message-global str message)
    (.notifyAll a)))

(defn producer []
  (reify Runnable
    (run [_]
      (let [important-info ["Mares eat oats",
                            "Does eat oats",
                            "Little lambs eat ivy",
                            "A kid will eat ivy too"]]
        (println "sending messages loop:")
        (loop [m important-info]
          (println "message to put: " (first m))
          (println "global-message: " @message-global)
          (put (first m))
          (try (Thread/sleep (long (rand-int 5000)))
               (catch InterruptedException e (.getMessage e)))
          (recur (rest m)))
        (put "DONE!")))))

(defn consumer []
  (reify Runnable
    (run [_]
      (let [m (take-fn)]
        (while (and
                (= @message-global m)
                (not= @message-global "DONE!")
                (= @message-global m))
          (printf "MESSAGE RECEIVED: %s%n" @message-global)
          (try (Thread/sleep (long (rand-int 5000)))
               (catch InterruptedException e (.getMessage e))))))))

(defn producer-consumer []
  (let [t1 (Thread. (producer))
        t2 (Thread. (consumer))]
    (println "Start")
    (.start t1)
    (.start t2)
    (.join t1)
    (.join t2)
    (println "Stop")))


(comment 
  (producer-consumer)
  (let [a (atom true)]
    (swap! a (constantly true))
    (swap! a not))
  
  (let [message (atom "")]
    (empty? @message))
  (when true 
    (println "false"))
  
(let [a {:message ""}]
  (assoc a :message "asda"))
  (when (= "Done" "Done")
    (println "da"))
  (long (rand-int 5000))
  (not true)
  
  0)