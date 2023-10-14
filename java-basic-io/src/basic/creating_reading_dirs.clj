(ns basic.creating-reading-dirs
  (:import java.nio.file.FileSystems
           java.nio.file.Paths
           java.nio.file.Files
           java.nio.file.attribute.FileAttribute
           java.nio.file.attribute.PosixFilePermissions
           java.io.IOException
           java.nio.file.DirectoryIteratorException
           java.nio.file.DirectoryStream))

(defn listing-file-sys-root-dirs []
  (let [dirs (-> (FileSystems/getDefault)
                 .getRootDirectories)]
    (for [dir-name dirs]
      (println "dir: " dir-name))))

;; (listing-file-sys-root-dirs)

(defn creating-dir [p]
  (let [string-more (into-array String [])
        path (Paths/get p string-more)
        perms (PosixFilePermissions/fromString "rwxr-x---")
        attr (PosixFilePermissions/asFileAttribute perms)
        attrs (into-array FileAttribute [attr])]
    (Files/createDirectories path attrs)))

;; (creating-dir "fruits/orangess/caliber/harvested")

(defn listing-directory-resources [p]
  (let [string-more (into-array String [])
        dir (Paths/get p string-more)]
    (with-open [stream (Files/newDirectoryStream dir)]
      (try (doseq [file stream]
             (println (.getFileName file)))
           (catch IOException e (println "IOException ERRRROR: " e))
           (catch DirectoryIteratorException e (println "DirectoryIteratorException ERRRROR: " e))))))

;;(listing-directory-resources "fruits")

(defn filtering-dir-using-globbing [p]
  (let [string-more (into-array String [])
        dir (Paths/get p string-more)]
    (with-open [stream (Files/newDirectoryStream dir "*.{clj}")]
      (try (doseq [file stream]
             (println (.getFileName file)))
           (catch IOException e (println "IOException ERRRROR: " e))
           (catch DirectoryIteratorException e (println "DirectoryIteratorException ERRRROR: " e))))))

;;(filtering-dir-using-globbing "fruits")

#_(defn simple-formatter
    "Clojure bridge for java.util.logging.SimpleFormatter.
   Can register a clojure fn as a logger formatter.

   * format-fn - clojure fn that receives the record to send to logging."
    (^SimpleFormatter [format-fn]
     (proxy [SimpleFormatter] []
       (format [record]
         (format-fn record)))))


(defn directories-only-filter [p]
  (let [string-more (into-array String [])
        dir (Paths/get p string-more)
        filter (proxy [DirectoryStream/Filter] []
                 (accept [^Path file]
                   (try
                     (Files/isDirectory file)
                     (catch IOException x
                               ;; Failed to determine if it's a directory.
                       (println x)
                       false))))]
    (with-open [stream (Files/newDirectoryStream dir)]
      (try (doseq [file stream]
             (println (filter file)))
           (catch IOException e (println "IOException ERRRROR: " e))
           (catch DirectoryIteratorException e (println "DirectoryIteratorException ERRRROR: " e))))))

;; TODO: read proxy and refactor the fn



