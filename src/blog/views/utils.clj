(ns blog.views.utils
  (:require [noir.session :as session]))

(defn flash-message [content type]
  "Type should be one of :alert, :success, :standard or :secondary"
  (session/flash-put! :messages (list {:type type :content content})))