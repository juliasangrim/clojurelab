(ns clojurelab.lab2.lab2-2)

(def step 0.5)

(defn count_one_sum
  [func x1 x2]
  (print "*")
  (* (* (+ (func x1) (func x2)) (- x2 x1)) 0.5)
  )

(defn integrate
  [fun]
  (map first (iterate
               (fn
                 [[sum index]]
                 [(+ sum (count_one_sum fun (* index step) (* (+ index 1) step))) (+ index 1)])
               [0 0]))
  )

(defn integrate-fun
  [fun]
  (let [integrate (integrate fun)]
    (fn [end]
      (let [max_index (int (/ end step))]                   ;считаем максимальный целый индекс, до которого будем интегрироваться
        (+ (nth integrate max_index)                        ;считаем интеграл до этого индекса
           (count_one_sum fun (* max_index step) end)       ;считаем остаток
           )
        )
      )
    )
  )

(println "\nWith infinite sequence:")
(let [fun (fn [x] (* x x))
      inf-seq (integrate-fun fun)]
  (println (time (inf-seq 5)))
  (println (time (inf-seq 5.6)))
  (println (time (inf-seq 5.7)))
  (println (time (inf-seq 5.8)))
  (println (time (inf-seq 6)))
  )

