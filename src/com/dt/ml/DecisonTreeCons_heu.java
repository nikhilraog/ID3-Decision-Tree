package com.dt.ml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class DecisonTreeCons_heu  {


	ArrayList<children> child; 
	public class children{
		DecisonTreeCons_heu childPointer;
		int value;
		children(DecisonTreeCons_heu address, int v){
			value = v;
			childPointer = address;
		}
	}
	HW1 mainClass = new HW1();
	ArrayList<Record> data;
	double variance;
	ArrayList<Integer> remainingattributes;
	int bestattributeindex; 
	String classvalue, className;
	boolean isLeaf;
	int pos_classcount;
	int neg_classcount;

	public DecisonTreeCons_heu(ArrayList<Record> records,ArrayList<Integer> remainingattributes1) {


		data = records;
		remainingattributes = new ArrayList<Integer>();
		for(int count = 0; count < remainingattributes1.size(); count++){
			this.remainingattributes.add(remainingattributes1.get(count));
		}

		pos_classcount = calc_poscount(data);
		neg_classcount = records.size() - pos_classcount; 
		variance = calcVariance(data);
		isLeaf = false;
		child = new ArrayList<DecisonTreeCons_heu.children>();

	}

	public void buildTree() throws FileNotFoundException {

		if(remainingattributes.size() == 0 ){
			setLeaf(1);

		}

		else if(pos_classcount == data.size() || neg_classcount == data.size()){ 
			setLeaf(2);

		}
		else{

			bestattributeindex = findBestAttribute();

			if(bestattributeindex != -1){
				className = mainClass.getAttributeName(remainingattributes.get(bestattributeindex));
				int temp = remainingattributes.get(bestattributeindex);
				remainingattributes.remove(bestattributeindex);

				for(int j = 0; j< 2;j++){

					ArrayList<Record> subrecordschild = getSubrecord(data, temp, Integer.toString(j));
					if(subrecordschild.size() != 0){

						DecisonTreeCons_heu child_temp = new DecisonTreeCons_heu(subrecordschild, remainingattributes);
						child.add(new children(child_temp, j));
						child_temp.buildTree();

					}

				}
			}
			else{
				setLeaf(1);
			}

		}
	}

	private void setLeaf(int i) {
		if(i == 2){
			isLeaf = true;
			classvalue = data.get(0).class_label;

		}
		else if( i == 1){
			isLeaf = true;
			if(pos_classcount> neg_classcount){
				classvalue = "1";
			}
			else{ classvalue ="0";
			}
		}

	}

	public int findBestAttribute() throws FileNotFoundException {

		double max_variance = 0;
		int index =  0;

		for(int i = 0; i< remainingattributes.size();i++){

			double sub_pos_neg_variance = 0;
			for(int j=0; j<2; j++){

				ArrayList<Record> subdata = getSubrecord(data, remainingattributes.get(i), Integer.toString(j));
				double  curvalue_variance  =  findCurrentVariance(subdata);
				//System.out.println(curvalue_variance+"---<");
				//sub_pos_neg_variance  += ( (double)(double)subdata.size()/ (double)data.size() )  * curvalue_variance;
				sub_pos_neg_variance  += (subdata.size()/ (double)data.size() ) * curvalue_variance;
			}
			
			//System.out.println("Sub variance for attribute : "+ mainClass.getAttributeName(i) + " : "+ sub_pos_neg_variance);
			if(max_variance < (variance - sub_pos_neg_variance)){

				max_variance = variance - sub_pos_neg_variance;
				index = i;	

			}
		}
		if(max_variance == 0){
			return -1;
		}

		return index;
	}


	private double findCurrentVariance(ArrayList<Record> subdata) {
		int p_count = 0; int n_count = 0;
		
		for(int i = 0; i< subdata.size();i++){
			Record record = subdata.get(i);

			if	(record.getClass_label().equals("1")){

				p_count += 1;
			}
		}
		n_count = subdata.size() - p_count;
		double a = (double)((double)p_count/ subdata.size());
		double b = (double)((double)n_count/ subdata.size());
		if( !Double.isNaN((a*b)) ){
			return (a*b);
		}
		else{
			return 0;
		}

	}

	public ArrayList<Record> getSubrecord(ArrayList<Record> datalist, int attributeposition, String attributevalue) {

		ArrayList<Record> subset = new ArrayList<Record>();
		for(int i = 0; i < datalist.size(); i++) {
			Record record = datalist.get(i);
			if(record.getRecord_row()[attributeposition].equals(attributevalue)) {
				subset.add(record);
			}
		}

		return subset;
	}


	private double calcVariance(ArrayList<Record> records) {

		int totalrecords = pos_classcount+neg_classcount;
		double a = (double)((double)pos_classcount/(double)totalrecords);
		double b = (double)((double)neg_classcount/(double)totalrecords);
		double c = a*b;
		if(!Double.isNaN(c)){
			return c;
		}
		else{
			return 0;
		}
	
	}



	private int calc_poscount(ArrayList<Record> records) {
		int count = 0;
		for(int j = 0; j < records.size(); j++) {
			Record record = records.get(j);
			int a = Integer.parseInt(record.getClass_label());
			if(a == 1) {
				count += 1;
			}

		}
		return count;
	}

	public String printDtree(int tabCount) {
		tabCount++;


		if(isLeaf ){

			return classvalue;
		}
		else{
			for(int counter = 0; counter < child.size(); counter++ ){
				System.out.println();

				for(int count = 0; count < tabCount; count++){
					System.out.print("| ");
				}
				System.out.print("|" + className + "= " + child.get(counter).value + ": ");
				String formatPrint = child.get(counter).childPointer.printDtree(tabCount);
				if(formatPrint.equals("0") || formatPrint.equals("1")){
					System.out.print(formatPrint);
				}
			}
			return "null";
		}
	}

	public String traverseTree(Record testRecord){

		DecisonTreeCons_heu node = this;
		while( node.isLeaf != true ){

			String best_attribute_name = node.className;
			int best_attribute_index = HW1.printattributes.indexOf(best_attribute_name);
			int testrec_value  = Integer.parseInt(testRecord.getRecord_row()[best_attribute_index]);
			node = node.child.get(testrec_value).childPointer;	
		}
		String obtained_classlabel = node.classvalue;
		return obtained_classlabel;
	}



}
