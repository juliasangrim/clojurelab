(ns clojurelab.lab3.lab3-1)

(defn heavy-calculate-condition
  [x]
  (Thread/sleep 100)
  (if (> x 3) true false)
  )

(defn calculate-part-size
  [part-size part-number]
  (int (- (Math/ceil (* part-size (inc part-number))) (Math/ceil (* part-size part-number))))
  )

(defn part
  [part-size part-num collection]
  (let [cur-batch-size (calculate-part-size part-size part-num),
        prev-batch-sizes-sum (int (Math/ceil (* part-size part-num)))]
    (take cur-batch-size (drop prev-batch-sizes-sum collection))
    )
  )

(defn parallel-filter
  [condition collection part-count]
  (let [part-size (/ (count collection) part-count)]
    (->>
      (iterate inc 0)
      (take part-count)
      (map #(future (doall (filter condition (part part-size %1 collection)))))
      (doall)
      (mapcat deref)))
  )

(println (parallel-filter heavy-calculate-condition (range 7) 4))

(time (->>
        (filter heavy-calculate-condition (range 7))
        (doall)
        )
      )
(time (->>
        (parallel-filter heavy-calculate-condition (range 7) 4)
        (doall)
        )
      )
(shutdown-agents)
