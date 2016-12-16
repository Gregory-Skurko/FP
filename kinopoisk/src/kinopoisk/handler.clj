(ns kinopoisk.handler
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [clojure.java.jdbc :as jdbc]
              [ring.util.response :refer [redirect]]
              [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
              [kinopoisk.views.views :as views]
              [kinopoisk.api :as api]
              [buddy.auth.backends.session :refer [session-backend]]
              [kinopoisk.server.server :as server]
              [kinopoisk.server.db.connector :as connector]))

(defroutes app-routes
    (GET "/" request 
        (views/index-get request))
    
    (GET "/register" request   
        (if (get-in request [:session :user_id])        
            (redirect "/")
            (views/register-get)))

    (POST "/register" request                      
            (views/register-post request                                                 
                #(redirect "/")
                #(api/render "register.html" (merge {:error-message "User already exists or invalid data."})))
                            
        )
    
    (GET "/login" request 
        (if (get-in request [:session :user_id])
            (redirect "/")
            (views/login-get)))

    (POST "/login" request 
        (server/login
            request 
            #(api/render "login.html" (merge {:error-message "Invalid data."})) 
            #(redirect "/")))

    (GET "/logout" request
        (views/logout request))

    (GET "/search" request
        (api/render "search.html"))

    (GET "/add-film" request                     
            (views/add-film-get request))        

    (POST "/add-film" request                      
            (views/add-film-post request                                                 
                #(redirect "/")
                #(api/render "add-film.html" (merge {:error-message "User already exists or invalid data."})))
                            
        )
    (POST "/remove-film" request                      
           (jdbc/delete! connector/db :films ["id = ?"  (get (get request :params) :film)])
           (redirect "/"))
    ;(GET "/profile" request
    ;    (views/profile-get request)
    ;    )

    ;(POST "/profile" request
    ;    (views/profile-post request))
    
    (route/resources "/")
    (route/not-found (api/render "404.html")))

(def backend (session-backend))

(def app
    (-> app-routes
        (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false)) 
        ))