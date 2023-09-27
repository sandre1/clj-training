(ns basic.try-with-resources
  (:import java.io.FileReader
           java.nio.file.Paths
           java.nio.file.Files
           java.nio.charset.StandardCharsets
           java.io.BufferedReader
           java.io.FileNotFoundException
           java.util.zip.ZipFile
           java.util.Enumeration
           java.nio.file.OpenOption
           java.nio.file.StandardOpenOption))

(defn read-first-line-from-file [path]
  (try (with-open [fr (FileReader. path)
                   br (BufferedReader. fr)]
         (.readLine br))
       (catch FileNotFoundException e (str "Fisierul nu exista: " (.getMessage e)))))

;; (read-first-line-from-file "a.txt")

(defn write-to-file-zip-contents [zip-f-name out-f-name]
  (let [string-more (into-array String [])
        charset (StandardCharsets/US_ASCII)
        output-file-path (Paths/get out-f-name string-more)
        zf (ZipFile. zip-f-name)
        opts (into-array OpenOption [StandardOpenOption/CREATE StandardOpenOption/CREATE_NEW StandardOpenOption/WRITE])
        entries (.entries zf)]
    (with-open
     [writer (Files/newBufferedWriter output-file-path charset opts)]
      (while (.hasMoreElements entries)
        (let [new-line (System/getProperty "line.separator")
              zip-entry-name (.getName (.nextElement entries))
              zip-entry-name-with-newline (str zip-entry-name new-line)]
          (println zip-entry-name-with-newline)
          (.write writer zip-entry-name-with-newline 0 (.length zip-entry-name-with-newline)))))))

(write-to-file-zip-contents "test.zip" "test.txt")
