(ns concurrency.producer-consumer
  (:import java.lang.Thread
           java.lang.InterruptedException))

(def empty-a (atom true))

(def message-global (atom nil))

(def a (Object.))


(defn take-fn []
  (locking a
    (while @empty-a
      (try
        (.wait a)
        (println "take-fn is waiting...")
        (catch InterruptedException e (.getMessage e))))
    (reset! empty-a true)
    (println "take-fn: i have reset empty to TRUE")
    (.notifyAll a)
    (println "take-fn: i have notified all, releasing lock and returning message-global:" @message-global)
    @message-global))


(defn put [message]
  (locking a
    (while (= false @empty-a)
      (try  (.wait a)
            (println "put is waiting...")
            (catch InterruptedException e (.getMessage e))))
    (reset! empty-a false)
    (println "put: i have reset empty to FALSE")
    (reset! message-global message)
    (println "put: i have reset message global to to:" message)
    (.notifyAll a)
    (println "put: i have notified all, released lock")))

(defn producer []
  (reify Runnable
    (run [_]
      (let [important-info ["Mares eat oats",
                            "Does eat oats",
                            "Little lambs eat ivy",
                            "A kid will eat ivy too"]]
        (println "sending messages loop:")
        (doseq [m important-info]
          (println "message to put: " m)
          (put m)
          (try (Thread/sleep (* 1000 (rand-int 6)))
               (catch InterruptedException e (.getMessage e))))
        (put "DONE!")))))

(defn consumer []
  (reify Runnable
    (run [_]
         (loop [message (take-fn)]
           (when (and
                    (= @message-global message)
                    (not= @message-global "DONE!"))
               (printf "MESSAGE RECEIVED: %s%n" @message-global)
               (try (Thread/sleep (* 1000 (rand-int 6)))
                    (catch InterruptedException e (.getMessage e))))
           (recur (take-fn))))))

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

  (let [important-info ["Mares eat oats",
                        "Does eat oats",
                        "Little lambs eat ivy",
                        "A kid will eat ivy too"]]

    (loop [s important-info]
      (println "message to put: " (first s))
      (recur (rest s))))
  
  (let [a []]
    (rest a))
  
  0)