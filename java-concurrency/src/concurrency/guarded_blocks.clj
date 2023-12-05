(ns concurrency.guarded-blocks
  (:import java.lang.Thread
           java.lang.InterruptedException))

(def joy (atom false))

(def o (Object.))

(defn guarded-joy []
  (locking o
    (println "started <guarded-joy>")
    (while (false? @joy)
      (try (.wait o)
           (catch InterruptedException e)))
    (println "Joy and efficiency have been achieved!")))



(defn notify-joy []
  (locking o
   (swap! joy not)
   (.notifyAll o)
    (println "i have notified all")))

(defn -main [& args]
  (future (guarded-joy))
  (Thread/sleep 5000)
  (future (notify-joy)))



(comment 
  (-main)
  (let [a (atom true)]
    (swap! a not)
    @a)
  (false? false)
  0)