(ns blog.views.preroutes
  (:use [noir.core :only [pre-route]]
        [blog.views.utils :only [flash-message]])
  (:require [noir.response :as resp]
            [blog.models.user :as users]))

(defn deny-access [redirect-url]
  (when-not (users/admin?)
    (flash-message "No tiene permisos para entrar a esta página" :alert)
    (resp/redirect redirect-url)))

;; (pre-route "/admin/*" []
;;            (deny-access "/"))

(pre-route "/posts/nuevo/*" []
           (deny-access "/blog/"))