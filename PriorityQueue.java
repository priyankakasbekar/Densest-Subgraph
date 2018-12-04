import java.util.ArrayList;
import java.util.stream.Stream;


public class PriorityQueue 
{
   private ArrayList<NodeInfo> nodes;
   
   public PriorityQueue()
   {
	   this.nodes = new ArrayList<NodeInfo>();
   }
   
   public void Insert(NodeInfo info)
   {
	   nodes.add(info);
	   BuildPriorityQueue();
	   //ArrangeNode();
   }
   
   private void BuildPriorityQueue()
   {
	   for(int i=(this.nodes.size()-1)/2; i>=0;i--)
	   {
		   minHeapify(i);
	   }
   }
   
   private void ArrangeNode()
   {
	   int i = nodes.size() - 1;
       int parent = parent(i);

       while (parent != i && nodes.get(i).degree < nodes.get(parent).degree) 
       {

           swap(i, parent);
           i = parent;
           parent = parent(i);
       }
   }
   
   private int right(int i) 
   {

       return 2 * i + 2;
   }

   private int left(int i) 
   {

       return 2 * i + 1;
   }
   
   private int parent(int i) {

       if (i % 2 == 1) {
           return i / 2;
       }

       return (i - 1) / 2;
   }
   
   private void swap(int i, int parent) 
   {

       NodeInfo temp = nodes.get(parent);
       nodes.set(parent, nodes.get(i));
       nodes.set(i, temp);
   }
   
   public NodeInfo Extractmin()
   {
	   NodeInfo node;
	   if(nodes.size() == 1)
	   {
		   node = nodes.remove(0);
		   return node;
	   }	   
	   else
	   {
		   node = nodes.get(0);
		   NodeInfo last_node = nodes.remove(nodes.size() - 1);
		   nodes.set(0, last_node);
		   minHeapify(0);
		   return node;
	   }
   }
   
   private void minHeapify(int i) 
   {

       int left = left(i);
       int right = right(i);
       int smallest = -1;

       // find the smallest key between current node and its children.
       if (left <= nodes.size() - 1 && nodes.get(left).degree < nodes.get(i).degree) 
       {
           smallest = left;
       } 
       else 
       {
           smallest = i;
       }

       if (right <= nodes.size() - 1 && nodes.get(right).degree < nodes.get(smallest).degree) 
       {
           smallest = right;
       }

       // if the smallest key is not the current key then bubble-down it.
       if (smallest != i) 
       {

           swap(i, smallest);
           minHeapify(smallest);
       }
   }
   
   public void UpdateDegree(int updatenode)
   {
	   Stream<NodeInfo> stream = nodes.stream();  
	   Stream<NodeInfo> filtered_stream  = stream.filter( node -> (node.node == updatenode));
	   
	   filtered_stream.forEach(node -> node.degree--);
   }

}
