;;; Comments on posts
(ns blog.views.comment
  (:use blog.views.common
        noir.core
        hiccup.form
        [blog.views.blog :only [blog-post]]
        [hiccup.element :only [link-to]]
        [blog.views.utils :only [format-comment-content flash-message]])
  (:require [blog.models.comment :as comments]
            [blog.time :as t]
            [blog.models.post :as posts]
            [noir.response :as resp]))

(defpartial show-comment [user content date]
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
    [:div.row.comment-controls
     (link-to "#" "Citar")]]])

(defpartial show-comments [comments]
  (map show-comment (map :user comments) (map :content comments) (map :date comments)))

(defpartial new-comment-form [post-id normalized-title]
  (form-to [:post (str "/posts/comentarios/" post-id "/")]
           [:div.row.new-comment-section
            [:div.row.new-comment-header
             [:p "Nuevo comentario:"]]
            [:div.row.new-comment-container
             [:textarea {:name :content :placeholder "Contenido"}]
             (hidden-field :normalized normalized-title)]
            [:div.row.new-comment-actions
             (submit-button {:class "button"} "Agregar")]]))

(defpartial comment-divider []
  [:div.row.comment-divider
   [:div.twelve.columns
    [:hr
     [:h2 "Comentarios"]]]])

(defpartial new-comment-divider []
  [:div.row.new-comment-divider
   [:div.twelve.columns
    [:hr]]])

(defpartial main-section [post comments]
  [:section.main
   [:div.row
    [:div.twelve.columns (blog-post (:content post) (:date post) "")
     (comment-divider)
     (show-comments comments)
     (new-comment-divider)
     (new-comment-form (str (:_id post)) (:normalized-title post))]]])

(defpage "/blog/posts/:title/comentarios/" {:keys [title]}
  (let [post (posts/get-by-name title)
        comments (when post (comments/get-all-comments (str (:_id post))))
        content (if-not post [:p "Este post no existe"]
                        (main-section post comments))
        content-map {:content content
                     :title (if post (:title post) "Post no existente")
                     :active "Blog"}]
    (base-layout content-map)))

(defpage [:post "/posts/comentarios/:post-id/"] {:keys [post-id content normalized]}
  (let [result (comments/new-comment post-id "cbp" content)]
    (if (= result :success)
      (flash-message "Tu comentario ha sido agregado" :success)
      (flash-message "Ha ocurrido un error al agregar tu comentario" :alert))
    (resp/redirect (str "/blog/posts/" normalized "/comentarios/"))))