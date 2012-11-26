(ns blog.views.contact
  (:use [noir.core :only [defpage defpartial]]
        [blog.views.common :only [base-layout]]
        hiccup.form
        [hiccup.element :only [mail-to]]
        [blog.views.utils :only [flash-message]])
  (:require [noir.response :as resp]))

(defpartial contact-form []
  [:div.row
   [:h3 "Envíanos un mensaje:"]
   (form-to [:post "/contacto/"]
            (text-field {:placeholder "Asunto"} :title)
            [:textarea.email-message {:placeholder "Mensaje" :name :message}]
            (submit-button {:class "button"} "Enviar mensaje"))])

(defpartial row-divider []
  [:div.row
   [:div.twelve.columns
    [:hr]]])

(defpartial contact-info []
  [:div.row
   [:h3 "¿Cómo contactarnos?"]
   [:p "Envíe un correo a " (mail-to "syscsoftware@gmail.com") " o llámenos al teléfono (722) 123 4567"]
   [:p "Dirección: Calle uno # 11, Colonia Santo Irineo, Toluca, Estado de México."]])

(defpartial main-section []
  [:section.main
   (contact-form)
   (row-divider)
   (contact-info)])

(defpage "/contacto/" []
  (let [content {:content (main-section)
                 :active "Contacto"
                 :title "Contacto"}]
    (base-layout content)))

(defpage [:post "/contacto/"] []
  (flash-message "Su mensaje ha sido enviado, le responderemos en unos cuantos minutos." :standard)
  (resp/redirect "/empresa/"))