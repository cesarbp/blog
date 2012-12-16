(ns blog.views.post
  (:use noir.core
        blog.views.common
        hiccup.form
        [blog.views.utils :only [flash-message]])
  (:require [blog.models.post :as posts]
            [noir.response :as resp]))

(defpartial new-post-form []
  (form-to [:post "/blog/posts/new/"]
    [:div.row
     [:p "The first line is the title."]]
    [:div.row
     [:textarea {:name :content :placeholder "Content" :style "height:640px;"}]]
    [:div.row
     [:div.four.colums.offset-by-eight
      (submit-button {:class "button"} "New Post")]]))

(defpage "/blog/posts/new/" []
  (let [content {:content (new-post-form)
                 :title "Adding a new blog post"
                 :active "New"}]
    (base-layout content)))

(defpage [:post "/blog/posts/new/"] {:keys [content]}
  (let [result (posts/add-post content)]
    (if (= :success result)
      (flash-message "Post has been added." :success)
      (flash-message "Post couldn't be added." :alert))
    (resp/redirect "/blog/")))