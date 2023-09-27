(ns basic.file-operations
  (:import java.nio.file.Files java.nio.file.Paths java.nio.file.StandardOpenOption java.nio.file.OpenOption
           java.nio.charset.Charset
           java.io.IOException
           java.nio.file.StandardCopyOption))

(defn try-with-resources []
  (let [string-more (into-array String [])
        charset (Charset/forName "US-ASCII")
        s " autumn"
        file (Paths/get "x.txt" string-more)
        opts (into-array OpenOption [StandardOpenOption/APPEND])]
    (with-open [writer (Files/newBufferedWriter file charset opts)]
               (println (.write writer s 0 (.length s))))))

;;(try-with-resources)

(defn varargs-example []
  (let [string-more (into-array String [])
        source (Paths/get "from/from.txt" string-more)
        target (Paths/get "to/abc.txt" string-more )
        move-opts (into-array StandardCopyOption [StandardCopyOption/ATOMIC_MOVE])]
    (Files/move source target move-opts)))

;;(varargs-example)

(comment 
  ;; (catch IOException e (printf "IOException %s%n " e))
  0)