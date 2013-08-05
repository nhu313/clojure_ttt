(ns tictactoe.board-spec
  (:require [speclj.core :refer :all]
            [tictactoe.board :refer :all]))

(describe "Board"

(def player1 "X")
(def empty-board [nil nil nil nil])
(def full-board [player1 player1 player1 player1])

(it "creates an empty board with specific size"
  (should= empty-board (create-board 2))))

(context "marking the board"
  (it "for an empty square"
    (should= [nil player1 nil nil] (mark-board empty-board 1 player1)))

    ; (it "marks a square that is already marked")
    ; (it "gives an input that is smaller than the first square")
    ; (it "gives an input that is greater than the last square")
    )

(context "full"
  (it "is not full when it is empty"
    (should= false (full? empty-board)))

  (it "is not full when one square is marked"
      (should= false (full? [nil player1 nil nil])))

  (it "is not full when one square is left unmarked"
      (should= false (full? [nil player1 player1 player1])))

  (it "is full when all squares are unmarked"
      (should= true (full? full-board))))

(context "available moves"
  (it "is all the squares when the board is empty"
    (should= (range (count empty-board)) (available-moves empty-board)))

  (it "is all the squares that is not marked"
    (should= (list 1 2) (available-moves [player1 nil nil player1])))

  (it "is nothing when the board is full"
    (should= (list) (available-moves full-board)))
)
