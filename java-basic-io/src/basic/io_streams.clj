(ns basic.io-streams
  (:require [clojure.math :as math])
  (:import
   (java.io
    FileInputStream
    FileOutputStream
    FileReader
    FileWriter
    BufferedReader
    BufferedWriter)
   (java.util Scanner Locale)))

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

(copy-bytes)

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

(copy-characters)

(defn copy-characters-with-buffer-stream []
  (with-open [input-stream (BufferedReader. (FileReader. "files/test-buffer.txt"))
              output-stream (BufferedWriter. (FileWriter. "files/output-test-buffer.txt"))]
    (loop [c (.read input-stream)]
      (println c)
      (if (not= c -1)
        (do (.write output-stream c)
            (recur (.read input-stream))) nil))))

(copy-characters-with-buffer-stream)

(defn break-input->tokens []
  (with-open [s (Scanner. (BufferedReader. (FileReader. "files/xanadu.txt")))]
    (while (.hasNext s)
      (println (.next (.useDelimiter s ",\\s*"))))))

(break-input->tokens)

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

(translating-individual-tokens)

(defn Root [n]
  (let [r (math/sqrt n)
        print-out #(.print System/out %)]
    (print-out "The square root of ")
    (print-out n)
    (print-out " is ")
    (print-out r)
    (print-out ".")
    (.println System/out (str "The square root of " n " is " r "."))))

(Root 5)


(comment
  (let [input "one two three"
        s (Scanner. input)]
    (println (-> (.useDelimiter s ",\\s*")
                  (.next)
                     )))
  (.read (FileInputStream. "empty.txt"))

  (.write (FileOutputStream. "x.txt") 101)
  (.print System/out "hello")
  0)