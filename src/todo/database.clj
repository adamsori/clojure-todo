(ns todo.database
  (:require [korma.db :as korma]))

(def db-connection-info
  (korma/mysql
   {:classname "com.mysql.cj.jdbc.Driver"
    :subprotocol "mysql"
    :user "root"
    :password "1234"
    :subname "//127.0.0.1:3306/todo"}))

; set up korma
#_{:clj-kondo/ignore [:unresolved-symbol]}
(korma/defdb db db-connection-info)