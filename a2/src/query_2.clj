(ns query-2
  (:require [ca-cities :as ca]))

; returns the total population of the cities
(defn total-population [cities]
  (apply + (map ca/parse-int (vec (map :pop cities))))
  )

; list of sorted [province-name population] pairs
(defn provincial-population
  []
  (sort-by second
    (for [list (group-by :province (ca/cities))]  [(first list), (total-population (second list))])
    )
  )
