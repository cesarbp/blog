(ns blog.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css include-js html5]]
        [hiccup.element :only [link-to]]
        [noir.session :only [flash-get]])
  (:require [blog.models.user :as users]))

(def res-includes
  {:base-css (include-css "/stylesheets/foundation.min.css")
   :base-js  (include-js "/javascripts/foundation.min.js")
   :app-js (include-js "/javascripts/app.js")
   :custom-css (include-css "/stylesheets/custom.css")
   :museo-css (include-css "/stylesheets/museo.css")
   :crimson-css (include-css "http://fonts.googleapis.com/css?family=Crimson+Text")
   :droidserif-css (include-css "http://fonts.googleapis.com/css?family=Droid+Serif")})

;;; incls is a sequence of keys that are in res-includes
(defpartial head [incls title]
  [:head
   [:meta {:charset "UTF-8"}]
   [:title (if (seq title) (str title " | BolPor Software")
               "BolPor Software")]
   (map res-includes incls)])

(def nav-links [["Company" "/company/"] ["Blog" "/blog/"] ["Contact" "/contact/"]])
(def nav-links-admin [["Company" "/company/"] ["Blog" "/blog/"] ["Contact" "/contact/"] ["New" "/blog/posts/new/"] ["Admin" "/admin/"] ["Messages" "/admin/messages/"] ["Logout" "/logout/"]])

(defpartial nav-bar [active nav-links]
  [:nav.top-bar
   [:ul
    [:li.name
     [:h1
      (link-to "/" "BolPor Software")]]
    [:li.toggle-topbar
     (link-to "#" "")]]
   [:section
    [:ul.right
     (interpose [:li.divider]
                (map (fn [[title lnk]]
                       [(if (and (string? active) (= title active)) :li.active :li) (link-to lnk title)])
                     nav-links))]]])

(defpartial display-messages [messages]
  [:div.row
   (map (fn [msg]
          (let [type (:type msg)
                alert-class (when-not (= type :standard) (name type))]
            [:div {:class (str "alert-box" (when alert-class " ") alert-class)}
             (:content msg)]))
        messages)])

(defpartial base-layout [content-map]
  (let [messages (flash-get :messages)]
    (html5
     (head [:base-css :droidserif-css :crimson-css :custom-css] (:title content-map))
     [:body.off-canvas
      (nav-bar (:active content-map) (if (users/admin?) nav-links-admin nav-links))
      (when messages
        (display-messages messages))
      (:content content-map)
      [:footer
       (res-includes :base-js)
       (res-includes :app-js)]])))
