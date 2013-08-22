(ns tictactoe.game
  (:require [tictactoe.rules :as rules]
            [tictactoe.board :as board]
            [tictactoe.player :as player]))

(def default-board-size 3)

(def game-types-str "1 - Human vs Computer\n2 - Computer vs Human\n3 - Human vs Human\n4 - Computer vs Computer")

(defn display-board [board]
  (println)
  (doseq [row (partition (board/board-size board) (board/replace-nil-with-index board))]
    (println (str "| " (clojure.string/join " | " row) " |"))))

(defn make-move [board player]
  (try
    (println (str (:marker player) ", please makes a move:"))
    (board/mark-board board (player/move player board) (:marker player))
    (catch Exception e
      (println "That is not a valid move.")
      (make-move board player))))

(defn change-player [players current-player]
  (if (= current-player (first players))
      (second players)
      (first players)))

(defn game-loop [new-board players]
  (loop [board new-board
         current-player (first players)]
    (display-board board)
    (if (rules/game-over? board)
        board
        (recur (make-move board current-player)
               (change-player players current-player)))))

(defn create-players [player1 player2]
  [(player/create-player player1 rules/x) (player/create-player player2 rules/o)])

(defn players []
  (println "Please enter a game type:")
  (println game-types-str)
  (case (read-line)
    "1" (create-players :human :computer)
    "2" (create-players :computer :human)
    "3" (create-players :human :human)
    "4" (create-players :computer :computer)
    (players)))

(defn play [size]
  (println "Welcome to Tic Tac Toe!")
  (let [board (game-loop (board/create-board size) (players))
        winner (rules/winner board)]
    (println (if winner (str winner " wins!") "Draw!"))))

(defn -main [& args]
  (play default-board-size))
