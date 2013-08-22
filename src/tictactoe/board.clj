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
    (every? (fn[item] (not (nil? item))) board))

(defn replace-nil-with-index [board]
  (map-indexed (fn [i item] (if item item i)) board))

(defn available-moves [board]
  (filter number? (replace-nil-with-index board)))
