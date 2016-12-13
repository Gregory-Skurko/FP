(ns kinopoisk.views.views
	(:require [kinopoisk.api :as api]))
	(use 'selmer.parser)

(defn register []
	(api/render "register.html"))

(defn register [{{:keys [username password] :as user} :params} success error]
  (try 
    (if-not (.read userdao username)
      (do (-> (merge user {:role "user"}) (encrypt) (.create userdao))
        (success))
      (error)) 
    (catch Exception exception
      (prn exception) 
      (error))))