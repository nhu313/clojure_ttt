(ns tictactoe.player-spec
  (:require [speclj.core :refer :all]
    [tictactoe.player :refer :all]
    [tictactoe.rules :refer [x o]]
    ))

(describe "Player"
  (context "human"
    (def human (create-player :human x))

    (it "marker is the value passed in"
      (should= x (:marker human)))

    (it "asks player for a move"
      (with-redefs [read-line (constantly "0")]
        (should= true (.contains (with-out-str (move human nil)) "X, please make a move:")))

    (it "returns user move"
      (with-redefs [read-line (constantly "3") println (constantly "")]
        (should= 3 (move human nil))))))

    (context "computer"
      (def computer (create-player :computer o))

      (it "marker is the value passed in"
        (should= o (:marker computer)))

      (it "returns move when there is only move available"
        (should= 7 (move computer [x  o  o
                                   o  x  x
                                   x nil o])))

      (it "returns a winning move when there is only a winning and losing move"
        (should= 7 (move computer [nil  o  x
                                    x   o  o
                                    x  nil x])))

      (it "returns a winning move when there is only a winning and draw move"
        (should= 8 (move computer [nil  x    o
                                    x   o    o
                                    x   o   nil])))

      (it "returns the draw move when there is only a draw and losing move"
        (should= 8 (move computer [x  o   o
                                   o  x   x
                                   x nil nil])))

      (it "chooses winning move when one is available when there are three left"
        (should= 6 (move computer [nil x o
                                   nil o x
                                   nil o x])))

      (it "chooses a blocking move when there are three moves left"
        (should= 6 (move computer [nil o x
                                   nil x o
                                   nil x o])))

      (it "chooses a draw move when that's the best option"
        (should= 8 (move computer [ x   o   x
                                   nil  o   x
                                   nil  x  nil])))

      (it "chooses winning move when one is available"
        (should= 7 (move computer [x   o  nil
                                  nil  o  nil
                                  nil nil nil])))

      (it "chooses a winning move before blocking"
        (should= 8 (move computer [ x  nil  o
                                    x  nil  o
                                   nil nil nil])))

      (it "chooses a move that create 2 winning moves"
        (should= 4 (move computer [o  x   nil
                                   x nil   x
                                   o nil  nil])))

      (it "chooses blocking move when there is no winning move"
        (should= 7 (move computer [nil  x  o
                                   nil  x  nil
                                   nil nil nil])))))
