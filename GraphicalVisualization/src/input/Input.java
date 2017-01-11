package input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Edge;
import model.Executer;
import model.Node;
import model.Path;

public class Input {

	public static void parse(String link)
	{
		Scanner inputReader = null;
		ArrayList<Node> nodes;
		ArrayList<Path> paths;

		//sets the input stream
		try
		{
			inputReader = new Scanner(new FileInputStream(link));

			nodes = NodeParse.nodeParser(inputReader);
			paths = PathParse.pathParser(inputReader,nodes);

			Executer.nodes = nodes;
			Executer.paths = paths;
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error (scanner): File not found");
			System.exit(0);
		}
	}

	//Output the nodes to the screen
	public static void printNodes(ArrayList<Node> Nodes)
	{
		String outputLine;
		Node node;
		int noOfAttributes;
		int noOfNodes;
		ArrayList<String> AttributeNames;
		ArrayList<String> Attributes;

		System.out.println("");

		noOfNodes = Nodes.size();
		if(noOfNodes>0)
		{
			node = Nodes.get(0);
			noOfAttributes = node.getAttributes().size();

			if(noOfAttributes>0)
			{
				AttributeNames = node.getAttributeNames();

				outputLine = "(";

				for(int i=0;i<(noOfAttributes-1);i++)
				{
					outputLine = outputLine + AttributeNames.get(i) +",";
				}
				outputLine = outputLine + AttributeNames.get(noOfAttributes-1) +")";
				outputLine = outputLine + " Nodes:";

				System.out.println(outputLine);

				for(int j=0;j<noOfNodes;j++)
				{
					node = Nodes.get(j);
					Attributes = node.getAttributes();

					outputLine = "(";

					for(int i=0;i<(noOfAttributes-1);i++)
					{
						outputLine = outputLine + Attributes.get(i) +",";
					}
					outputLine = outputLine + Attributes.get(noOfAttributes-1) +")";
					outputLine = outputLine + " " + node.getNumber() + " ";
					outputLine = outputLine + node.getRealXcoordinate() + ",";
					outputLine = outputLine + node.getRealYcoordinate();

					System.out.println(outputLine);
				}
			}
			else
			{
				System.out.println("Nodes:");

				for(int i=0;i<(Nodes.size());i++)
				{
					System.out.printf("%d %s,%s\n",Nodes.get(i).getNumber(),Nodes.get(i).getRealXcoordinate()
							,Nodes.get(i).getRealYcoordinate());
				}
			}
		}
	}

	//Output the paths to the screen
	public static void printPaths(ArrayList<Path> Paths)
	{
		String Outputpath;
		int noOfPathAttributes;
		int noOfEdgeAttributes;
		ArrayList<Edge> OutputEdges = new ArrayList<Edge>(0);
		ArrayList<String> attributeNames;
		ArrayList<String> attributes;
		Path path;
		Edge edge;

		System.out.println("");

		if(Paths.size()>0)
		{
			path = Paths.get(0);
			edge = path.getEdges().get(0);
			noOfPathAttributes = path.getAttributes().size();
			noOfEdgeAttributes = edge.getAttributes().size();

			if(noOfPathAttributes > 0)
			{
				attributeNames = path.getAttributeNames();
				Outputpath = "(";

				if(noOfPathAttributes>1)
				{
					for(int i=0;i<(noOfPathAttributes-1);i++)
					{
						Outputpath = Outputpath + attributeNames.get(i) +",";
					}
				}

				Outputpath = Outputpath + attributeNames.get(noOfPathAttributes-1) +")";
				Outputpath = Outputpath + " Paths";

				if(noOfEdgeAttributes > 0)
				{
					attributeNames = edge.getAttributeNames();
					Outputpath = Outputpath + "(";

					if(noOfEdgeAttributes>1)
					{
						for(int i=0;i<(noOfEdgeAttributes-1);i++)
						{
							Outputpath = Outputpath + attributeNames.get(i) +",";
						}
					}

					Outputpath = Outputpath + attributeNames.get(noOfEdgeAttributes-1) +")";
				}
				
				Outputpath = Outputpath + ":";
				System.out.println(Outputpath);

				for(int i=0;i<(Paths.size());i++)
				{
					path = Paths.get(i);
					OutputEdges = path.getEdges();
					attributes = path.getAttributes();
					Outputpath = "(";

					if(noOfPathAttributes>1)
					{
						for(int j=0;j<(noOfPathAttributes-1);j++)
						{
							Outputpath = Outputpath + attributes.get(j) +",";
						}
					}

					Outputpath = Outputpath + attributes.get(noOfPathAttributes-1) + ")" + " ";

					if(noOfEdgeAttributes > 0)
					{
						for(int j=0;j<OutputEdges.size();j++)
						{
							attributes = OutputEdges.get(j).getAttributes();
							Outputpath = Outputpath + OutputEdges.get(j).getNode1().getNumber() +"-"+OutputEdges.get(j).getNode2().getNumber()+" ";
							Outputpath = Outputpath + "(";
							
							if(noOfEdgeAttributes>1)
							{
								for(int k=0;k<(noOfEdgeAttributes-1);k++)
								{
									Outputpath = Outputpath + attributes.get(k) +",";
								}
							}

							Outputpath = Outputpath + attributes.get(noOfEdgeAttributes-1) +")" +" ";
						}
					}
					else
					{
						for(int j=0;j<OutputEdges.size();j++)
						{
							Outputpath = Outputpath + OutputEdges.get(j).getNode1().getNumber() +"-"+OutputEdges.get(j).getNode2().getNumber()+" ";
						}
					}

					System.out.println(Outputpath);
				}
			}
			else
			{
				System.out.println("Paths:");

				for(int i=0;i<(Paths.size());i++)
				{
					path = Paths.get(i);
					OutputEdges = path.getEdges();

					for(int j=0;j<OutputEdges.size();j++)
					{
						Outputpath = OutputEdges.get(j).getNode1().getNumber() +"-"+OutputEdges.get(j).getNode2().getNumber()+" ";
						System.out.printf("%s",Outputpath);
					}
					System.out.println("");
				}
			}
		}
	}

	public static ArrayList<String> getAttributesFromString(String string)
	{
		ArrayList<String> Attributes = new ArrayList<String>(0);
		String[] lineSplit;
		int noOfAttributes;


		if(string.contains(","))
		{
			lineSplit = string.split(",");
			noOfAttributes = lineSplit.length;

			for(int i=0;i<noOfAttributes;i++)
			{
				lineSplit[i] = lineSplit[i].trim();

				if(lineSplit[i].equals(""))
				{
					Input.FileNotCompatible("empty attribute name");
				}
				else
				{
					Attributes.add(lineSplit[i]);
				}
			}
		}
		else
		{
			string = string.trim();

			if(string.equals(""))
			{
				Input.FileNotCompatible("empty attribute name");
			}
			else
			{
				noOfAttributes = 1;
				Attributes.add(string);
			}
		}

		return(Attributes);
	}

	public static void FileNotCompatible(String message)
	{
		System.out.println("Error (scanner): File not compatible: "+message);
		System.exit(0);
	}
}
