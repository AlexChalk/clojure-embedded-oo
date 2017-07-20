(ns clojure-embedded-oo.core
 (:gen-class))

(def apply-message-to 
  (fn [class instance message args]
    (let [method (message (:__instance_methods__ class))]
      (apply method instance args))))
                                                 
(def make
 (fn [class & args]
   (let [seeded {:__class_symbol__ (:__own_symbol__ class)}]
    (apply-message-to class seeded :add-instance-values args))))
          
(def send-to
  (fn [instance message & args]
    (let [class (eval (:__class_symbol__ instance))]
      (apply-message-to class instance message args))))

(def Point
  {
   :__own_symbol__ 'Point
   :__instance_methods__
   {
    :add-instance-values
    (fn [this x y]
      (assoc this :x x :y y))
    :x :x
    :y :y
    :class :__class_symbol__
    :shift (fn [this xinc yinc]
             (make Point (+ (:x this) xinc)
                         (+ (:y this) yinc)))
    :add (fn [this other_point]
           (send-to this :shift (send-to other_point :x)
                                (send-to other_point :y)))}}) 

