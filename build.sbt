name := "spark-twitter-streaming-ex"
version := "1.0"
scalaVersion := "2.11.0"
libraryDependencies ++= Seq(
"org.apache.spark" %% "spark-streaming" % "2.2.1" % "provided",
"org.apache.spark" %% "spark-core" % "2.2.1" % "provided",
"org.apache.spark" %% "spark-mllib" % "2.2.1",
"org.apache.spark" %% "spark-sql" % "2.2.1",
"org.apache.spark" %% "spark-streaming-flume" % "2.2.1",
"org.apache.spark" %% "spark-streaming-flume-sink" % "2.2.1",
"edu.stanford.nlp" %% "stanford-corenlp" % "3.5.1",
"edu.stanford.nlp" %% "stanford-corenlp" % "3.5.1" classifier "models"
)




