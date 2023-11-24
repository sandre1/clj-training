(ns basic.finding-files
  (:import java.nio.file.Paths
           java.nio.file.FileSystems))

(defn finding-files [p]
  (let [string-more (into-array String [])
        dir (Paths/get p string-more)
        pattern "*.clj"
        matcher ]))