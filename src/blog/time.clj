(ns blog.time
  (:require [clj-time.local :as t]
            [clj-time.format :as f]
            [clj-time.core :as c]
            [clojure.string :as s]))

(defn now []
  (str (t/local-now)))

(defn get-date [time-as-str]
  (first (s/split time-as-str #"T")))

(defn get-hour [time-as-str]
  (second (s/split time-as-str #"(T|\.)")))

(defn get-timezone [time-as-str]
  (str (c/default-time-zone)))

