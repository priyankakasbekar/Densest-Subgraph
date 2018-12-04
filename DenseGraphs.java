import java.util.*;
import java.io.*;

public class DenseGraphs 
{
	static AdjacencyList graph_adj_list = new AdjacencyList(); 
	static PriorityQueue node_queue = new PriorityQueue();
	static AdjacencyList densest_subgraph = new AdjacencyList(); //stores the densest subgraph after every vertex elimination
	static GraphVisualize visualize_obj = new GraphVisualize();
	
	static void visualizeGraph(AdjacencyList list)
	{
		GraphVisualize visualizeobj = new GraphVisualize();
		List<Integer> vertices = new ArrayList<Integer>(list.degree.keySet());
		int layout=0;
		if(vertices.size()>100){
			layout=1;
		}
		for(int node : vertices)
		{
			visualizeobj.addVertex(node);
			LinkedList<Integer> edgelist_node = list.getList(node);
			for(int vertex:edgelist_node)
			{
				if(!visualizeobj.containsVertex(vertex))
					visualizeobj.addVertex(vertex);
				if(!visualizeobj.containsEdge("edge"+Integer.toString(node)+"-"+ Integer.toString(vertex)))
					visualizeobj.addEdge("edge"+Integer.toString(node)+"-"+Integer.toString(vertex), node, vertex);
			}
		}
		visualizeobj.DrawGraph(layout);
	}
	
	//Finds Densest subgraph by calculating density of each subgraph
	static void findDensestSubGraph()
	{
		
		double maxDensity=0;
		//Calculate the density of the given graph and keep it as maximum 
		double density=calculateDensity(graph_adj_list.degree);
		maxDensity=Math.max(density, maxDensity);
		//Choose he vertex with minimum degree and eliminate it 
		while(graph_adj_list.degree.size()>=1)
		{
			int removeVertex=node_queue.Extractmin().node;
			LinkedList<Integer> temp = graph_adj_list.getList(removeVertex);
			if(temp != null)
			{
			Boolean success = graph_adj_list.removeVertex(removeVertex);
			if(success)
			{
			//Update degree of every node that is adjacent to the vertex removed in the PriorityQueue
				Iterator<Integer> it=temp.iterator();
				while(it.hasNext())
				{
				node_queue.UpdateDegree(it.next());
				}
				if(graph_adj_list.degree.size()>=1)
				{
					density=calculateDensity(graph_adj_list.degree);
					if(density > maxDensity)
					{
					//densest_subgraph = (AdjacencyList) SerializationUtils.clone(graph_adj_list);
					densest_subgraph = new AdjacencyList(graph_adj_list);
					}
					maxDensity=Math.max(density, maxDensity);	
				}
				}
			//Update the densest subgraph only when the new density found is greater than the maxDensity	
			}
		}
		System.out.println("Max Density:" + maxDensity);
	}
	
		//creates a priority queue with node having the least degree as first element
	static void createPriorityQueue()
	{
		for (Map.Entry<Integer, Integer> entry : graph_adj_list.degree.entrySet()) 
		{
		    int key = entry.getKey();
		    int value = entry.getValue();
		    NodeInfo node=new NodeInfo(key,value);
		    node_queue.Insert(node);
		}	
	}
	
	//creates adjacency list representation of the given input graph
	static void createAdjacencyList(ArrayList<NodePair> input_graph)
	{
		for(int i=0; i<input_graph.size() ;i++)
		{
			graph_adj_list.addEdge(input_graph.get(i).edge_node1,input_graph.get(i).edge_node2);
		}
	}
	
	//calculates density of the subgraph
	static double calculateDensity(Map<Integer,Integer> d)
	{
		double sum=0;
		for (Map.Entry<Integer,Integer> entry: d.entrySet()){
			sum+=entry.getValue();
		}
		return (sum+0.0)/d.size();
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//ArrayList<List<Integer>> input_graph = new ArrayList<List<Integer>>();
		ArrayList<NodePair> input_graph = new ArrayList<NodePair>();
		String infile = String.valueOf(args[0]);
		//String infile="C:\\Users\\User\\Desktop\\HighEnergyCollab.csv";
		NodePair pair = new NodePair();
		try
		{
			//To read the input graph
			BufferedReader reader = new BufferedReader(new FileReader(infile));
			while (true) 
			{
				String line = reader.readLine();
				if (line == null) 
				break;
				line = line.replaceAll(","," ");
				
				Scanner linescanner =new Scanner(line);			
				//List<Integer> row= new ArrayList<Integer>();				
				while (linescanner.hasNext())
				{		
					pair = new NodePair();
					pair.edge_node1 = linescanner.nextInt();
					pair.edge_node2 = linescanner.nextInt();
					//row.add(linescanner.nextInt());
				}
				linescanner.close();
				input_graph.add(pair);	
			}
			reader.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		//System.out.println(input_graph);	
		
		createAdjacencyList(input_graph);
		visualizeGraph(graph_adj_list);
		createPriorityQueue();
		findDensestSubGraph();		
		visualizeGraph(densest_subgraph);		
	}
		
	
	
}