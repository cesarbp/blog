;;; Messages left by the users
(ns blog.models.message
  (:use somnium.congomongo)
  (:require [blog.time :as t]))

(def messages-coll :messages)

(def message-props [:email :date :subject :message])

(defn setup! []
  (when (collection-exists? messages-coll)
    (drop-coll! messages-coll))
  (create-collection! messages-coll))

(defn add-message [email subject message]
  (when (and (seq email) (seq message))
    (insert! messages-coll {:email email :subject subject :message message :date (t/now)})
    :success))

(defn get-all-messages []
  (fetch messages-coll :sort {:date -1}))