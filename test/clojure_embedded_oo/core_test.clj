(ns clojure-embedded-oo.core-test
  (:require [clojure.test :refer :all]
            [clojure-embedded-oo.core :refer :all]))

(deftest point-test
  (testing "creates a point object"
    (is (= (Point 1 2)
         {:x 1, :y 2, :__class_symbol__ 'Point}))))

(deftest x-test
  (testing "returns x property"
    (is (= (x (Point 1 2))
           1))))

(deftest y-test
  (testing "returns y property"
    (is (= (y (Point 1 2))
           2))))

(deftest class-of-test
  (testing "returns class"
    (is (= (class-of (Point 1 2))
          'Point)))) 

(deftest shift-test
  (testing "moves point designated distance"
    (is (= (shift (Point 1 2) 2 3)
           {:x 3, :y 5, :__class_symbol__ 'Point}))))

(deftest add-no-shift-test
  (testing "returns point that is sum of two arg points"
    (is (= (add-no-shift (Point 1 2) (Point 2 3))
           {:x 3, :y 5, :__class_symbol__ 'Point}))))

(deftest add-with-shift-test
  (testing "returns point that is sum of two arg points"
    (is (= (add-with-shift (Point 1 2) (Point 2 3))
           {:x 3, :y 5, :__class_symbol__ 'Point}))))

(deftest triangle-test
  (testing "creates a triangle object"
    (is (= (Triangle (Point 1 2)
                     (Point 1 3)
                     (Point 3 1))
           {:__class_symbol__ 'Triangle
            :point1 {:__class_symbol__ 'Point :x 1 :y 2}
            :point2 {:__class_symbol__ 'Point :x 1 :y 3}
            :point3 {:__class_symbol__ 'Point :x 3 :y 1}}))))

(deftest make-test
  (testing "make function can create a point"
    (is (= (make Point 1 2)
           {:x 1, :y 2, :__class_symbol__ 'Point})))

  (testing "make function can create a triangle"
    (is (= (make Triangle (make Point 1 2)
                          (make Point 1 3)
                          (make Point 3 1))
           {:__class_symbol__ 'Triangle
            :point1 {:__class_symbol__ 'Point :x 1 :y 2}
            :point2 {:__class_symbol__ 'Point :x 1 :y 3}
            :point3 {:__class_symbol__ 'Point :x 3 :y 1}}))))

(deftest equal-triangles-test
  (def right-triangle (Triangle (Point 0 0)
                                (Point 0 1)
                                (Point 1 0)))

  (def equal-right-triangle (Triangle (Point 0 0)
                                      (Point 0 1)
                                      (Point 1 0)))

  (def different-triangle (Triangle (Point 0 0)
                                    (Point 0 10)
                                    (Point 10 0)))

  (testing "reports right triangle is equal to right triangle"
    (is (= (equal-triangles? right-triangle right-triangle)
           true)))

  (testing "reports right triangle is equal to equal-right-triangle"
    (is (= (equal-triangles? right-triangle equal-right-triangle)
           true)))

  (testing "reports right-triangle != different-triangle"
    (is (= (equal-triangles? right-triangle different-triangle)
           false)))

  (testing "correctly compares multiple triangles"
    (is (= (equal-triangles? right-triangle 
                             equal-right-triangle
                             different-triangle)
           false))))

(deftest valid-triangle-test
  (testing "returns true when no points are duplicates"
    (is (= (valid-triangle? (Point 1 2) (Point 0 1) (Point 0 2))
           true)))

  (testing "returns false when one or more points are duplicates"
    (is (= (valid-triangle? (Point 1 2) (Point 0 2) (Point 0 2))
           false)))

  (testing "returns false when no of points != 3"
    (is (= (valid-triangle? (Point 1 2) (Point 0 2))
           false))))
