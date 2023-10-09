(ns basic.reading-writing-creating-files
  (:import java.nio.file.Paths
           java.nio.file.Files
           java.nio.file.OpenOption
           java.nio.file.StandardOpenOption
           java.nio.charset.Charset
           java.io.IOException
           java.io.BufferedReader
           java.io.InputStreamReader
           java.nio.ByteBuffer
           java.nio.file.attribute.PosixFilePermissions
           java.nio.file.FileAlreadyExistsException
           java.nio.channels.FileChannel))

(defn reading-all-bites-or-lines-from-a-file [file]
  (let [string-more (into-array String [])
        path (Paths/get file string-more)
        new-path (Paths/get "xyz.txt" string-more)
        file-array (Files/readAllBytes path)
        opts (into-array OpenOption [StandardOpenOption/WRITE StandardOpenOption/CREATE StandardOpenOption/APPEND])]
    (Files/write new-path file-array opts)))

;; (reading-all-bites-or-lines-from-a-file "test.txt")

(defn reading-file-buffered-stream-example [path]
  (let [string-more (into-array String [])
        file (Paths/get path string-more)
        charset (Charset/forName "US-ASCII")]
    (try (let [reader (Files/newBufferedReader file charset)]
           (loop [line (.readLine reader)]
             (when line
               (println line)
               (recur (.readLine reader)))))
         (catch IOException e (printf "IOException: %s%n " e)))))

;; (reading-file-buffered-stream-example "xyz.txt")

(defn writing-file-buffered-stream-example [path]
  (let [string-more (into-array String [])
        file (Paths/get path string-more)
        s "into-the-night.txt"
        charset (Charset/forName "US-ASCII")
        opts (into-array OpenOption [StandardOpenOption/WRITE StandardOpenOption/CREATE StandardOpenOption/APPEND])]
    (with-open [writer (Files/newBufferedWriter file charset opts)]
      (.write writer s 0 (count s)))))

;; (writing-file-buffered-stream-example "ala.txt")

(defn reading-file-using-stream-io [path]
  (let [string-more (into-array String [])
        file (Paths/get path string-more)
        opts (into-array OpenOption [])]
    (with-open [in (Files/newInputStream file opts)
                reader (BufferedReader. (InputStreamReader. in))]
      (loop [line (.readLine reader)]
        (when line
          (println line)
          (recur (.readLine reader)))))))

;; (reading-file-using-stream-io "ala.txt")

(defn read-file [path]
  (let [string-more (into-array String [])
        file (Paths/get path string-more)
        opts (into-array OpenOption [StandardOpenOption/READ StandardOpenOption/WRITE])]
    (with-open [sbc (Files/newByteChannel file opts)]
      (let [BUFFER_CAPACITY 10
            buf (ByteBuffer/allocate BUFFER_CAPACITY)
            encoding (System/getProperty "file.encoding")]
        (when (> (.read sbc buf) 0)
          (.flip buf)
          (println (.decode (Charset/forName encoding) buf))
          (.clear buf))))))

;; (read-file "x.txt")

(defn log-file-permissions-test []
  (let [string-more (into-array String [])
        file (Paths/get "./permissions.log" string-more)
        options (hash-set StandardOpenOption/APPEND
                          StandardOpenOption/CREATE)
        perms (PosixFilePermissions/fromString "rw-r-----")
        attr (into-array [(PosixFilePermissions/asFileAttribute perms)])
        s "Hello World! "
        data (.getBytes s)
        bb (ByteBuffer/wrap data)]
    (try (let [sbc (Files/newByteChannel file options attr)]
           (.write sbc bb))
         (catch IOException x (println "Exception THROWN: " x)))))

;; (log-file-permissions-test)

(defn create-file [path]
  (let [string-more (into-array String [])
        file (Paths/get path string-more)
        perms (PosixFilePermissions/fromString "rw-r-----")
        attr (into-array [(PosixFilePermissions/asFileAttribute perms)])]
    (try (Files/createFile file attr)
         (catch FileAlreadyExistsException _ (printf "File named %s already exists%n" file))
         (catch IOException e (printf "createFile error: %s%n" e)))))

;;(create-file "alacazam.log")

(defn create-temporary-file []
  (try (let [perms (PosixFilePermissions/fromString "rw-r-----")
             attr (into-array [(PosixFilePermissions/asFileAttribute perms)])
             temp-file (Files/createTempFile nil ".my-app" attr)]
         (printf "The temporary file has been created %s%n" temp-file))
       (catch IOException e (printf "IOException: %s%n " e))))

;;(create-temporary-file)

(defn random-access-files [path]
  (let [string-more (into-array String [])
        file (Paths/get path string-more)
        s "I was here!\n"
        data (.getBytes s)
        out (ByteBuffer/wrap data)
        copy (ByteBuffer/allocate 12)]
    (try (let [fc (FileChannel/open file (into-array [StandardOpenOption/READ StandardOpenOption/WRITE]))]
           (loop [nread 0]
             (when (and (not= nread -1) (.hasRemaining copy))
               (recur (.read fc copy))))
           (.position fc 0)
           (when (.hasRemaining out)
             (.write fc out))
           (.rewind out)
           (let [lenght (.size fc)]
             (.position fc (- lenght 1))
             (.flip copy)
             (while (.hasRemaining copy)
               (.write fc copy))
             (while (.hasRemaining out)
               (.write fc out))))
         (catch IOException e (println "I/O Exception: " e)))))

;; (random-access-files "x.txt") 





