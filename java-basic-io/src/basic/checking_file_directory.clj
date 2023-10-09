(ns basic.checking-file-directory
  (:import java.nio.file.Paths
           java.nio.file.Files
           java.nio.file.NoSuchFileException
           java.nio.file.DirectoryNotEmptyException
           java.nio.file.CopyOption
           java.nio.file.attribute.BasicFileAttributes
           java.nio.file.attribute.PosixFileAttributes
           java.nio.file.attribute.PosixFilePermissions
           java.nio.file.attribute.FileTime
           java.nio.file.attribute.FileAttribute
           java.nio.file.attribute.UserPrincipalLookupService
           java.nio.file.LinkOption
           java.io.IOException
           java.io.FileSystem
           ))

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

;; (two-paths-locate-same-file? "symbolic-folder/link-to-test.txt" "test.txt")

(defn deleting-file-or-dir [p]
  (try (let [string-more (into-array String [])
             path (Paths/get p string-more)]
         (Files/delete path))
       (catch NoSuchFileException _ (printf "%s: no such file or directory%n" p))
       (catch DirectoryNotEmptyException _ (printf "%s: not emptyyy%n" p))
       (catch IOException e (.println System/err e))))

;;(deleting-file-or-dir "not-empty-folde")

(defn copying-file-dir [s d]
 (let [string-more (into-array String [])
       source-path (Paths/get s string-more)
       dest-path (Paths/get d string-more)]
   (Files/copy source-path dest-path (into-array CopyOption []))))

;; (copying-file-dir "to/abc.txt" "dest")

(defn moving-file-dir [s d]
  (let [string-more (into-array String [])
        source-path (Paths/get s string-more)
        dest-path (Paths/get d string-more)]
    (Files/move source-path dest-path (into-array CopyOption []))))

;; (moving-file-dir "to/sss.txt" "from/sss.txt")

(defn show-file-metadata [file]
  (let [string-more (into-array String [])
        file-path (Paths/get file string-more)
        link-opts (into-array LinkOption [LinkOption/NOFOLLOW_LINKS])]
    (println (Files/size file-path))
    (println (Files/isDirectory file-path link-opts))
    (println (Files/isRegularFile file-path link-opts))
    (println (Files/getLastModifiedTime file-path link-opts))
    (println (Files/getOwner file-path link-opts))
    (println (Files/getPosixFilePermissions file-path link-opts))
    (println (Files/getAttribute file-path "unix:uid" link-opts))))

;; (show-file-metadata "to/abc.txt") ;; TODO: why does folders have size of 4096 ?

(defn get-basic-file-atributes [file]
  (let [string-more (into-array String [])
        file-path (Paths/get file string-more)
        link-opts (into-array LinkOption [LinkOption/NOFOLLOW_LINKS])
        attr (Files/readAttributes file-path BasicFileAttributes link-opts)]
    (println "creation-time: " (.creationTime attr))
    (println "last-access-time " (.lastAccessTime attr))
    (println "last-modified-time " (.lastModifiedTime attr))
    (println "size: " (.size attr))))

(get-basic-file-atributes "portocala.txt")

(defn setting-time-stamp [file]
  (let [string-more (into-array String [])
        file-path (Paths/get file string-more)
        link-opts (into-array LinkOption [LinkOption/NOFOLLOW_LINKS])
        attr (Files/readAttributes file-path BasicFileAttributes link-opts)
        current-time (System/currentTimeMillis)
        ft (FileTime/fromMillis current-time)]
    (Files/setLastModifiedTime file-path ft)))

;; (setting-time-stamp "portocala.txt")

(defn get-posix-file-permissions [file]
  (let [string-more (into-array String [])
        file-path (Paths/get file string-more)
        link-opts (into-array LinkOption [LinkOption/NOFOLLOW_LINKS])
        attr (Files/readAttributes file-path PosixFileAttributes link-opts)]
    (printf "%s %s %s%n" 
            (.getName (.owner attr)) 
            (.getName (.group attr))
            (PosixFilePermissions/toString (.permissions attr)))))

(get-posix-file-permissions "permissions.log")

(defn same-attrs-new-file [file new-file]
  (let [string-more (into-array String [])
        source-file (Paths/get file string-more)
        new-file-path (Paths/get new-file string-more)
        link-opts (into-array LinkOption [LinkOption/NOFOLLOW_LINKS])
        attrs (Files/readAttributes source-file PosixFileAttributes link-opts)
        attr (PosixFilePermissions/asFileAttribute (.permissions attrs))]
    (Files/createFile new-file-path (into-array FileAttribute [attr]))))

;; (same-attrs-new-file "x.txt" "example-new.txt")

(defn hardcoded-attrs-new-file [new-file]
  (let [string-more (into-array String [])
        new-file-path (Paths/get new-file string-more)
        perms (PosixFilePermissions/fromString "rw----r--")
        attr (PosixFilePermissions/asFileAttribute perms)]
    (Files/setPosixFilePermissions new-file-path perms)))

;; (hardcoded-attrs-new-file "example-new.txt")

(defn setting-a-file-or-group-owner [file-str]
  (let [string-more (into-array String [])
        file (Paths/get file-str string-more)
        owner (-> file
                  (.getFileSystem)
                  (.getUserPrincipalLookupService)
                  (.lookupPrincipalByName "andrei"))]
    (Files/setOwner file owner)))

(setting-a-file-or-group-owner "example-new.txt") ;; TODO: ; Execution error (FileSystemException) at sun.nio.fs.UnixException/translateToIOException (UnixException.java:100).
; example-new.txt: Operation not permitted

(defn file-store-attrib [file-str]
  (let [string-more (into-array String [])
        file (Paths/get file-str string-more)
        store (Files/getFileStore file)
        total (/ (.getTotalSpace store) 1024)
        used (/ (- (.getTotalSpace store) (.getUnallocatedSpace store)) 1024)
        avail (/ (.getUsableSpace store) 1024)]
    (println total)
    (println used)
    (println avail)))

(file-store-attrib "test.txt")





