name := "Project"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.0"
libraryDependencies += "InitialDLab" % "simba_2.11" % "1.0"

mainClass in (Compile, run) := Some("Project")
mainClass in (Compile, packageBin) := Some("Project")