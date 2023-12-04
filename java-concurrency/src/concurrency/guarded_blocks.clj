(ns concurrency.guarded-blocks
  (:import java.lang.Thread
           java.lang.InterruptedException))

(def joy (atom false))

(def o (Object.))

(defn guarded-joy []
  (locking o
    (println "started guarded joy")
    (while (false? @joy)
      (try (.wait o)
           (catch InterruptedException e))
      (println "Joy and efficiency have been achieved!"))))



(defn notify-joy []
  (locking o
   (reset! joy true)
   (.notifyAll o)
    (println "i have notified all")))

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