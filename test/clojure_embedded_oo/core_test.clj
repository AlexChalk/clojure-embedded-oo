(ns clojure-embedded-oo.core-test
  (:require [clojure.test :refer :all]
            [clojure-embedded-oo.core :refer :all]))

(deftest x-test
 (testing "returns x property"
   (is (= (send-to (make Point 1 2) :x)
          1))))

(deftest y-test
  (testing "returns y property"
    (is (= (send-to (make Point 1 2) :y)
           2))))

(deftest class-name-test
  (testing "returns class"
    (is (= (send-to (make Point 1 2) :class-name)
          'Point)))) 

(deftest shift-test
  (testing "moves point designated distance"
    (def point (send-to (make Point 1 2) :shift 2 3))
    (is (and (= 3 (send-to point :x))
             (= 5 (send-to point :y))))))

(deftest add-test
  (testing "returns point that is sum of two arg points"
    (def point2 (send-to (make Point 1 2) :add (make Point 3 5)))
    (is (and (= 4 (send-to point2 :x))
             (= 7 (send-to point2 :y))))))

(deftest send-to-test
  (testing "send-to function calls an object method on object"
    (def point3 (make Point 1 2))
    (is (= (send-to point3 :x)
           1)))

  (testing "if function call is unknown, send-to will return any matching object attribute"
    (def point4 (make Point 1 2))
    (is (= (send-to point4 :__class_symbol__)
         'Point))))

(deftest make-test
  (testing "make function can create a point"
    (is (= (send-to (make Point 1 2) :class-name)
           'Point))))
