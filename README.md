# CC2
Install Java Development Kit (JDK)
If you haven't already installed JDK, you can do so by running the following commands:

sudo apt update
sudo apt install default-jdk

Verify the installation by checking the Java version using command:

java -version

Install Apache Spark
You can download Apache Spark from the official website: https://spark.apache.org/downloads.html
Extract the downloaded Spark archive to a suitable location on your system.

Set Up Environment Variables
Add Spark's bin directory to your PATH environment variable:

export PATH=/path/to/spark/bin:$PATH
Replace /path/to/spark with the actual path where Spark is extracted.

Install all the necessary jar files like

org.apache.spark.api.java.JavaSparkContext;
org.apache.spark.SparkConf;
org.apache.spark.sql.SparkSession;
org.apache.spark.sql.Dataset;
org.apache.spark.sql.Row;
org.apache.spark.ml.feature.VectorAssembler;
org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
org.apache.spark.ml.classification.RandomForestClassifier;
org.apache.spark.ml.classification.RandomForestClassificationModel;

Compile the Java Code
Navigate to the directory containing your Java code (WineQualityPrediction.java) and compile it using javac:

javac -cp "/path/to/spark/jars/*" WineQualityPrediction.java
Replace /path/to/spark/jars/* with the actual path to Spark's JAR files.

Run the Java Application
After successful compilation, you can run the Java application:

java -cp "/path/to/spark/jars/*":. WineQualityPrediction
Replace /path/to/spark/jars/* with the actual path to Spark's JAR files.

