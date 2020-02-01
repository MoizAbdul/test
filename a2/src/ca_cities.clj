(ns ca-cities
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def csv-file "data/ca.csv")
(defn- rad [degree] (* Math/PI (/ degree 180.)))

(defn reader []
  (io/reader csv-file)
  )

(defn parse-float [s]
  (Float/parseFloat s)
  )
(defn parse-int [s]
  (Integer/parseInt s)
  )

(defn parse-record [line]
  (def tV (string/split (str line) #","))
  {:name (nth tV 0),
 :lat (nth tV 1),
 :long (nth tV 2),
 :country (nth tV 3),
 :province (nth tV 5),
 :pop (nth tV 7)}
  )

(defn cities []
  (with-open [rdr (reader)]
  (vec (for [pr (next (line-seq rdr))] (parse-record pr))))
  )

(defn city [name]
  (first (filter #(= (:name %) name) (cities)))
  )

(defn distance [record1 record2]
  (def lat1 (rad (parse-float (get record1 :lat))))
  (def lat2 (rad (parse-float (get record2 :lat))))
  (def long1 (rad (parse-float (get record1 :long))))
  (def long2 (rad (parse-float (get record2 :long))))

  (def DiffLat (- lat2 lat1))
  (def DiffLong (- long2 long1))
  (def RadiusEarth 6371)
  (def a (+ (Math/pow (Math/sin ( / DiffLat 2)) 2) (* (Math/cos lat1) (Math/cos lat2) (Math/pow (Math/sin (/ DiffLong 2)) 2) )))
  (def c (* 2 (Math/atan2 (Math/sqrt a), (Math/sqrt (- 1 a)) )))
  (* RadiusEarth c)
  )
