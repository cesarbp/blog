(ns blog.views.blog
  (:use noir.core
        blog.views.common
        [hiccup.element :only [link-to]])
  (:require [blog.time :as t]
            [blog.models.post :as posts]))


;;; Homepage: view the posts

;;; Creates html paragraps per paragraph in the content.
;;; Also adds an h1 to the first line.
;;; This should probably parse markdown in the future
(defpartial format-post-content [content]
  (let [paragraphs (clojure.string/split content #"(\r|\n)+")]
    (conj (map (fn [p] [:p p]) (rest paragraphs))
          [:h1 (first paragraphs)])))

(defpartial blog-post [content date comments-link]
  [:div.row
   [:div.twelve-columns.post-details-header
    [:div.four.colums.offset-by-eight
     [:p (str "Fecha: " (t/get-date date) " | Hora: " (t/get-hour date))]]]]
  [:div.row.post-content
   (format-post-content content)]
  [:div.row.post-options-footer
   [:div.twelve-columns
    [:div.four.colums.offset-by-eight
     (link-to comments-link "Discutir esta entrada")]]])

;;; main section consists of the posts
(defpartial main-section [posts]
  [:section.main
   [:div.row
    [:div.twelve-columns
     (map blog-post (map :content posts) (map :date posts)
          (map (fn [post]
                 (str "/blog/posts/" (:normalized-title post) "/comentarios/"))
               posts))]]])

(defpage "/blog/" []
  (let [latest-posts (posts/get-latest)
        content {:content (main-section latest-posts)
                 :title "Blog"
                 :active "Blog"}]
    (base-layout content)))


