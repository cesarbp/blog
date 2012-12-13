;;; Sales point views
(ns blog.views.spoint
  (:use blog.views.common
        noir.core
        [hiccup.element :only [link-to image]]))

(defpartial row-divider []
  [:div.row
   [:hr]])

(defpartial title []
  [:div.row
   [:h1 "Punto de ventas y sitio de administración"]
   [:p "Este programa permite la administración de un negocio pequeño o mediano y tiene la capacidad de funcionar como cajero. Puede funcionar a través de internet o de manera local. Puede funcionar en cualquier computadora que disponga de un navegador de internet<small>1</small>. Y funciona con los dispositivos comunes como lector de código de barras e impresora de matriz de puntos<small>2</small>."]
   [:p "A continuación puede ver imágenes con descripciones de cada una de las características de este producto."]])

(defpartial cashier-feature []
  [:div.row
   [:div.twelve.columns.product-feature
    [:div.four.columns.feature-description
     [:h2 "Cajero"]
     [:p "Cuenta con un cajero con todas las características que puede esperar."]
     [:ul.feature-description-list
      [:li "Funciona completamente con el teclado y lector de código de barras (aunque el ratón puede ayudar)."]
      [:li "Interfaz fácil de usar."]
      [:li "Permite agregar artículos que no se encuentren registrados a la venta."]
      [:li "Incluye más controles, más intuitivos y atractivos que un punto de ventas común, ya que utiliza todo el poder gráfico que ofrece una página web."]]]
    [:div.eight.columns.feature-image
     (image "/images/sp-pitch.png" "Cajero")]]])

(defpartial ticket-feature []
  [:div.row
   [:div.twelve.columns.product-feature
    [:div.four.columns.feature-description
     [:h2 "Tickets y facturas"]
     [:p "Impresión de tickets y facturas, que funcionan con dispositivos comunes. Como impresoras de matriz de puntos. Si el dispositivo puede conectarse a su computadora, el sistema lo puede utilizar."]]
    [:div.eight.columns.feature-image
     (image "/images/sp-ticket.png" "Ticket de venta")]]])

(defpartial articles-feature []
  [:div.row
   [:div.twelve.columns.product-feature
    [:div.four.columns.feature-description
     [:h2 "Diseñado para el usuario."]
     [:p "Consultas y administración de artículos personalizada."]
     [:ul.feature-description-list
      [:li "Sistema completo de administración de artículos."]
      [:li "Opciones personalizadas."]
      [:li "La información necesaria de la manera más pronta y clara."]]]
    [:div.eight.columns.feature-image
     (image "/images/sp-search.png" "Resultados de una busqueda")]]])

(defpartial dataquality-feature []
  [:div.row
   [:div.twelve.columns.product-feature
    [:div.four.columns.feature-description
     [:h2 "El sistema trabaja con tu base de datos actual."]
     [:p "Una fácil transición para tu base de datos."]
     [:ul.feature-description-list
      [:li "Herramientas para mantener una buena calidad en los datos."]
      [:li "Tanto para detectar errores como para corregirlos."]
      [:li "Criterios personalizados para mantener la base de datos en un buen estado."]]]
    [:div.eight.columns.feature-image
     (image "/images/sp-dberrors.png" "Corregir errores de la base de datos")]]])

(defpartial backups-feature []
  [:div.row
   [:div.twelve.columns.product-feature
    [:div.four.columns.feature-description
     [:h2 "Sistema completo de respaldos."]
     [:p "Elige dónde y con qué frecuencia se respaldan los datos de tu negocio."]
     [:ul.feature-description-list
      [:li "Respaldos automáticos o manuales."]
      [:li "Facilidad para trabajar a partir de un respaldo."]]]
    [:div.eight.columns.feature-image
     (image "/images/sp-backups.png" "Respaldos de la base de datos")]]])

(defpartial security-feature []
  [:div.row
   [:div.twelve.columns.product-feature
    [:div.four.columns.feature-description
     [:h2 "La seguridad necesaria para su negocio."]
     [:p "Sistema de cuentas de usuario."]
     [:ul.feature-description-list
      [:li "Cada uno con sus privilegios necesarios."]
      [:li "Protección ante intrusos."]]]
    [:div.eight.columns.feature-image
     (image "/images/sp-security.png" "Seguridad del sistema")]]])

(defpartial platforms-feature []
  [:div.row
   [:div.twelve.columns.product-feature
    [:div.four.columns.feature-description
     [:h2 "Sistema multiplataforma."]
     [:p "Funciona en Windows, Mac OS y Linux, con cualquier navegador moderno."]
     [:ul.feature-description-list
      [:li "El sistema puede estar distribuído o en una sola computadora."]
      [:li "Cada máquina puede tener un diferente sistema operativo."]
      [:li "La experiencia es la misma independiente de la plataforma en la que se encuentre."]
      [:li "¡El sistema también funciona en dispositivos móbiles!"]]]
    [:div.eight.columns.feature-image
     (image "/images/sp-platforms.png" "Sistema multiplataforma")]]])

(defpartial main-section []
  [:section.main
   (title)
   (row-divider)
   (cashier-feature)
   (ticket-feature)
   (articles-feature)
   (dataquality-feature)
   (backups-feature)
   (security-feature)
   (platforms-feature)])

(defpage "/punto-venta/" []
  (base-layout {:content (main-section)
                :active "Empresa"}))