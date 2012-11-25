(ns blog.views.utils
  (:use [noir.core :only [defpartial]])
  (:require [noir.session :as session]))

(defn flash-message [content type]
  "Type should be one of :alert, :success, :standard or :secondary"
  (session/flash-put! :messages (list {:type type :content content})))

;;; Creates html paragraps per paragraph in the content.
;;; Also adds an h1 to the first line.
;;; This should probably parse markdown in the future
(defpartial format-post-content [content]
  (let [paragraphs (clojure.string/split content #"(\r|\n)+")]
    (conj (map (fn [p] [:p p]) (rest paragraphs))
          [:h1 (first paragraphs)])))

(defpartial format-comment-content [content]
  (let [paragraphs (clojure.string/split content #"(\r|\n)+")]
    (map (fn [p] [:p p]) paragraphs)))