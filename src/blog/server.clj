(ns blog.server
  (:require [noir.server :as server]
            [blog.models.utils :as db]))

(server/load-views-ns 'blog.views)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'blog})
    (db/maybe-init!)))

