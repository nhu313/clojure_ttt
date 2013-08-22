(ns tictactoe.game-spec
  (:require [speclj.core :refer :all]
            [tictactoe.game :refer :all]
            [tictactoe.board :refer [create-board]]
            [tictactoe.player :refer [create-player]]
            ))

(describe "Tic Tac Toe Game"
(def player1 "X")
(def player2 "O")
(def empty-board (create-board default-board-size))
(def draw-board (vec (range 9)))
(def winning-board [player1 player1 player2 player2 player1 player2 player1 player2 player1])

(around [it]
  (with-out-str (it)))

  (defn test-output-string
    ([expected func]
    (should= true (.contains (with-out-str (func)) expected)))
    ([expected func args]
    (should= true (.contains (with-out-str (func args)) expected)))
    ([expected func arg1 arg2]
    (should= true (.contains (with-out-str (func arg1 arg2)) expected))))

  (defn test-display-board [expected board]
    (should= expected (with-out-str (display-board board))))

  (defn test-play-message [expected-str board]
    (with-redefs [game-loop (constantly board) players (constantly "")]
      (test-output-string expected-str play default-board-size)))

(context "display board"
  (it "shows numbers for all unmarked squares"
    (test-display-board "\n| 0 | 1 |\n| 2 | 3 |\n" (create-board 2)))

  (it "shows value for the square that is marked"
    (test-display-board "\n| 0 | O |\n| X | 3 |\n" [nil player2 player1 nil]))

  (it "prints 3x3 board"
    (test-display-board "\n| 0 | 1 | 2 |\n| 3 | 4 | 5 |\n| 6 | 7 | 8 |\n" (create-board 3))))

(context "starts game"
  (it "displays a welcome message"
    (test-play-message "Welcome to Tic Tac Toe!" empty-board))

  (it "calls play with a new board"
    (with-redefs [play (fn[board value] (should= board empty-board) board)]
      (with-out-str play default-board-size))))

(context "ends game"
  (it "displays winner when one is available"
    (test-play-message (str player1 " wins!") winning-board))

  (it "notifies draw"
   (test-play-message "Draw!" draw-board)))

(context "game loop"
  (it "doesn't do anything when the game is over"
      (should= draw-board (game-loop draw-board player1)))

  (it "prints the board"
    (test-output-string "| 0 | 1 | 2 |\n| 3 | 4 | 5 |\n| 6 | 7 | 8 |\n" game-loop draw-board player1))

  (it "marks the board with player move"
    (let [expected-board [player1 player1 player1 nil nil nil nil nil nil]
          input-board [nil player1 player1 nil nil nil nil nil nil]]
      (should= expected-board
          (with-in-str "0\n" (game-loop input-board (create-players :human :human))))))

  (it "marks player 2 moves after player 1 moves"
    (let [expected-board [player1 player2 player2 player2 player1 player2 player1 player1 player2]
         input-board    [player1 player2 player2 player2 player1 player2 player1 nil nil]
         players (create-players :human :human)]
        (should= expected-board (with-in-str "7\n8\n" (game-loop input-board players))))))

(context "create players"
  (it "displays game types"
    (with-redefs [game-loop (constantly empty-board) read-line (constantly "1")]
      (test-output-string "1 - Human vs Computer\n2 - Computer vs Human\n3 - Human vs Human\n4 - Computer vs Computer"
                          play default-board-size)))

  (defn test-create-players [game-type player1 player2]
      (should= [(create-player player1 "X") (create-player player2 "O")] (with-in-str game-type (players))))

  (it "returns human and computer players when user enters 1"
    (test-create-players "1" :human :computer))

  (it "returns computer and human players when user enters 2"
    (test-create-players "2" :computer :human))

  (it "returns human and human players when user enters 3"
    (test-create-players "3" :human :human))

  (it "returns computer and computer players when user enters 4"
    (test-create-players "4" :computer :computer))

  (it "gets another input when user did not enters a number on the list"
    (test-create-players "9999\n1\n" :human :computer)))

(context "change players"
  (it "should change to player 2 when current player is player 1"
    (let [players (create-players :human :human)]
      (should= (second players) (change-player players (first players)))))

  (it "should change to player 1 when current player is player 2"
    (let [players (create-players :human :human)]
      (should= (first players) (change-player players (second players)))))))
