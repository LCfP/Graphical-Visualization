package input;

import java.util.ArrayList;
import java.util.Scanner;

import model.Node;

public class NodeParse {

	public static ArrayList<Node> nodeParser(Scanner inputScanner)
	{
		String line;
		String text;
		String[] lineSplit;
		String[] lineSplit2;
		boolean started = false;
		int noOfAttributes=-1;
		int nodenumber;
		double xcoordinate;
		double ycoordinate;
		ArrayList<Node> Nodes = new ArrayList<Node>(0);
		ArrayList<String> AttributeNames = new ArrayList<String>(0);

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

			if(line.endsWith("Nodes:")|line.endsWith("nodes:")|line.endsWith("Nodes")|line.endsWith("nodes"))
			{
				if(line.startsWith("("))
				{
					if(line.contains(")"))
					{
						lineSplit = line.split("\\)");

						if(lineSplit.length == 2)
						{
							text = lineSplit[0].substring(1);

							if(text.contains(","))
							{
								lineSplit = text.split(",");
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
										AttributeNames.add(lineSplit[i]);
									}
								}
							}
							else
							{
								text = text.trim();

								if(text.equals(""))
								{
									Input.FileNotCompatible("empty attribute name");
								}
								else
								{
									noOfAttributes = 1;
									AttributeNames.add(text);
								}
							}
						}
						else
						{
							Input.FileNotCompatible("multiple \\)");
						}
					}
					else
					{
						Input.FileNotCompatible(") missing");
					}
				}
				else
				{
					noOfAttributes = 0;
				}

				started = true;
				continue;
			}

			if(noOfAttributes==0)
			{
				lineSplit = line.split(" ");

				try
				{
					nodenumber = Integer.parseInt(lineSplit[0]);

					if(lineSplit[lineSplit.length-1].contains(","))
					{
						lineSplit = lineSplit[lineSplit.length-1].split(",");

						lineSplit[0] = lineSplit[0].trim();
						xcoordinate = Double.parseDouble(lineSplit[0]);
						lineSplit[1] = lineSplit[1].trim();
						ycoordinate = Double.parseDouble(lineSplit[1]);

						Nodes.add(new Node(xcoordinate,ycoordinate,nodenumber));
					}
					else
					{
						Input.FileNotCompatible(", missing");
					}
				}
				catch(NumberFormatException e)
				{
					Input.FileNotCompatible("nodenumber/coordinate is not an integer/double");
				}

				System.out.println("Node added");
			}
			else if(noOfAttributes>0)
			{
				if(line.startsWith("(") & line.contains(")"))
				{
					lineSplit = line.split("\\)");

					lineSplit[1] = lineSplit[1].trim();
					lineSplit2 = lineSplit[1].split(" ");

					try
					{
						nodenumber = Integer.parseInt(lineSplit2[0]);

						if(lineSplit2[lineSplit2.length-1].contains(","))
						{
							lineSplit2 = lineSplit2[lineSplit2.length-1].split(",");

							lineSplit2[0] = lineSplit2[0].trim();
							xcoordinate = Double.parseDouble(lineSplit2[0]);
							lineSplit2[1] = lineSplit2[1].trim();
							ycoordinate = Double.parseDouble(lineSplit2[1]);

							Nodes.add(new Node(xcoordinate,ycoordinate,nodenumber));
						}
						else
						{
							Input.FileNotCompatible(", missing");
						}
					}
					catch(NumberFormatException e)
					{
						Input.FileNotCompatible("nodenumber/coordinate is not an integer/double");
					}

					text = lineSplit[0].substring(1);

					if(text.contains(","))
					{
						lineSplit = text.split(",");
						if(lineSplit.length == noOfAttributes)
						{
							for(int i=0;i<noOfAttributes;i++)
							{
								lineSplit[i] = lineSplit[i].trim();

								if(lineSplit[i].equals(""))
								{
									Input.FileNotCompatible("empty attribute");
								}
								else
								{
									Nodes.get(Nodes.size()-1).addAttribute(lineSplit[i]);
									Nodes.get(Nodes.size()-1).addAttributeName(AttributeNames.get(i));
								}
							}
						}
						else
						{
							Input.FileNotCompatible("inconsistent amount of attributes");
						}
					}
					else
					{
						if(noOfAttributes == 1)
						{
							text = text.trim();

							if(text.equals(""))
							{
								Input.FileNotCompatible("empty attribute name");
							}
							else
							{
								Nodes.get(Nodes.size()-1).addAttribute(text);
								Nodes.get(Nodes.size()-1).addAttributeName(AttributeNames.get(0));
							}
						}
						else
						{
							Input.FileNotCompatible("inconsistent amount of attributes");
						}
					}

					System.out.println("Node added");
				}
				else
				{
					Input.FileNotCompatible("\\( or \\) missing");
				}
			}
		}

		return(Nodes);
	}

	public static Node getNode(int nodeno,ArrayList<Node> Nodes)
	{
		Node node;
		Node outputNode = new Node(0,0,0);
		boolean found = false;
		boolean twice = false;
		int noOfNodes = Nodes.size();

		for(int i=0;i<noOfNodes;i++)
		{
			node = Nodes.get(i);

			if(node.getNumber()==nodeno)
			{
				if(!found)
				{
					outputNode = node;
					found = true;
				}
				else
				{
					twice = true;
					break;
				}
			}
		}

		if(found & (!twice))
		{
			return(outputNode);
		}
		else
		{
			Input.FileNotCompatible("edge node not found or more than once");
			return(null);
		}
	}
}
