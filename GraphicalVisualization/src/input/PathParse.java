package input;

import java.util.ArrayList;
import java.util.Scanner;

import model.Edge;
import model.Node;
import model.Path;
import output.WindowContent;

public class PathParse {

	public static ArrayList<Path> pathParser(Scanner inputScanner,ArrayList<Node> Nodes)
	{
		String line;
		String text;
		String[] lineSplit;
		String[] lineSplit2;
		boolean started = false;
		int pathcounter=1;
		int noOfEdgeAttributes=-1;
		int noOfPathAttributes=-1;
		ArrayList<Path> Paths = new ArrayList<Path>(0);
		ArrayList<Edge> UniqueEdges = new ArrayList<Edge>(0);
		ArrayList<Edge> EdgesInPath;
		ArrayList<String> EdgeAttributeNames = new ArrayList<String>(0);
		ArrayList<String> PathAttributeNames = new ArrayList<String>(0);
		Edge tempEdge;
		int noOfEdges;
		Path path;
		Edge edge;
		ArrayList<Integer> edgeCounter = new ArrayList<Integer>(0);

		//read out the paths
		while(inputScanner.hasNextLine())
		{
			line = inputScanner.nextLine();
			line = line.trim();

			if(line.equals(""))
			{
				if(started)
				{
					break;
				}
				else
				{
					continue;
				}
			}

			if(line.endsWith("Paths:")|line.endsWith("paths:")|line.endsWith("Paths")|line.endsWith("paths"))
			{
				if(line.startsWith("("))
				{
					if(line.contains(")"))
					{
						lineSplit = line.split("\\)");
						text = lineSplit[0].substring(1);
						PathAttributeNames = Input.getAttributesFromString(text);
						noOfPathAttributes = PathAttributeNames.size();
						noOfEdgeAttributes = 0;
					}
					else
					{
						Input.FileNotCompatible(") missing");
					}
				}
				else
				{
					noOfPathAttributes = 0;
				}

				started = true;
				continue;
			}
			else if(line.substring(1).contains("("))
			{
				lineSplit = line.split("\\(");

				if(lineSplit.length==3)
				{
					text = lineSplit[1];
					text = text.trim();

					if(text.endsWith("Paths:")|text.endsWith("paths:")|text.endsWith("Paths")|text.endsWith("paths"))
					{
						if(text.contains(")"))
						{
							text = text.split("\\)")[0];
							PathAttributeNames = Input.getAttributesFromString(text);
							noOfPathAttributes = PathAttributeNames.size();
						}
						else
						{
							Input.FileNotCompatible(") missing");
						}

						text = lineSplit[2];
						text = text.trim();

						if(text.contains(")"))
						{
							text = text.split("\\)")[0];
							EdgeAttributeNames = Input.getAttributesFromString(text);
							noOfEdgeAttributes = EdgeAttributeNames.size();
						}
						else
						{
							Input.FileNotCompatible(") missing");
						}

						started = true;
						continue;
					}
				}
				else if(lineSplit.length==2)
				{
					text = lineSplit[0];
					text = text.trim();

					if(text.endsWith("Paths:")|text.endsWith("paths:")|text.endsWith("Paths")|text.endsWith("paths"))
					{
						text = lineSplit[1];
						text = text.trim();

						if(text.contains(")"))
						{
							text = text.split("\\)")[0];
							EdgeAttributeNames = Input.getAttributesFromString(text);
							noOfEdgeAttributes = EdgeAttributeNames.size();
							noOfPathAttributes = 0;
						}
						else
						{
							Input.FileNotCompatible(") missing");
						}

						started = true;
						continue;
					}
				}
			}

			if(noOfPathAttributes == 0 & noOfEdgeAttributes == 0)
			{
				path = new Path();

				lineSplit = line.split(" ");

				for(int i=0;i<lineSplit.length;i++)
				{
					if(!lineSplit[i].equals(""))
					{
						EdgeParse.addEdge(lineSplit[i],path,Nodes,UniqueEdges,edgeCounter);
					}
				}

				path.setColor(WindowContent.inventColor(pathcounter-1));
				Paths.add(path);

				System.out.println("Path added");

				pathcounter ++;
			}
			else if(noOfPathAttributes == 0 & noOfEdgeAttributes > 0)
			{
				path = new Path();

				lineSplit = line.split("\\)");

				for(int i=0;i<lineSplit.length;i++)
				{
					if(lineSplit[i].contains("("))
					{
						lineSplit2 = lineSplit[i].split("\\(");

						lineSplit2[0] = lineSplit2[0].trim();
						edge = EdgeParse.addEdge(lineSplit2[0],path,Nodes,UniqueEdges,edgeCounter);

						lineSplit2[1] = lineSplit2[1].trim();
						EdgeParse.addAttributes(Input.getAttributesFromString(lineSplit2[1]),EdgeAttributeNames,edge);
					}
					else
					{
						Input.FileNotCompatible("( missing");
					}
				}

				path.setColor(WindowContent.inventColor(pathcounter-1));
				Paths.add(path);

				System.out.println("Path added");

				pathcounter ++;
			}
			else if(noOfPathAttributes > 0 & noOfEdgeAttributes == 0)
			{
				path = new Path();

				lineSplit = line.split("\\)");

				lineSplit[0] = lineSplit[0].trim();
				if(lineSplit[0].startsWith("("))
				{
					addAttributes(Input.getAttributesFromString(lineSplit[0].substring(1)),PathAttributeNames,path);
				}
				else
				{
					Input.FileNotCompatible("( missing");
				}

				lineSplit[1] = lineSplit[1].trim();
				lineSplit = lineSplit[1].split(" ");

				for(int i=0;i<lineSplit.length;i++)
				{
					if(!lineSplit[i].equals(""))
					{
						EdgeParse.addEdge(lineSplit[i],path,Nodes,UniqueEdges,edgeCounter);
					}
				}

				path.setColor(WindowContent.inventColor(pathcounter-1));
				Paths.add(path);

				System.out.println("Path added");

				pathcounter ++;
			}
			else if(noOfPathAttributes > 0 & noOfEdgeAttributes > 0)
			{
				path = new Path();

				lineSplit = line.split("\\)");

				lineSplit[0] = lineSplit[0].trim();
				if(lineSplit[0].startsWith("("))
				{
					addAttributes(Input.getAttributesFromString(lineSplit[0].substring(1)),PathAttributeNames,path);
				}
				else
				{
					Input.FileNotCompatible("( missing");
				}

				for(int i=1;i<lineSplit.length;i++)
				{
					if(lineSplit[i].contains("("))
					{
						lineSplit2 = lineSplit[i].split("\\(");

						lineSplit2[0] = lineSplit2[0].trim();
						edge = EdgeParse.addEdge(lineSplit2[0],path,Nodes,UniqueEdges,edgeCounter);

						lineSplit2[1] = lineSplit2[1].trim();
						EdgeParse.addAttributes(Input.getAttributesFromString(lineSplit2[1]),EdgeAttributeNames,edge);
					}
					else
					{
						Input.FileNotCompatible("( missing");
					}
				}

				path.setColor(WindowContent.inventColor(pathcounter-1));
				Paths.add(path);

				System.out.println("Path added");

				pathcounter ++;
			}
		}

		for(int i=0;i<(pathcounter-1);i++)
		{
			EdgesInPath = Paths.get(i).getEdges();
			noOfEdges = EdgesInPath.size();

			for(int j=0;j<noOfEdges;j++)
			{
				tempEdge = new Edge(EdgesInPath.get(j));
				tempEdge.setVirtualordering(0);
				tempEdge.removeAttributes();
				tempEdge.removeAttributeNames();

				EdgesInPath.get(j).setTotalcopies(edgeCounter.get(UniqueEdges.indexOf(tempEdge)));
			}
		}

		return(Paths);
	}

	public static void addAttributes(ArrayList<String> Attributes,ArrayList<String> AttributeNames,Path path)
	{
		int noOfAttributes = Attributes.size();
		int noOfAttributeNames = AttributeNames.size();

		if(noOfAttributes == noOfAttributeNames)
		{
			for(int i=0;i<noOfAttributes;i++)
			{
				path.addAttribute(Attributes.get(i));
				path.addAttributeName(AttributeNames.get(i));
			}
		}
		else
		{
			Input.FileNotCompatible("unequal amount of attributes and attributenames");
		}
	}
}
