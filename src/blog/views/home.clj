(ns blog.views.home
  (:use blog.views.common
        noir.core)
  (:require [noir.response :as resp]))

(defpage "/" []
  (resp/redirect "/blog/"))

