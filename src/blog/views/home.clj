(ns blog.views.home
  (:use blog.views.common
        noir.core)
  (:require [noir.response :as resp]
            [blog.models.user :as users]))

(defpage "/" []
  (resp/redirect "/company/"))

(defpage "/logout/" []
  (when (users/admin?)
    (users/logout!)
    (resp/redirect "/company/")))

