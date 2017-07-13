(ns clojure-embedded-oo.core
  (:gen-class))

(def Point
  (fn [x y]
    {:x x 
     :y y
     :__class_symbol__ 'Point}))

;; The two below are equivalent
(def y (fn [this] (:y this)))
(def x :x)

(def class-of :__class_symbol__)
(def shift
  (fn [this xinc yinc]
    (Point (+ (x this) xinc)
           (+ (y this) yinc))))


(def first-object (Point 3 2))
(x first-object)
(shift first-object -1 -200)

