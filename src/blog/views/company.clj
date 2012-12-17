(ns blog.views.company
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.element :only [link-to mail-to image]]
        [blog.views.common :only [base-layout]]))


(defpartial header-part []
  [:div.row.company-header
   [:h1.Museo-300 "BolPor - Software that generates value for your business"]])

(defpartial row-divider []
  [:div.row
   [:hr]])

(def slide-imgs ["slides1.png" "slides2.png" "slides3.png" "slides4.png"])

(defpartial slides []
  [:div#slides
   (map (fn [p]
          (image (str "/images/" p)))
        slide-imgs)])

(defpartial products-list []
  [:div.row.products-list
   [:h2 "Our products"]
   [:div.row.product-row
    [:div.four.columns.product-description
     [:h3 "Sales Point"]
     [:p "Sales points and admin systems for medium and small businesses."]
     [:p "Features:"]
     [:ul.feature-description-list
      [:li "A cashier."]
      [:li "Management capabilities."]
      [:li "Works with standard devices."]
      [:li "Works locally or over the Internet."]
      [:li "Simple, intuitive and beautiful."]]
     [:div {:style "text-align:center;"}
      (link-to {:class "small button"} "/sales-point/" "View details")]]
    [:div.eight.columns.slides
     (slides)]]])

(defpartial who-part []
  [:div.row.who-part
   [:h3 "BolPor Software"]
   [:p "My name is César Bolaños, I have studies in software engineering. I do software development and consulting for medium and small companies. If you are interested in either of those things, send an email to " (mail-to "cesar@bolpor.com") " and I shall answer you as soon as possible."]
   [:p "If you aren't interested you can still read my " (link-to "/blog/" "blog") ", where I talk about software, my company, my projects and other things I deem of interest."]])

(defpartial main-section []
  [:section.main
   [:div.twelve.columns
    (header-part)
    (row-divider)
    (products-list)
    (row-divider)
    (who-part)]])

(defpage "/company/" []
  (let [content {:content (main-section)
                 :active "Company"
                 :title "Company"}]
    (base-layout content)))