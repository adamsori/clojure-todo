(defproject todo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-core "1.9.0"]
                 [ring/ring-json "0.5.0"]
                 [korma "0.4.3"]
                 [mysql/mysql-connector-java "8.0.23"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-mock "0.4.0"]
                 [org.clojure/data.json "2.4.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [org.clojure/java.jdbc "0.7.12"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler todo.handler/app
         :auto-reload? true
         :open-browser? false}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.4.0"]]}})