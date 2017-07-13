(ns clojure-embedded-oo.core
  (:gen-class))

(def Point
  (fn [x y]
    {:x x 
     :y y
     :__class_symbol__ 'Point}))

;; The two definitions below are equivalent
(def y (fn [this] (:y this)))
(def x :x)

(def class-of :__class_symbol__)

(def shift
  (fn [this xinc yinc]
    (Point (+ (x this) xinc)
           (+ (y this) yinc))))

(def Triangle
  (fn [point1 point2 point3]
    {:point1 point1, :point2 point2, :point3 point3
     :__class_symbol__ 'Triangle}))


(def right-triangle (Triangle (Point 0 0)
                              (Point 0 1)
                              (Point 1 0)))

(def equal-right-triangle (Triangle (Point 0 0)
                                    (Point 0 1)
                                    (Point 1 0)))

(def different-triangle (Triangle (Point 0 0)
                                  (Point 0 10)
                                  (Point 10 0)))

(def add-no-shift
  (fn [point1 point2]
    (Point (+ (x point1) (x point2))
           (+ (y point1) (y point2)))))

(def add-with-shift
  (fn [point1 point2]
    (shift point1 (x point2) (y point2))))

