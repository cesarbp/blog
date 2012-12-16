(ns blog.views.preroutes
  (:use [noir.core :only [pre-route]]
        [blog.views.utils :only [flash-message]])
  (:require [noir.response :as resp]
            [blog.models.user :as users]))

(defn deny-access [redirect-url]
  (when-not (users/admin?)
    (flash-message "You can't access this page." :alert)
    (resp/redirect redirect-url)))

(pre-route "/admin/*" []
           (deny-access "/company/"))
(pre-route "/posts/nuevo/*" []
           (deny-access "/company/"))