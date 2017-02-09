(ns taxif.views

  (:require [taxif.db.core :as db]
            [clojure.string :as str]
            [hiccup.page :as hic-p])
  )

(defn gen-page-head
  [title]
  [:head
   [:title (str "Routes: " title)]
   (hic-p/include-css "/css/style.css")])

(def header-links
  [:div#header-links

   [:a {:href "/"} "Home"]
   " * "
   [:a {:href "/add-route"} "Add a route"]
   " * "
   [:a {:href "/all-routes"} "View All Routes"]
   ])

(defn home-page
  []
  (hic-p/html5
    (gen-page-head "Home")
    header-links
    [:h1 "Home"]
    [:h2 "Store daily taxi routes"]))

(defn add-routes-page
  []
  (hic-p/html5
    (gen-page-head "Add a route")
    header-links
    [:h1 "Add a Route"]
    [:form {:action "/add-route" :method "POST"}
     [:p "Start point: " [:input {:type "text" :name "start"}]]
     [:p "End point: " [:input {:type "text" :name "end"}]]
     [:p "Price: " [:input {:type "number" :name "price"}]]
     [:p "Date: " [:input {:type "date" :name "date"}]]
     [:p [:input {:type "submit" :value "Submit" :id "submit" }]]]))

(defn add-route-results-page
  [{:keys [start end price date]}]
  (db/create-route! start end price date)
    (hic-p/html5
      (gen-page-head "Added Routes")
      header-links
      [:h1 "Route is saved"
       [:p "Route: " ]
       [:p "Start Point:" start ]
       [:p "End Point:" end]
       [:p "Price: " price]


       ])  )                                                ;OK

(defn route-page
  [id]
  (let [{start :start end :end price :price date :date } (str (db/get-route id))]
    (hic-p/html5
      (gen-page-head (str "Route " id))
      header-links
      [:h1 "Route"]
      [:p "id: " id]
      [:p "Start point: " start]
      [:p "End point: "  end ]
      [:p "Price: "  price ]
      [:p "Date: "  date  ]
      [:a {:href (str  "/route-del/" id )} "Delete"]
      [:a {:href (str "/update-route/" id)} "Change"]
      ))


  )


(defn route-del

  [id]
  (do (db/delete! id)
      (hic-p/html5
        (gen-page-head (str "Route " id))
        header-links
        [:h1 " Route Deleted!"]

        [:a {:href "/add-route"} "Add new route"]
        " | "
        [:a {:href "/all-routes"} "View All Routes in DB"]
        ;[:a {:href (str "route" id "/route-del/")} "Delete"]
        ;[:a {:href (str "/route-update/" id)} "Change"]
        ))

  )



(defn route-update-page
  [id  ]

  (hic-p/html5
    (gen-page-head (str "Update a route"
                        ) )
    header-links
    [:h1 "Update a Route" ]

    [:form {:post  "/update-route/:id" :method "POST"}
     [:h2 "Route id:" id ]
     [:p "Start point: " [:input {:type "text" :name "start" } ]]
     [:p "End point: " [:input {:type "text" :name "end" }]]
     [:p "Price: " [:input {:type "number" :name "price" }]]
     [:p "Date: " [:input {:type "date" :name "date" }]]
     [:p "ID: " id  ]                                                                          ;[:label{:type "number" :name "id" }]
     [:p [:input {:type "submit" :value "Submit" :id "submit" }]]]))    ;working




(defn update-route-results-page
  [{:keys [ id start end price date  ]}]
  (db/saver! id start end price date )
    (hic-p/html5
      (gen-page-head "Updated Routes")
      header-links
      [:h1 "Route is saved"
       [:p "Route: " ]
       [:p "Start Point:" start ]
       [:p "End Point:" end ]
       [:p "Price: " price]
       [:p "Date: " date ]
       [:p "ID: " id ]
       ]
      ) )



(defn all-routes-page
  []
  (let [all-routes (db/get-all-routes)]
    (hic-p/html5
      (gen-page-head "All Routes in the db")
      header-links
      [:h1 "All Routes"]
      [:table
       [:tr [:th "Id"] [:th "Start"] [:th "End"] [:th "Price"] [:th "Date"][:th "Delete"][:th "Update"]]
       (for [route all-routes]
         [:tr [:td (:id route)] [:td (:start route)] [:td (:end route)] [:td (:price route)] [:td (:date route)][:td [:a {:href (str  "/route-del/" (:id route) )} "Delete"]][:td [:a {:href (str  "/update-route/" (:id route) )} "Update"]]])]

      ; [:table

      ; [:tr [:th "Total"]]
      ; (for [route all-routes]
      ;   [:tr [:td (:total route)]]
      ;)]                   ;[:td (:price route)]

      )))