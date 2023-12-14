(ns clojurelab.lab3.lab3-2)
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

(def part-size 10)

(defn lazy-parallel-filter [condition collection part-count]
  (lazy-cat (concat (parallel-filter condition (take part-size collection) part-count)
                    (if (empty? collection)
                      '()
                      (lazy-parallel-filter condition (drop part-size collection) part-count)))))


(println (take 10 (lazy-parallel-filter heavy-calculate-condition (iterate inc 0) 3)))

(time (->>
        (filter heavy-calculate-condition (iterate inc 0))
        (take 10)
        (doall)
        )
      )
(time (->>
        (lazy-parallel-filter heavy-calculate-condition (iterate inc 0) 1)
        (take 10)
        (doall)
        )
      )
(shutdown-agents)
