import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.classification.RandomForestClassificationModel;

public class WineQualityPrediction {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("WineQualityPrediction").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        // Load training dataset
        Dataset<Row> trainingData = spark.read().option("header", true).csv("TrainingDataset.csv");

        // Load validation dataset
        Dataset<Row> validationData = spark.read().option("header", true).csv("ValidationDataset.csv");

        // Preprocess data
        String[] featureColumns = {"fixed acidity", "volatile acidity", "citric acid", "residual sugar",
                "chlorides", "free sulfur dioxide", "total sulfur dioxide",
                "density", "pH", "sulphates", "alcohol"};
        VectorAssembler assembler = new VectorAssembler().setInputCols(featureColumns).setOutputCol("features");
        trainingData = assembler.transform(trainingData);
        validationData = assembler.transform(validationData);

        // Train a RandomForestClassifier
        RandomForestClassifier rf = new RandomForestClassifier()
                .setLabelCol("quality")
                .setFeaturesCol("features")
                .setNumTrees(10);
        RandomForestClassificationModel model = rf.fit(trainingData);

        // Make predictions on validation data
        Dataset<Row> predictions = model.transform(validationData);

        // Evaluate model performance
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setLabelCol("quality")
                .setPredictionCol("prediction")
                .setMetricName("f1");
        double f1Score = evaluator.evaluate(predictions);

        System.out.println("F1 Score: " + f1Score);

        spark.stop();
    }
}
