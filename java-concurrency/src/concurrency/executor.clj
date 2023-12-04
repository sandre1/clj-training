(ns concurrency.executor
  (:import java.util.concurrent.Executor
           java.lang.Thread))

(defn create-thread-executor []
  (reify
    java.util.concurrent.Executor
    (execute [_ task]
      (let [f #(try
                 (task)
                 (catch Throwable e
                   (println "something went wgrong!")))]
        (Thread/sleep 5000) 
        (doto
         
         (Thread. f)
          
          (.start))))))

(defn demo-task []
  (println "hi!")
  1243)

;; NOTE on callable and runnable


(def my-executor (create-thread-executor))

(.execute my-executor demo-task)

(comment 


  0)




