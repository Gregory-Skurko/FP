(ns kinopoisk.server.db.connector)

(use 'clojure.java.jdbc)

(let [db-host "localhost"
	  db-port 3306
	  db-name "kinopoisk"]

(def db {	
			:classname "com.mysql.jdbc.Driver"  
			:subprotocol "mysql"
           	:subname (str "//" db-host ":" db-port "/" db-name)
           	:user "root"
           	:password ""
           	:zeroDateTimeBehavior "convertToNull"}))
