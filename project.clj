(defproject marceline-lab "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aot :all
  :profiles {:provided {:dependencies [[storm "0.9.0.1"]]}}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [yieldbot/marceline "0.1.0"]
                 ]
  :min-lein-version "2.0.0"
  )
