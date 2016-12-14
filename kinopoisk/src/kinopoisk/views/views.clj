(ns kinopoisk.views.views
	(:require [kinopoisk.api :as api]
            [noir.util.crypt :as crypt] 
            [kinopoisk.server.db.data :as data]
            [kinopoisk.server.db.connector :as connector])
  (:use [digest] :reload-all))

(defn register_get []
	(api/render "register.html"))

(defn register_post [{{:keys [username password] :as user} :params} success error]
  (try 
      (data/insert-value username (digest "sha-256" password))
  (catch Exception exception
   (prn exception) 
    (error))))