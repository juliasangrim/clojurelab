(ns clojurelab.lab1.lab1-4)
(defn connect-char-to-word
  [word char]
  (if (not= (str (last word)) char)                         ; если последний элемент слова != добавляемому символу
    (str word char)                                         ; добавляем символ в конец
    nil)
  )

(defn make-word-from-seq
  [word seq]
  (filter some? (map (fn [elem] (connect-char-to-word word elem)) seq)))

(defn make-words-from-seq
  [words-list seq]
  (reduce (fn [acc, elem] (into acc (make-word-from-seq elem seq))) `() words-list))

(defn make-words
  [seq n]
  (if (empty? seq)
    `()
    (reduce
      (fn [res _]
        (if (empty? res)
          seq
          (make-words-from-seq res seq)))
      []
      (range 0 n)
  )))

(println (make-words (distinct `("a" "b" "c" )) 2))