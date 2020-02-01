(ns query-1
  (:require [ca-cities :as ca]))

; check if the city has more than 0.5E6 people
(defn large? [city]
  (if (> (ca/parse-int (get city :pop)) 500000) true false)
  )

; check if the two cities are no more than 600 km apart
(defn close? [c1 c2]
  (if (< (ca/distance c1 c2) 600) true false)
  )

; find distinct pairs of city names of *large* cities
; that are close.
(defn closest-city-pairs []
  (def city1 (filter identity (for [city (ca/cities)] (if (large? city) city))))
  (def pairs (filter identity (for [c1 city1 c2 city1]
    (if (close? c1 c2)
      (if (> (count (get c1 :name)) (count (get c2 :name)))
        [(get c1 :name), (get c2 :name), (ca/distance c1 c2)] [(get c2 :name), (get c1 :name), (ca/distance c1 c2)])))))
  (drop-last 2 (distinct (filter identity (for [p pairs] (if (not= (first p) (second p)) p)))))
  )
;(map #(large? %) (ca/cities))
