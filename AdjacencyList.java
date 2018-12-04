import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
  
public class AdjacencyList
{
    //Stores Linked list for every node	as a Key value pair
    private  ConcurrentHashMap<Integer , LinkedList<Integer>> adjacencyList; 
    //Stores Degree of every node
    public  ConcurrentHashMap<Integer , Integer> degree;
    
    public AdjacencyList(AdjacencyList copyadjacencyList)
    {    	
    	this.adjacencyList = new ConcurrentHashMap<>(copyadjacencyList.adjacencyList);    	
    	this.degree = new ConcurrentHashMap<>(copyadjacencyList.degree);    	
    }
    
    public AdjacencyList()
    {
    	 adjacencyList = new ConcurrentHashMap<>();
    	 degree = new ConcurrentHashMap<>();
    }

    // Appends a new Edge to the linked list
    public void addEdge(int startVertex, int endVertex) 
    {
    	int vertex_degree;
    	LinkedList<Integer> head=new LinkedList<Integer>();
    	LinkedList<Integer> headend=new LinkedList<Integer>();
    	if(startVertex != endVertex)
    	{
    	if(!adjacencyList.containsKey(startVertex)) //If the node is not present in the adjacency list
    	{    		
    		adjacencyList.put(startVertex,head);
    		degree.put(startVertex, 0);
    	}
    	
    	//If the node is already present  
    	
    	head=adjacencyList.get(startVertex);   	
    	head.add(endVertex);
    	
    	//update the degree of the vertex    
    	vertex_degree=degree.get(startVertex);
    	degree.put(startVertex,vertex_degree+1);
    	
    	//If there is no node corresponding to the endvertex in the adjacency list, add it and create a linkedlist for it
    	if(!adjacencyList.containsKey(endVertex))
    	{    		
    		adjacencyList.put(endVertex,headend);
    		degree.put(endVertex, 0);
    	}
    	
    	//Add the startvertex into the linkedlist/adjacency list of the endvertex    	    	
    	headend=adjacencyList.get(endVertex);
    	headend.add(startVertex);
    	
    	//update the degree of endvertex
    	vertex_degree = degree.get(endVertex);
    	degree.put(endVertex,vertex_degree+1);
    	}
    }
      
    // Returns number of vertices
    // Does not change for an object
    public int getNumberOfVertices() 
    {
        return adjacencyList.size();
    }
      
    // Returns number of outward edges from a vertex
    public int getDegree(int vertex) 
    {
    	if(degree.containsKey(vertex))
    	{
    		return degree.get(vertex);
    	}
    	return 0;
    }
      
      
    // Prints the Adjacency List
 /*   public void printAdjacencyList() {
    	Enumeration adj;
    	adj=adjacencyList.
          
        while(adj.hasMoreElements()) {
        	Integer a=Integer(adj.nextElement());
        	System.out.print("adjacencyList[" + a + "] -> ");
        	LinkedList<Integer> head= adjacencyList.get(a);  
            System.out.println(head);
        }
    }*/
    
    //Returns the adjacency list of the vertex passed as the parameter
    public LinkedList<Integer> getList(int vertex)
    {
    	return adjacencyList.get(vertex);
    }
    
    /* Removes a vertex from the list of adjacent_vertex. Called when vertex is removed from the graph 
    and adjacency list of every node adjacent to the vertex should be updated */
    public boolean removeVertexFromList(int adjacent_vertex, int vertex)
    {
    	LinkedList<Integer> head=adjacencyList.get(adjacent_vertex);
    	try
    	{
    	if(head != null && head.contains(vertex))
    	{
    	head.remove(head.indexOf(vertex));
    	degree.put(adjacent_vertex, degree.get(adjacent_vertex)-1);    	
    	return true;
    	}
    	}
    	catch(Exception ex)
    	{
    		return false;
    	}
    	
    	return false;
    }
    
    //Removes a vertex from the adjacency list. Removes each vertex adjacent to it as well
    public boolean removeVertex(int vertex) 
    {
    	LinkedList<Integer> head=adjacencyList.get(vertex);
    	try
    	{
    	if(head != null)
    	{
    		Iterator <Integer> a=head.iterator();
    		while(a.hasNext())
    		{
    			int adjacent_vertex =a.next();
    			removeVertexFromList(adjacent_vertex,vertex);    		
    		}
    		
    		adjacencyList.remove(vertex);  
    		degree.remove(vertex);
    		
    		return true;
    	}
    	}
    	catch(Exception ex)
    	{
    		return false;
    	}
    	return false;
    }
}