(ns basic.io-nio2
  (:import java.nio.file.Paths
           java.nio.file.FileSystems
           java.nio.file.NoSuchFileException
           java.nio.file.LinkOption
           java.io.IOException
           java.net.URI))

(defn paths-example
  [& args]
  (let [string-more (into-array String ["put" "me" "on" "the" "path"])
        p1 (Paths/get "/new/foo" string-more)
        p2 (Paths/get (first args) string-more)
        p3 (Paths/get (URI/create "file:///Users/joe/FileTest.java"))
        p4 (-> (FileSystems/getDefault)
               (.getPath "/users/sally" string-more))
        p5 (Paths/get (System/getProperty "user.home") (into-array String ["logs" "foo.log"]))]
    p2))

;; (paths-example "abc")

(defn path-info [& args]
  (let [string-more (into-array String [])
        p (Paths/get "/home/nas/foo" string-more)]
    (printf "toString: %s%n" (.toString p))
    (printf "getFileName: %s%n" (.getFileName p))
    (printf "getName: %s%n" (.getName p 0))
    (printf "getNameCount: %s%n" (.getNameCount p))
    (printf "subpath(0,2): %s%n" (.subpath p 0 3))
    (printf "getParent %s%n" (.getParent p))
    (printf "getRoot %s%n" (.getRoot p))))

;; (path-info)

(defn converting-a-path []
  (let [string-more (into-array String [])
        p1 (Paths/get "/home/logfile" string-more)]
    (printf "%s%n" (.toUri p1))))

;; (converting-a-path)

(defn file-test [& args]
  (let [string-more (into-array String [])]
    (when (< (count args) 1)
      (do (println "usage: (file-test file)")
          (System/exit -1)))
    (let [input-path (Paths/get (first args) string-more)
          full-path (.toAbsolutePath input-path)]
      full-path)))

;; (file-test "/home/nas/file")

(defn to-real-path [& args]
  (let [string-more (into-array String [])
        input-path (Paths/get (first args) string-more)]
    (try (let [real-path (.toRealPath input-path (into-array LinkOption [(LinkOption/NOFOLLOW_LINKS)]))]
           real-path)
         (catch IOException e (printf "%s: no such file or directory%n" (.getMessage e)))
         (catch IOException x (printf System/err "GGGG %s%n " x)))))

;; (to-real-path "x.txt")

(defn joining-two-paths [args]
  (let [string-more (into-array String [])
        p1 (Paths/get args string-more)
        passed-path "bar"
        resolved-path (.resolve p1 passed-path)]
    (printf  "%s%n" resolved-path)))

;; (joining-two-paths "/home/nas/foo")

(defn one->two-paths [& args]
  (let [string-more (into-array String [])
        p1 (Paths/get "home" string-more)
        p2 (Paths/get "sally" string-more)
        p3 (Paths/get "home/sally/bar" string-more)
        p1-to-p2 (.relativize p1 p2)
        p1-to-p3 (.relativize p1 p3)
        p3-to-p1 (.relativize p3 p1)]
    p3-to-p1))

;; (one->two-paths) ;; there is a Copy example to try

(defn comparing-two-paths [args]
  (let [string-more (into-array String [])
        p1 (Paths/get args string-more)
        p2 (Paths/get "/home/adi/documents/images" string-more)
        beginning (Paths/get "/home" string-more)
        ending (Paths/get "foo" string-more)]
    (if (.equals p1 p2)
      (println "The paths are eqal")
      (println "The paths are not equal"))
    (if (.startsWith p1 beginning)
      (println "//path begins with /home")
      (println "does not start with home"))
    (if (.endsWith p1 ending)
      (println "//path ends with foo")
      (println "does not ends with foo"))))

;; (comparing-two-paths "/home/adi/documents/foo")

(defn iterator-example []
  (let [string-more (into-array String [])
        path (Paths/get "/home/nas/proiecte/clj-training/basic-io" string-more)]
    (doseq [name path]
      (println name))))

;; (iterator-example)

(defn compare-paths [args]
  (let [string-more (into-array String [])
        p1  (Paths/get "/home/nas" string-more)
        p2 (Paths/get args string-more)]
    (.compareTo p1 p2)))

;; (compare-paths "/home/nas")


(comment
  
  (Paths/get (URI/create "file:///Users/joe/FileTest.java"))
  (java.nio.file.Paths/get "E:/path/to/random/data/" (into-array String []))

  (-> (FileSystems/getDefault)
      (.getPath "/users/sally" (into-array String [])))
  (Paths/get "my-path" (into-array String [""]))

  (.toRealPath (Paths/get "/home/nas" (into-array String [])))

  (type (into-array LinkOption [(LinkOption/NOFOLLOW_LINKS)]))

  



  
  )