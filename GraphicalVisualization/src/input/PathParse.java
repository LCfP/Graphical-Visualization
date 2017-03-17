package input;

import java.util.ArrayList;
import java.util.Scanner;

import model.Executer;
import model.Node;
import model.Path;

public class PathParse {

	public static ArrayList<Path> pathParser(Scanner inputScanner,ArrayList<Node> Nodes)
	{
		String line;
		String text;
		String[] lineSplit;
		String[][] lineSplit2;
		String[] lineSplit3;
		boolean started = false;
		int noOfEdgeAttributes=-1;
		int noOfPathAttributes=-1;
		ArrayList<Path> Paths = new ArrayList<Path>(0);
		ArrayList<String> EdgeAttributeNames = new ArrayList<String>(0);
		ArrayList<String> PathAttributeNames = new ArrayList<String>(0);
		ArrayList<String> Attributes = new ArrayList<String>(0);
		Path path;

		//read out the paths
		while(inputScanner.hasNextLine())
		{
			line = inputScanner.nextLine();
			line = line.trim();

			if(line.equals(""))
			{
				continue;
			}

			if(!started)
			{
				if(line.startsWith("("))
				{
					if(line.contains(")"))
					{
						lineSplit = line.split("\\)");
						text = lineSplit[0] + ")";
						PathAttributeNames = Input.getAttributesFromString(text);
						noOfPathAttributes = PathAttributeNames.size();

						lineSplit[1] = lineSplit[1].trim();
						if(lineSplit[1].startsWith("Paths:")|lineSplit[1].startsWith("paths:")|lineSplit[1].startsWith("Paths")|lineSplit[1].startsWith("paths"))
						{
							if(lineSplit[1].contains("("))
							{
								text = "(" + lineSplit[1].split("\\(")[1] + ")";
								EdgeAttributeNames = Input.getAttributesFromString(text);
								noOfEdgeAttributes = EdgeAttributeNames.size();
							}
							else
							{
								noOfEdgeAttributes = 0;
							}
						}
						else
						{
							Input.FileNotCompatible("Paths: missing");
						}
					}
					else
					{
						Input.FileNotCompatible(") missing");
					}

					started = true;
					continue;
				}
				else if(line.startsWith("Paths:")|line.startsWith("paths:")|line.startsWith("Paths")|line.startsWith("paths"))
				{
					noOfPathAttributes = 0;

					if(line.contains("("))
					{
						if(line.contains(")"))
						{
							text = "(" + line.split("\\(")[1];
							text = text.split("\\)")[0] + ")";
							EdgeAttributeNames = Input.getAttributesFromString(text);
							noOfEdgeAttributes = EdgeAttributeNames.size();
						}
						else
						{
							Input.FileNotCompatible(") missing");
						}
					}
					else
						noOfEdgeAttributes = 0;
				}
				else
				{
					Input.FileNotCompatible("Paths: missing");
				}

				started = true;
				continue;
			}
			else
			{
				if(noOfPathAttributes == 0 & noOfEdgeAttributes == 0)
				{
					path = new Path();

					lineSplit = line.split(" ");
					lineSplit2 = new String[lineSplit.length][];

					for(int i=0;i<lineSplit.length;i++)
					{
						lineSplit2[i] = lineSplit[i].split("\t");

						for(int j=0;j<lineSplit2[i].length;j++)
						{
							if(!lineSplit2[i][j].equals(""))
							{
								addEdge(lineSplit2[i][j],path,Nodes,null);
							}
						}
					}

					Paths.add(path);

					System.out.println("Path added");
				}
				else if(noOfPathAttributes == 0 & noOfEdgeAttributes > 0)
				{
					path = new Path();

					lineSplit = line.split("\\)");

					for(int i=0;i<lineSplit.length;i++)
					{
						if(lineSplit[i].contains("("))
						{
							lineSplit3 = lineSplit[i].split("\\(");

							if(lineSplit3.length == 2)
							{
								lineSplit3[0] = lineSplit3[0].trim();
								lineSplit3[1] = lineSplit3[1].trim();
								Attributes = Input.getAttributesFromString("("+lineSplit3[1]+")");

								if(Attributes.size() == noOfEdgeAttributes)
								{
									addEdge(lineSplit3[0],path,Nodes,Input.convertToArray(Attributes));
								}
								else
								{
									Input.FileNotCompatible("inconsistent number of edge attributes");
								}
							}
							else
							{
								Input.FileNotCompatible("too many (");
							}
						}
						else
						{
							Input.FileNotCompatible("( missing");
						}
					}

					Paths.add(path);

					System.out.println("Path added");
				}
				else if(noOfPathAttributes > 0 & noOfEdgeAttributes == 0)
				{
					path = new Path();

					lineSplit = line.split("\\)");

					text = lineSplit[0].trim();
					if(text.startsWith("("))
					{
						Attributes = Input.getAttributesFromString(text + ")");

						if(Attributes.size() == noOfPathAttributes)
						{
							for(int i=0;i<noOfPathAttributes;i++)
							{
								path.addPathAttribute(Attributes.get(i));
							}

							lineSplit[1] = lineSplit[1].trim();
							lineSplit = lineSplit[1].split(" ");
							lineSplit2 = new String[lineSplit.length][];

							for(int i=0;i<lineSplit.length;i++)
							{
								lineSplit2[i] = lineSplit[i].split("\t");

								for(int j=0;j<lineSplit2[i].length;j++)
								{
									if(!lineSplit2[i][j].equals(""))
									{
										addEdge(lineSplit2[i][j],path,Nodes,null);
									}
								}
							}
						}
						else
						{
							Input.FileNotCompatible("Inconsistent number of path attributes");
						}
					}
					else
					{
						Input.FileNotCompatible("( missing");
					}

					Paths.add(path);

					System.out.println("Path added");
				}
				else if(noOfPathAttributes > 0 & noOfEdgeAttributes > 0)
				{
					path = new Path();

					lineSplit = line.split("\\)");

					text = lineSplit[0].trim();
					if(text.startsWith("("))
					{
						Attributes = Input.getAttributesFromString(text + ")");

						if(Attributes.size() == noOfPathAttributes)
						{
							for(int i=0;i<noOfPathAttributes;i++)
							{
								path.addPathAttribute(Attributes.get(i));
							}

							for(int i=1;i<lineSplit.length;i++)
							{
								if(lineSplit[i].contains("("))
								{
									lineSplit3 = lineSplit[i].split("\\(");

									if(lineSplit3.length == 2)
									{
										lineSplit3[0] = lineSplit3[0].trim();
										lineSplit3[1] = lineSplit3[1].trim();

										Attributes = Input.getAttributesFromString("("+lineSplit3[1]+")");

										if(Attributes.size() == noOfEdgeAttributes)
										{
											addEdge(lineSplit3[0],path,Nodes,Input.convertToArray(Attributes));
										}
										else
										{
											Input.FileNotCompatible("inconsistent number of edge attributes");
										}
									}
									else
									{
										Input.FileNotCompatible("too many (");
									}
								}
								else
								{
									Input.FileNotCompatible("( missing");
								}
							}
						}
						else
						{
							Input.FileNotCompatible("Inconsistent number of path attributes");
						}
					}
					else
					{
						Input.FileNotCompatible("( missing");
					}

					Paths.add(path);

					System.out.println("Path added");
				}
				else
				{
					Input.FileNotCompatible("something went wrong");
				}
			}
		}

		Executer.pathAttributeNames = PathAttributeNames;
		Executer.edgeAttributeNames = EdgeAttributeNames;
		return(Paths);
	}

	public static void addEdge(String string,Path path,ArrayList<Node> nodes, String[] attributes)
	{
		string = string.trim();
		String[] stringSplit;
		int noofnodes = nodes.size();
		int[] node = new int[2];

		if(string.contains("-"))
		{
			stringSplit = string.split("-");

			if(stringSplit.length == 2)
			{
				stringSplit[0] = stringSplit[0].trim();
				stringSplit[1] = stringSplit[1].trim();

				try
				{
					node[0] = Integer.parseInt(stringSplit[0]);
					node[1] = Integer.parseInt(stringSplit[1]);

					if(node[0] >= 0 & node[0] < noofnodes & node[1] >= 0 & node[1] < noofnodes)
					{
						path.addNodes(node);
						path.addEdgeAttribute(attributes);
					}
					else
					{
						Input.FileNotCompatible("unknown node");
					}
				}
				catch(NumberFormatException e)
				{
					Input.FileNotCompatible("unknown node");
				}
			}
			else
			{
				Input.FileNotCompatible("too many -");
			}
		}
		else
		{
			Input.FileNotCompatible("- missing");
		}
	}
}
