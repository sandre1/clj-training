(ns basic.io-nio2
  (:import java.nio.file.Paths
           java.nio.file.FileSystems))



(comment
  (-> (java.nio.file.FileSystems/getDefault)
      (.getPath "/users/sally"))
  (.getPath (FileSystems/getDefault) (str "/users/nas"))

  (let [path (Paths/get (System/getProperty "user.dir"))]
    (printf "tostring: %s%n" (.toString path)))
  0
  )