(ns taxif.db.core
  (:require [clojure.java.jdbc :as sql]
            [config.core :refer [env]]
    [yesql.core :refer [defqueries]]
            ))


(def db-spec {:classname "org.h2.Driver"
              :connection-uri (:database-url env)
              :make-pool? true
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})
(defqueries "sql/queries.sql" {:connection db-spec})

(defn create-route!
  [start end price date]

  (sql/insert! db-spec :routes
                     {:start start :end end :price price :date date})
     )




(defn delete!
  [id]

                       (sql/delete! db-spec :routes ["id=?" id]
                                        ))




(defn saver!  [ id start end price date]
  (sql/update! db-spec  :routes   {:start start :end end :price price :date date}  ["id=?" id ] )


                              )




(defn get-route
  [id]

  (sql/query db-spec
                          ["select start, end, price, date from routes where id = ?" id]
                                                            ))


(defn get-all-routes
  []

  (sql/query db-spec
                          ["select id, start, end, price, date from routes"]
                          ;  ["select sum(price) as total from routes"]

                          ))






