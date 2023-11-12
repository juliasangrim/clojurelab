(ns clojurelab.lab1.lab1-3)

(defn my-map
  [func coll]
  (seq (reduce
         (fn [acc, elem] (conj acc (func elem)))
               [] coll)
       )
  )

(defn my-filter
  [pred coll]
  (seq (reduce
         (fn [acc, elem] (if (pred elem) (conj acc elem) acc))
         [] coll)
       )
  )

(println (map inc [1,2,3,4]))
(println (my-map inc [1,2,3,4]))

(println (filter some? [1,nil,2,3,nil,4, nil]))
(println (my-filter some? [1,nil,2,3,nil,4, nil]))
