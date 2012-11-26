(ns blog.views.company
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.element :only [link-to]]
        [blog.views.common :only [base-layout]]))


(defpartial header-part []
  [:div.row.company-header
   [:h1.Museo-300 "SYSC - Soluciones para negocios"]
   [:h3 "Consultoría dedicada a la promoción del crecimiento de empresas."]])

(defpartial row-divider []
  [:div.row
   [:div.twelve.columns
    [:hr]]])

(defpartial products-list []
  [:div.row.products-list
   [:div.four.columns
    [:a.product-item {:href "#"}
     [:h3 "Puntos de venta"]
     [:p "Puntos de venta y sitios de administración para mercados medianos y pequeños"]]]
   [:div.four.columns
    [:a.product-item {:href "#"}
     [:h3 "Sistemas de control escolar"]
     [:p "Sistemas para la administración de escuelas medianas y pequeñas"]]]
   [:div.four.columns
    [:a.product-item {:href "#"}
     [:h3 "Blogs"]
     [:p "Crea tus propios blogs de manera sencilla."]]]])

(defpartial services-list []
  [:div.row.services-list
   [:div.four.columns
    [:h4 "Consultoría para negocios"]
    [:p "Nosotros estamos comprometidos a encontrar puntos de mejora en su negocio. Con el fin de incrementar sus ganancias y reducir sus costos."]]
   [:div.four.columns
    [:h4 "Soluciones incrementales"]
    [:p "Cada semana es un nuevo hito. Su empresa puede ver un progreso y crecimiento significativo apenas unas semanas de habernos contratado."]]
   [:div.four.columns
    [:h4 "Precios y tiempos de entrega claros"]
    [:p "Después de un análisis de su negocio. Se generan propuestas con sus tiempos de finalización y su costo. Ud. podrá escoger de manera clara las soluciones que desea para su negocio sabiendo exactamente el tiempo y precio requerido."]]])

(defpartial who-part []
  [:div.row.who-part
   [:h3 "¿Quiénes somos?"]
   [:p "SYSC es una empresa de consultoría dedicada a efectuar el crecimiento de sus clientes."]
   [:h3 "¿Qué ofrecemos?"]
   [:p "La mayoría de nuestras soluciones involucran software. Creamos herramientas para incrementar el potencial de su negocio, incrementar su clientela, mejorar su distribución, automatizar sus procesos, analizar sus resultados y ayudar en la toma de decisiones."]
   [:h3 "¿Cómo trabajamos?"]
   [:p "Nosotros realizamos primero un estudio de su negocio. Encontramos puntos de mejora y a través de esto generamos un menú de propuestas. A cada una de estas propuestas le asignamos un precio y un tiempo para finalizarla. Su empresa puede entonces escoger las soluciones que mejor le convengan. Nos compromentemos a mostrar resultados cada semana."]])

(defpartial main-section []
  [:section.main
   (header-part)
   (row-divider)
   (products-list)
   (row-divider)
   (services-list)
   (row-divider)
   (who-part)])

(defpage "/empresa/" []
  (let [content {:content (main-section)
                 :active "Empresa"
                 :title nil}]
    (base-layout content)))