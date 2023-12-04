(ns concurrency.producer-consumer
  (:import java.lang.Thread
           java.lang.InterruptedException))


(def lock (Object.))

(defn put-fn [status message lock]
  (locking lock 
    (when (false? @status)
                     (try (.wait lock)
                          (catch InterruptedException)))
           (swap! status not)
           (swap! message assoc :message message)
           (.notifyAll lock)))

(defn dropp []
  (let [m (atom {:message ""})
        empty-m (atom true)
        my-lock (.Object)
        take (locking my-lock
               (while @empty-m
                 (try (.wait my-lock)
                      (catch InterruptedException)))
               (.notifyAll my-lock)
               (println @m))
        put (put-fn empty-m m my-lock)]))


(comment 

(let [a {:message ""}]
  (assoc a :message "asda"))
  0)