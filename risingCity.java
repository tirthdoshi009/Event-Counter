import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class risingCity {
	

	public static void main(String[] args) throws FileNotFoundException {
		// Initializing the variables
		String filename = args[0];
		File file = new File(filename);
		File output_file = new File("output.txt");
		PrintWriter out = new PrintWriter(output_file);
		Scanner scan = new Scanner(file);
		String command = scan.nextLine();
		int x= command.indexOf(":");
		int current_time = Integer.parseInt(command.substring(0, x)),checkprint2 = -999
		,globalcounter = 0, totaltime =0, picknextbuilding = 1, no_of_days_worked = 0, a=0, b=0
		,checkprint = -999;
		minheapnode x1 = null;
		RedBlackTree rb = new RedBlackTree();
		minheap mp = new minheap();
		minheapnode minheappointer;
		RedBlackTree.RedBlackNode redblackpointer;
		while(globalcounter<=totaltime || scan.hasNextLine() || rb.root!=rb.dummy || b>=globalcounter)
		{		
			//Storing the command in temporary buffer
			String temp = command;
			if(current_time == globalcounter)
			{
				//Read the next line
				if(scan.hasNextLine())
				{
					command= scan.nextLine();
				}
				a = command.indexOf(":");
				b = Integer.parseInt(command.substring(0, a));
				x= command.indexOf(":");
				current_time = Integer.parseInt(command.substring(0,x));
				
				//if the command is insert store the node in minheap and redblack
				//Maintain a pointer from both the sides
				
				if(temp.contains("Insert"))
				{
					int i = temp.indexOf("(");
					int j = temp.indexOf(",");
					int k = temp.indexOf(")");
					int buildingnumber =  Integer.parseInt(temp.substring(i+1, j));
					int total_time = Integer.parseInt(temp.substring(j+1, k));
					
					//insert into the minheap
					minheappointer = mp.insertminheap(buildingnumber,total_time,0);
					//insert to the redblack tree
					redblackpointer = rb.insertprint(buildingnumber, total_time,0);
					// set the pointer to redblack in minheap
					minheappointer.pointertorb = redblackpointer;
					//set the pointer to minheap in redblack
					redblackpointer.ptrtominheap = minheappointer;
					//Increment the total time
					totaltime = totaltime + total_time;
				}
				//There are 2 cases in print
				//When the print has , and when print does not contain ,
				if(temp.contains("Print") && temp.contains(","))
				{
					if(checkprint!=globalcounter)
					{
						if(temp.contains(","))
						{
							
							ArrayList<RedBlackTree.RedBlackNode> res = new ArrayList<>();
							int i = temp.indexOf("(");
							int j = temp.indexOf(",");
							int k = temp.indexOf(")");
							int b1 = Integer.parseInt(temp.substring(i+1, j));
							
							int b2 = Integer.parseInt(temp.substring(j+1, k));
							if(x1!=null)
							{
								if(x1.buildingNum>=b1 && x1.buildingNum<=b2 && x1.executed_time+1 == x1.total_time)
								{
									x1.executed_time++;
									x1.pointertorb.executed_time++;
								}
							}
							//Store the print range in res array
							res = rb.printrange(b1, b2);
							if(res.size()>0)
							{
								for(int n = 0; n<res.size();n++)
								{
									
									RedBlackTree.RedBlackNode building = res.get(n);
									//print the corresponding buildings
									out.print("("+building.BuildingNumber+","+building.executed_time+","+building.total_time+")");
									if(n<res.size()-1)
									{
										//Print , for every building except for the last one in the query
										out.print(",");
									}
								}
								out.println();
							}
							else						{
								out.println("(0,0,0)");
							}							
						}
					}
				}
				//This is the second case when the print contains a ","
				else if(temp.contains("Print") && !temp.contains(",") && checkprint2!=globalcounter)
					{
						int i = temp.indexOf("(");
						int j = temp.indexOf(")");
						int bno = Integer.parseInt((temp.substring(i+1, j)));
						RedBlackTree.RedBlackNode node = rb.dummy;
						node = rb.printsingle(bno);
						if(node == rb.dummy)
						{
							out.println("(0,0,0)");
						}
						else if(node!=rb.dummy)
						{
							out.println("("+node.BuildingNumber+","+node.executed_time+","+node.total_time+")");
						}
					}
			}
			
			//Here is the logic for selecting the building to work on and working on it
			//The next building to be worked on is picked i.e x1
			
			if(picknextbuilding== 1 && mp.size>0)
			{
				no_of_days_worked=0;
				x1= mp.extractmin();
			}
			
			if(x1!=null)
			{
				
				//Work on the building if executed time is less than the total time and no of days worked is less than 5
				if(x1.executed_time<x1.total_time && no_of_days_worked<5)
				{
					
					x1.executed_time++;
					x1.pointertorb.executed_time++;
					no_of_days_worked++;
					picknextbuilding=0;
				}
				// if the picked building is worked for 5 consecutive days and the executed time is not the total time,
				//insert it back in the min heap and make pick next building as 1
				if(no_of_days_worked == 5 && (x1.executed_time!= x1.total_time ))
				{
					mp.insert(x1);
					picknextbuilding =1;
					no_of_days_worked=0;
				}
				// Case when the executed time is equal to the total time
					if( (x1.executed_time == x1.total_time) && mp.size>=0 && x1!=null)
				{
						//Initializing variables
						int z = command.indexOf(":");
						int day = Integer.parseInt(command.substring(0, z));
						//if there is a print statement on the same day as executed time = total time
						//We print the building first and then delete it from the redblack tree
					if(command.contains("Print") && command.contains(",") && day-1 == globalcounter)
					{
						int i = command.indexOf("(");
						int j = command.indexOf(",");
						int k = command.indexOf(")");
						
						int b1 = Integer.parseInt(command.substring(i+1, j));
						int b2 = Integer.parseInt(command.substring(j+1, k));
						if(x1.buildingNum>=b1 && x1.buildingNum<=b2)
						{
							ArrayList<RedBlackTree.RedBlackNode> res1 = new ArrayList<>();
							res1 = rb.printrange(b1, b2);
							if(res1.size()>0)
							{
								for(int n = 0; n<res1.size();n++)
								{
									
									RedBlackTree.RedBlackNode building = res1.get(n);
									out.print("("+building.BuildingNumber+","+building.executed_time+","+building.total_time+")");
									if(n<res1.size()-1)
									{
										out.print(",");
									}
								}
								out.println();
							}
							else						{
								out.println("(0,0,0)");
							}
							out.println("("+x1.buildingNum+","+ (globalcounter+1)+")");
							
							int n = command.indexOf(":");
							checkprint = Integer.parseInt(command.substring(0, n));
						}
					}
					//Similarly, if there is print statement when the executed time == total time we print the building first
					//Then remove from the red black tree
					else if(command.contains("Print") && !command.contains(",") && day-1 == globalcounter)
					{
						int i = command.indexOf("(");
						int j = command.indexOf(")");
						int bno = Integer.parseInt((command.substring(i+1, j)));
						RedBlackTree.RedBlackNode node = rb.dummy;
						if(bno == x1.buildingNum)
						{
							node = rb.printsingle(bno);
							if(node == rb.dummy)
							{
								out.println("(0,0,0)");
							}	
						else if(node!=rb.dummy)
						{
							out.println("("+node.BuildingNumber+","+node.executed_time+","+node.total_time+")");
						}
							out.println("("+x1.buildingNum+","+(globalcounter+1)+")");
							int k = command.indexOf(":");
							checkprint2 = Integer.parseInt(command.substring(0, k));
						}
					}
					else
						{
							out.println("("+x1.buildingNum+","+ (globalcounter+1)+")");
						}
					//Restore the variables 
					picknextbuilding =1;
					no_of_days_worked = 0;
					rb.delete(x1.pointertorb);
					x1= null;	
				}
			}
			//Increment the global counter
				globalcounter++;
		}
		out.close();
	}
}