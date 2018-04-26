import org.apache.spark.sql.simba.{Dataset, SimbaSession}
import org.apache.spark.sql.simba.index.{RTreeType, TreapType}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.catalyst.ScalaReflection

/**
  * Created by dongx on 3/7/2017.
  */
object Project {
  case class PointData(x: Double, y: Double, z: Double, other: String)
  case class POI(pid: Long, tags: String, poi_lon: Double, poi_lat: Double)
  case class Trajectory(tid: Long, oid: Long, lon: Double, lat: Double, time: String)

  def main(args: Array[String]): Unit = {
    val simba = SimbaSession
      .builder()
      .master("local[4]")
      .appName("Project")
      .config("simba.index.partitions", "64")
      .config("simba.index.partitions", "16")
      .getOrCreate()
    simba.sparkContext.setLogLevel("ERROR")

    import simba.implicits._
    import simba.simbaImplicits._

    val schema = ScalaReflection.schemaFor[POI].dataType.asInstanceOf[StructType]
    val pois = simba.read.
      option("header", "true").
      schema(schema).
      csv("/home/and/Documents/PhD/TA/CS236FinalProject/Datasets/POIs.csv").
      as[POI]
    pois.show(truncate = false)
    println(pois.count())
    pois.filter(_.tags contains "restaurant").collect().foreach(println)

    simba.stop()
  }

  private def buildIndex(simba: SimbaSession): Unit = {
    import simba.implicits._
    val datapoints = Seq(PointData(1.0, 1.0, 3.0, "1"),  PointData(2.0, 2.0, 3.0, "2"), PointData(2.0, 2.0, 3.0, "3"),
      PointData(2.0, 2.0, 3.0, "4"),PointData(3.0, 3.0, 3.0, "5"),PointData(4.0, 4.0, 3.0, "6")).toDS

    datapoints.createOrReplaceTempView("a")

    simba.indexTable("a", RTreeType, "testqtree",  Array("x", "y") )

    simba.showIndex("a")
  }

  private def useIndex1(simba: SimbaSession): Unit = {
    import simba.implicits._
    import simba.simbaImplicits._
    val datapoints = Seq(PointData(1.0, 1.0, 3.0, "1"),  PointData(2.0, 2.0, 3.0, "2"), PointData(2.0, 2.0, 3.0, "3"),
      PointData(2.0, 2.0, 3.0, "4"),PointData(3.0, 3.0, 3.0, "5"),PointData(4.0, 4.0, 3.0, "6")).toDF()

    datapoints.createOrReplaceTempView("b")

    simba.indexTable("b", RTreeType, "RtreeForData",  Array("x", "y") )

    simba.showIndex("b")

    val res = simba.sql("SELECT * FROM b")
    res.knn(Array("x", "y"),Array(1.0, 1.0),4).show(4)

  }

  private def useIndex2(simba: SimbaSession): Unit = {
    import simba.implicits._
    val datapoints = Seq(PointData(1.0, 1.0, 3.0, "1"),  PointData(2.0, 2.0, 3.0, "2"), PointData(2.0, 2.0, 3.0, "3"),
      PointData(2.0, 2.0, 3.0, "4"),PointData(3.0, 3.0, 3.0, "5"),PointData(4.0, 4.0, 3.0, "6")).toDF()

    datapoints.createOrReplaceTempView("b")

    simba.indexTable("b", RTreeType, "RtreeForData",  Array("x", "y") )

    simba.showIndex("b")

    simba.sql("SELECT * FROM b where b.x >1 and b.y<=2").show(5)

  }

  private def useIndex3(simba: SimbaSession): Unit = {
    import simba.implicits._
    val datapoints = Seq(PointData(0.0, 1.0, 3.0, "1"),  PointData(2.0, 2.0, 3.0, "2"), PointData(2.0, 2.0, 3.0, "3"),
      PointData(2.0, 2.0, 3.0, "4"),PointData(3.0, 3.0, 3.0, "5"),PointData(4.0, 4.0, 3.0, "6")).toDS()

    import simba.simbaImplicits._

    datapoints.index(TreapType, "indexForOneTable",  Array("x"))

    datapoints.range(Array("x"),Array(1.0),Array(2.0)).show(4)
  }
}
