(ns tictactoe.rules
  (:require [tictactoe.board :as board]))

(def x "X")
(def o "O")

(defn winning-square-sets [board]
  (reduce into [(board/rows board)
               (board/cols board)
               (board/diagonals board)]))

(defn- winner? [board squares]
  (and
    (board (first squares))
    (apply = (map board squares))))

(defn- winner-in-set [board squares]
  (let [marker (first squares)]
    (if (every? #(= marker %) squares) marker nil)))

(defn winner [board]
  (some #(winner-in-set board %) (winning-square-sets board)))

(defn game-over? [board]
  (or (winner board)
    (board/full? board)))
