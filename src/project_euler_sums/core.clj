(ns project-euler-sums.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; Problem 1

(defn sum-of-multi
  "Returns the sum of al numbers divisible by 3 & 5"
  [inp]
  (reduce +
          (filter (fn[x]
                    (if
                        (or (= 0 (mod x 3))
                            (= 0 (mod x 5)))
                      x))
                  (range 1 inp))))

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

(defn sum-even-fibo
  "Returns sum of even valued fibonacci series"
  []
  (reduce +
          (take-while (fn [x]
                        (if (<= x 4000000)
                          x))
                      (filter even? (get-fibo-num)))))

;; Problem 3

;;Ineffeicient solution- Crashes in repl execution
(defn get-factors [inp]
  "Returns the factors of given number"
  (filter (fn [x] (if (= (mod inp x) 0) x)) (range 1 inp)))

(defn largest-prime-factor [inp]
  "Returns the largest prime factor of a number"
  (first (reverse (filter (fn [x]
                    (if (= 1 (count (get-factors x)))
                      x))
                  (get-factors inp)))))

#_(largest-prime-factor 600851475143)

;;Efficient one

(defn helper-large
  [inp fact]
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

(defn palindrome?
  "Returns true if number is palindrome else returns false"
  [inp]
  (if (= (str inp) (clojure.string/reverse (str inp)))
    true
    false))

(defn helper-palin
  "Returns the list of palindrones between x and 999-1"
  ([x] (helper-palin x []))

  ([x out]

   (loop [y 999
          out []]
     (if (= y 1)
       out
       (if (palindrome? (* x y))
         (conj out (* x y))
         (recur (dec y) out))))))

(defn largest-palin
  "Returns the largest 3-digit palindrone"
  []
  (loop [x 999
         out []]
    (if (= x 1)
      (last  (sort  out))
      (recur (dec x) (conj out (helper-palin x))))))

;;Updated Solution using Loop

(defn largest-palin-for []
  (last (sort (for [x (range 100 999)
                    y (range 100 999)
                    :when (palindrome? (* x y))]
                (* x y)))))
;; Problem 5

(defn isdiv?
  "Returns true if input divisble by 1-10 else returns nil"
  [inp]
  (loop [i 20
            flag true]
       (when flag
         (if (> i 0)
           (if (= 0 (mod inp i))
             (recur (dec i) true)
             (recur i false))
           flag))))

(defn new-isdiv?
  "Returns true if input is divisible by 1-10 else returns false"
  [inp]
  (if (= (range 1 11)
         (filter (fn [x] (if (zero? (mod inp x))
                           true))
               (range 1 11)))
    true
    false))


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

(def num 73167176531330624919225119674426574742355349194934969835203127745063262395783180169848018694788518438586156078911294949545950173795833195285320880551112540698747158523863050715693290963295227443043557668966489504452445231617318564030987111217223831136222989342338030813533627661428280644448664523874930358907296290491560440772390713810515859307960866701724271218839987979087922749219016997208880937766572733300105336788122023542180975125454059475224352584907711670556013604839586446706324415722155397536978179778461740649551492908625693219784686224828397224137565705605749026140797296865241453510047482166370484403199890008895243450658541227588666881164271714799244429282308634656748139191231628245869178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450)

;; SOLUTION 1
(defn multiply-parts
  ([inp] (multiply-parts inp []))
  ([inp out]
   (if (empty? inp)
     out
     (recur (rest inp) (conj out (reduce * (first inp)))))))

(defn split-digits [num]
  (map (fn [^Character c]
         (Character/digit c 10))
       (str num)))
(def m-multiply-parts (memoize multiply-parts))

(defn max-product
  [inp]
  (apply max (m-multiply-parts inp)))

(defn split-parts
  [lim]
  (loop [in (split-digits num)
         out []]
    (if (< (count in) lim)
      out
      (recur (rest in) (conj out (max-product (partition lim in)))))))

(defn greatest-product
  [lim]
  (apply max (split-parts lim)))

;; SOLUTION 2

(defn new-split
  [lim]
  (loop [in (split-digits num)
         out 0]
    (if(< (count in) lim)
      out
      (if (> out (reduce * (take lim in)))
        (recur (rest in) out)
        (recur (rest in) (reduce * (take lim in)))))))


;; Problem 9

(defn square-of
  [inp]
  (long (Math/pow inp 2)))
(defn special-triplet
  [sum]
  (first  (for [a (range 1 (/ sum 2))
                b (range 1 (/ sum 2))
                :let [c (- sum a b)]
                :when (= (square-of c) (+  (square-of a) (square-of b)))]
            (* a b c))))

;; Problem 10

(defn prime-sum
  [inp]
  (reduce + (filter prime? (range 2 inp))))

;; Problem 11




















;; Problem 12

(defn get-tri-nums
  []
  (map (fn [x] (reduce + (range 1 (inc x)))) (range)))

(defn highly-div-tri
  [inp]
  (take 1 (filter (fn [x]
                    (if (> (count (get-factors x))
                           (dec inp))
                      x))
                  (get-tri-nums))))
q
