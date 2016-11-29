package chap5.java.science.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import meka.classifiers.multilabel.PS;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.Utils;

public class Meka {
	public static void main(String args[]) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader("C:/Users/sust_admin/Downloads/meka-release-1.9.0/meka-release-1.9.0/data/Enron.arff"));
		Instances training = new Instances(reader);
		reader = new BufferedReader(new FileReader("C:/Users/sust_admin/Downloads/meka-release-1.9.0/meka-release-1.9.0/data/Enron.arff"));
		Instances testing = new Instances(reader);
		reader.close();
		training.setClassIndex(training.numAttributes()-1);
		testing.setClassIndex(testing.numAttributes()-1);

		SMO smo = new SMO();
		PS ps = new PS();
		ps.setClassifier(smo);
		ps.setOptions(Utils.splitOptions("-threshold PCut1 -verbosity 3"));
		ps.buildClassifier(training);

		Evaluation eval = new Evaluation(training);
		eval.evaluateModel(ps, testing);
		System.out.println(eval.toSummaryString());
		eval.crossValidateModel(ps, testing, 10, new Random(1));
		System.out.println(eval.toSummaryString());
	}
}