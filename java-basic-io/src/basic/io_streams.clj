(ns basic.io-streams
  (:require [clojure.math :as math])
  (:import
   (java.io
    FileInputStream
    FileOutputStream
    FileReader
    FileWriter
    BufferedReader
    BufferedWriter
    Console
    BufferedOutputStream
    BufferedInputStream
    DataInputStream
    DataOutputStream
    ObjectInputStream
    ObjectOutputStream
    EOFException)
   (java.util Scanner Locale Arrays Calendar)))

(defn copy-bytes []
  (let [in (FileInputStream. "files/test.txt")
        out (FileOutputStream. "files/outagain.txt")]
    (try
      (loop [c (.read in)]
        (println c)
        (if (not= c -1)
          (do (.write out c)
              (recur (.read in)))
          nil))
      (finally
        (if (not= in nil) (.close in) nil)
        (if (not= out nil) (.close out) nil)))))


(defn copy-characters []
  (let [in-stream (FileReader. "files/test.txt")
        out-stream (FileWriter. "files/output-char-stream.txt")]
    (try
      (loop [c (.read in-stream)]
        (println c)
        (if (not= c -1)
          (do (.write out-stream c)
              (recur (.read in-stream))) nil))
      (finally
        (if (not= in-stream nil) (.close in-stream) nil)
        (if (not= out-stream nil) (.close out-stream) nil)))))


(defn copy-characters-with-buffer-stream []
  (with-open [input-stream (BufferedReader. (FileReader. "files/test-buffer.txt"))
              output-stream (BufferedWriter. (FileWriter. "files/output-test-buffer.txt"))]
    (loop [c (.read input-stream)]
      (println c)
      (if (not= c -1)
        (do (.write output-stream c)
            (recur (.read input-stream))) nil))))


(defn break-input->tokens []
  (with-open [s (Scanner. (BufferedReader. (FileReader. "files/xanadu.txt")))]
    (while (.hasNext s)
      (println (.next (.useDelimiter s ",\\s*"))))))


(defn translating-individual-tokens []
  (let [thesum (atom 0.0)]
    (with-open [s (doto (Scanner.
                         (BufferedReader.
                          (FileReader. "files/usnumbers.txt")))
                    (.useLocale Locale/US))]
      (while (.hasNext s)
        (if (.hasNextDouble s)
          (swap! thesum + (.nextDouble s))
          (.next s))))
    (println @thesum)))


(defn Root [n]
  (let [r (math/sqrt n)
        print-out #(.print System/out %)]
    (print-out "The square root of ")
    (print-out n)
    (print-out " is ")
    (print-out r)
    (print-out ".")
    (.println System/out (str "The square root of " n " is " r "."))))


#_(defn Root2 [i]
  (let [r (math/sqrt i)]
    (.printf System/out "The square root of %d is %f.%n" i r)))


(def user (atom {:id 222 
                 :pass 12345}))

(defn verify [login password]
  (let [u @user]
    (and (= (:id u) 222)
         (= (:pass u) 12345))))

(defn change [login password]
  (swap! user assoc :pass password)
  (println "Change called with login: " login " and password: " password))

(defn password [&args]
  (let [c (System/console)]
    (when (= c nil) (do
                      (println "No console.")
                      (System/exit 1)))
    (let [login (.readLine c "Enter your login: " nil)
          oldPassword (.readPassword c "Enter your old password: " nil)]
      (when (verify login oldPassword)
        (let [newPassword1 (.readPassword c "Enter your new password: " nil)
              newPassword2 (.readPassword c "Enter new password again: " nil)
              noMatch (not (Arrays/equals newPassword1 newPassword2))]
          (while noMatch
            (if noMatch
              (.format c "Passwords don't match. Try again.%n" nil)
              (change login newPassword1))))))))



(defn print-login [&args]
  (let [c (System/console)
        login (.readLine c "Enter login: " nil)]
    (println "You have entered: " login)))


(defn data-streams []
  (let [data-file "invoicedata"
        prices [19.99, 9.99, 15.99, 3.99, 4.99]
        units [12 8 13 29 50]
        descs ["Java T-shirt",
               "Java Mug",
               "Duke Juggling Dolls",
               "Java Pin",
               "Java Key Chain"]
        total (atom 0.0)]
    (with-open [out (DataOutputStream. (BufferedOutputStream. (FileOutputStream. data-file)))]
      (loop [i 0]
        (when (< i (count prices))
          (let [price (nth prices i)
                unit (nth units i)
                desc (nth descs i)]
            (.writeDouble out price)
            (.writeInt out unit)
            (.writeUTF out desc)
            (recur (inc i))))))
    (with-open [in (DataInputStream. (BufferedInputStream. (FileInputStream. data-file)))]
      (try (while true
             (let [price (.readDouble in)
                   unit (.readInt in)
                   desc (.readUTF in)]
               (printf "You ordered %d units of %s at $%.2f%n" unit desc price)
               (swap! total + (* unit price))))
           (catch EOFException _ (printf "For a TOTAL of: $%.2f%n" @total)))
      )))


(defn object-streams [] ;;TODO : de rescris codul ?!
  (let [data-file "invoice-data"
        prices [19.99 9.99 15.99 3.99 4.99]
        units [12 8 13 29 50]
        descs ["Java T-shirt",
               "Java Mug",
               "Duke Juggling Dolls",
               "Java Pin",
               "Java Key Chain"]
        total (atom 0)
        now (Calendar/getInstance)]
    (with-open [out (ObjectOutputStream. (BufferedOutputStream. (FileOutputStream. data-file)))]
      (.writeObject out now)
      (loop [i 0]
        (when (< i (count prices))
          (let [price (nth prices i)
                unit (nth units i)
                desc (nth descs i)]
            (.writeObject out price)
            (.writeInt out unit)
            (.writeUTF out desc)
            (recur (inc i))))))
    (with-open [in (ObjectInputStream. (BufferedInputStream. (FileInputStream. data-file)))]
      (let [date (cast Calendar (.readObject in))]
        (printf "On %tA, %tB %<te, %<tY:%n" date nil)
        (try (while true
               (let [price (cast BigDecimal (.readObject in))
                     unit (.readInt in)
                     desc (.readUTF in)]
                 (printf "You ordered %d units of %s at $%.2f%n" unit desc price)
                 (swap! total + (* unit price))))
             (catch EOFException _ (printf "For a TOTAL of: $%.2f%n" @total)))))))

(object-streams)


(comment
  (let [input "one two three"
        s (Scanner. input)]
    (println (-> (.useDelimiter s ",\\s*")
                 (.next))))
  (.read (FileInputStream. "empty.txt"))

  (.write (FileOutputStream. "x.txt") 101)
  (.print System/out "hello")
  (printf "You ordered %s" 122)
  (let [a1 (to-array "nas")
        h (Arrays/equals a1 a1)]
    h)
  (let [c (System/console)
        login (.readLine c "Enter login")]
    login)
  (let [u (atom {:name "nas"
                 :pass "parolica123"})
        user @u]
    (Arrays/equals "nas" (:name user)))
  (let [prices [19.99, 9.99, 15.99, 3.99, 4.99]]
    (loop [x 0]
      (when (< x (count prices))
        (let [num (nth prices x)]
          (if (< num 10)
            (do (println num)
                (recur (inc x)))
            (recur (inc x)))))))
  
(printf "On %tA, %tb %<te, %<tY:%n" (cast Calendar (.readObject in)))
  
  0
  )