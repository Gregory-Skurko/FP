(ns kinopoisk.handler
  	(:require [compojure.core :refer :all]
    	      [compojure.route :as route]
    	      [ring.util.response :refer [redirect]]
        	  [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
        	  [kinopoisk.views.views :as views]
        	  [kinopoisk.api :as api]))

(defroutes app-routes
	(GET "/register" [] (views/register_get))
	(POST "/register" request (views/register_post
    	request #(redirect "/") #(api/render "register.html" (merge {:error-message "User with such credentials already exists."}))))
	(route/resources "/")
	(route/not-found "Not Found"))

(def app
	(->	app-routes
		(wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))