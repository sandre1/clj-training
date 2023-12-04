(ns concurrency.prod-cons)

(defn create-drop []
  (let [message (atom "")
        empty (atom true)
        my-lock (Object.)]
    {:message message
     :empty empty
     :my-lock my-lock}))

(defn takee [drop]
  (locking (:my-lock drop)
    (while @(:empty drop)
      (try (.wait (:my-lock drop))
           (catch InterruptedException e))))
  (let [msg @(:message drop)]
    (reset! (:empty drop) true)
    (.notifyAll (:my-lock drop))
    msg))

(defn put [drop message]
  (locking (:my-lock drop)
    (while (not @(:empty drop))
      (try (.wait (:my-lock drop))
           (catch InterruptedException e))))
  (reset! (:empty drop) false)
  (reset! (:message drop) message)
  (.notifyAll (:my-lock drop)))

(comment
  (def droppp (create-drop))


  (.start (Thread. #(put droppp "Hello, world!")))
  

  (.start (Thread. #(println (takee droppp))))
  0)