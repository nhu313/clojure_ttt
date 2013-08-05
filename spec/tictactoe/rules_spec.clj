(ns tictactoe.rules-spec
  (:require [speclj.core :refer :all]
            [tictactoe.rules :refer :all]))

(describe "Tic Tac Toe Rules"

(defn player "X")
(defn opponent "O")

(context "winner"
  (it "has no winner when the board is empty"
    (should= nil (winner [nil nil nil nil nil nil nil nil nil])))

  (context "wins horizontally"
    (it "is nil when players mark two squares in each row"
      (should= nil (winner [player player nil nil opponent opponent player player nil])))
    (it "is the player when player marks the whole first row"
      (should= player (winner [player player player nil nil nil nil nil nil])))
    (it "is the player when player marks the whole second row"
      (should= player (winner [nil nil nil player player player nil nil nil])))
    (it "is the opponent when opponent marks the whole third row"
      (should= opponent (winner [nil nil nil nil nil nil opponent opponent opponent]))))

  (context "wins vertically"
    (it "is nil when players mark two squares in each column"
      (should= nil (winner [player opponent nil player opponent player nil nil player])))
    (it "is the opponent when opponent marks the whole first column"
      (should= opponent (winner [opponent nil nil opponent nil nil opponent nil nil])))
    (it "is the player when player marks the whole second column"
      (should= player (winner [nil player nil nil player nil nil player nil])))
    (it "is the opponent when opponent marks the whole third column"
      (should= opponent (winner [nil nil opponent nil nil opponent nil nil opponent]))))

  (context "wins diagonally"
    (it "is nil when player marks two of the diagonal squares"
      (should= nil (winner [nil nil nil nil player nil player nil player])))
    (it "is the opponent when opponent marks the diagonal squares from left to right"
      (should= opponent (winner [opponent nil nil nil opponent nil nil nil opponent])))
    (it "is the player when player marks the diagonal squares from right to left"
      (should= player (winner [nil nil player nil player nil player nil nil])))))

  (it "it nil when there is a draw"
    (should= nil (winner [opponent player opponent opponent player opponent player opponent player])))

(context "game over"
  (it "is not over when the board is empty"
    (should= false (game-over? [nil nil nil nil nil nil nil nil nil])))
  (it "is not over when the board has marks but there is no winner"
    (should= false (game-over? [nil player nil nil nil nil nil nil nil])))
  (it "is over when the board is full and there is no winner (draw)"
    (should= true (game-over? [player opponent player player player opponent opponent player opponent])))
  (it "is over when there is a winner and the board is not full"
    (should= true (game-over? [player player player nil nil nil nil nil nil])))))
