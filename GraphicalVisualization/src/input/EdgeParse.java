package input;

import java.util.ArrayList;

import model.Edge;
import model.Node;
import model.Path;

public class EdgeParse {

	public static void addAttributes(ArrayList<String> Attributes,ArrayList<String> AttributeNames,Edge edge)
	{
		int noOfAttributes = Attributes.size();
		int noOfAttributeNames = AttributeNames.size();

		if(noOfAttributes == noOfAttributeNames)
		{
			for(int i=0;i<noOfAttributes;i++)
			{
				edge.addAttribute(Attributes.get(i));
				edge.addAttributeName(AttributeNames.get(i));
			}
		}
		else
		{
			Input.FileNotCompatible("unequal amount of attributes and attributenames");
		}
	}

	public static Edge addEdge(String string,Path path,ArrayList<Node> Nodes,ArrayList<Edge> Edges,ArrayList<Integer> edgeCounter)
	{
		String[] lineSplit;
		Edge edge;
		Node node1;
		Node node2;

		if(string.contains("-"))
		{
			lineSplit = string.split("-");

			if(lineSplit.length==2)
			{
				try{
					node1 = NodeParse.getNode(Integer.parseInt(lineSplit[0]),Nodes);
					node2 = NodeParse.getNode(Integer.parseInt(lineSplit[1]),Nodes);

					edge = new Edge(node1,node2,true,0);

					if(Edges.contains(edge))
					{
						edgeCounter.set(Edges.indexOf(edge),edgeCounter.get(Edges.indexOf(edge))+1);
						edge.setVirtualordering(edgeCounter.get(Edges.indexOf(edge)));
					}
					else
					{
						Edges.add(new Edge(edge));
						edgeCounter.add(0);
					}

					path.addEdge(edge);

					return(edge);
				}
				catch(NumberFormatException e)
				{
					Input.FileNotCompatible("edge node is not an integer");
				}
			}
		}
		else
		{
			Input.FileNotCompatible("- missing");
		}

		return(null);
	}
}
