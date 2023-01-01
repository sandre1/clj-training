(ns user
  "Userspace functions you can run by default in your local REPL."
  (:require
   [clojure.pprint]
   [clojure.spec.alpha :as s]
   [clojure.tools.namespace.repl :as repl]
   [criterium.core :as c]                                  ;; benchmarking
   [expound.alpha :as expound]
   [integrant.core :as ig]
   [integrant.repl :refer [clear go halt prep init reset reset-all]]
   [integrant.repl.state :as state]
   [kit.api :as kit]
   [lambdaisland.classpath.watch-deps :as watch-deps]      ;; hot loading for deps
   [nas.htmx-demo.core :refer [start-app]]
   [clojure.tools.trace :as trace]
   [portal.api :as p]))


(comment
  (def p (p/open)) ; Open a new inspector  
  ;; or with an extension installed, do:
  (def p (p/open {:launcher :vs-code}))  ; jvm / node only

  (add-tap #'p/submit) ; Add portal as a tap> target

  )

;; uncomment to enable hot loading for deps
(watch-deps/start! {:aliases [:dev :test]})

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(add-tap (bound-fn* clojure.pprint/pprint))

(defn dev-prep!
  []
  (integrant.repl/set-prep! (fn []
                              (-> (nas.htmx-demo.config/system-config {:profile :dev})
                                  (ig/prep)))))

(defn test-prep!
  []
  (integrant.repl/set-prep! (fn []
                              (-> (nas.htmx-demo.config/system-config {:profile :test})
                                  (ig/prep)))))

;; Can change this to test-prep! if want to run tests as the test profile in your repl
;; You can run tests in the dev profile, too, but there are some differences between
;; the two profiles.
(dev-prep!)

(repl/set-refresh-dirs "src/clj")

(def refresh repl/refresh)



(comment
  (go)
  (reset)
  (trace/trace-ns nas.htmx-demo.web.routes.ui)
  (trace/trace-vars nas.htmx-demo.web.routes.ui/edit)

  (kit/sync-modules)
  (kit/list-modules)
  (kit/install-module :kit/html)
  (kit/install-module :kit/htmx)

  (tap> {:name "nas"})
  (prn (type @p))
  (prn (:body (first @p)))
  (def a @p)
  (let [f1 (first a)]
    a)

  )
