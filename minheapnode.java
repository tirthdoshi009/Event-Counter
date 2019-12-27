//This is simply a node class
public class minheapnode {
	public int buildingNum;
	public int total_time;
	public int executed_time;
	public RedBlackTree.RedBlackNode pointertorb;
	
	/**
	 * 
	 */
	public minheapnode(int building_num, int totaltime, int executedtime){
		this.executed_time = executedtime;
		this.buildingNum = building_num;
		this.total_time = totaltime;
	}
	
}
