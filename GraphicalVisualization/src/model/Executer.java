package model;
import input.Input;
import output.Resize;
import output.WindowContent;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Executer extends Application
{

	//globally defined nodes and paths
	public static ArrayList<Node> nodes;
	public static ArrayList<Path> paths;

	public static Circle[] nodecircles;
	public static Arc[] edgearcs;
	public static Polygon[] arrowpolygons;

	//default screensizes
	public static double defaultWidth = 800;
	public static double defaultHeight = 600;

	public static void main(String[] args)
	{
		int edgecounter = 0;

		nodes = new ArrayList<Node>(0);
		paths = new ArrayList<Path>(0);

		//checks whether the program has 1 argument
		if(args.length != 1)
		{
			System.out.println("Error (executer): Program needs 1 argument");
			System.exit(0);
		}

		//Reads in the nodes and paths
		Input.parse(args[0]);
		//nodes = Input.nodeReader(inputReader);
		//paths = Input.pathReader(inputReader,nodes);

		nodecircles = new Circle[nodes.size()];

		for(int i=0;i<paths.size();i++)
		{
			edgecounter += paths.get(i).getEdges().size();
		}

		edgearcs = new Arc[edgecounter];
		arrowpolygons = new Polygon[edgecounter];

		//Prints nodes and paths to the screen
		Input.printNodes(nodes);
		Input.printPaths(paths);

		//Creating a window object
		launch(args);
	}

	//Standard JavaFX method
	public void start(Stage stage) throws Exception {
		stage.setTitle("Nodes and paths");

	    GridPane root = new GridPane();

		Scene scene = new Scene(root,defaultWidth,defaultHeight);
		stage.setScene(scene);

		Canvas textcanvas = new Canvas(0.2*defaultWidth,defaultHeight);
		root.add(textcanvas,1,1);

		Pane pane = new Pane();
		root.add(pane, 2,1);

	    scene.heightProperty().addListener(Resize.getListener(scene,textcanvas,pane));
	    scene.widthProperty().addListener(Resize.getListener(scene,textcanvas,pane));

	    WindowContent.drawAll(textcanvas,pane,nodes,paths);

	    stage.show();
	}

}
