(ns nas.htmx-demo.htmx-examples.inline-validation
  (:require [nas.htmx-demo.web.htmx :refer [ui page] :as htmx]))

(defn home [request]
  (page [:div "hello from inline-validation"]))