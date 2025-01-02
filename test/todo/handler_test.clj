(ns todo.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [todo.handler :refer :all]
            [clojure.data.json :as json]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "GET /api/todos"
    (let [response (app (mock/request :get "/api/todos"))]
      (is (= (:status response) 200))
      (is (vector? (json/read-str (:body response) :key-fn keyword)))))

  (testing "GET /api/todos/:id"
    (let [response (app (mock/request :get "/api/todos/1"))]
      (is (= (:status response) 200))
      (is (map? (json/read-str (:body response) :key-fn keyword)))))

  (testing "POST /api/todos"
    (let [response (app (-> (mock/request :post "/api/todos")
                            (mock/content-type "application/json")
                            (mock/body (json/write-str {:title "Test" :description "Test description"}))))]
      (is (= (:status response) 200))
      (is (map? (json/read-str (:body response) :key-fn keyword)))))

  (testing "PUT /api/todos/:id"
    (let [response (app (-> (mock/request :put "/api/todos/1")
                            (mock/content-type "application/json")
                            (mock/body (json/write-str {:title "Updated Test" :is_complete true}))))]
      (is (= (:status response) 200))
      (is (not (nil? (:body response))))
      (is (map? (json/read-str (:body response) :key-fn keyword)))))

  (testing "DELETE /api/todos/:id"
    (let [response (app (mock/request :delete "/api/todos/1"))]
      (is (= (:status response) 200))
      (is (not (nil? (:body response))))
      (is (map? (json/read-str (:body response) :key-fn keyword))))))