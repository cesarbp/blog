(ns blog.views.company
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.element :only [link-to image]]
        [blog.views.common :only [base-layout]]))


(defpartial header-part []
  [:div.row.company-header
   [:h1.Museo-300 "BolPor - Software que genera valor para su negocio."]])

(defpartial row-divider []
  [:div.row
   [:hr]])

(defpartial products-list []
  [:div.row.products-list
   [:h2 "Nuestros productos"]
   [:div.row.product-row
    [:div.four.columns.product-description
     [:h3 "Puntos de venta"]
     [:p "Puntos de venta y sitios de administración para negocios medianos y pequeños"]
     (link-to "/punto-venta/" "> Ver detalles")]
    [:div.eight.columns
     (image "/images/sp-pitch.png" "Pantalla del punto de ventas")]]])

(defpartial who-part []
  [:div.row.who-part
   [:h3 "BolPor Software"]
   [:p "Mi nombre es César Bolaños, tengo estudios en ingeniería en sistemas. Me dedico a crear productos de software y a dar servicios de consultoría a pequeñas y medianas empresas. Si alguna de estas cosas te interesa, puedes enviarme un correo a cesar@bolpor.com, respondo a <i>casi</i> todos mis correos conforme me lo permite mi tiempo. Me interesa mucho hacer crecer a tu pequeña o mediana empresa."]
   [:p "Si no te interesan mis servicios o productos puedes leer mi " (link-to "/blog/" "blog") ", donde hablo hacerca de software, mi empresa, mis proyectos y otras cosas de interés."]])

(defpartial main-section []
  [:section.main
   (header-part)
   (row-divider)
   (products-list)
   (row-divider)
   (who-part)])

(defpage "/empresa/" []
  (let [content {:content (main-section)
                 :active "Empresa"
                 :title nil}]
    (base-layout content)))