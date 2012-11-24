(ns blog.models.utils
  (:use somnium.congomongo
        [somnium.congomongo.config :only [*mongo-config*]]))


(defn split-mongo-url [url]
  "Parses mongodb url from heroku, like mongodb://user:pass@localhost:1234/db"
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)] ;; Setup the regex.
    (when (.find matcher) ;; Check if it matches.
      (zipmap [:match :user :pass :host :port :db] (re-groups matcher)))))

(defn maybe-init! []
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*))
    (let [mongo-url (or (get (System/getenv) "MONGOHQ_URL") ;; Heroku location
                        "mongodb://admincesarbp:thisaintaverysecurewebsitejimmy@localhost:27017/blog") ;; Local db
          config (split-mongo-url mongo-url)]
      (println "Initializing mongo @ " mongo-url)
      (mongo! :db (:db config) :host (:host config) :port (Integer. (:port config)))
      (authenticate (:user config) (:pass config)))))

(defn modify-db-map [new old olds-key]
  (conj new {olds-key (conj (olds-key old) (dissoc old olds-key))}))
(defn update-db-map [mod old olds-key]
  (conj old mod {olds-key (conj (olds-key old) (dissoc old olds-key))}))

;;; Get a string and create a mongo id out of it if its valid
(defn make-id [id]
  (try (object-id id)
       (catch IllegalArgumentException e
         nil)))
;;; Removes accents and other special chars from a string and replaces whitespace with
;;; underscores.
(defn normalize [str]
  (-> str
      (java.text.Normalizer/normalize java.text.Normalizer$Form/NFKD)
      (clojure.string/replace #"\p{InCombiningDiacriticalMarks}" "")
      (clojure.string/replace #"\s+" "_")))

