(ns concurrency.guarded-blocks
  (:import java.lang.Thread
           java.lang.InterruptedException))

(def joy (atom false))

(defn guarded-joy []
  (locking joy
    (println "started guarded boy")
    (while (false? @joy)
      (try (.wait (Object.))
           (catch InterruptedException e)))
    (println "Joy and efficiency have been achieved!")))



(defn notify-joy []
  (reset! joy true)
  (.notifyAll (Object.))
  (println "i have notified all"))

(defn -main [& args]
  (future (guarded-joy))
  (Thread/sleep 5000)
  (future (notify-joy)))

(-main)

(comment 
  (let [a (atom false)]
    (reset! a true)
    @a)
  0)