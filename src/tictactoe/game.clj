(ns tictactoe.game
  (:require [tictactoe.rules :as rules]
            [tictactoe.board :as board]
            [tictactoe.player :as player]))

(def default-board-size 3)

(def game-types [[:human :computer] [:computer :human] [:human :human] [:computer :computer]])

(defn- player-str [player]
  (if (= player :human) "Human" "Computer"))

(defn game-types-str []
  (map-indexed (fn [i item] (str (+ i 1) " - " (player-str (first item)) " vs "(player-str (last item)) "\n")) game-types))

(defn display-board [board]
  (println)
  (doseq [row (partition (board/board-size board)
                         (board/replace-nil-with-index board))]
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

(defn create-players [players]
  [(player/create-player (first players) rules/x)
   (player/create-player (last players) rules/o)])

(defn- read-game-type []
  (println "Please enter a game type:")
  (- (Integer. (read-line)) 1))

(defn players []
  (print " ")
  (apply println (game-types-str))
  (try
    (let [players (nth game-types (read-game-type))]
      (create-players players))
    (catch Exception e
      (println "That is not a valid game type.")
      (players))))

(defn play [size]
  (println "Welcome to Tic Tac Toe!")
  (let [board (game-loop (board/create-board size) (players))
        winner (rules/winner board)]
    (println (if winner (str winner " wins!") "Draw!"))))

(defn -main [& args]
  (play default-board-size))
