;;; Blog posts

(ns blog.models.post
  (:use somnium.congomongo
        [blog.models.utils :only [modify-db-map update-db-map make-id normalize]])
  (:require [blog.time :as t]))

(def post-coll :post)
;;; Edits are past versions of the post
(def post-attrs [:date :content :edits :title :normalized-title])
(def front-page-post-count 10)

(defn setup! []
  (if (collection-exists? post-coll)
    (drop-coll! post-coll))
  (create-collection! post-coll))

;;; Fetch posts
(defn get-by [cond-map]
  (fetch-one post-coll :where cond-map))

(defn get-latest []
  (fetch post-coll :limit front-page-post-count :sort {:date -1}))

;;; Add new posts
(defn add-post
  ([content] (add-post content nil))
  ([content title]
      (let [now (t/now)
            title (if (seq title) title (first (clojure.string/split content #"(\r|\n)+")))
            normalized-title (normalize title)]
        (insert! post-coll {:date now :normalized-title normalized-title
                            :content content :title title :edits []})
        :success)))

;;; Change existing posts
(defn edit-post [id new-content]
  (let [now (t/now)
        m {:date now :content new-content}
        id (make-id id)
        old (when id (get-by {:_id id}))]
    (when old
      (do
        (update! post-coll old (modify-db-map m (dissoc old :_id) :edits))
        :success))))

;;; Delete posts
(defn delete-post [id]
  (let [id (make-id id)]
    (when id
      (destroy! post-coll {:_id id})
      :success)))