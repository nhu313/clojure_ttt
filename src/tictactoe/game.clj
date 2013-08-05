(ns tictactoe.game
  (:require [tictactoe.rules :refer :all ]))

(defn create-board[size]
  (vec (repeat (* size size) nil)))

(defn- board-size[board]
  (int (java.lang.Math/sqrt (count board))))

(defn replace-nil-with-number[board]
  (vec (map-indexed (fn [i item] (if item item i)) board)))

(defn display-board[board]
  (doseq [row (partition (board-size board) (replace-nil-with-number board))]
    (println (str "| " (clojure.string/join " | " row) " |"))))

(defn user-input[]
  (Integer. (read-line)))

(defn mark-board[board move value]
  (assoc board move value))

(defn make-move[board player]
  (println (format "%s, please make a move:" player))
  (mark-board board (user-input) player))

(defn change-player[current-player]
  (if (= current-player "X") "O" "X"))

(defn play[new-board first-player-value]
  (loop [board new-board player first-player-value]
    (display-board board)
    (if (game-over? board) board
      (recur (make-move board player) (change-player player)))))

(defn players[]
  (println "Please enter a game type:")
  (println "1 - Human vs Computer\n2 - Computer vs Human\n3 - Human vs Human\n4 - Computer vs Computer"))

(defn start[size]
  (println "Welcome to Tic Tac Toe!")
  (players)
  (let [board (play (create-board size) "X") winner (winner board)]
    (println (if winner (str winner " wins!") "Draw!"))))

(defn -main [& args]
  (start 3))
