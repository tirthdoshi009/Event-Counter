public class minheap {
	public minheapnode[] Heaparray;
	public int size;
	public int capacity;
	public static final int first=1;
	//Initializing the min heap
	public minheap()
	{
		this.capacity= 2000;
		this.size = 0;
		this.Heaparray = new minheapnode[this.capacity + 1];
		Heaparray[0] = new minheapnode(-999, -999,0);
		Heaparray[0].executed_time= Integer.MIN_VALUE;
	}
	
	//This is the minheapify at index i
	
	private void minheapify(int i)
	{ int smallest = i;
	//Make the smallest as the current index
		int l = 2*i;
		int r = 2*i+1;
		//set the left child and right child index
		//if the left child executed time is less than the executed time of the parent
		//set smallest as l
		//consider the case when the executed time are equal compare their building numbers
		{
			if(l<=size && Heaparray[l].executed_time<Heaparray[i].executed_time)
			{
				smallest = l;
			}
			if(l<=size && Heaparray[l].executed_time == Heaparray[i].executed_time)
			{
				if(Heaparray[l].buildingNum<Heaparray[i].buildingNum)
				{
					smallest = l;
				}
			}
			//Similarly if r is less than smallest then r is the smallest
			if(r<=size && Heaparray[r].executed_time<Heaparray[smallest].executed_time)
			{
				smallest = r;
			}
			if(r<=size && Heaparray[r].executed_time == Heaparray[smallest].executed_time)
			{
				if(Heaparray[r].buildingNum<Heaparray[smallest].buildingNum)
				{
					smallest = r;
				}
			}
			// swap function
			if(smallest !=i)
			{
				minheapnode temp;
				temp = Heaparray[i];
				Heaparray[i] = Heaparray[smallest];
				Heaparray[smallest] = temp;
				minheapify(smallest);
			}
		}
	}

	//Insert and at the last point in the heap and heapify at that point
	public void insert(minheapnode node)
	{
		Heaparray[++size] = node;
        int current = size;
        insertheapify(current);
	}
	// the insert heapify will simply compare the executed time child and its parent and swap when required
	public void insertheapify(int child)
	{
		if(child == 1)
			return;
		int parent = child/2;
		if(Heaparray[child].executed_time<Heaparray[parent].executed_time)
		{
			minheapnode temp ;
			temp = Heaparray[child];
			Heaparray[child]= Heaparray[parent];
			Heaparray[parent]=temp;
			insertheapify(parent);
		}
		if(Heaparray[child].executed_time == Heaparray[parent].executed_time)
		{
			if(Heaparray[child].buildingNum<Heaparray[parent].buildingNum)
			{
				minheapnode temp2 ;
				temp2 = Heaparray[child];
				Heaparray[child]= Heaparray[parent];
				Heaparray[parent]=temp2;
				insertheapify(parent);
			}
		}
	}
	// this is the insert function that returns a minheapnode that is used to in the redblack node as a minheap pointer
	public minheapnode insertminheap(int a, int b, int c)
	{
		minheapnode x = new minheapnode(a,b,c);
		insert(x);
		return x;
	}
	
	//This simply prints the minheap
	
	   public void print()
	    {
	        for (int i = 1; i <= size / 2; i++ )
	        {
	            System.out.print(" PARENT : " + Heaparray[i].executed_time +"Parent Building Number:"+Heaparray[i].buildingNum+ " LEFT CHILD : " + Heaparray[2*i].executed_time 
	                + " RIGHT CHILD :" + Heaparray[2 * i  + 1].executed_time);
	            System.out.println();
	        } 
	    }
	   
	   public void minHeap()
	    {
	        for (int pos = (size / 2); pos >= 1 ; pos--)
	        {
	            minheapify(pos);
	        }
	    }
	   
	   //This function puts the last element at index 1
	   //After that it does the heapify at index 1
	
	public minheapnode extractmin()
	{	  
		        minheapnode popped = Heaparray[first];
		        Heaparray[first] = Heaparray[size--]; 
		        minheapify(first);
		        return popped;   
	}
}
