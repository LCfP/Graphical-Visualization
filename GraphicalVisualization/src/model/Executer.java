package model;
import input.Input;
import output.WindowContent;

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
import javafx.stage.Stage;

public class Executer extends Application
{

	//globally defined nodes and paths
	private static ArrayList<Node> nodes;
	private static ArrayList<Path> paths;

	//default screensizes
	public static int defaultWidth = 800;
	public static int defaultHeight = 600;

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
		nodes = Input.nodeReader(inputReader);
		paths = Input.pathReader(inputReader,nodes);

		//Prints nodes and paths to the screen
		Input.printNodes(nodes);
		Input.printPaths(paths);

		//Creating a window object
		launch(args);
	}

	//Standard JavaFX method
	public void start(Stage stage) throws Exception {
		stage.setTitle("Nodes and paths");
		Group root = new Group();
	    Canvas canvas = new Canvas(defaultWidth,defaultHeight);
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    WindowContent.drawNodes(gc,nodes);
	    WindowContent.drawPaths(gc,paths);
	    root.getChildren().add(canvas);
	    stage.setScene(new Scene(root));
		stage.show();

	}

}