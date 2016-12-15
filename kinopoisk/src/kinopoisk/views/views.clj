(ns kinopoisk.views.views
	(:require [kinopoisk.api :as api]
            [noir.util.crypt :as crypt] 
            [kinopoisk.server.db.data :as data]
            [kinopoisk.server.db.connector :as connector])
  (:use [digest] :reload-all))


(defn index_get []
  (api/render "index.html" {:films (data/select-all-films)}))

(defn register_get []
	(api/render "register.html"))

(defn register_post [{{:keys [username password] :as user} :params} success error]
  (try 
      (data/insert-user username (digest "sha-256" password))
  (catch Exception exception
   (prn exception) 
    (error))))