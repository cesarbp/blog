;;; Views to setup the database via the site
(ns blog.views.setup
  (:use [noir.core :only [defpage]]
   [blog.views.utils :only [flash-message]])
  (:require
   [blog.models.comment :as comments]
   [blog.models.post :as posts]
   [blog.models.user :as users]
   [noir.response :as resp]))

(defpage "/reset/" []
  (do (users/setup! "password")         ;This is totally final
      (posts/setup!)
      (comments/setup!)
      (flash-message "Done!" :standard)
      (resp/redirect "/blog/")))