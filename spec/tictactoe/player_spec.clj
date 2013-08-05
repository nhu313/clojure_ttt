(ns tictactoe.player-spec
  (:require [speclj.core :refer :all]
            [tictactoe.player :refer :all]))

(describe "Player"
  (context "human"
    (def human (tictactoe.player.Human. "X"))

    (it "value is the value passed in"
      (should= "X" (:value human)))

    (it "asks player for a move"
      (with-redefs [read-line (constantly "0")]
        (should= true (.contains (with-out-str (move human nil)) "X, please make a move:"))))

    (it "returns user move"
      (with-redefs [read-line (constantly "3") println (constantly "")]
        (should= 3 (move human nil)))))

  (context "computer"
    (def computer (tictactoe.player.Computer. "O"))

    (it "value is the value passed in"
      (should= "O" (:value computer)))

    (it "returns first move"
        (should= 2 (move computer ["X" "X" nil nil])))
  )
)
