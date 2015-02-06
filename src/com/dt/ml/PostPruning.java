package com.dt.ml;

import java.util.ArrayList;

public class PostPruning {
	
	DecisonTreeCons D;
	DecisonTreeCons Dbest;
	DecisonTreeCons Dprime;
	HW1 mainClass = new HW1();
	
	public PostPruning(int l, int k, ArrayList<Record> records){
		
	
		D = new DecisonTreeCons(records, HW1.attributes);
		D.buildTree();
		//dtree_best.printDtree(-1);
		
	}
	
	
	public DecisonTreeCons prune(int l, int k){
		Dbest = D;
		Dprime = new DecisonTreeCons();
		for(int i = 1 ; i<= l;l++){
			//Copy the tree D into a new tree D-prime
			
			//M = a random number between 1 and K
			int m = getRandom_number(1, k);
			
			for(int j = 1; j<=m;j++){
				//Let N denote the number of non-leaf nodes in the decision
				int n = get_numNonLeaf(Dprime);
				
				
				
				
				
				//Order the nodes in D-prime from 1 to N
				
				//P = a random number between 1 and N;
				int p = getRandom_number(1, n);
				
				//Replace the subtree rooted at P in D0 by a leaf node.
				
				//Assign the majority class of the subset of the data at P to
				//the leaf node.;
				
			}
			
			//double accracy_dprime = 
			if( accracy_dprime > accuracy_dbest){
				Dbest = Dprime;
			}
		}
		return Dbest;
			
	
		
	}
	
	
	
	private int get_numNonLeaf(DecisonTreeCons dprime) {

			int num_nonleaf = 0;
			DecisonTreeCons node = dprime;
			
			for(DecisonTreeCons node: dprime.child.get(index))
			
			for(int counter = 0; counter < node.child.size(); counter++ ){
				
				while(node.classvalue.equals(null)){
					num_nonleaf +=1;
					node =  node.child.get(counter).childPointer;
				}
				
							
			}
			return num_nonleaf;
			
		
		
	}


	public int getRandom_number(int num1, int num2 ){
		int r = (int) (Math.random() * (num2 - num1)) + num1;
		return r;
	}
}
