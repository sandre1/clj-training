(ns concurrency.interrupt
   (:import java.lang.Thread
           java.lang.InterruptedException))

(defn heavy-crunch [input]
  (when (= input nil)
    (.interrupt (Thread/currentThread))))

(defn sleep-messages []
  (let [important-info ["Mares eat oats",
                        "Does eat oats",
                        "Little lambs eat ivy",
                        "A kid will eat ivy too"]]
    (loop [m important-info]
      (try (Thread/sleep 1000)
           (heavy-crunch (first m))
           (when (Thread/interrupted)
             (println "Thread interrupted.\n
                       No more Heavy Crunch")
             (System/exit 0))
           (println (first m))
           (catch InterruptedException e
             (println "RETURN!")))
      (recur (rest m)))))

(defn -main [& args]
  (try
    (sleep-messages)
    (catch InterruptedException e
      (println "Thread interupted:" (.getMessage e)))))

;; TODO fix this fn

(comment
  (-main)
  (heavy-crunch "abc")
  )

