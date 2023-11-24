(ns basic.walking-file-tree
  (:import java.nio.file.SimpleFileVisitor
           java.nio.file.attribute.BasicFileAttributes
           java.nio.file.FileVisitResult
           java.nio.file.FileSystems
           java.nio.file.Paths))


#_(def print-files
  (proxy [SimpleFileVisitor] []
    (^FileVisitResult visitFile [file attr]
      (if (.isSymbolicLink attr)
        (printf "Symbolic link: %s " file)
        (if (.isRegularFile attr)
          (printf "Regular file: %s " file)
          (printf "Other: %s " file)))
      (println "(" (.size attr) " bytes)")
      FileVisitResult/CONTINUE)
    (^FileVisitResult postVisitDirectory [dir exc]
      (printf "Directory: %s%n" dir)
      FileVisitResult/CONTINUE)
    (^FileVisitResult postVisitDirectory [dir exc]
      (printf "Directory: %s%n" dir)
      FileVisitResult/CONTINUE)))



(comment 
  
  (defrecord Person [fname lname address])

  (defrecord Address [street city state zip])

  (def stu (Person. "Stu" "Halloway"
                    (Address. "200 N Mangum"
                              "Durham"
                              "NC"
                              27701)))
  
  (-> stu :address :zip)
  
  0)