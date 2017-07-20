(ns clojure-embedded-oo.core
 (:gen-class))

(def method-from-message
  (fn [message class]
    (message (:__instance_methods__ class))))

(def class-from-instance
  (fn [instance]
    (eval (:__class_symbol__ instance))))

(def class-instance-methods
  (fn [class-symbol]
    (:__instance_methods__ (eval class-symbol))))

(def apply-message-to
  (fn [class instance message args]
    (apply (or (method-from-message message class)
               message)
           instance args)))

(def make
 (fn [class & args]
   (let [seeded {:__class_symbol__ (:__own_symbol__ class)}]
    (apply-message-to class seeded :add-instance-values args))))
          
(def send-to
  (fn [instance message & args]
    (let [class (eval (:__class_symbol__ instance))]
      (apply-message-to (class-from-instance instance) instance message args))))

(def Point
  {
   :__own_symbol__ 'Point
   :__superclass_symbol__ 'Anything
   :__instance_methods__
   {
    :add-instance-values
    (fn [this x y]
      (assoc this :x x :y y))
    :x :x
    :y :y
    :origin (fn [this] (make Point 0 0))
    :class-name :__class_symbol__
    :class (fn [this] (eval (:__class_symbol__ this)))
    :shift (fn [this xinc yinc]
             (make Point (+ (:x this) xinc)
                         (+ (:y this) yinc)))
    :add (fn [this other_point]
           (send-to this :shift (send-to other_point :x)
                                (send-to other_point :y)))}}) 

(def Anything
  {
   :__own_symbol__ 'Anything
   :__instance_methods__
   {
    :add-instance-values identity

    :class-name :__class_symbol__
    :class (fn [this] (eval (:__class_symbol__ this)))}})
