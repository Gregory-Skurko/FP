(ns kinopoisk.server.db.data
	(:require [clojure.java.jdbc :as jdbc]
    	      [clojure.java.jdbc.sql :as sql]
            [kinopoisk.server.db.protocol :as protocol]
            [kinopoisk.server.db.connector :as connector]))

(defn insert-value
    [username, password]
    (clojure.java.jdbc/with-connection
      connector/db
      (clojure.java.jdbc/transaction      
      (insert-user-sql username password))))

(defn insert-user-sql 
    [username, password]
    (clojure.java.jdbc/insert-values
      :user 
      [:username :password]
      [username password]))
