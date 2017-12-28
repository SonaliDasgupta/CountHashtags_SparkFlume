
import org.apache.spark.mllib.classification.NaiveBayesModel
import org.apache.spark.streaming.{ Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume._
import org.apache.spark.streaming.flume.FlumeUtils._

object FlumeSparkPopularHashtags{
val conf=new SparkConf().setMaster("yarn").setAppName("spark fluem streaming")
val sc=new SparkContext(conf)

/*def isTweetInEnglish(status: Status): Boolean = {
    status.getLang == "en" && status.getUser.getLang == "en"
}

 def replaceNewLines(tweetText: String): String = {
    tweetText.replaceAll("\n", "")
}

def predictSentiment(status: Status): (Long, String, String, Int, Int, String, String) = {
      val tweetText = replaceNewLines(status.getText)
      val (corenlpSentiment, mllibSentiment) = {
        // If tweet is in English, compute the sentiment by MLlib and also with Stanford CoreNLP.
        if (isTweetInEnglish(status)) {
          (CoreNLPSentimentAnalyzer.computeWeightedSentiment(tweetText),
            MLlibSentimentAnalyzer.computeSentiment(tweetText, stopWordsList, naiveBayesModel))
        } else {
          // TODO: all non-English tweets are defaulted to neutral.
          // TODO: this is a workaround :: as we cant compute the sentiment of non-English tweets with our current model.
          (0, 0)
        }
      }
      (status.getId,
        status.getUser.getScreenName,
        tweetText,
        corenlpSentiment,
        mllibSentiment,
        status.getUser.getOriginalProfileImageURL,
        simpleDateFormat.format(status.getCreatedAt))
    }
*/

def main(args: Array[String]){
//sc.setLogLevel("WARN")
val ssc=new StreamingContext(sc,Seconds(5))
val filter=args.takeRight(args.length)
val stream=FlumeUtils.createStream(ssc,"localhost",9988)
val tweets=stream.map(e=> new String(e.event.getBody.array))
tweets.print()

//for each tweet print sentiment of it, later we can filter tweets of any topic
//val classifiedTweets=tweets.map(predictSentiment)
val model=NaiveBayesModel.load(sc.sparkContext,"src/main/model")
//extract hashtags
val hashTags=tweets.flatMap(status=> status.split(" ").filter(_.startsWith("#")))

//top hashtags for minute
val topCounts60=hashTags.map((_,1)).reduceByKeyAndWindow(_+_,Seconds(60)).map { case (topic,count) => (count,topic) }.transform(_.sortByKey(false))

 //top hashtags for 10 sec
val topCount10=hashTags.map((_,1)).reduceByKeyAndWindow(_+_,Seconds(10)).map{ case (topic,count)=> (count,topic)}.transform(_.sortByKey(false))

//print top 10 hashtags for 60 sec
topCounts60.foreachRDD(rdd=> {
val topList=rdd.take(10)
println("popular hastags for last 60 sec: (%s total)".format(rdd.count()))
topList.foreach{ case (count, tag)=> println("%s (%s tweets)".format(tag, count))
}
})

topCount10.foreachRDD(rdd=> {
val topList=rdd.take(10)
println("popular hastags for last 10 sec: (%s total)".format(rdd.count()))
topList.foreach{ case (count,tag)=> println("%s (%s tweets)".format(tag,count))}})

stream.count.map(cnt => "Received "+cnt+" flume events.").print()
ssc.start()
ssc.awaitTermination()
}
}





