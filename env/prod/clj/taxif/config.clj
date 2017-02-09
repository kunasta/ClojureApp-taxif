(ns taxif.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[taxif started successfully]=-"))
   :middleware identity})
