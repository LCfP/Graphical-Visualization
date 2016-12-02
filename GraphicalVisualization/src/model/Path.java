package model;
import java.util.ArrayList;

public class Path {

	//contains an arraylist of edges
	private ArrayList<Edge> Edges;

	//creates an empty path
	public Path()
	{
		Edges = new ArrayList<Edge>(0);
	}

	//creates a non-empty path
	public Path(ArrayList<Edge> edges)
	{
		Edges = edges;
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
	}

	//equals method
	public boolean equals(Path path)
	{
		if(path.getEdges().equals(Edges)){
			return true;
		}
		else{
			return false;
		}
	}

	//toString method
	public String toString()
	{
		String output = "";
		int edgeslength = Edges.size();

		if(edgeslength>0)
		{
			for(int i=0;i<edgeslength;i++)
			{
				output.concat("edge "+String.valueOf(i+1)+": "+Edges.get(i).toString());
			}
		}
		else
		{
			output = "null";
		}

		return(output);
	}

	//adds deep copy of an edge
	public void addEdge(Edge edge){
		Edges.add(new Edge(edge));
	}

	//returns edges
	public ArrayList<Edge> getEdges(){
		return Edges;
	}
}
