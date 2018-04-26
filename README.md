# CS236_Spring2018

1. Check version for SBT...

```bash
sbt sbtVersion
[info] Loading project definition from /opt/CS236_Spring2018/project
[info] Set current project to cs236_spring2018 (in build file:/opt/CS236_Spring2018/)
[info] 1.1.1

```
and Scala ...

```bash
scala -version
Scala code runner version 2.11.8 -- Copyright 2002-2016, LAMP/EPFL
```

2. Check Spark 2.1 installation.  (Optional) Set $SPARK_HOME environment variable and add $SPARK_HOME/bin to your path. Let's have a look at a simple example...

```bash
spark-submit --class org.apache.spark.examples.SparkPi /path/to/Spark/examples/jars/spark-examples_2.11-2.1.0.jar 
Pi is roughly 3.1452957264786323
```

3. Let's run an custom example (WordCount) in Spark...

  * `cd WordCount/`
  * `sbt package`
  * `spark-submit /opt/CS236_Spring2018/WordCount/target/scala-2.11/pflock_2.11-2.0.jar`

4. Compile and test Simba...

  * Download code from [https://github.com/InitialDLab/Simba]
  * From Simba folder package by `sbt package`
  * Move the simba jar file to the Spark jar folder : `mv /path/to/Simba/target/scala-2.11/simba_2.11-1.0.jar /path/to/Spark/jars/`
  * Let's run some of the examples... 
```bash
spark-submit --class org.apache.spark.sql.simba.examples.BasicSpatialOps /path/to/Simba/target/scala-2.11/simba_2.11-1.0.jar

+---+---+---+-----+
|  x|  y|  z|other|
+---+---+---+-----+
|1.0|1.0|3.0|    1|
|2.0|2.0|3.0|    2|
|2.0|2.0|3.0|    3|
|2.0|2.0|3.0|    4|
|3.0|3.0|3.0|    5|
+---+---+---+-----+

+---+---+---+-----+
|  x|  y|  z|other|
+---+---+---+-----+
|1.0|1.0|3.0|    1|
|2.0|2.0|3.0|    2|
|2.0|2.0|3.0|    3|
|2.0|2.0|3.0|    4|
+---+---+---+-----+
...

```
4. How to read CSV files...
  * 
