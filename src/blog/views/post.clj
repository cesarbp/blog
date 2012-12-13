(ns blog.views.post
  (:use noir.core
        blog.views.common
        hiccup.form
        [blog.views.utils :only [flash-message]])
  (:require [blog.models.post :as posts]
            [noir.response :as resp]))

(defpartial new-post-form []
  (form-to [:post "/posts/nuevo/"]
    [:div.row
     [:p "La primera linea del post es el titulo"]]
    [:div.row
     [:textarea {:name :content :placeholder "Contenido" :style "height:640px;"}]]
    [:div.row
     [:div.four.colums.offset-by-eight
      (submit-button {:class "button"} "Nuevo Post")]]))

(defpage "/posts/nuevo/" []
  (let [content {:content (new-post-form)
                 :title "Agregando un nuevo post"
                 :active "Nuevo"}]
    (base-layout content)))

(defpage [:post "/posts/nuevo/"] {:keys [content]}
  (let [result (posts/add-post content)]
    (if (= :success result)
      (flash-message "El post ha sido agregado." :success)
      (flash-message "El post no ha podido ser agregado." :alert))
    (resp/redirect "/blog/")))