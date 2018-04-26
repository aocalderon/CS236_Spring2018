import org.apache.spark.sql.simba.SimbaSession
import org.apache.spark.sql.simba.index.RTreeType
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.catalyst.ScalaReflection

object Project {
  case class POI(pid: Long, tags: String, poi_lon: Double, poi_lat: Double)

  def main(args: Array[String]): Unit = {
    val simba = SimbaSession
      .builder()
      .master("local[4]")
      .appName("Project")
      .config("simba.index.partitions", "8")
      .getOrCreate()

    import simba.implicits._

    val schema = ScalaReflection.schemaFor[POI].dataType.asInstanceOf[StructType]
    val pois = simba.read
      .option("header", "false")
      .schema(schema)
      .csv(args(0))
      .as[POI]
    pois.show(truncate = false)
    println(pois.count())
    
    import simba.simbaImplicits._
    pois.index(RTreeType, "rtPOIs", Array("poi_lon", "poi_lat"))
    val results = pois.range(Array("poi_lon", "poi_lat"), Array(-64750.0,3364840.0), Array(-64760.0,3364860.0))
    results.show()
    
    simba.stop()
  }
}
