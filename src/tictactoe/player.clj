(ns tictactoe.player
  (:require [tictactoe.board :as board]
            [tictactoe.rules :as rules]))

(defn opponent [player]
  (if (= rules/x player) rules/o rules/x))

(defn negamax-score [board player]
  (let [w (rules/winner board)]
    (cond
      (= w player) 100
      (= w nil) 0
      :else -100)))

(defn best-move [moves]
  (first (sort #(> (last %1) (last %2)) moves)))

(defn score-move [board player depth func]
  (if (rules/game-over? board)
    (- (negamax-score board player) depth)
    (- 0 (last (func board (opponent player) (inc depth))))))

; Not sure what to do here. Exctracting the function means I have to pass in a bunch of parameters T.T
(defn negamax[board player depth]
  (best-move (map (fn[move] [move (score-move (board/mark-board board move player) player depth negamax)])
                  (board/available-moves board))))

(defprotocol Player
  (marker [this])
  (move [this board]))

(defrecord Computer [marker]
  Player
  (marker [this] (:marker this))
  (move [this board]
    (first (negamax board (:marker this) 0))))

(defrecord Human [marker]
  Player
  (marker [this] (:marker this))
  (move [this board]
    (println (str (:marker this) ", please make a move:"))
    (Integer. (read-line))))

(defn create-player [type marker]
  (case type
    :human (tictactoe.player.Human. marker)
    :computer (tictactoe.player.Computer. marker)))
