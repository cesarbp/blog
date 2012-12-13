;;; Views to setup the database via the site
(ns blog.views.setup
  (:use [noir.core :only [defpage defpartial render]]
        hiccup.form
        blog.views.common
   [blog.views.utils :only [flash-message]])
  (:require
   [blog.models.comment :as comments]
   [blog.models.post :as posts]
   [blog.models.user :as users]
   [noir.response :as resp]))

(defpartial admin-pass-form []
  (form-to [:post "/admin/"]
           (label :pass "New pass")
           (password-field :pass)
           (submit-button {:class "button"} "Continuar")))

(defpage [:post "/admin/"] {:keys [pass]}
  (when-not (users/theres-admin?)
    (let [result (users/setup! pass)]
      (if (= :success result)
        (flash-message "The password has been initialized" :success)
        (flash-message "Error adding the password" :alert))
      (resp/redirect "/blog/"))))

(defpage "/admin/" []
  (if-not (users/theres-admin?)
    (base-layout {:content [:section.main
                            [:div.row
                             (admin-pass-form)]]})
    (do
      (flash-message "Already have an admin pass" :alert)
      (resp/redirect "/blog/"))))

(defpartial login-form []
  (form-to [:post "/login/"]
           (label :user "Usuario")
           (text-field :user)
           (label :pass "Password")
           (password-field :pass)
           (submit-button {:class "button"} "Firmarse")))

(defpage "/login/" []
  (base-layout {:content [:section.main
                          [:div.row
                           (login-form)]]}))

(defpage [:post "/login/"] {:keys [user pass]}
  (if (= user "admin")
    (let [result (users/verify-pass pass)]
      (if result
        (do
          (users/login!)
          (resp/redirect "/blog/"))
        (do (flash-message "Usuario o password incorrecto" :alert)
            (render "/login/"))))
    (do
      (flash-message "Usuario o password incorrecto" :alert)
      (render "/login/"))))

(defpage [:post "/reset/"] []
  (do (posts/setup!)
      (comments/setup!)
      (flash-message "Done!" :standard)
      (resp/redirect "/blog/")))
