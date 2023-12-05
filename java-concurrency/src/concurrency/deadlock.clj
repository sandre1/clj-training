(ns concurrency.deadlock
  (:import java.lang.Thread))

(def lock1 (Object.))
(def lock2 (Object.))

(declare bow-back)

(defn bow
  [bower friend bower-lock friend-lock]
  (locking bower-lock
    (printf "%s: %s has bowed to me!%n" bower friend)
    (Thread/sleep 1000)
    (bow-back friend bower friend-lock)))

(defn bow-back 
  [friend bower lock]
  (locking lock
    (printf "%s: %s has bowed back to me!%n" friend bower)))

(comment 
(let [t1 (Thread. (reify Runnable
                   (run [_]
                     (bow "Alphonse" "Gaston" lock1 lock2))))
      t2 (Thread. (reify Runnable
                    (run [_]
                      (bow "Gaston" "Alphonse" lock2 lock1))))]
  (println "Start")
  (.start t1)
  (.start t2)
  (println "Done")) 
  0)