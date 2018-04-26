name := "PFlock"
organization := "UCR-DBLab"
version := "2.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.1.0"

mainClass in (Compile, run) := Some("WordCount")
mainClass in (Compile, packageBin) := Some("WordCount")

