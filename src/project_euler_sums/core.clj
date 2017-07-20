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

(def grid [[8 2 22 97 38 15 0 40 0 75 04 05 07 78 52 12 50 77 91 8]
           [49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 4 56 62 0]
           [81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 3 49 13 36 65]
           [52 70 95 23 4 60 11 42 69 24 68 56 1 32 56 71 37 2 36 91]
           [22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80]
           [24 47 32 60 99 3 45 2 44 75 33 53 78 36 84 20 35 17 12 50]
           [32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70]
           [67 26 20 68 2 62 12 20 95 63 94 39 63 8 40 91 66 49 94 21]
           [24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72]
           [21 36 23 9 75 0 76 44 20 45 35 14 0 61 33 97 34 31 33 95]
           [78 17 53 28 22 75 31 67 15 94 3 80 4 62 16 14 9 53 56 92]
           [16 39 5 42 96 35 31 47 55 58 88 24 0 17 54 24 36 29 85 57]
           [86 56 00 48 35 71 89 7 5 44 44 37 44 60 21 58 51 54 17 58]
           [19 80 81 68 5 94 47 69 28 73 92 13 86 52 17 77 4 89 55 40]
           [4 52 8 83 97 35 99 16 7 97 57 32 16 26 26 79 33 27 98 66]
           [88 36 68 87 57 62 20 72 3 46 33 67 46 55 12 32 63 93 53 69]
           [4 42 16 73 38 25 39 11 24 94 72 18 8 46 29 32 40 62 76 36]
           [20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 4 36 16]
           [20 73 35 29 78 31 90 1 74 31 49 71 48 86 81 16 23 57 5 54]
           [1 70 54 71 83 51 54 69 16 92 33 48 61 43 52 1 89 19 67 48]])


(defn down
  [i j]
  (let [a (get-in grid [i j] 1)
        b (get-in grid [(+ i 1) j] 1)
        c (get-in grid [(+ i 2) j] 1)
        d (get-in grid [(+ i 3) j] 1)]
    (reduce * [a b c d])))


(defn right
  [i j]
  (let [a (get-in grid [i j])
        b (get-in grid [i (+ j 1)] 1)
        c (get-in grid [i (+ j 2)] 1)
        d (get-in grid [i (+ j 3)] 1)]
    (reduce * [a b c d])))


(defn diagonal-right
  [i j]
  (let [a (get-in grid [i j])
        b (get-in grid [(+ i 1) (+ j 1)] 1)
        c (get-in grid [(+ i 2) (+ j 2)] 1)
        d (get-in grid [(+ i 3) (+ j 3)] 1)]
    (reduce * [a b c d])))

(defn diagonal-left
  [i j]
  (let [a (get-in grid [i j])
        b (get-in grid [(+ i 1) (- j 1)] 1)
        c (get-in grid [(+ i 2) (- j 2)] 1)
        d (get-in grid [(+ i 3) (- j 3)] 1)]
    (reduce * [a b c d])))

(defn largest-prod-grid
  [inp]
  (apply max (for [i (range 0 20)
                   j (range 0 20)]
               (max (right i j) (down i j) (diagonal-right i j) (diagonal-left i j)))))

















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
