;;; Comments on posts
(ns blog.views.comment
  (:use blog.views.common
        noir.core
        hiccup.form
        [blog.views.blog :only [blog-post]]
        [hiccup.element :only [link-to]]
        [blog.views.utils :only [format-comment-content flash-message escape-html]])
  (:require [blog.models.comment :as comments]
            [blog.time :as t]
            [blog.models.post :as posts]
            [noir.response :as resp]
            [blog.models.user :as users]))

(defpartial comment-controls [id]
  [:div.row.comment-controls])

(defpartial comment-controls-admin [id]
  [:div.row.comment-controls
   (link-to (str "/admin/comments/delete/" id "/") "Delete comment")])

(defpartial show-comment [id user content date admin]
  [:div.row.comment-section
   [:div.twelve.columns
    [:div.row.comment-details
     [:div.four.columns.comment-user
      [:p user]]
     [:div.eight.columns.comment-date
      [:p (str (t/get-date date) " | " (t/get-hour date))]]]
    [:div.row.comment-content
     [:div.twelve.columns
      (format-comment-content content)]]
    (if admin (comment-controls-admin id)
        (comment-controls id))]])

(defpartial show-comments [comments admin]
  (if (seq comments)
    (map show-comment (map (comp str :_id) comments) (map :user comments) (map :content comments) (map :date comments) (repeat admin))
    [:p "No comments on this entry so far."]))

(defpartial new-comment-form [post-id normalized-title]
  (form-to [:post (str "/blog/posts/comments/" post-id "/")]
           [:div.row.new-comment-section
            [:div.row.new-comment-header
             [:p "Add another comment:"]]
            [:div.row.new-comment-container
             (label :user "Your name")
             (text-field :user)
             (label :user "Your email")
             (text-field :email)
             [:textarea {:name :content :placeholder "Content"}]
             (hidden-field :normalized normalized-title)]
            [:div.row.new-comment-actions
             (submit-button {:class "button"} "Add")]]))

(defpartial comment-divider []
  [:div.row.comment-divider
   [:div.twelve.columns
    [:hr
     [:h2 "Comments"]]]])

(defpartial new-comment-divider []
  [:div.row.new-comment-divider
   [:div.twelve.columns
    [:hr]]])

(defpartial main-section [post comments admin]
  [:section.main
   [:div.row
    (blog-post (:content post) (:date post) "")
    (comment-divider)
    (show-comments comments admin)
    (new-comment-divider)
    (new-comment-form (str (:_id post)) (:normalized-title post))]])

(defpage "/blog/posts/:title/comments/" {:keys [title]}
  (let [post (posts/get-by-name title)
        admin (users/admin?)
        comments (when post (comments/get-all-comments (str (:_id post))))
        content (if-not post [:p "This blog post doesn't exist"]
                        (main-section post comments admin))
        content-map {:content content
                     :title (if post (:title post) "Inexistent blog post")
                     :active "Blog"}]
    (base-layout content-map)))

(defpage [:post "/blog/posts/comments/:post-id/"] {:keys [post-id user email content normalized]}
  (let [sanitized-content (escape-html content)
        sanitized-user (escape-html user)
        result (comments/new-comment post-id sanitized-user email sanitized-content)]
    (if (= result :success)
      (flash-message "Your comment has been added" :success)
      (flash-message "There has been an error adding your comment" :alert))
    (resp/redirect (str "/blog/posts/" normalized "/comments/"))))

(defpartial delete-comment-form [comment]
  (let [id (str (:_id comment))
        content (:content comment)]
    (form-to [:post (str "/admin/comments/delete/" id "/")]
             [:p content]
             (submit-button {:class "alert button"} "Delete comment"))))

(defpage "/admin/comments/delete/:id/" {:keys [id]}
  (let [comment (comments/get-comment id)
        content (delete-comment-form comment)]
    (base-layout {:content content
                  :title "Delete a comment"
                  :active "Admin"})))

(defpage [:post "/admin/comments/delete/:id/"] {:keys [id]}
  (when (users/admin?)
    (let [comment (comments/get-comment id)
          post-id (:post-id comment)
          post-title (:normalized-title (posts/get-by-id post-id))
          result (comments/delete-comment id)]
      (flash-message "Comment deleted" :standard)
      (resp/redirect (str "/blog/posts/" post-title "/comments/")))))