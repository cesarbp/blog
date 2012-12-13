(ns blog.models.user
  (:use somnium.congomongo)
  (:require [noir.util.crypt :as crypt]
            [blog.models.utils :as db]
            [noir.session :as session]))

(def users-coll :users)

(defn admin? []
  (session/get :admin))

(defn theres-admin? []
  (and (collection-exists? users-coll)
       (fetch-one users-coll :where {:user "admin"})))

(defn setup! [admin-pass]
  (when (collection-exists? users-coll)
    (drop-coll! users-coll))
  (create-collection! users-coll)
  (insert! users-coll {:user "admin" :pass (crypt/encrypt admin-pass)}))

(defn verify-pass [pass]
  (let [admin-pass (:pass (fetch-one users-coll :where {:user "admin"}))]
    (crypt/compare pass admin-pass)))

(defn login! []
  (session/put! :user :admin)
  (session/put! :admin true))