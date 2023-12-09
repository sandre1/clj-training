(ns concurrency.lock-objects
  (:import java.util.concurrent.locks.Lock
           java.util.concurrent.locks.ReentrantLock
           java.lang.Thread))

(defprotocol Person
  (lock [this])
  (getName [this])
  (impending-bow [this bower])
  (bow [this bower])
  (bow-back [this bower]))


(defrecord Friend [name]
  Person
  (lock [this] (ReentrantLock.))
  (getName [this]
           (:name this))
  (impending-bow [this bower]
                 (let [status (atom {:my-lock false
                                     :your-lock false})]
                   (try (swap! status assoc :my-lock (.tryLock lock))
                        (swap! status assoc :your-lock (.tryLock (lock bower)))
                        (finally
                          (when (not (and (:my-lock @status) (:your-lock @status)))
                            (when (:my-lock @status)
                              (.unlock lock))
                            (when (:your-lock @status)
                              (.unlock (lock bower))))))
                   (and (:my-lock @status) (:your-lock @status))))
  (bow-back [this bower]
            (printf "%s: %s has bowed back to me!%n" (:name this) (getName bower)))
  (bow [this bower]
       (if (impending-bow this bower)
         (try
           (printf "%s: %s has bowed to me!%n" (:name this) (getName bower))
           (bow-back this bower)
           (finally (.unlock lock)
                    (.unlock (lock bower))))
         (printf "%s: %s started to bow to me, 
                  but saw that I was already bowing
                  to him.%n" (:name this) (getName bower)))))

(defprotocol BowL
  (bow-loop [this bower bowee])
  (run [this Bwr Bwe]))

(defrecord BowLoop [bower bowee]
  BowL
  (bow-loop [this Bwr Bwe]
    (reify Runnable
      (run [_]
        (let [this-ref this
              bower Bwr
              bowee Bwe]
          (loop []
            (try (Thread/sleep (long (rand-int 10)))
                 (catch InterruptedException e (.getMessage e)))
            (bow bowee bower)
            (recur)))))))

(defn -main [& args]
  (let [alphonse (->Friend "Alphonse")
        gaston (->Friend "Gaston")
        t1 (Thread. (proxy [Thread] [] (run [] 
                                         (let [bow-loop (->BowLoop alphonse gaston)]
                                           (.start (Thread. bow-loop))
                                           ))))
        t2 (Thread. (proxy [Thread] [] (run [] (let [bow-loop (->BowLoop gaston alphonse)]
                                                 (.start (Thread. bow-loop))))))]
    t1
    t2
    (println "stop")))

(-main)



