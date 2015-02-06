package com.dt.ml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import sun.swing.AccumulativeRunnable;

@SuppressWarnings("unused")
public class HW1 {

	public static ArrayList<Integer> attributes = new ArrayList<Integer>();
	public static ArrayList<String> usedattributes = new ArrayList<String>();
	public static ArrayList<String> printattributes = new ArrayList<String>();
	public static ArrayList<Record> records = new ArrayList<Record>();
	public static ArrayList<Record> testrecords = new ArrayList<Record>();


	public static void readTrainingFile(String filename) throws Exception{

		BufferedReader br = new BufferedReader(new FileReader(filename));
		String eachline = br.readLine();

		String[] featurelist =  eachline.split(",");
		int num_features = featurelist.length - 1; //20 +1->class label

		int i = 0;
		for(String s: eachline.split(",") ){
			attributes.add(i);
			printattributes.add(s);
			i++;
		}
		attributes.remove(num_features); 
		printattributes.remove(num_features);

		while(eachline!=null){

			eachline = br.readLine();
			if(eachline !=null)
			{

				Record r = new Record(num_features);
				String eachlinenew = eachline.substring(0,2*(num_features));
				String arr1[] = eachlinenew.split(",");
				String arr2[] = eachline.split(",");
				r.setRecord_row(arr1);
				r.setClass_label(arr2[num_features]);
				records.add(r);

			}
		}
		br.close();
	}
	
	public static void readTestFile(String testfilename) throws Exception{

		BufferedReader br = new BufferedReader(new FileReader(testfilename));
		String eachline = br.readLine();
		String[] t_featurelist =  eachline.split(",");
		int t_num_features = t_featurelist.length - 1; 
		while(eachline!=null){

			eachline = br.readLine();
			if(eachline !=null)
			{

				Record r = new Record(t_num_features);
				String eachlinenew = eachline.substring(0,2*(t_num_features));
				String arr1[] = eachlinenew.split(",");
				String arr2[] = eachline.split(",");
				r.setRecord_row(arr1);
				r.setClass_label(arr2[t_num_features]);
				testrecords.add(r);
			}
		}
		br.close();
	}


	public String getAttributeName(int attValue){
		return printattributes.get(attValue);
	}

	public static void main(String[] args) throws Exception {
		if(args.length!= 6){
			System.out.println("Enter input as .program <L> <K> <training-set> <validation-set> <test-set> <to-print>");
			return;
		}

		int l = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		String trainingfile = args[2];
		String validationfile = args[3];
		String testfile = args[4];
		String toprint = args[5];
		
		readTrainingFile("training_set.csv");
		DecisonTreeCons Dtree = new DecisonTreeCons(records, attributes);
		Dtree.buildTree();

		DecisonTreeCons_heu Dtree_heu = new DecisonTreeCons_heu(records, attributes);
		Dtree_heu.buildTree();

	if(toprint.equals("yes")){
			System.out.println();
			System.out.println(" Tree constructed with Entropy or Information Gain as heuristic : ");
		    Dtree.printDtree(-1);
			System.out.println();
			System.out.println("-------------------------------------------------------------------");
			System.out.println(" Tree constructed with Impurity variance as heuristic : ");
			Dtree_heu.printDtree(-1);

		}


		readTestFile("test_set.csv");
		int acc1 = 0;
		for( int i = 0; i < testrecords.size();i++){

			String obtained_cl = Dtree.traverseTree(testrecords.get(i));
			if(obtained_cl.equals(testrecords.get(i).class_label)){
				acc1 +=1;
			}	
		}
		double accu = (double)(((double)acc1/(double)testrecords.size())*100);
		System.out.println();
		System.out.println();
		System.out.println(" Accuracy with out prunning - with Entropy as heuristic : "+ accu);
		
		//PostPruning p1 = new PostPruning(l, k, records);
		
		int acc2 = 0;
		for( int i = 0; i < testrecords.size();i++){

			String impurity_classvalue = Dtree_heu.traverseTree(testrecords.get(i));
			if(impurity_classvalue.equals(testrecords.get(i).class_label)){
				acc2 +=1;
			}	
		}
		
		double accu2 = (double)(((double)acc2/(double)testrecords.size())*100);
		
		System.out.println();
		System.out.println(" Accuracy with out prunning - with Variance impurity as heuristic : "+ accu2);
		
	}

}

