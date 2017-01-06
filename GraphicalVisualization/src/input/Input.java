package input;

import java.util.ArrayList;
import java.util.Scanner;

import model.Edge;
import model.Node;
import model.Path;

public class Input {

	public static ArrayList<Node> nodeReader(Scanner inputScanner)
	{
		String text;
		int nodecounter = 0;
		int nodenumber;
		double xcoordinate;
		double ycoordinate;
		ArrayList<Node> Nodes = new ArrayList<Node>(0);

		//read out the nodes
		if(inputScanner.hasNextLine())
		{
			if(inputScanner.nextLine().equals("Nodes:"))
			{
				while(inputScanner.hasNextLine())
				{
					text = inputScanner.nextLine();
						if(text.equals("")){
						break;
					}

					try
					{
						nodenumber = Integer.parseInt(text.split("\t")[0]);
						xcoordinate = Double.parseDouble(text.split("\t")[1].split(",")[0]);
						ycoordinate = Double.parseDouble(text.split("\t")[1].split(",")[1]);

						if(nodenumber != nodecounter)
						{
							System.out.println("Error (scanner): File not compatible");
							System.exit(0);
						}

						if(nodenumber==0)
						{
							Nodes.add(new Node(xcoordinate,ycoordinate,nodenumber,true));
						}
						else
						{
							Nodes.add(new Node(xcoordinate,ycoordinate,nodenumber,false));
						}

						System.out.println("Node added");

						nodecounter ++;
					}
					catch(NumberFormatException e)
					{
						System.out.println("Error (scanner): File not compatible");
						System.exit(0);
					}
				}
			}
		}

		return(Nodes);
	}

	public static ArrayList<Path> pathReader(Scanner inputScanner,ArrayList<Node> Nodes)
	{
		String text;
		String[] Pathnodes;
		int[] pathnodes;
		int pathnodeslength;
		int pathcounter=1;
		ArrayList<Path> Paths = new ArrayList<Path>(0);
		ArrayList<Edge> Edges = new ArrayList<Edge>(0);
		ArrayList<Edge> EdgesInPath;
		Edge tempEdge;
		int noOfEdges;
		Edge edge;
		ArrayList<Integer> edgeCounter = new ArrayList<Integer>(0);

		//read out the paths
		if(inputScanner.hasNextLine())
		{
			if(inputScanner.nextLine().equals("Paths:"))
			{
				while(inputScanner.hasNextLine())
				{
					text = inputScanner.nextLine();

					if(text.equals("")){
						break;
					}

					try
					{
						Pathnodes = text.split(" ");
						pathnodeslength = Pathnodes.length;

						if(pathnodeslength<1)
						{
							System.out.println("Error (scanner): File not compatible");
							System.exit(0);
						}

						pathnodes = new int[2];

						Paths.add(new Path());

						for(int i=0;i<pathnodeslength;i++)
						{
							pathnodes[0] = Integer.parseInt(Pathnodes[i].split("-")[0]);
							pathnodes[1] = Integer.parseInt(Pathnodes[i].split("-")[1]);

							if(pathnodes[0]<0 | pathnodes[0]>(Nodes.size()) | pathnodes[1]<0 | pathnodes[1]>(Nodes.size()))
							{
								System.out.println("Error (scanner): File not compatible");
								System.exit(0);
							}

							edge = new Edge(Nodes.get(pathnodes[0]),Nodes.get(pathnodes[1]),true,0);

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

							Paths.get(pathcounter-1).addEdge(edge);
						}

						System.out.println("Path added");

						pathcounter ++;
					}
					catch(NumberFormatException e)
					{
						System.out.println("Error (scanner): File not compatible");
						System.exit(0);
					}
					catch(IndexOutOfBoundsException f){
						System.out.println("Error (scanner): File not compatible");
						System.exit(0);
					}
				}
			}
		}

		for(int i=0;i<Edges.size();i++){
			System.out.println(Edges.get(i).getVirtualordering());
			System.out.println(Edges.get(i).getTotalcopies());
		}
		for(int i=0;i<(pathcounter-1);i++)
		{
			EdgesInPath = Paths.get(i).getEdges();
			noOfEdges = EdgesInPath.size();

			for(int j=0;j<noOfEdges;j++)
			{
				tempEdge = new Edge(EdgesInPath.get(j));
				tempEdge.setVirtualordering(0);

				System.out.println(i+","+j);
				System.out.println(tempEdge.getTotalcopies());


				EdgesInPath.get(j).setTotalcopies(edgeCounter.get(Edges.indexOf(tempEdge)));
			}
		}

		return(Paths);
	}

	//Output the nodes to the screen
	public static void printNodes(ArrayList<Node> Nodes)
	{
		System.out.println("");
		System.out.println("Nodes:");

		for(int i=0;i<(Nodes.size());i++)
		{
			System.out.printf("%d\t%s,%s\n",Nodes.get(i).getNumber(),Nodes.get(i).getRealXcoordinate()
					,Nodes.get(i).getRealYcoordinate());
		}
	}

	//Output the paths to the screen
	public static void printPaths(ArrayList<Path> Paths)
	{
		String Outputpath;
		ArrayList<Edge> OutputEdges = new ArrayList<Edge>(0);

		System.out.println("");
		System.out.println("Paths:");

		for(int i=0;i<(Paths.size());i++)
		{
			OutputEdges = Paths.get(i).getEdges();

			for(int j=0;j<OutputEdges.size();j++)
			{
				Outputpath = OutputEdges.get(j).getNode1().getNumber() +"-"+OutputEdges.get(j).getNode2().getNumber()+" ";
				System.out.printf("%s",Outputpath);
			}

			System.out.println("");
		}
	}
}
