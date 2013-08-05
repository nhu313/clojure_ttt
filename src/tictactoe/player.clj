(ns tictactoe.player
  (:require [tictactoe.board :refer :all]))

(defprotocol Player
	(marker [this])
	(move [this board]))

(defrecord Human [marker]
	Player
	(marker [this] (:marker this))
	(move [this board]))

(defrecord Computer [marker]
	Player
	(marker [this] (:marker this))
	(move [this board]))

; (defn computer[value])

(defmulti move (fn [this board] (str (type this))))

(defmethod move "class tictactoe.player.Human" [this board]
  (println (str (:marker this) ", please make a move:"))
  (Integer. (read-line)))

(defmethod move "class tictactoe.player.Computer" [this board]
	(first (available-moves board)))
