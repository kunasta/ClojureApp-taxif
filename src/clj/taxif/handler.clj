(ns taxif.handler

  (:require [taxif.views :as views]
            [compojure.core :as cc]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [taxif.db.core :as db]
            [ring.util.response :as resp]
            [compojure.core :refer [defroutes routes wrap-routes]]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]  ]
            [taxif.layout :refer [error-page]]
            [taxif.routes.home :refer [home-routes]]
            [taxif.middleware :as middleware]
            [clojure.tools.logging :as log]
            [compojure.route :as route]
            [config.core :refer [env]]
            [taxif.config :refer [defaults]]
            [mount.core :as mount]
            [luminus.logger :as logger]
            ))
;; Default
(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (logger/init env)
  (doseq [component (:started (mount/start))]
    (log/info component "started"))
  ((:init defaults)))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (log/info "taxif is shutting down...")
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (log/info "shutdown complete!"))



(cc/defroutes app-routes
              (cc/GET "/"
                      []
                (views/home-page))

              ;add
              (cc/GET "/add-route"
                      []
                (views/add-routes-page))



              (cc/POST "/add-route"
                       {params :params}
                (views/add-route-results-page params)
                )

              ;single rroute view
              (cc/GET "/route/:id"
                      [id]
                (views/route-page id))

              ;delete

              (cc/GET "/route-del/:id" [id]

                (views/route-del id)

                )


              ;Edit & Update
              (cc/GET "/update-route/:id" [id]
                (views/route-update-page id
                                         ))                 ; working



               (cc/POST "/update-route/:id"       [ & params]
                       {params :params}
                (views/update-route-results-page  params
                ))

              ; all
              (cc/GET "/all-routes"
                      []
                (views/all-routes-page))
              (route/resources "/")
              (route/not-found "Not Found"))

(def app
  (handler/site app-routes))