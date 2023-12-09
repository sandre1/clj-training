(ns concurrency.producer-consumer
  (:import java.lang.Thread
           java.lang.InterruptedException))

(def empty-a (atom true))

(def message-global (atom nil))

(def a (Object.))


(defn take-fn [lock]
  (locking lock
    (while @empty-a
      (try
        (.wait lock)
        (println "take-fn is waiting...")
        (catch InterruptedException e (.getMessage e))))
    (reset! empty-a true)
    (println "take-fn: i have reset empty to TRUE")
    (.notifyAll lock)
    (println "take-fn: i have notified all, releasing lock and returning message-global:" @message-global)
    @message-global))


(defn put [message lock]
  (locking lock
    (while (= false @empty-a)
      (try  (.wait lock)
            (println "put is waiting...")
            (catch InterruptedException e (.getMessage e))))
    (reset! empty-a false)
    (println "put: i have reset empty to FALSE")
    (reset! message-global message)
    (println "put: i have reset message global to:" message)
    (println "put: notifying all, released lock")
    (.notifyAll lock)))

(defn producer [lock]
  (reify Runnable
    (run [_]
      (let [important-info ["Mares eat oats",
                            "Does eat oats",
                            "Little lambs eat ivy",
                            "A kid will eat ivy too"
                            "DONE!"]]
        (println "sending messages loop:")
        (doseq [m important-info]
          (println "message to put: " m)
          (put m lock)
          (try (Thread/sleep (* 1000 (rand-int 3)))
               (catch InterruptedException e (.getMessage e))))
        (put "DONE!" lock)))))

(defn consumer [lock]
  (reify Runnable
    (run [_]
         (loop [message (take-fn lock)]
           (when (not= message "DONE!")
             (printf "MESSAGE RECEIVED: %s%n" @message-global)
             (try (Thread/sleep (* 1000 (rand-int 3)))
                  (catch InterruptedException e (.getMessage e)))
             (recur (take-fn lock)))))))

(defn producer-consumer [lock]
  (let [t1 (Thread. (producer lock))
        t2 (Thread. (consumer lock))]
    (println "Start")
    (.start t1)
    (.start t2)
    (.join t1)
    (.join t2)
    (println "Stop")))


(comment 
  (producer-consumer a)
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