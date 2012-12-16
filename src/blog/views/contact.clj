(ns blog.views.contact
  (:use [noir.core :only [defpage defpartial]]
        [blog.views.common :only [base-layout]]
        hiccup.form
        [hiccup.element :only [mail-to]]
        [blog.views.utils :only [flash-message]])
  (:require [noir.response :as resp]))

(defpartial contact-form []
  [:div.row
   [:h2 "Leave me a message"]
   (form-to [:post "/contact/"]
            (text-field {:placeholder "Your email"} :email)
            (text-field {:placeholder "Subject"} :title)
            [:textarea.email-message {:placeholder "Message" :name :message}]
            (submit-button {:class "button"} "Send message"))])

(defpartial row-divider []
  [:div.row
   [:div.twelve.columns
    [:hr]]])

(defpartial contact-info []
  [:div.row
   [:h2 "Or send me an email"]
   [:p "Send it to " (mail-to "cesar@bolpor.com") " and I will answer it as quickly as I can (time permitting)."]])

(defpartial main-section []
  [:section.main
   (contact-form)
   (row-divider)
   (contact-info)])

(defpage "/contact/" []
  (let [content {:content (main-section)
                 :active "Contact"
                 :title "Contact"}]
    (base-layout content)))

(defpage [:post "/contacto/"] []
  (flash-message "Your message has been received, we will answer it shortly." :standard)
  (resp/redirect "/empresa/"))