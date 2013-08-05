(ns tictactoe.player
  (:require [tictactoe.board :refer :all]))

(defprotocol Player
	(value [this])
	(move [this board]))

(defrecord Human [value]
	Player
	(value [this] (:value this))
	(move [this board]))

(defrecord Computer [value]
	Player
	(value [this] (:value this))
	(move [this board]))

(defmulti move (fn [this board] (str (type this))))

(defmethod move "class tictactoe.player.Human" [this board]
  (println (str (:value this) ", please make a move:"))
  (Integer. (read-line)))

(defmethod move "class tictactoe.player.Computer" [this board]
	(first (available-moves board)))

