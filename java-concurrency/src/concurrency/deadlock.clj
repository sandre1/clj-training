(ns concurrency.deadlock
  (:import java.lang.Thread))

(defprotocol Friend
  (get-name [this])
  (bow [this bower])
  (bow-back [this bower]))

(def p (Object.))

(defrecord FriendImplement [name]
  Friend
  (get-name [_] name)
  (bow [this bower]
       (printf "%s: %s has bowed to me!%n" (get-name this) (get-name bower))
       (.start (Thread. #(bow-back this bower))))
  (bow-back [this bower]
    (printf "%s: %s has bowed back to me!" (get-name this) (get-name bower))))


(defn -main [& args]
  (let [alphonse (->FriendImplement "Alphonse")
        gaston (->FriendImplement "Gaston")]
    (future
      (locking p
        (.start (Thread. #(bow alphonse gaston)))))
    (future (locking p
              (.start (Thread. #(bow gaston alphonse)))))
    (println "abcd")))


(comment 
 (-main) 
  0)