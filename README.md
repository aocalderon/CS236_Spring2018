# CS236_Spring2018

1. Check version for Scala and SBT...

```bash
sbt sbtVersion
[info] Loading project definition from /opt/CS236_Spring2018/project
[info] Set current project to cs236_spring2018 (in build file:/opt/CS236_Spring2018/)
[info] 1.1.1

scala -version
Scala code runner version 2.11.8 -- Copyright 2002-2016, LAMP/EPFL
```

2. Check Spark 2.1 installation.  I like to set $SPARK_HOME environment variable and add $SPARK_HOME/bin to my path. Let's have a look at a simple example...

```bash
spark-submit --class org.apache.spark.examples.SparkPi /opt/Spark/spark-2.1.0-bin-hadoop2.7/examples/jars/spark-examples_2.11-2.1.0.jar 
Pi is roughly 3.1452957264786323
```

3. Let's run an custom example in Spark...

3.1. `cd WordCount/`
3.2. `sbt package`
3.3. `spark-submit /opt/CS236_Spring2018/WordCount/target/scala-2.11/pflock_2.11-2.0.jar`

4. Compile and test Simba...

4.1. Download code from [https://github.com/InitialDLab/Simba]
4.2. From Simba folder package by `sbt package`
4.3. Move the simba jar file to the Spark jar folder : `mv /path/to/Simba/target/scala-2.11/simba_2.11-1.0.jar /path/to/Spark/jars/`

