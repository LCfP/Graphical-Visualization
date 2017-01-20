package model;
import java.util.ArrayList;

public class Path {

	//contains an arraylist of edges
	private ArrayList<Edge> Edges;
	private int[] color;

	//attributes of the path
	private ArrayList<String> Attributes;
	private ArrayList<String> AttributeNames;

	//creates an empty path
	public Path()
	{
		Edges = new ArrayList<Edge>(0);
		color = new int[3];
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);
	}

	//creates a non-empty path
	public Path(ArrayList<Edge> edges)
	{
		Edges = edges;
		color = new int[3];
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);
	}

	//deep copy class constructor
	public Path(Path path){
		ArrayList<Edge> temp = path.getEdges();
		int edgelength = temp.size();
		int[] pathcolor = path.getColor();
		ArrayList<String> pathAttributes = path.getAttributes();
		ArrayList<String> pathAttributeNames = path.getAttributeNames();
		int noOfAttributes = pathAttributes.size();
		int noOfAttributeNames = pathAttributeNames.size();
		Edges = new ArrayList<Edge>(0);
		color = new int[3];
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);

		for(int i=0;i<edgelength;i++)
		{
			Edges.add(new Edge(temp.get(i)));
		}

		for(int i=0;i<3;i++)
		{
			color[i] = pathcolor[i];
		}

		for(int i=0;i<noOfAttributes;i++)
		{
			Attributes.add(pathAttributes.get(i));
		}

		for(int i=0;i<noOfAttributeNames;i++)
		{
			AttributeNames.add(pathAttributeNames.get(i));
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
		ArrayList<String> pathAttributes = path.getAttributes();
		ArrayList<String> pathAttributeNames = path.getAttributeNames();

		if(path.getEdges().equals(Edges) & pathcolor[0] == color[0]	& pathcolor[1] == color[1]
				& pathcolor[2] == color[2] & pathAttributes.equals(Attributes) & pathAttributeNames.equals(AttributeNames)){
			return true;
		}
		else{
			return false;
		}
	}

	//assigns color to path
	public void setColor(int[] newcolor)
	{
		color = newcolor;
	}

	//add attribute
	public void addAttribute(String attribute)
	{
		Attributes.add(attribute);
	}

	public void addAttributeName(String attributeName)
	{
		AttributeNames.add(attributeName);
	}

	//remove attributes
	public void removeAttributes()
	{
		Attributes = new ArrayList<String>(0);
	}

	public void removeAttributeNames()
	{
		AttributeNames = new ArrayList<String>(0);
	}

	//adds an edge
	public void addEdge(Edge edge){
		Edges.add(edge);
	}

	//returns edges
	public ArrayList<Edge> getEdges(){
		return Edges;
	}

	//returns color
	public int[] getColor()
	{
		return color;
	}

	//returns Attributes
	public ArrayList<String> getAttributes()
	{
		return Attributes;
	}

	public ArrayList<String> getAttributeNames()
	{
		return AttributeNames;
	}
}
