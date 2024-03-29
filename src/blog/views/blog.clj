(ns blog.views.blog
  (:use noir.core
        blog.views.common
        [hiccup.element :only [link-to]]
        [blog.views.utils :only [format-post-content]])
  (:require [blog.time :as t]
            [blog.models.post :as posts]))


;;; Homepage: view the posts
(defpartial blog-post [content date comments-link]
  [:div.row.post-details-header
   [:div.four.colums.offset-by-eight
    [:p (str "Date: " (t/get-date date) " | Time: " (t/get-hour date))]]]
  [:div.row.post-content
   (format-post-content content)]
  [:div.row.post-options-footer
   (if (seq comments-link)
     [:div.four.colums.offset-by-eight
      (link-to {:class "small button"} comments-link "Discuss this entry")])])

;;; main section consists of the posts
(defpartial main-section [posts]
  [:section.main
   [:div.row
    [:div.twelve.columns
     (map blog-post (map :content posts) (map :date posts)
          (map (fn [post]
                 (str "/blog/posts/" (:normalized-title post) "/comments/"))
               posts))]]])

(defpage "/blog/" []
  (let [latest-posts (posts/get-latest)
        content {:content (main-section latest-posts)
                 :title "Blog"
                 :active "Blog"}]
    (base-layout content)))


