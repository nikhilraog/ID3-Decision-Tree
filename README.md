# ID3-Decision-Tree
ID3 Algorithm for Decision Tree Implementation with Post Pruning

Steps to Run the program:
1. compile the mian class driver file : HW1.java
    
	command : javac HW1.java
	
2. Run the class file as below
	 command : java HW1 1000 20 ../training_set.csv ../validation_set.csv ../test_set.csv yes

Additional notes :

Java version:

nikhil@ubuntu:~/Desktop/decisiontree_pruning_v2/src$ java -version
java version "1.7.0_76"
Java(TM) SE Runtime Environment (build 1.7.0_76-b13)
Java HotSpot(TM) Client VM (build 24.76-b04, mixed mode)
nikhil@ubuntu:~/Desktop/decisiontree_pruning_v2/src$ 


Exact run on my Ubuntu 12.04 - linux machine :

Before any step :
nikhil@ubuntu:~/Desktop/decisiontree_pruning_v2/src$ ls
DecisonTreeCons_heu.java  DecisonTreeCons.java  HW1.java  Pruning_heu.java  Pruning.java  Record.java

After step1:

nikhil@ubuntu:~/Desktop/decisiontree_pruning_v2/src$ javac HW1.java 

nikhil@ubuntu:~/Desktop/decisiontree_pruning_v2/src$ ls
DecisonTreeCons$children.class      DecisonTreeCons_heu.class  HW1.class      Pruning_heu.class  Record.class
DecisonTreeCons.class               DecisonTreeCons_heu.java   HW1.java       Pruning_heu.java   Record.java
DecisonTreeCons_heu$children.class  DecisonTreeCons.java       Pruning.class  Pruning.java
nikhil@ubuntu:~/Desktop/decisiontree_pruning_v2/src$ 

After step2 :
nikhil@ubuntu:~/Desktop/decisiontree_pruning_v2/src$ java HW1 1000 20 ../training_set.csv ../validation_set.csv ../test_set.csv yes


 Accuracy with out prunning - with Entropy as heuristic : 72.33333333333334
  Best accuracy after pruning : entropy heuritic is: 77.5


