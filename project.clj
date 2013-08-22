(defproject tictactoe "0.1.0-SNAPSHOT"
  :main tictactoe.game
  :description "Simple Tic Tac Toe with AI"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles {:dev {:dependencies [[speclj "2.5.0"]]}}
  :plugins [[speclj "2.5.0"]]
  :test-paths ["spec"])
