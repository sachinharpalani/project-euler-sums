
(ns project-euler-sums.core-test
  (:require #_[clojure.test :refer :all]
            [project-euler-sums.core :refer :all]
            [expectations :as expect]))

#_(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
;; Problem 1 Test Cases
(expect/expect 23 (sum-of-multi 10))

(expect/expect 233168 (sum-of-multi 1000))

;; Problem 2 Test Cases

(expect/expect '(1 2 3 5 8 13 21 34 55 89) (take 10 (get-fibo-num)))
#_(expect/expect 44 (sum-even-fibo 10))
(expect/expect 4613732 (sum-even-fibo))

;; Problem 3 Test Cases

(expect/expect '(1 3 5) (get-factors 15))
(expect/expect 29 (largest-prime-factor 13195))
(expect/expect 29 (largest-prime-factor-1 13195))
(expect/expect 6857 (largest-prime-factor-1 600851475143))

;; Problem 4 Test Cases

(expect/expect [906609] (largest-palin))

;; Problem 5 Test Cases

(expect/expect nil (isdiv? 2520))
#_(expect/expect 232792560 (smallest-div-num)) ;;Returns success but takes too much time to execute

;; Problem 6 Test Cases

(expect/expect 338350 (sum-of-squares))
(expect/expect 25502500 (square-of-sum))
(expect/expect 25164150 (final-difference))

;; Problem 7 Test Cases

(expect/expect true (prime? 7))
(expect/expect false (prime? 9))
(expect/expect 13 (prime-no-index 6))

;; Problem 8 Test

;; Problem 9 Test Cases

(expect/expect true (triplet? 3 4 5))
(expect/expect false (triplet? 6 7 8))

(expect/expect 12 (special-triplet 1 2 3))
