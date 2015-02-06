package com.dt.ml;

import java.util.ArrayList;

public class DecisonTreeCons {

	ArrayList<children> child; 
	public class children{
		DecisonTreeCons childPointer;
		int value;
		children(DecisonTreeCons address, int v){
			value = v;
			childPointer = address;
		}
	}

	ArrayList<Record> data;
	double entropy;
	ArrayList<Integer> remainingattributes;
	int bestattributeindex; 
	int pos_classcount;
	int neg_classcount;
	boolean isLeaf;
	String classvalue, className;
	HW1 mainClass = new HW1();


	public DecisonTreeCons(){

	}

	public DecisonTreeCons(ArrayList<Record> records, ArrayList<Integer> remainingattributes1){

		data = records;
		remainingattributes = new ArrayList<Integer>();
		for(int count = 0; count < remainingattributes1.size(); count++){
			this.remainingattributes.add(remainingattributes1.get(count));
		}
		pos_classcount = calc_poscount(data);//calculates # of positive class labels for a set of records
		neg_classcount = records.size() - pos_classcount; 
		entropy = calcEntropy(data);
		isLeaf = false;
		child = new ArrayList<DecisonTreeCons.children>();

	}

	public void buildTree() {

		if(remainingattributes.size() == 0 ){
			setLeaf(1); //Leaf Node-- remaining attributes are same
		}

		else if(pos_classcount == data.size() || neg_classcount == data.size()){ 
			setLeaf(2); //Leaf -- class values are same
		}
		else{

			bestattributeindex = findBestAttribute();

			if(bestattributeindex != -1){
				//System.out.println("Defining attribute is: " + remainingattributes.get(bestattributeindex));
				int temp = remainingattributes.get(bestattributeindex);
				className = mainClass.getAttributeName(remainingattributes.get(bestattributeindex));
				remainingattributes.remove(bestattributeindex);

				for(int j = 0; j< 2;j++){

					ArrayList<Record> subrecordschild = getSubrecord(data, temp, Integer.toString(j));
					if(subrecordschild.size() != 0){

						DecisonTreeCons child_temp = new DecisonTreeCons(subrecordschild, remainingattributes);
						child.add(new children(child_temp, j));
						child_temp.buildTree();
					}
					else{
						;//System.out.println(j + " this values has 0 records");
					}
				}
			}
			else{
				setLeaf(1);//Leaf -- Information gain is zero
			}
		}
	}

	public void setLeaf(int i) {
		if(i == 2){
			isLeaf = true;
			classvalue = data.get(0).class_label;

		}
		else if( i == 1){
			isLeaf = true;
			if(pos_classcount> neg_classcount){
				classvalue = "1";
			}
			else{ 
				classvalue ="0";
			}
		}

	}

	public int findBestAttribute() {

		double max_infogain = 0;
		int index =  0;

		for(int i = 0; i< remainingattributes.size();i++){

			double sub_pos_neg_entropies = 0;

			for(int j=0; j<2; j++){

				ArrayList<Record> subdata = getSubrecord(data, remainingattributes.get(i), Integer.toString(j));
				double  curvalue_ent  =  findCurrentEnt(subdata);
				sub_pos_neg_entropies  += ( (double)(double)subdata.size()/(double)data.size() )* curvalue_ent;

			}
			;
			if(max_infogain < (entropy - sub_pos_neg_entropies)){

				max_infogain = entropy - sub_pos_neg_entropies;
				index = i;	
			}
			

		}
		if(max_infogain == 0){
			return -1;//  zero info gain
		}

		return index;
	}


	private double findCurrentEnt(ArrayList<Record> subdata) {

		double negativeent = 0;
		double positiveevnt =  0;
		for(int i = 0 ; i<subdata.size();i++){
			if(subdata.get(i).class_label.equals("0")){
				negativeent += 1;
			}
			else{
				positiveevnt += 1;
			}

		}
		double subdatasize = subdata.size();
		double a = -plogp(positiveevnt/subdatasize);
		double b = -plogp(negativeent/subdatasize);
		return (a+b);

	}


	public ArrayList<Record> getSubrecord(ArrayList<Record> datalist, int attributeposition, String attributevalue) {
		//returns array list of records having particular attribute - indicated by attribute position -- as attribute value - 0 or 1

		ArrayList<Record> subset = new ArrayList<Record>();
		for(int i = 0; i < datalist.size(); i++) {

			Record record = datalist.get(i);
			if(record.getRecord_row()[attributeposition].equals(attributevalue)) {
				subset.add(record);
			}
		}
		return subset;
	}


	public double calcEntropy(ArrayList<Record> records){

		if(records.size() == 0)
			return -1;

		int poscount_class = 0;
		int negcount_class = 0;
		double recsize = records.size();

		for(int j = 0; j < records.size(); j++) {
			Record record = records.get(j);

			int a = Integer.parseInt(record.getClass_label());
			if(a == 1) {
				poscount_class += 1;
			}
			else{
				negcount_class += 1;
			}
		}

		double pos_prob = (double)poscount_class/(double)recsize; 
		double neg_prob = (double)negcount_class/(double)recsize;
		double ent =  -plogp(pos_prob) - plogp(neg_prob);
		return ent;
	}


	private static double plogp(double value) {
		if ( value == 0 ){
			return 0;
		}
		double a =  value *( Math.log(value) / Math.log(2));
		if(!Double.isNaN(a)){
			return a;
		}
		else{
			return 0;
		}
	}


	public int calc_poscount(ArrayList<Record> records) {

		//calculates # of positive class labels for a set of records
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

		DecisonTreeCons node = this;
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
