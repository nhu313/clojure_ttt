(ns tictactoe.game-spec
  (:require [speclj.core :refer :all]
            [tictactoe.game :refer :all]))

(describe "Tic Tac Toe Game"
(def player1 "X")
(def player2 "O")
(def empty-board (create-board 3))
(def draw-board (vec (range 9)))
(def winning-board [player1 player1 player2 player2 player1 player2 player1 player2 player1])
(def board-size 3)

  (defn test-output-string
    ([expected func]
    (should= true (.contains (with-out-str (func)) expected)))
    ([expected func args]
    (should= true (.contains (with-out-str (func args)) expected)))
    ([expected func arg1 arg2]
    (should= true (.contains (with-out-str (func arg1 arg2)) expected))))

(context "board"
  (it "creates an empty board with specific size"
    (should= [nil nil nil nil] (create-board 2))))

  (context "display board"
    (defn test-display-board[expected board]
      (should= expected (with-out-str (display-board board))))

    (it "shows numbers for all unmarked squares"
      (test-display-board "| 0 | 1 |\n| 2 | 3 |\n" (create-board 2)))

    (it "shows value for the square that is marked"
      (test-display-board "| 0 | O |\n| X | 3 |\n" [nil player2 player1 nil]))

    (it "prints 3x3 board"
      (test-display-board "| 0 | 1 | 2 |\n| 3 | 4 | 5 |\n| 6 | 7 | 8 |\n" (create-board board-size))))

  (context "marking the board"
    (it "for an empty square"
      (should= [nil player1 nil nil nil nil nil nil nil] (mark-board empty-board 1 player1)))

    ; (it "marks a square that is already marked")
    ; (it "gives an input that is smaller than the first square")
    ; (it "gives an input that is greater than the last square")
    )

(it "gets user move"
  (should= 3 (with-in-str "3" (user-input))))

(context "starts game"
  (it "displays a welcome message"
    (with-redefs [play (constantly empty-board)]
      (test-output-string "Welcome to Tic Tac Toe!" start board-size)))

  (it "calls play with a new board"
    (with-redefs [play (fn[board value] (should= board empty-board) board)]
      (with-out-str start board-size)))
  (it "displays game type"
    (with-redefs [play (constantly empty-board)]
      (test-output-string "1 - Human vs Computer\n2 - Computer vs Human\n3 - Human vs Human\n4 - Computer vs Computer"
                          start board-size)
    ))
      )

(context "ends game"
  (it "displays winner when one is available"
    (with-redefs [play (constantly winning-board)]
      (test-output-string (str player1 " wins!") start board-size)))

  (it "notifies draw"
    (with-redefs [play (constantly draw-board)]
      (test-output-string "Draw!" start board-size))))

(context "play"
  (it "doesn't do anything when the game is over"
    (with-redefs [println (constantly "")]
      (should= draw-board (play draw-board player1))))

  (it "prints the board"
    (test-output-string "| 0 | 1 | 2 |\n| 3 | 4 | 5 |\n| 6 | 7 | 8 |\n" play draw-board player1))

  (it "asks player for a move"
    (with-redefs [read-line (constantly "0")]
      (test-output-string "X, please make a move:" play (vec (conj (rest winning-board) nil)) player1)))

  (it "marks the board with player move"
    (with-redefs [println (constantly "")]
      (should= [player1 player1 player1 nil nil nil nil nil nil]
              (with-in-str "0\n" (play [nil player1 player1 nil nil nil nil nil nil] player1)))))

  ; (it "asks player2 for a move when there are two moves left"
  ;   (with-redefs [read-line (constantly "0")]
  ;     (test-output-string "O, please make a move:" play (vec (conj (rest winning-board) nil)) player1)))

  (it "marks player 2 moves"
    (with-redefs [println (constantly "")]
      (should= [player1 player2 player2 player2 player1 player2 player1 player1 player2]
              (with-in-str "7\n8\n" (play [player1 player2 player2 player2 player1 player2 player1 nil nil] player1))))))

)
