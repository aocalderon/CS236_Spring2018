import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object WordCount {
  def main(args: Array[String]) {
		val spark = SparkSession.builder
			.master("local[*]")
			.appName("WordCount")
			.getOrCreate()
		import spark.implicits._
		
		val data = spark.read.text("src/main/resources/data.txt")
			.as[String]
		val words = data.flatMap(value => value.split("\\s+"))
		val groupedWords = words.groupByKey(_.toLowerCase)	
		val counts = groupedWords.count().toDF("word", "count")
		counts.orderBy(desc("count")).show()
		
		spark.close()		
  }
}
