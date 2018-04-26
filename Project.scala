package org.apache.spark.sql.simba.examples

import org.apache.spark.sql.simba.SimbaSession
import org.apache.spark.sql.simba.index.RTreeType
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.catalyst.ScalaReflection

object Project {
  case class PointData(pid: Int, x: Double, y: Double, tag: String)

  def main(args: Array[String]): Unit = {
    val simba = SimbaSession
      .builder()
      .master("local[4]")
      .appName("Project")
      .config("simba.index.partitions", "8")
      .getOrCreate()

    import simba.implicits._

    val schema = ScalaReflection.schemaFor[PointData].dataType.asInstanceOf[StructType]
    val points = simba.read
      .option("header", "false")
      .schema(schema)
      .csv(args(0))
      .as[PointData]
    points.show(truncate = false)
    println(points.count())
    
    import simba.simbaImplicits._
    points.index(RTreeType, "rtPoints", Array("x", "y"))
    val results = points.range(Array("x", "y"), Array(1.0, 1.0), Array(6.0, 6.0))
    results.show()
    
    simba.stop()
  }
}
