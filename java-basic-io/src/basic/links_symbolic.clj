(ns basic.links-symbolic
  (:import java.nio.file.Paths
           java.nio.file.Files
           java.io.IOException
           java.lang.UnsupportedOperationException
           java.nio.file.attribute.FileAttribute))

(defn create-symbolic-link [new target]
  (let [string-more (into-array String [])
        new-link (Paths/get new string-more)
        target-link (Paths/get target string-more)
        attr (into-array FileAttribute [])]
    (try (Files/createSymbolicLink new-link target-link attr)
         (catch IOException e (println e))
         (catch UnsupportedOperationException e (println e)))))

;; (create-symbolic-link "ccc.txt" "symbolic-folder/b.txt")

(defn create-hard-link [new existing-file]
  (let [string-more (into-array String [])
        new-link (Paths/get new string-more)
        existing-f (Paths/get existing-file string-more)]
    (try (Files/createLink new-link existing-f)
         (catch IOException e (println e))
         (catch UnsupportedOperationException e (println e)))))

;;(create-hard-link "new-link.txt" "fruits/avocados/recipe.txt")

(defn is-symbolic-link [file]
  (let [string-more (into-array String [])
        path (Paths/get file string-more)
        is-symbolic-link? (Files/isSymbolicLink path)]
    is-symbolic-link?))

;; (is-symbolic-link "ccc.txt")

(defn find-target-of-a-link [file]
  (let [string-more (into-array String [])
        link (Paths/get file string-more)
        read-link (Files/readSymbolicLink link)]
    (try (printf "Target of link '%s' is '%s'%n" link read-link)
         (catch IOException e (println e)))))

;; (find-target-of-a-link "ccc.txt")


