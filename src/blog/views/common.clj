(ns blog.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css include-js html5]]
        [hiccup.element :only [link-to]]))

(def res-includes
  {:base-css (include-css "/stylesheets/foundation.min.css")
   :base-js  (include-js "/javascripts/foundation.min.js")
   :custom-css (include-css "/stylesheets/custom.css")}
  )

;;; incls is a sequence of keys that are in res-includes
(defpartial head [incls title]
  [:head
   [:meta {:charset "UTF-8"}]
   [:title (if (seq title) (str title " | BolPor Software")
               ("BolPor Software"))]
   (map res-includes incls)])

(defpartial nav-bar [active]
  (let [nav-links [["Empresa" "/empresa/"] ["Blog" "/blog/"] ["Contacto" "/contacto/"]]]
    [:nav.top-bar
     [:ul
      [:li.name
       [:h1
        (link-to "/" "BolPor Software")]]]
     [:section
      [:ul.right
       (interpose [:li.divider]
                  (map (fn [[title lnk]]
                         [(if (and (string? active) (= title active)) :li.active :li) (link-to lnk title)])
                       nav-links))]]]))

(defpartial base-layout [content-map]
  (html5
   (head [:base-css :base-js :custom-css] (:title content-map))
   [:body
    (nav-bar (:active content-map))
    (:content content-map)]))
