(ns clojurelab.lab2.lab2-1)

(def step 0.1)
(def start 0)

(defn count_one_sum
  [func x1 x2]
  (print "*")
  (* (* (+ (func x1) (func x2)) (- x2 x1)) 0.5)
  )

(defn integrate
  [func func_integrate i]
  (if (< start i)
    (+ (count_one_sum func (* (- i 1) step) (* i step)) (func_integrate func func_integrate (- i 1)))
    0
    )
  )

(def memoize_integrate
  (memoize integrate)
  )

(defn integral_calc
  [func end]
  (let [i (Math/floor (/ end step))]
    (+ (integrate func integrate i) (count_one_sum func (* i step) end)))
  )

(defn memoize_integral_calc
  [func end]
  (let [i (Math/floor (/ end step))]
    (+ (memoize_integrate func memoize_integrate i) (count_one_sum func (* i step) end)))
  )

(defn func_integrate_calc
  [fun]
  (fn [end] (integral_calc fun end))
  )

(defn mem_func_integrate_calc
  [fun]
  (fn [end] (memoize_integral_calc fun end))
  )


(println "without memoization")
(let [fun (fn [x] (* x x))]
  (println (time ((func_integrate_calc fun) 5.6)))
  (println (time ((func_integrate_calc fun) 5.6)))
  )

(println "with memoization")
(let [fun (fn [x] (* x x))]
  (println (time ((mem_func_integrate_calc fun) 5.5)))
  (println (time ((mem_func_integrate_calc fun) 6)))
  )
