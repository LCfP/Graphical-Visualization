package model;
import java.util.ArrayList;

public class Path {

	//contains an arraylist of edges
	private ArrayList<int[]> Nodes;

	//attributes of the path and edge
	private ArrayList<String> PathAttributes;
	private ArrayList<String[]> EdgeAttributes;

	//creates an empty path
	public Path()
	{
		Nodes = new ArrayList<int[]>(0);
		PathAttributes = new ArrayList<String>(0);
		EdgeAttributes = new ArrayList<String[]>(0);
	}

	//creates a non-empty path
	public Path(ArrayList<int[]> nodes)
	{
		Nodes = nodes;
		PathAttributes = new ArrayList<String>(0);
		EdgeAttributes = new ArrayList<String[]>(0);
	}

	//deep copy class constructor
	public Path(Path path){
		ArrayList<int[]> temp = path.getNodes();
		int noOfNodes = temp.size();
		ArrayList<String> pathAttributes = path.getPathAttributes();
		ArrayList<String[]> allEdgeAttributes = path.getEdgeAttributes();
		int noOfPathAttributes = pathAttributes.size();
		int noOfEdges = allEdgeAttributes.size();
		int noOfEdgeAttributes = allEdgeAttributes.get(0).length;
		Nodes = new ArrayList<int[]>(0);
		PathAttributes = new ArrayList<String>(0);
		EdgeAttributes = new ArrayList<String[]>(0);
		int[] nodes = new int[2];
		String[] edgeAttributes;
		String[] edgeattributes = new String[noOfEdgeAttributes];

		for(int i=0;i<noOfNodes;i++)
		{
			nodes[0] = temp.get(i)[0];
			nodes[1] = temp.get(i)[1];
			Nodes.add(nodes);
		}

		for(int i=0;i<noOfPathAttributes;i++)
		{
			PathAttributes.add(pathAttributes.get(i));
		}

		for(int i=0;i<noOfEdges;i++)
		{
			edgeAttributes = allEdgeAttributes.get(i);

			for(int j=0;j<noOfEdgeAttributes;j++)
			{
				edgeattributes[j] = edgeAttributes[j];
			}

			EdgeAttributes.add(edgeattributes);
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
		ArrayList<String> pathAttributes = path.getPathAttributes();
		ArrayList<String[]> edgeAttributes = path.getEdgeAttributes();
		ArrayList<int[]> allnodes = path.getNodes();
		int noofnodes = Nodes.size();
		int noofedges = EdgeAttributes.size();
		int noofedgeattributes1 = 0;
		int noofedgeattributes2 = 0;
		int[] nodes1;
		int[] nodes2;
		String[] edgeattributes1;
		String[] edgeattributes2;

		if(allnodes.size() == noofnodes)
		{
			for(int i=0;i<noofnodes;i++)
			{
				nodes1 = allnodes.get(i);
				nodes2 = Nodes.get(i);

				if(nodes1[0] != nodes2[0] | nodes1[1] != nodes2[1])
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}

		if(edgeAttributes.size() == noofedges)
		{
			for(int i=0;i<noofedges;i++)
			{
				edgeattributes1 = edgeAttributes.get(i);
				edgeattributes2 = EdgeAttributes.get(i);

				try{
					noofedgeattributes1 = edgeattributes1.length;
					noofedgeattributes2 = edgeattributes2.length;
				}
				catch(NullPointerException e){}

				if(noofedgeattributes1 == noofedgeattributes2)
				{
					for(int j=0;j<noofedgeattributes1;j++)
					{
						if(edgeattributes1[j] != edgeattributes2[j])
						{
							return false;
						}
					}
				}
				else{
					return false;
				}
			}
		}
		else
		{
			return false;
		}

		if(pathAttributes.equals(PathAttributes)){
			return true;
		}
		else{
			return false;
		}
	}

	//add attribute
	public void addPathAttribute(String attribute)
	{
		PathAttributes.add(attribute);
	}

	public void addEdgeAttribute(String[] attribute)
	{
		EdgeAttributes.add(attribute);
	}

	//remove attributes
	public void removePathAttributes()
	{
		PathAttributes = new ArrayList<String>(0);
	}

	public void removeEdgeAttributes()
	{
		EdgeAttributes = new ArrayList<String[]>(0);
	}

	//adds an edge
	public void addNodes(int[] nodes){
		Nodes.add(nodes);
	}

	//returns edges
	public ArrayList<int[]> getNodes(){
		return Nodes;
	}

	//returns path attributes
	public ArrayList<String> getPathAttributes()
	{
		return PathAttributes;
	}

	//returns path attributes
	public ArrayList<String[]> getEdgeAttributes()
	{
		return EdgeAttributes;
	}
}
