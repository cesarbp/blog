(ns blog.models.utils
  (:use somnium.congomongo
        [somnium.congomongo.config :only [*mongo-config*]]))

(def access-fpath "access.clj")

(defn maybe-init! []
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*))
    (let [{:keys [host user password port db]} (read-string (slurp access-fpath))]
      (println "Initializing mongo")
      (mongo! :db db :host host :port (Integer. port))
      (authenticate user password))))

;;; Does not keep the unmodified values
(defn modify-db-map [new old olds-key]
  (conj new {olds-key (conj (olds-key old) (dissoc old olds-key))}))
;;; Keeps the unmodified values and leaves them the same
(defn update-db-map [mod old olds-key]
  (conj old mod {olds-key (conj (olds-key old) (dissoc old olds-key))}))

;;; Get a string and create a mongo id out of it if its valid
(defn make-id [id]
  (try (object-id id)
       (catch IllegalArgumentException e
         nil)))

(defn remove-invalid-url-chars [s]
  (-> s
      (clojure.string/replace #"[^A-Za-z0-9-._~]+" "_")
      (clojure.string/replace #"_$" "")))

;;; Removes accents and other special chars from a string and replaces whitespace with
;;; underscores.
(defn normalize [str]
  (-> str
      (java.text.Normalizer/normalize java.text.Normalizer$Form/NFKD)
      (clojure.string/replace #"\p{InCombiningDiacriticalMarks}" "")
      (remove-invalid-url-chars)))

