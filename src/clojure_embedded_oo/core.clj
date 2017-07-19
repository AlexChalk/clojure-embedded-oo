(ns clojure-embedded-oo.core
  (:gen-class))

(def make
  (fn [object & args]
    (apply object args)))

(def send-to
  (fn [object message & args]
    (apply (message (:__methods__ object)) object args)))

(def Point
  (fn [x y]
    {:x x 
     :y y
     :__class_symbol__ 'Point
     :__methods__ {:class :__class_symbol__
                   :get-x (fn [this]
                            (:x this))
                   :get-y (fn [this]
                            (:y this))
                   :shift (fn [this xinc yinc]
                           (make Point (+ (send-to this :get-x) xinc)
                                       (+ (send-to this :get-y) yinc)))
                   :add (fn [this other_point]
                          (send-to this :shift (send-to other_point :get-x)
                                               (send-to other_point :get-y)))}})) 

(def y (fn [this] (:y this)))
(def x :x)
