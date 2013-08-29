(ns tictactoe.board)

(defn create-board [size]
  (vec (repeat (* size size) nil)))

(defn board-size [board]
  (int (java.lang.Math/sqrt (count board))))

(defn- available? [board move]
  (if (or (< move 0) (> move 8))
    false
    (not (nth board move))))

(defn mark-board [board move value]
  (if (available? board move)
    (assoc board move value)
    (throw (new IllegalArgumentException))))

(defn full? [board]
  (every? identity board))

(defn replace-nil-with-index [board]
  (map-indexed (fn [i item] (if item item i)) board))

(defn available-moves [board]
  (filter number? (replace-nil-with-index board)))

(defn rows [board]
  (let [size (board-size board)]
    (partition size board)))

(defn map-cols [board start step]
  (map #(nth board %) (range start (count board) step)))

(defn cols [board]
  (let [size (board-size board)
        squares-indexes (take size (range))]
    (map #(map-cols board % size) squares-indexes)))

(defn- top-left-diagonal [board]
  (let [size (board-size board)
        step (+ size 1)
        square-indexes (range 0 (count board) step)]
        (map #(nth board %) square-indexes)))

(defn- top-right-diagonal [board]
  (let [size (board-size board)
        step (- size 1)
        square-indexes (range (- size 1) (- (count board) 1) step)]
        (map #(nth board %) square-indexes)))

(defn diagonals [board]
  [(top-left-diagonal board) (top-right-diagonal board)])
