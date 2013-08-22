(ns tictactoe.rules
  (:require [tictactoe.board :refer :all ]))

(def x "X")
(def o "O")

(def win-square-sets
  [[0 1 2], [3 4 5], [6 7 8], [0 3 6], [1 4 7], [2 5 8], [0 4 8], [2 4 6]])

(defn- winner? [board squares]
  (and
    (board (first squares))
    (apply = (map board squares))))

(defn- winner-in-set [board squares]
  (if (winner? board squares)
    (board (first squares))))

(defn winner [board]
  (some #(winner-in-set board %) win-square-sets))

(defn draw?[board]
  (every? (fn[item] (not (nil? item))) board))

(defn game-over? [board]
  (or (winner board)
    (draw? board)))
