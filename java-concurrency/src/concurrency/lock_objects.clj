(ns concurrency.lock-objects
  (:import java.util.concurrent.locks.Lock
           java.util.concurrent.locks.ReentrantLock
           java.lang.Thread))

(def global-lock (ReentrantLock.))

(defprotocol Person
  (lock [this])
  (getName [this])
  (impendingBow [this Friend-bower])
  (bow [this Friend-bower])
  (bow-back [this Friend-bower]))

(defrecord Friend [name lock]
  Person
  (lock [this] (:lock this))
  (getName [this] 
           (:name this))
  (impendingBow
   [this Friend-bower]
   (let [bower-lock (lock Friend-bower)
         a (atom {:my-lock false
                  :your-lock false})
         my-lock-status (.tryLock global-lock)
         bower-lock-status (.tryLock bower-lock)]
     (try (swap! a assoc :my-lock my-lock-status)
          (swap! a assoc :your-lock bower-lock-status)
          (finally
            (let [{:keys [my-lock your-lock]} @a]
              (when (not (and my-lock your-lock))
                (when my-lock
                  (.unlock my-lock))
                (when your-lock (.unlock bower-lock))))))
     (let [{:keys [my-lock your-lock]} @a]
       (and my-lock your-lock))))
  (bow-back [this Friend-bower]
            (printf "%s: %s has bowed back to me!%n", 
                    (:name this) (getName Friend-bower)))
  (bow
   [this Friend-bower]
   (if (impendingBow this Friend-bower)
     (try (printf "%s: %s has bowed to me!%n"
                  (:name this) (getName Friend-bower))
          (bow-back this Friend-bower)
          (finally (.unlock global-lock)
                   (.unlock (lock Friend-bower))))
     (printf "%s : %s started to bow to me, but saw that i was already
              bowing to him. %n" (:name this) (getName Friend-bower)))))

(defrecord BowLoop [Friend-bower Friend-bowee]
 Runnable
  (run [_]
       (loop []
         (try (Thread/sleep (long (rand-int 10)))
              (catch InterruptedException e (.getMessage e)))
         (bow Friend-bowee Friend-bower)
         (recur))))

(defn -main [& args]
  (let [alphonse (->Friend "Alphonse" (ReentrantLock.))
        gaston (->Friend "Gaston" (ReentrantLock.))
        t1 (Thread. (->BowLoop alphonse gaston))
        t2 (Thread. (->BowLoop alphonse gaston))]
    (.start t1)
    (.start t2)
    (.join t1)
    (.join t2)))





(comment 
  (.tryLock (ReentrantLock.))
  (-main)
  (let [a (atom {:my-lock true :your-lock false})]
    (swap! a assoc :my-lock false :your-lock true))
  (defprotocol Perso
    (lock [this]))
  
  (defrecord Fr [name ^java.util.concurrent.locks.Lock lock]
    Person
    (lock [this] lock))
  
(let [some-lock (Lock.)
      adi (->Fr "Adi" some-lock)]
  adi)
  
  (let [p (->Fr "nas" (ReentrantLock.))
        myLock (:lock p)
        yourLock  (lock p)]
    (println myLock)
    yourLock)
  
  0)