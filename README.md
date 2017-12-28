# CountHashtags_SparkFlume
Spark streaming integration with Flume to analyse twitter data and list most recent popular hashtags trending on Twitter.

To run the project (in Linux):

1. Run "sbt package" inside the directory to build the target jar.
2. Deploy the target jar using the following command:

"spark-submit --class FlumeSparkPopularHashtags --master yarn-client --jars spark-streaming-flume-assemble_2.11-2.2.1.jar <target-jar-name>" > output.txt

Make sure to download and include the spark-streaming-flume-assembly jar in your classpath.

3. Once YARN has accepted the job, start the flume agent using:

"flume-ng agent -f flume_twitter.conf -n TwitterAgent"

to start streaming tweets from Twitter.

4. The streaming application should be good to go !! The output (most popular hastags in last 60 seconds and 10 seconds) can be viewed in output.txt.

Incase of any issues, try separating the spark-streaming and flume files in different directories.

