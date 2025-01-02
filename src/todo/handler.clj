(ns todo.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response status]]
            [todo.query :refer :all]
            [clojure.tools.logging :as log]))

(defroutes app-routes
  (GET "/" []
    (response "Hello World"))
  (GET "/api/todos" []
    (response (get-todos)))
  (GET "/api/todos/:id" [id]
    (let [todo (get-todo (Integer/parseInt id))]
      (if todo
        (response todo)
        (-> (response {:error "Todo not found"})
            (status 404)))))
  (POST "/api/todos" {:keys [params]}
    (let [{:keys [title description]} params]
      (response (add-todo title description))))
  (PUT "/api/todos/:id" [id :as request]
    (let [{:keys [title is_complete description]} (:params request)
          is-complete (if (boolean? is_complete) is_complete (Boolean/parseBoolean (str is_complete)))
          updated (update-todo (Integer/parseInt id) title is-complete description)]
      (log/info "Updated : " updated)
      (if updated
        (response {:status "Todo updated successfully"})
        (-> (response {:error "Todo not found or not updated"})
            (status 404)))))
  (DELETE "/api/todos/:id" [id]
    (let [deleted (delete-todo (Integer/parseInt id))]
      (log/info "Delete handler result:" deleted)
      (if deleted
        (response {:status "Todo deleted"})
        (-> (response {:error "Todo not found"})
            (status 404)))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))