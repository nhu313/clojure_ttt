(ns tictactoe.player
  (:require [tictactoe.board :refer :all]
            [tictactoe.rules :refer :all]))

(defn opponent [player]
  (if (= x player) o x))

(defn negamax-score [board player]
  (let [w (winner board)]
    (cond
      (= w player) 100
      (= w nil) 0
      :else -100)))

(defn best-move [moves]
  (first (sort #(> (last %1) (last %2)) moves)))

(defn score-move [board player depth func]
  (if (game-over? board)
    (- (negamax-score board player) depth)
    (- 0 (last (func board (opponent player) (inc depth))))))

; Not sure what to do here. Exctracting the function means I have to pass in a bunch of parameters T.T
(defn negamax[board player depth]
  (best-move (map (fn[move] [move (score-move (mark-board board move player) player depth negamax)])
                  (available-moves board))))

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

; (defmulti move (fn[this board] (str (type this))))


; (defmethod move "class tictactoe.player.Computer" [this board]
;   (first (negamax board (:marker this) 0)))
