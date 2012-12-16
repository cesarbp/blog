(ns blog.views.message
  (:use noir.core
        blog.views.common)
  (:require [blog.models.message :as messages]))

(defpartial show-messages [messages]
  (if-not (seq messages)
    [:p "No messages to be shown."]
    [:table
     [:tr
      [:th "Date"]
      [:th "Email"]
      [:th "Subject"]
      [:th "Message"]]
     (map (fn [m]
            [:tr
             [:td (:date m)]
             [:td (:email m)]
             [:td (:subject m)]
             [:td (:message m)]])
          messages)]))

(defpartial main-section [messages]
  [:div.row
   [:div.twelve.columns
    (show-messages messages)]])

(defpage "/admin/messages/" []
  (let [messages (messages/get-all-messages)]
    (base-layout {:content (main-section messages)
                  :title "Messages"
                  :active "Messages"})))
