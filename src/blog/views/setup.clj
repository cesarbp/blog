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
  (form-to [:post "/admin/reset-pass/"]
           (label :pass "New pass")
           (password-field :pass)
           (submit-button {:class "button"} "Continuar")))

(defpartial reset-form []
  (form-to [:post "/admin/reset/"]
           (label :pass "Confirm pass to reset")
           (password-field :pass)
           (submit-button {:class "button"} "Reset db")))

(defpage "/admin/" []
  (let [content [:section#main
                 (admin-pass-form)
                 (reset-form)]]
    (base-layout {:content content
                  :active "Admin"
                  :title "Admin"})))

(defpage [:post "/admin/reset-pass/"] {:keys [pass]}
  (let [result (users/setup! pass)]
    (if (= :success result)
      (flash-message "The password has been initialized" :success)
      (flash-message "Error adding the password" :alert))
    (resp/redirect "/blog/")))

(defpartial login-form []
  (form-to [:post "/login/"]
           (label :user "User")
           (text-field :user)
           (label :pass "Password")
           (password-field :pass)
           (submit-button {:class "button"} "Login")))

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

(defpage [:post "/admin/reset/"] {:keys [pass]}
  (if (users/verify-pass pass)
    (do
      (posts/setup!)
      (comments/setup!)
      (flash-message "Done!" :standard)
      (resp/redirect "/blog/"))
    (do
      (flash-message "Error" :alert)
      (resp/redirect "/blog/"))))