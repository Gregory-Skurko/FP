(defproject kinopoisk "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://github.com/luciys/FP"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [org.clojure/java.jdbc "0.3.0-alpha5"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [selmer "1.0.9"]
                 [markdown-clj "0.9.89"]
                 [metosin/ring-http-response "0.8.0"]
                 [bouncer "1.0.0"]
                 [noir "1.0.0" :exclusions [org.clojure/clojure hiccup]]
                 [digest "1.4.5"]
                 [buddy/buddy-auth "1.3.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler kinopoisk.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
