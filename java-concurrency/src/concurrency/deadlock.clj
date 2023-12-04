(ns concurrency.deadlock
  (:import java.lang.Thread))

(defprotocol Friend
  (get-name [this])
  (bow [this bower])
  (bow-back [this bower]))

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
    (.start (Thread. #(bow alphonse gaston)))
    (.start (Thread. #(bow gaston alphonse)))))


(comment 
 (-main) 
  0)