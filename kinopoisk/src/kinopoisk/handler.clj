(ns kinopoisk.handler
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [ring.util.response :refer [redirect]]
              [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
              [kinopoisk.views.views :as views]
              [kinopoisk.api :as api]
              [buddy.auth.backends.session :refer [session-backend]]
              [kinopoisk.server.server :as server]))

(defroutes app-routes
    (GET "/" request 
        (views/index-get request))
    
    (GET "/register" request   
        (if (get-in request [:session :user_id])        
            (redirect "/")
            (views/register-get)))

    (POST "/register" request 
        (if (get-in request [:session :user_id]) 
            (redirect "/")
            (views/register-post
                request 
                #(redirect "/") 
                #(api/render "register.html" (merge {:error-message "User with such credentials already exists."})))))
    
    (GET "/login" request 
        (if (get-in request [:session :user_id])
            (redirect "/")
            (views/login-get)))

    (POST "/login" request 
        (server/login
            request 
            #(api/render "login.html" (merge {:error-message "Invalid data."})) 
            #(redirect "/")))
    
    (route/resources "/")
    (route/not-found "Not Found"))

(def backend (session-backend))

(def app
    (-> app-routes
        (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false)) 
        ))