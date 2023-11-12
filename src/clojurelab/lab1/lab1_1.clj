(ns clojurelab.lab1.lab1-1)

(defn connect-char-to-word
  [word char]
  (if (not= (str (last word)) char)                         ; если последний элемент слова != добавляемому символу
    (str word char)                                         ; добавляем символ в конец
    nil)
  )

(defn make-word-from-seq
  [word seq acc]
  (if (not-empty seq)
    (let [new-word (connect-char-to-word word (first seq))
          new-seq (rest seq)]
      (if (nil? new-word)
        (make-word-from-seq word new-seq acc)
        (make-word-from-seq word new-seq (into acc (list new-word)))
        )
      )
    acc                                                     ;если набор пуст, возвращаем слова, которые получили
    )
  )

(defn make-words-from-seq
  [words-list seq acc]
  (if (not-empty words-list)
    (make-words-from-seq (rest words-list)
                         seq
                         (into acc (make-word-from-seq (first words-list) seq [])))
    acc
    )
  )

(defn make-words
  [words-list seq n]
    (if (empty? words-list)
      (make-words seq seq n)
      (if (= (count (first words-list)) n)
        words-list
        (make-words (make-words-from-seq words-list seq []) seq n)
        )
      )
  )

(def words, (make-words [] (distinct `("a" "b" "c" "d")) 10))
(println words)