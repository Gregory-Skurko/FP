(ns kinopoisk.server.server 
	(:require [noir.util.crypt :as crypt] 
              [kinopoisk.server.db.data :as data])
			(:use [digest] :reload-all))

(defn login [{{:keys [username password] :as user} :params session :session} error success]  
	(println password)		
	(let [{id :id stored-pass :password} (data/select-user username)]     	
		(println stored-pass)
    	(if (= stored-pass (digest "sha-256" password))    		
      		(-> (success) (assoc :session (assoc session :user_id id :username username)))
      (error))))