(ns kinopoisk.views.views
	(:require [kinopoisk.api :as api]
            [kinopoisk.server.server :as server]
            [kinopoisk.server.db.data :as data]
            [kinopoisk.server.db.connector :as connector]
            [ring.util.response :refer [redirect]])
  (:use [digest] :reload-all))

(defn index-get [{session :session}]
  (api/render "index.html" {:films (data/select-all-films) :session session}))

(defn register-get []
	(api/render "register.html"))

(defn add-film-get [request]
  (api/render "add-film.html"))


(defn add-film-post [{{:keys [title description] :as film} :params session :session} success error] 
  (println title description) 
  (try 
    (data/insert-film title description)
    (success)
  (catch Exception exception
   (prn exception) 
    (error))))

(defn register-post [{{:keys [username password] :as user} :params session :session} success error]  
  (try 
    (data/insert-user username (digest "sha-256" password))
    (let [{id :id stored-pass :password} (data/select-user username)]           
      (if (= stored-pass (digest "sha-256" password))       
          (-> (success) (assoc :session (assoc session :user_id id :username username)))
      (error)))
  (catch Exception exception
   (prn exception) 
    (error))))

(defn login-get []
  (api/render "login.html"))

(defn login-post [{{:keys [username password] :as user} :params} success error]  
  (server/login username (digest "sha-256" password))
  (api/render "login.html"))

(defn logout [request]
    (server/logout request #(redirect "/")))

; (defn profile-get [{session :session}]
;   (println session)
;   (if (get-in session [:user_id])        
;     (api/render "profile.html" { :session session})
;     (redirect "/")))

; (defn profile-post [{{:keys [password new-password confirm-password] :as user} :params} success error]
;   (println session)
;   (if (get-in session [:user_id])        
;     (if (= new-password confirm-password) 
;       (server/update-user-password (digest "sha-256" password) (digest "sha-256" new-password) (digest "sha-256" confirm-password))
;       (error))
;     (redirect "/")))