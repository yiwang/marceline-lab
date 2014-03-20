(ns marceline-lab.core
  (:require [marceline.storm.trident :as t])
  (:import storm.trident.TridentTopology
            [storm.trident.testing FixedBatchSpout MemoryMapState$Factory]
            [backtype.storm LocalDRPC LocalCluster StormSubmitter])
  (:use [backtype.storm config])
  (:gen-class))

(defn mk-fixed-batch-spout [max-batch-size]
  (FixedBatchSpout.
    ;; Name the tuples that the spout will emit.
    (t/fields "sentence")
    max-batch-size
    (into-array (map t/values '("lord ogdoad"
                                     "master of level eight shadow world"
                                     "the willing vessel offers forth its pure essence")))))

(defn mk-topology []
  (let [trident-topology (TridentTopology.)
        spout (doto (mk-fixed-batch-spout 3)
                (.setCycle true))]
    (t/new-stream trident-topology "word-counts" spout)
    trident-topology))


(defn run-local! []
  (let [cluster (LocalCluster.)]
    (.submitTopology cluster "wordcounter" {TOPOLOGY-DEBUG true} (.build (mk-topology)))
    (Thread/sleep 10000)
    (.shutdown cluster)
    ))

(defn submit-topology! [name]
  (StormSubmitter/submitTopology
    name
    {TOPOLOGY-DEBUG true
     TOPOLOGY-WORKERS 3}
    (.build (mk-topology))))

(defn -main
  ([]
    (run-local!))
  ([name]
    (submit-topology! name)))

