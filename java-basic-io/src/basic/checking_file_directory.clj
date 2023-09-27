(ns basic.checking-file-directory
  (:import java.nio.file.Paths
           java.nio.file.Files))

(defn checking-file-accessibility [p]
  (let [string-more (into-array String [])
        file (Paths/get p string-more)
        regular-executable-file? (or (Files/isReadable file)
                                     (Files/isReadable file)
                                     (Files/isExecutable file))]
    regular-executable-file?))

;; (checking-file-accessibility "xt.txt")

(defn two-paths-locate-same-file? [path1 path2]
  (let [string-more (into-array String [])
        p1 (Paths/get path1 string-more)
        p2 (Paths/get path2 string-more)]
    (if (Files/isSameFile p1 p2)
      (println "The two paths indicate same file")
      (println "Different paths"))))

(two-paths-locate-same-file? "symbolic-folder/link-to-test.txt" "test.txt")