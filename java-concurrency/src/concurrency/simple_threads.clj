(ns concurrency.simple-threads
  (:import java.lang.Thread
           java.lang.InterruptedException
           java.lang.System))

(defn thread-message [message]
  (let [thread-name (.getName (Thread/currentThread))]
    (printf "%s : %s%n" thread-name message)))

(defn message-loop []
  (let [important-info ["Mares eat oats",
                        "Does eat oats",
                        "Little lambs eat ivy",
                        "A kid will eat ivy too"]]
    
    (try
      (doseq [info important-info]
        (Thread/sleep 4000)
        (println info)
        (thread-message info))
      (catch InterruptedException e (thread-message "i wasn't DONE!")))))

(defn -main [& args]
  (let [default-patience (* 1000 60 60)
        patience (if (seq args)
                   (try
                     (* (Long/parseLong (first args)) 1000)
                     (catch NumberFormatException _
                       (do (println "argument must be an integer")
                           (System/exit 1))))
                   default-patience)]
    (try
      (thread-message "Starting message-loop thread")
      (let [start-time (System/currentTimeMillis)
            t (Thread. message-loop)]
        (.start t)
        (thread-message "Waiting for message-loop thread to finish.")
        (loop []
          (when (.isAlive t)
            (thread-message "Still waiting...")
            (.join t 1000)
            (when true
              (thread-message "Tired of waiting!")
              (.interrupt t)
              (.join t))))
        (thread-message "Finally!"))
      (catch InterruptedException e
        (thread-message (str "Interrupted Exception in main: " (.getMessage e)))))))

(-main "20")


(comment 
  (let [m ["a" "b" "c" "d" "e"]]
    (loop [letter m]
      (when ( seq letter)
        (println (first letter))
        (recur (rest letter)))))

  (message-loop)
(let [t (Thread. message-loop)]
  (.start t))

  (println "between")

  0)