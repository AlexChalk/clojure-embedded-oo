(ns clojure-embedded-oo.core-test
  (:require [clojure.test :refer :all]
            [clojure-embedded-oo.core :refer :all]))

(deftest x-test
  (testing "returns x property"
    (is (= (send-to (Point 1 2) :get-x)
           1))))

(deftest y-test
  (testing "returns y property"
    (is (= (send-to (Point 1 2) :get-y)
           2))))

(deftest class-of-test
  (testing "returns class"
    (is (= (send-to (Point 1 2) :class)
          'Point)))) 

(deftest shift-test
  (testing "moves point designated distance"
    (def point (send-to (Point 1 2) :shift 2 3))
    (is (and (= 3 (send-to point :get-x))
             (= 5 (send-to point :get-y))))))

(deftest add-test
  (testing "returns point that is sum of two arg points"
    (def point2 (send-to (Point 1 2) :add (Point 3 5)))
    (is (and (= 4 (send-to point2 :get-x))
             (= 7 (send-to point2 :get-y))))))

(deftest send-to-test
  (testing "send-to function calls an object method on object"
    (def point3 (make Point 1 2))
    (is (= (send-to point3 :get-x)
           1))))

(deftest make-test
  (testing "make function can create a point"
    (is (= (send-to (make Point 1 2) :class)
           'Point))))
