package model;
import input.Input;
import output.Resize;
import output.Sort;
import output.WindowContent;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
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

	public static Circle[][][] nodecircles;
	public static Arc[][][] edgearcs;
	public static Polygon[][][] arrowpolygons;

	public static Menu sortMenu;
	public static double menuBarHeight;
	public static String attributeName;

	public static Label titleLabel;
	public static Label mainLabel;

	public static void main(String[] args)
	{
		nodes = new ArrayList<Node>(0);
		paths = new ArrayList<Path>(0);
		attributeName = "";

		//checks whether the program has 1 argument
		if(args.length != 1)
		{
			System.out.println("Error (executer): Program needs 1 argument");
			System.exit(0);
		}

		//Reads in the nodes and paths
		Input.parse(args[0]);

		//Prints nodes and paths to the screen
		Input.printNodes(nodes);
		Input.printPaths(paths);

		//Creating a window object
		launch(args);
	}

	//Standard JavaFX method
	public void start(Stage stage) throws Exception {
		stage.setTitle("Nodes and paths");

	    BorderPane root = new BorderPane();

		Scene scene = new Scene(root,WindowContent.defaultWidth,WindowContent.defaultHeight);
		stage.setScene(scene);

		MenuBar menuBar = new MenuBar();
	    menuBar.prefWidthProperty().bind(stage.widthProperty());
	    root.setTop(menuBar);

	    GridPane middlePane = new GridPane();
	    root.setCenter(middlePane);
	    Pane drawPane = new Pane();
		middlePane.add(drawPane,1,1);

	    Menu displayMenu = new Menu("Display");
	    menuBar.getMenus().add(displayMenu);

		Sort.makeSortMenu(drawPane);
	    displayMenu.getItems().add(sortMenu);

	    stage.show();

	    menuBarHeight = menuBar.getHeight();

		titleLabel = new Label("");
		mainLabel = new Label("");
		drawPane.getChildren().addAll(titleLabel,mainLabel);

	    scene.heightProperty().addListener(Resize.getListener(scene,drawPane));
	    scene.widthProperty().addListener(Resize.getListener(scene,drawPane));

	    WindowContent.drawAll(drawPane,nodes,paths);
	}
}
