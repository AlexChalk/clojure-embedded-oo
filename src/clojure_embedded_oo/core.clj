(ns clojure-embedded-oo.core
 (:gen-class))

(def class-from-instance
  (fn [instance]
    (eval (:__class_symbol__ instance))))

(def class-instance-methods
  (fn [class-symbol]
    (:__instance_methods__ (eval class-symbol))))

(def class-symbol-above
  (fn [class-symbol]
    (assert (symbol? class-symbol))
    (:__superclass_symbol__ (eval class-symbol))))

(def lineage-1
  (fn [class-symbol so-far]
    (if (nil? class-symbol)
      so-far
      (lineage-1 (class-symbol-above class-symbol)
                 (cons class-symbol so-far)))))
(def lineage
  (fn [class-symbol]
    (lineage-1 class-symbol [])))

(def method-cache
  (fn [class]
    (let [class-symbol (:__own_symbol__ class)
          method-maps (map class-instance-methods
                           (lineage class-symbol))]
      (apply merge method-maps))))

(def apply-message-to
  (fn [class instance message args]
    (apply (or (message (method-cache class))
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
    :hey-class-symbol-please :__class_symbol__
    :class-name :__class_symbol__
    :class (fn [this] (eval (:__class_symbol__ this)))}})

