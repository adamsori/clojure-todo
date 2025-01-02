(ns todo.query
  (:require [clojure.java.jdbc :as jdbc]
            [todo.database :refer [db-connection-info]]
            [clojure.tools.logging :as log]))

(defn get-todos []
  (jdbc/query db-connection-info ["SELECT * FROM items"]))

(defn add-todo [title description]
  (jdbc/insert! db-connection-info :items {:title title :description description}))

(defn delete-todo [id]
  (let [result (jdbc/delete! db-connection-info :items ["id = ?" id])]
    (log/info "Delete result:" result)
    (pos? (first result))))

(defn update-todo [id title is-complete description]
  (let [result (jdbc/execute! db-connection-info
                              ["UPDATE items SET title = ?, is_complete = ?, description = ? WHERE id = ?" title is-complete description id])]
    (pos? (first result))))

(defn get-todo [id]
  (first (jdbc/query db-connection-info ["SELECT * FROM items WHERE id = ?" id])))