package model;
import java.util.ArrayList;

public class Path {

	//contains an arraylist of edges
	private ArrayList<Edge> Edges;
	private double costs;
	private int[] color;

	//creates an empty path
	public Path()
	{
		Edges = new ArrayList<Edge>(0);
		costs = 0;
		color = new int[3];
	}

	//creates a non-empty path
	public Path(ArrayList<Edge> edges, double cost)
	{
		Edges = edges;
		costs =  cost;
		color = new int[3];
	}

	//deep copy class constructor
	public Path(Path path){
		ArrayList<Edge> temp = path.getEdges();
		int edgelength = temp.size();

		Edges = path.getEdges();
		for(int i=0;i<edgelength;i++)
		{
			Edges.add(new Edge(temp.get(i)));
		}

		costs = path.getCosts();

		color = new int[3];
		int[] pathcolor = path.getColor();

		for(int i=0;i<3;i++)
		{
			color[i] = pathcolor[i];
		}
	}

	//official equals method
	public boolean equals(Object obj)
	{
		if (obj==null)
		{
			return false;
		}

		Path path = (Path) obj;

		return equals(path);
	}

	//equals method
	public boolean equals(Path path)
	{
		int[] pathcolor = path.getColor();

		if(path.getEdges().equals(Edges) & path.getCosts() == costs & pathcolor[0] == color[0]
				& pathcolor[1] == color[1] & pathcolor[2] == color[2]){
			return true;
		}
		else{
			return false;
		}
	}

	//sets costs
	public void setCosts(double cost)
	{
		costs = cost;
	}

	//assigns color to path
	public void setColor(int[] newcolor)
	{
		color = newcolor;
	}

	//adds an edge
	public void addEdge(Edge edge){
		Edges.add(edge);
	}

	//returns edges
	public ArrayList<Edge> getEdges(){
		return Edges;
	}

	//returns costs
	public double getCosts()
	{
		return costs;
	}

	//returns color
	public int[] getColor()
	{
		return color;
	}
}
