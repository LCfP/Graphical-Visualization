import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Executer extends Application
{
	//globally defined nodes and paths
	static ArrayList<Node> nodes;
	static ArrayList<Path> paths;
	
	//default screensizes
	final int defaultWidth = 800;
	final int defaultHeigth = 800;

	private static ArrayList<Node> nodeReader(Scanner inputScanner)
	{
		String text;
		int nodecounter = 1;
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

						Nodes.add(new Node(xcoordinate,ycoordinate,nodenumber));
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

	private static ArrayList<Path> pathReader(Scanner inputScanner,ArrayList<Node> Nodes)
	{
		String text;
		String[] Pathnodes;
		int[] pathnodes;
		int pathnodeslength;
		int pathcounter=1;
		ArrayList<Path> Paths = new ArrayList<Path>(0);

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

							if(pathnodes[0]<1 | pathnodes[0]>(Nodes.size()) | pathnodes[1]<1 | pathnodes[1]>(Nodes.size()))
							{
								System.out.println("Error (scanner): File not compatible");
								System.exit(0);
							}

							Paths.get(pathcounter-1).addEdge(new Edge(Nodes.get(pathnodes[0]-1),Nodes.get(pathnodes[1]-1),true));
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

		return(Paths);
	}

	//Output the nodes to the screen
	public static void printNodes(ArrayList<Node> Nodes)
	{
		System.out.println("");
		System.out.println("Nodes:");

		for(int i=0;i<(Nodes.size());i++)
		{
			System.out.printf("%d\t%s,%s\n",Nodes.get(i).getNumber(),Nodes.get(i).getXcoordinate(),Nodes.get(i).getYcoordinate());
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

	public static void main(String[] args)
	{
		Scanner inputReader = null;

		nodes = new ArrayList<Node>(0);
		paths = new ArrayList<Path>(0);

		//checks whether the program has 1 argument
		if(args.length != 1)
		{
			System.out.println("Error (executer): Program needs 1 argument");
			System.exit(0);
		}

		//sets the input stream
		try
		{
			inputReader = new Scanner(new FileInputStream(args[0]));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error (scanner): File not found");
			System.exit(0);
		}

		//Reads in the nodes and paths
		nodes = nodeReader(inputReader);
		paths = pathReader(inputReader,nodes);

		//Prints nodes and paths to the screen
		printNodes(nodes);
		printPaths(paths);

		//Launches a screen
		launch(args);
	}

	//Standard JavaFX method
	public void start(Stage stage) throws Exception {
		stage.setTitle("Nodes and paths");
		Group root = new Group();
        Canvas canvas = new Canvas(defaultWidth,defaultHeigth);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawNodes(gc);
        drawPaths(gc);
        root.getChildren().add(canvas);
		stage.setScene(new Scene(root));
		stage.show();

	}

	//Draws all nodes as red circles
	private void drawNodes(GraphicsContext GC)
	{
		int NoNodes = nodes.size();
        double[] xcoordinates = new double[NoNodes];
        double[] ycoordinates = new double[NoNodes];
        double[] adjustedxcoordinates = new double[NoNodes];
        double[] adjustedycoordinates = new double[NoNodes];

        for(int i=0;i<NoNodes;i++){
        	xcoordinates[i] = nodes.get(i).getXcoordinate();
        	ycoordinates[i] = nodes.get(i).getYcoordinate();
        }

        adjustedxcoordinates = transformXCoordinates(xcoordinates);
        adjustedycoordinates = transformYCoordinates(ycoordinates);

		GC.setFill(Color.GREEN);
        GC.setStroke(Color.RED);
        GC.setLineWidth(3);

        for(int i=0;i<NoNodes;i++){
        	GC.strokeOval(adjustedxcoordinates[i],adjustedycoordinates[i], 10, 10);
        	GC.fillText(String.valueOf(i+1),adjustedxcoordinates[i],adjustedycoordinates[i]+25);
        }
	}

	//Draws all paths as blue lines
	private void drawPaths(GraphicsContext GC)
	{
		int NoPaths = paths.size();
		ArrayList<Edge> TempEdges = new ArrayList<Edge>(0);

		for(int i=0;i<NoPaths;i++)
		{
			TempEdges.addAll(paths.get(i).getEdges());
		}

		int NoEdges = TempEdges.size();
		double[] xcoordinates = new double[2*NoEdges];
        double[] ycoordinates = new double[2*NoEdges];
        double[] adjustedxcoordinates = new double[2*NoEdges];
        double[] adjustedycoordinates = new double[2*NoEdges];

        for(int i=0;i<NoEdges;i++)
        {
        	xcoordinates[2*i]=TempEdges.get(i).getNode1().getXcoordinate();
        	xcoordinates[2*i+1]=TempEdges.get(i).getNode2().getXcoordinate();
        	ycoordinates[2*i]=TempEdges.get(i).getNode1().getYcoordinate();
        	ycoordinates[2*i+1]=TempEdges.get(i).getNode2().getYcoordinate();
        }

        adjustedxcoordinates = transformXCoordinates(xcoordinates);
        adjustedycoordinates = transformYCoordinates(ycoordinates);

        GC.setFill(Color.GREEN);
        GC.setStroke(Color.BLUE);
        GC.setLineWidth(2);

        for(int i=0;i<NoEdges;i++)
        {
        	GC.strokeLine(adjustedxcoordinates[2*i]+5,adjustedycoordinates[2*i]+5,adjustedxcoordinates[2*i+1]+5,adjustedycoordinates[2*i+1]+5);
        }
	}

	//Transforms the x-coordinates of the nodes to x-coordinates on the screen
	private double[] transformXCoordinates(double[] coordinates)
	{
		double min;
		double max;
		int length = coordinates.length;
		double[] newCoordinates = new double[length];

		min = max = coordinates[0];

		if(length>0)
		{
			for(int i=1;i<length;i++)
			{
				if(coordinates[i]<min)
				{
					min=coordinates[i];
				}

				if(coordinates[i]>max)
				{
					max=coordinates[i];
				}
			}

			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = 0.05*defaultWidth + 0.9*defaultWidth*(coordinates[i]-min)/(max-min);
			}
		}
		else{
			newCoordinates[0] = 0;
		}

		return(newCoordinates);
	}

	//Transforms the y-coordinates of the nodes to y-coordinates on the screen
	private double[] transformYCoordinates(double[] coordinates)
	{
		double min;
		double max;
		int length = coordinates.length;
		double[] newCoordinates = new double[length];

		min = max = coordinates[0];

		if(length>0)
		{
			for(int i=1;i<length;i++)
			{
				if(coordinates[i]<min)
				{
					min=coordinates[i];
				}

				if(coordinates[i]>max)
				{
					max=coordinates[i];
				}
			}

			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = 0.95*defaultHeigth - 0.9*defaultHeigth*(coordinates[i]-min)/(max-min);
			}
		}
		else{
			newCoordinates[0] = 0;
		}

		return(newCoordinates);
	}
}
