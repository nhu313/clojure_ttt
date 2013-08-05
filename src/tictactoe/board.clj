(ns tictactoe.board
  (:require [tictactoe.rules :refer :all ]))

(defn create-board[size]
  (vec (repeat (* size size) nil)))

(defn board-size[board]
  (int (java.lang.Math/sqrt (count board))))

(defn mark-board[board move value]
  (assoc board move value))

(defn full?[board]
    (every? (fn[item] (not (nil? item))) board))

(defn replace-nil-with-number[board]
  (map-indexed (fn [i item] (if item item i)) board))

(defn available-moves[board]
  (filter number? (replace-nil-with-number board)))
