(ns concurrency.deadlock
  (:import java.lang.Thread))

(defprotocol Friend
  (get-name [this])
  (bow [this bower lock])
  (bow-back [this bower lock]))



(defrecord FriendImplement [name]
  Friend
  (get-name [_] name)
  (bow [this bower lock]
       (locking lock
         (printf "%s: %s has bowed to me!%n" (get-name this) (get-name bower))
         (.start (Thread. #(bow-back this bower lock)))))
  (bow-back [this bower lock]
    (locking lock
      (printf "%s: %s has bowed back to me!" (get-name this) (get-name bower)))))


(defn -main [& args]
  (let [alphonse (->FriendImplement "Alphonse")
        gaston (->FriendImplement "Gaston")
        lock (Object.)]
    (.start (Thread. #(bow alphonse gaston lock)))
    (.start (Thread. #(bow gaston alphonse lock)))))


(comment 
 (-main) 
  0)