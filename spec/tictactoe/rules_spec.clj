(ns tictactoe.rules-spec
  (:require [speclj.core :refer :all]
    [tictactoe.rules :refer :all]))

(describe "Tic Tac Toe Rules"

  (context "winner"
    (it "has no winner when the board is empty"
      (should-be-nil (winner [nil nil nil
                            nil nil nil
                            nil nil nil])))

  (context "wins horizontally"
    (it "is nil when xs mark two squares in each row"
      (should-be-nil (winner [ x   x  nil
                            nil  o   o
                             x   x   nil])))

    (it "is the x when x marks the whole first row"
      (should= x (winner [ x   x   x
                          nil nil nil
                          nil nil nil])))

    (it "is the x when x marks the whole second row"
      (should= x (winner [nil nil nil
                           x   x   x
                          nil nil nil])))

    (it "is the o when o marks the whole third row"
      (should= o (winner [nil nil nil
                          nil nil nil
                           o   o   o]))))

  (context "wins vertically"
    (it "is nil when xs mark two squares in each column"
      (should-be-nil (winner [ x   o  nil
                             x   o   x
                            nil nil  x])))

    (it "is the o when o marks the whole first column"
      (should= o (winner [o nil nil
                          o nil nil
                          o nil nil])))

    (it "is the x when x marks the whole second column"
      (should= x (winner [nil x nil
                          nil x nil
                          nil x nil])))

    (it "is the o when o marks the whole third column"
      (should= o (winner [nil nil o
                          nil nil o
                          nil nil o]))))

  (context "wins diagonally"
    (it "is nil when x marks two of the diagonal squares"
      (should-be-nil (winner [nil nil nil
                            nil  x  nil
                            x   nil  x ])))

    (it "is the o when o marks the diagonal squares from left to right"
      (should= o (winner [ o  nil nil
                          nil  o  nil
                          nil nil o])))

    (it "is the x when x marks the diagonal squares from right to left"
      (should= x (winner [nil nil  x
                          nil  x  nil
                           x  nil nil]))))

    (it "it nil when there is a draw"
      (should-be-nil (winner [o x o
                            o x o
                            x o x]))))

  (context "game over"
    (it "is not over when the board is empty"
        (should-not (game-over? [nil nil nil
                                  nil nil nil
                                  nil nil nil])))

    (it "is not over when the board has marks but there is no winner"
        (should-not (game-over? [nil  x  nil
                                  nil nil nil
                                  nil nil nil])))

    (it "is over when the board is full and there is no winner (draw)"
      (should (game-over? [x o x
                                 x x o
                                 o x o])))

    (it "is over when there is a winner and the board is not full"
      (should= x  (game-over? [x   x   x
                                 nil nil nil
                                 nil nil nil])))))

