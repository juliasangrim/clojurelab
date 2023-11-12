(ns clojurelab.lab1.lab1-2)

(defn connect-char-to-word
  [word char]
  (if (not= (str (last word)) char)                         ; если последний элемент слова != добавляемому символу
    (str word char)                                         ; добавляем символ в конец
    nil)
  )


(defn make-word-from-seq
  [word seq acc]
  (if (empty? seq)
    acc
    (let [new-word (connect-char-to-word word (first seq))
          new-seq (rest seq)]
      (if (nil? new-word)
        (recur word new-seq acc)
        (recur word new-seq (into acc (list new-word)))))))

(defn make-words-from-seq
  [words-list seq acc]
  (if (empty? words-list)
    acc
    (recur (rest words-list)
                         seq
                         (into acc (make-word-from-seq (first words-list) seq [])))))

(defn make-words
  [words-list seq n]
  (if (empty? seq)
    `()
    (if (empty? words-list)
      (recur seq seq n)
      (if (= (count (first words-list)) n)
        words-list
        (recur (make-words-from-seq words-list seq []) seq n)))))

(def words, (make-words [] (distinct `("a" "b" "c" "d" )) 10))
(println words)