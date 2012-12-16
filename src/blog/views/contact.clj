(ns blog.views.contact
  (:use [noir.core :only [defpage defpartial]]
        [blog.views.common :only [base-layout]]
        hiccup.form
        [hiccup.element :only [mail-to]]
        [blog.views.utils :only [flash-message]])
  (:require [noir.response :as resp]
            [blog.models.message :as messages]))

(defpartial contact-form []
  [:div.row
   [:h2 "Leave me a message"]
   (form-to [:post "/contact/"]
            (text-field {:placeholder "Your email"} :email)
            (text-field {:placeholder "Subject"} :subject)
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

(defpage [:post "/contact/"] {:keys [email subject message]}
  (let [result (messages/add-message email subject message)]
    (if (= result :success)
      (flash-message "Your message has been received, I will answer it shortly." :standard)
      (flash-message "There has been an error sending your message. Check that you've no missing fields." :alert))
    (resp/redirect "/company/")))







