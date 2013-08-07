(ns tictactoe.game
  (:require [tictactoe.rules :refer :all]
            [tictactoe.board :refer :all]
            [tictactoe.player :refer :all]))

(def default-board-size 3)
(def game-types-str "1 - Human vs Computer\n2 - Computer vs Human\n3 - Human vs Human\n4 - Computer vs Computer")

(defn display-board[board]
  (println)
  (doseq [row (partition (board-size board) (replace-nil-with-number board))]
    (println (str "| " (clojure.string/join " | " row) " |"))))

(defn make-move[board player]
  (mark-board board (move player board) (:marker player)))

(defn change-player[players current-player]
  (if (= current-player (first players)) (second players) (first players)))

(defn game-loop[new-board players]
  (loop [board new-board current-player (first players)]
    (display-board board)
    (if (game-over? board) board
      (recur (make-move board current-player) (change-player players current-player)))))

(defn create-players[player1 player2]
  [(create-player player1 "X") (create-player player2 "O")])

(defn players[]
  (println "Please enter a game type:")
  (println game-types-str)
  (case (read-line)
    "1" (create-players :human :computer)
    "2" (create-players :computer :human)
    "3" (create-players :human :human)
    "4" (create-players :computer :computer)
    (players)))
    ;

(defn play[size]
  (println "Welcome to Tic Tac Toe!")
  (let [board (game-loop (create-board size) (players)) winner (winner board)]
    (println (if winner (str winner " wins!") "Draw!"))))

(defn -main [& args]
  (play default-board-size))
