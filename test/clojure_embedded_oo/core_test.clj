(ns clojure-embedded-oo.core-test
  (:require [clojure.test :refer :all]
            [clojure-embedded-oo.core :refer :all]))

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
