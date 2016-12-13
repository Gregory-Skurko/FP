(ns kinopoisk.handler
  	(:require [compojure.core :refer :all]
    	      [compojure.route :as route]
        	  [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
        	  [kinopoisk.views.views :as views]))

(defroutes app-routes
	(GET "/register" [] (views/register))
	(route/resources "/")
	(route/not-found "Not Found"))

(def app
	(wrap-defaults app-routes site-defaults))
