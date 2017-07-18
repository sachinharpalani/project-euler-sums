(ns project-euler-sums.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; Problem 1

(defn sum-of-multi [inp]
  "Returns the sum of al numbers divisible by 3 & 5"
  (reduce + (filter (fn[x] (if(or (= 0 (mod x 3)) (= 0 (mod x 5))) x)) (range 1 inp))))

;; Problem 2

(defn get-fibo-num
  "REturns lazy sequence of fibonacci numbers"
  ([] (get-fibo-num 1 2))
  ([a b]
   (lazy-seq (cons a (get-fibo-num b (+ a b))))))

#_(defn sum-even-fibo []
  "Returns sum of even valued fibonacci series"
  (reduce +
          (filter (fn [x] (if (<= x 4000000)
                            x)) (filter even? (get-fibo-num)))))

(defn sum-even-fibo []
  "Returns sum of even valued fibonacci series"
  (reduce +
          (take-while (fn [x] (if (<= x 4000000)x)) (filter even? (get-fibo-num)))))

;; Problem 3

;;Ineffeicient solution- Crashes in repl execution
(defn get-factors [inp]
  "Returns the factors of given number"
  (filter (fn [x] (if (= (mod inp x) 0) x)) (range 1 inp)))

(defn largest-prime-factor [inp]
  "Returns the largest prime factor of a number"
  (first (reverse
          (filter (fn [x]
                    (if (= 1 (count (get-factors x)))
                      x))
                  (get-factors inp)))))

#_(largest-prime-factor 600851475143)

;;Efficient one

(defn helper-large [inp fact]
  (loop [num inp
         div 2
         fact []]
    (if (= num 1)
      (last fact)
      (if (= 0 (mod num div))
        (recur (/ num div) div (conj fact div))
        (recur num (inc div) fact)))))

(defn largest-prime-factor-1 [inp]
  (helper-large inp []))

;; Problem 4

(defn palindrome? [inp]
  "Returns true if number is palindrome else returns false"
  (if (= (str inp) (clojure.string/reverse (str inp)))
    true
    false))

(defn helper-palin
  ([x] (helper-palin x []))

  ([x out]
   "Returns the list of palindrones between x and 999-1"
   (loop [y 999
          out []]
     (if (= y 1)
       out
       (if (palindrome? (* x y))
         (conj out (* x y))
         (recur (dec y) out))))))

(defn largest-palin []
  "Returns the largest 3-digit palindrone"
  (loop [x 999
         out []]
    (if (= x 1)
      (last  (sort  out))
      (recur (dec x) (conj out (helper-palin x))))))

;; Problem 5

(defn isdiv? [inp]
  "Returns true if input divisble by 1-10 else returns nil"
  (loop [i 20
         flag true]
    (when flag
      (if (> i 0)
        (if (= 0 (mod inp i))
          (recur (dec i) true)
          (recur i false))
        flag))))

(defn smallest-div-num []
  (second (filter isdiv? (range))))

;; Problem 6

(defn sum-of-squares []
  (int  (reduce + (map (fn [x] (Math/pow x 2)) (range 1 101)))))

(defn square-of-sum []
  (int  (Math/pow (reduce + (range 1 101)) 2)))

(defn final-difference []
  (- (square-of-sum) (sum-of-squares)))

;; Problem 7

(defn prime? [inp]
  (if (= 1 (count (get-factors inp)))
    true
    false))
(defn prime-no-index [index]
  (last (take index (filter prime? (range)))))

;; Problem 8

#_(defn convert-to-parts [no inp o]
  (loop [i 1
         n inp
         out o]
    (if (= i no)
      (convert-to-parts no inp out)
      (recur (inc i) (rest inp) (conj out (first inp))))))
