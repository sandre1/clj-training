(ns concurrency.pausing-execution-sleep
  (:import java.lang.Thread
           java.lang.InterruptedException))

(defn sleep-messages []
  (let [important-info ["Mares eat oats",
                        "Does eat oats",
                        "Little lambs eat ivy",
                        "A kid will eat ivy too"]]
    (loop [m important-info]
      (Thread/sleep 4000)
      (println (first m))
      (recur (rest m)))))

(defn -main [& args]
  (try
    (sleep-messages)
    (catch InterruptedException e
      (println "Thread interupted:" (.getMessage e)))))

(comment
  (-main))


