(ns blog.models.comment
  (:use somnium.congomongo
        [blog.models.utils :only [make-id update-db-map]])
  (:require [blog.time :as t]))

(def comments-coll :comments)
(def comments-props [:user :date :content :edits :post-id])

(defn setup! []
  (when-not (collection-exists? comments-coll)
    (drop-coll! comments-coll))
  (create-collection! comments-coll))

;;; Fetch comments
(defn get-comment [comment-id]
  (let [id (make-id (str comment-id))]
    (when id
      (fetch-one comments-coll :where {:_id id}))))

(defn get-all-comments [post-id]
  (fetch comments-coll :where {:post-id post-id} :sort {:date -1}))

;;; Create comments
(defn new-comment [post-id user content]
  (let [date (t/now)]
    (insert! comments-coll {:post-id (str post-id) :content content :user user :edits [] :date date})
    :success))

(defn edit-comment [comment-id new-content]
  (let [date (t/now)
        new-map {:content new-content :date date}
        id (make-id comment-id)
        old (when id (get-comment comment-id))
        new-comment (when old (update-db-map new-map (dissoc old :_id) :edits))]
    (when new-comment (update! comments-coll old (dissoc new-comment :_id))
          :success)))