import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import edu.uci.ics.jung.algorithms.layout.*;


public class GraphVisualize 
{
	private UndirectedSparseGraph<Integer,String> simplegraph;
	public List<Integer> vertexset= new ArrayList<Integer>();
	
	GraphVisualize()
	{
		simplegraph =  new UndirectedSparseGraph<Integer,String>();
	}
	
	public void addVertex(int vertex)
	{
		simplegraph.addVertex(vertex);
	}
	
	public void addEdge(String edgename, int startvertex, int endvertex)
	{
		simplegraph.addEdge(edgename,startvertex,endvertex);
	}
	
	public void removeVertex(int vertex)
	{
		simplegraph.removeVertex(vertex);
	}
	
	public boolean containsVertex(int vertex)
	{
		if(simplegraph.containsVertex(vertex))
			return true;
		else
			return false;
	}
	
	public boolean containsEdge(String edge)
	{
		if(simplegraph.containsEdge(edge))
			return true;
		else
			return false;
	}
	
	public void DrawGraph(int layout)
	{
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int height= screenSize.height -10;
		int width= screenSize.width -10;
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(width,height));
		VisualizationImageServer<Integer,String> vs;
		if(layout==0)
		{
			vs = new VisualizationImageServer<Integer,String>(new CircleLayout<Integer,String>(simplegraph), new Dimension(width - 50, height - 100));
		}
		else
		{
			vs = new VisualizationImageServer<Integer,String>(new SpringLayout<Integer,String>(simplegraph), new Dimension(width - 50, height - 100));
		}
		frame.getContentPane().add(vs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}	
	
	
}
