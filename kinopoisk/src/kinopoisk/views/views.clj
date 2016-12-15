(ns kinopoisk.views.views
	(:require [kinopoisk.api :as api]
            [kinopoisk.server.server :as server]
            [kinopoisk.server.db.data :as data]
            [kinopoisk.server.db.connector :as connector])
  (:use [digest] :reload-all))

(defn index-get [{session :session}]
  (api/render "index.html" {:films (data/select-all-films) :session session}))

(defn register-get []
	(api/render "register.html"))

(defn register-post [{{:keys [username password] :as user} :params} success error]
  (try 
    (data/insert-user username (digest "sha-256" password))
  (catch Exception exception
   (prn exception) 
    (error))))

(defn login-get []
  (api/render "login.html"))

(defn login-post [{{:keys [username password] :as user} :params} success error]
  (server/login username (digest "sha-256" password))
  (api/render "login.html"))
