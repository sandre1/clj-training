(ns nas.htmx-demo.config
  (:require
    [kit.config :as config]))

(def ^:const system-filename "system.edn")

(defn system-config
  [options]
  (config/read-config system-filename options))
