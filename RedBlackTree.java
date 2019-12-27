
import java.util.ArrayList;
import java.util.Scanner;

public class RedBlackTree {

    public final int red = 0;
    public final int black = 1;
    public class RedBlackNode{

        int BuildingNumber = -1;
		int executed_time = 0;
		int total_time;
		int color = black;
        RedBlackNode left = dummy, right = dummy, parent = dummy;
        minheapnode ptrtominheap;

        RedBlackNode(int BuildingNumber, int total_time, int executedtime) {
            this.BuildingNumber = BuildingNumber;
			this.executed_time = executedtime;
			this.total_time = total_time;
			this.left = dummy;
			this.right = dummy;
			this.parent = dummy;
        } 
    }

    //Dummy variable simply sets the value of the redblack node to -999,0,0.
    //This dummy variable is used so that there is no null pointer exception
    public final RedBlackNode dummy = new RedBlackNode(-999,0,0); 
    public RedBlackNode root = dummy;
    
    //This function prints the entire tree given the root value
    public void printTree(RedBlackNode root) {
        if (root == dummy) {
            return;
        }
        printTree(root.left);
        System.out.print(((root.color==red)?"Color: red ":"Color: black ")+"Key: "+root.BuildingNumber+" Parent: "+root.parent.BuildingNumber+"\n");
        printTree(root.right);
    }

    //This function searches for a particular node and returns the node when found
    //else it returns null
    private RedBlackNode Search(RedBlackNode findRedBlackNode, RedBlackNode temproot) {
        if (root == dummy) {
            return null;
        }

        if (findRedBlackNode.BuildingNumber < temproot.BuildingNumber) {
            if (temproot.left != dummy) {
                return Search(findRedBlackNode, temproot.left);
            }
        } else if (findRedBlackNode.BuildingNumber > temproot.BuildingNumber) {
            if (temproot.right != dummy) {
                return Search(findRedBlackNode, temproot.right);
            }
        } else if (findRedBlackNode.BuildingNumber == temproot.BuildingNumber) {
            return temproot;
        }
        return null;
    }
    //This is the insert function of the redblack tree
    //This function simply inserts the node as if it is inserting in a Binary Search Tree
    //Later it calls the fix function that does the rotation to fix the tree
    private void insert(RedBlackNode  insertnode) {
        RedBlackNode temp = root;
        if (root == dummy) {
            root = insertnode;
            insertnode.color = black;
            insertnode.parent = dummy;
        } else {
            insertnode.color = red;
            while (true) {
                if (insertnode.BuildingNumber < temp.BuildingNumber) {
                    if (temp.left == dummy) {
                        temp.left = insertnode;
                        insertnode.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (insertnode.BuildingNumber > temp.BuildingNumber) {
                    if (temp.right == dummy) {
                        temp.right = insertnode;
                        insertnode.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
                else if(insertnode.BuildingNumber == temp.BuildingNumber)
                {
                	System.out.println("Cannot insert duplicate building numbers");
                }
            }
            fix(insertnode);
        }
    }
    
  //Takes as argument the newly inserted RedBlackNode
    //
    private void fix(RedBlackNode insertednode) {
        while (insertednode.parent.color == red) {
            RedBlackNode sibling = dummy;
            if (insertednode.parent == insertednode.parent.parent.left) {
                sibling = insertednode.parent.parent.right;
                //if sibling color is red then we simply need to do the color flip and continue going upwards
                if (sibling != dummy && sibling.color == red) {
                    insertednode.parent.color = black;
                    insertednode.color = black;
                    insertednode.parent.parent.color = red;
                    insertednode = insertednode.parent.parent;
                    continue;
                } 
                //RL Rotation 
                if (insertednode == insertednode.parent.right) {
                    //Double rotation needed
                    insertednode = insertednode.parent;
                    rotateLeft(insertednode);
                }
                //RR rotation
                insertednode.parent.color = black;
                insertednode.parent.parent.color = red;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation 
                rotateRight(insertednode.parent.parent);
            } else {
                sibling = insertednode.parent.parent.left;
                //if sibling color is red we simply do the color flips
                 if (sibling != dummy && insertednode.color == red) {
                    insertednode.parent.color = black;
                    sibling.color = black;
                    insertednode.parent.parent.color = red;
                    insertednode = insertednode.parent.parent;
                    continue;
                }
                 //LR and LL rotations
                if (insertednode == insertednode.parent.left) {
                    //Double rotation needed
                    insertednode = insertednode.parent;
                    rotateRight(insertednode);
                }
                insertednode.parent.color = black;
                insertednode.parent.parent.color = red;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotateLeft(insertednode.parent.parent);
            }
        }
        root.color = black;
    }
    

    //this is the rotate left function
    void rotateLeft(RedBlackNode insertednode) {
        if (insertednode.parent != dummy) {
            if (insertednode == insertednode.parent.left) {
                insertednode.parent.left = insertednode.right;
            } else {
                insertednode.parent.right = insertednode.right;
            }
            insertednode.right.parent = insertednode.parent;
            insertednode.parent = insertednode.right;
            if (insertednode.right.left != dummy) {
                insertednode.right.left.parent = insertednode;
            }
            insertednode.right = insertednode.right.left;
            insertednode.parent.left = insertednode;
        } else {
            RedBlackNode right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = dummy;
            root = right;
        }
    }
    
//Basic right rotation function
    void rotateRight(RedBlackNode insertednode) {
        if (insertednode.parent != dummy) {
            if (insertednode == insertednode.parent.left) {
                insertednode.parent.left = insertednode.left;
            } else {
                insertednode.parent.right = insertednode.left;
            }

            insertednode.left.parent = insertednode.parent;
            insertednode.parent = insertednode.left;
            if (insertednode.left.right != dummy) {
                insertednode.left.right.parent = insertednode;
            }
            insertednode.left = insertednode.left.right;
            insertednode.parent.right = insertednode;
        } else {
            RedBlackNode left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = dummy;
            root = left;
        }
    }
    //If a particular node goes empty, we need to change the parent wrt to that node
    //The change parent function simply changes the corresponding parent and child pointers after deletion
    void changeparent(RedBlackNode py , RedBlackNode y){ 
        if(py.parent == dummy){
            root = y;
        }else if(py == py.parent.left){
            py.parent.left = y;
        }else
            py.parent.right = y;
        y.parent = py.parent;
  }
    
    
    //This function simply deletes from a BST and changes the parent pointers respectively.
    //The main work is done by the deletefix function
    boolean delete(RedBlackNode deletednode){
        if((deletednode = Search(deletednode, root))==null)return false;
        RedBlackNode x;
        RedBlackNode y = deletednode; // temporary reference y
        int og_color_y = y.color;
        
        if(deletednode.left == dummy){
            x = deletednode.right;  
            changeparent(deletednode, deletednode.right);  
        }else if(deletednode.right == dummy){
            x = deletednode.left;
            changeparent(deletednode, deletednode.left); 
        }else{
            y = successor(deletednode.right);
            og_color_y = y.color;
            x = y.right;
            if(y.parent == deletednode)
                x.parent = y;
            else{
                changeparent(y, y.right);
                y.right = deletednode.right;
                y.right.parent = y;
            }
            changeparent(deletednode, y);
            y.left = deletednode.left;
            y.left.parent = y;
            y.color = deletednode.color; 
        }
        if(og_color_y==black)
            deletefix(x);  
        return true;
    }


    void deletefix(RedBlackNode deficientnode){
        while(deficientnode!=root && deficientnode.color == black){ 
            if(deficientnode == deficientnode.parent.left){
            	// When the deficient node is the left child of the parent then the right child of parent is w
                RedBlackNode w = deficientnode.parent.right;
                if(w.color == red){
                	//When w is red we simply do a color flip and then rotate left and set the new w
                    w.color = black;
                    deficientnode.parent.color = red;
                    rotateLeft(deficientnode.parent);
                    w = deficientnode.parent.right;
                }
               

                if(w==dummy)
                {
                	w.left = dummy;
                	w.right = dummy;
     
                }
                //When w has a left and right child black
                //Simply change the color of w and set the deficient node as the parent of deficient node
                if(w.left.color == black && w.right.color == black){
                    w.color = red;
                    deficientnode = deficientnode.parent;
                    continue;
                }
                //Case when the right color of w is black
                //Change the left child of w to be black and w's color to be red
                //Rotate right wrt to these 3
                //Set the new W
                else if(w.right.color == black){
                    w.left.color = black;
                    w.color = red;
                    rotateRight(w);
                    w = deficientnode.parent.right;
                }
                //Case when the right color of w is red
                //w takes the color of the parent of the deficient node
                //right color of w becomes black and then we rotate left
                //deficient node becomes root
                if(w.right.color == red){
                    w.color = deficientnode.parent.color;
                    deficientnode.parent.color = black;
                    w.right.color = black;
                    rotateLeft(deficientnode.parent);
                    deficientnode = root;
                }
            }else{
            	//Now we have symmetric cases, just that the w becomes the left child of its parent and the deficient node
            	//is to the right
                RedBlackNode w = deficientnode.parent.left;
                //This is symmetric cases discussed previously
                if(w.color == red){
                    w.color = black;
                    deficientnode.parent.color = red;
                    rotateRight(deficientnode.parent);
                    w = deficientnode.parent.left;
                }
                //Symmetric to cases discussed previously
                if(w.right.color == black && w.left.color == black){
                    w.color = red;
                    deficientnode = deficientnode.parent;
                    continue;
                }
                else if(w.left.color == black){
                    w.right.color = black;
                    w.color = red;
                    rotateLeft(w);
                    w = deficientnode.parent.left;
                }
                //Symmetric to cases discussed previously
                if(w.left.color == red){
                    w.color = deficientnode.parent.color;
                    deficientnode.parent.color = black;
                    w.left.color = black;
                    rotateRight(deficientnode.parent);
                    deficientnode = root;
                }
            }
        }
        deficientnode.color = black; 
    }
    
    //This function simply gets the leftmost child in the right subtree
    RedBlackNode successor(RedBlackNode node){
        while(node.left!=dummy){
            node = node.left;
        }
        return node;
    }
    //This function simply inserts the node and returns the node that was inserted
    
    public RedBlackNode insertprint(int a, int b, int c)
    {
    	RedBlackNode x = new RedBlackNode(a, b, c);
    	insert(x);
    	return x;
    }
    // This function deletes the node from the redblack tree
    public void rbdelete(int a, int b,int c)
    {
    	RedBlackNode x = new RedBlackNode(a,b,c);
    	delete(x);
    }
    //Print single function is when a single value is given to be printed
    public RedBlackNode printsingle(int a)
    {
    
    	return printsinglewithroot(a, root);
    }
    //This function searches the value to be printed and returns the corresponding node
    public RedBlackNode printsinglewithroot(int bno, RedBlackNode temproot)
    {
    	if(temproot == dummy)
    		return temproot;
    	if(temproot.BuildingNumber == bno)
    	{
    		
    		return temproot;
    	}
    	if(bno<temproot.BuildingNumber)
    	{
    		temproot = printsinglewithroot(bno,temproot.left);
    	}
    	if(bno>temproot.BuildingNumber)
    	{
    		temproot = printsinglewithroot(bno,temproot.right);
    	}
    	return temproot;
    }
    //This function returns an arraylist we have a print range query
    public ArrayList<RedBlackNode> printrange(int b1, int b2)
    {
    	
    	 ArrayList<RedBlackNode> res = new ArrayList<>();
    	 printwithroot(b1,b2,root,res);
    	 return res;
    }
    //This function finds the range and stores all the nodes in the range in a result array
    public void printwithroot(int b1,int b2, RedBlackNode temproot,ArrayList<RedBlackNode>res)
    {
   
    	if(temproot == dummy)
    		return;
    	//if building number is less than the left side then search the right subtree 
    	if(temproot.BuildingNumber<b1)
    	{
    		printwithroot(b1,b2,temproot.right,res);
    	}
    	//If building number of the root is greater than the right child, search the left subtree
    	if(temproot.BuildingNumber>b2)
    	{
    		 printwithroot(b1, b2, temproot.left,res);
    	}
    	//if the building number is in between the range search in both subtrees and add the building in the arraylist res
    	if(temproot.BuildingNumber>=b1 && temproot.BuildingNumber<=b2)
    	{
    		 printwithroot(b1, b2, temproot.left,res);
    		 res.add(temproot);
    		 printwithroot(b1, b2, temproot.right,res);
    	}
    	return;
    	
    }
}
