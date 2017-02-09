(ns taxif.config
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [taxif.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[taxif started successfully using the development profile]=-"))
   :middleware wrap-dev})
