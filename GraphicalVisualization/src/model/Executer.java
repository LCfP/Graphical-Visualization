package model;
import input.Input;
import output.Coordinates;
import output.Sort;
import output.WindowContent;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Executer extends Application
{

	//globally defined nodes and paths
	public static ArrayList<Node> nodes;
	public static ArrayList<Path> paths;

	public static ArrayList<String> nodeAttributeNames;
	public static ArrayList<String> edgeAttributeNames;
	public static ArrayList<String> pathAttributeNames;

	public static double[][] normalizedCoordinates;
	public static double aspectratio;

	public static int[][] pathColors;

	public static Circle[][][] nodecircles;
	public static Arc[][] edgearcs;
	public static Polygon[][] arrowpolygons;
	public static Line[][] edgelines;
	public static Polygon[][] linearrowpolygons;

	public static Menu sortMenu;
	public static String sortingAttribute;
	public static double menuBarHeight;

	public static Label titleLabel;
	public static Label mainLabel;

	public static Pane drawPane;
	public static ScrollPane leftPane;
	public static ScrollPane rightPane;

	public static void main(String[] args)
	{
		nodes = new ArrayList<Node>(0);
		paths = new ArrayList<Path>(0);

		//checks whether the program has 1 argument
		if(args.length != 1)
		{
			System.out.println("Error (executer): Program needs 1 argument");
			System.exit(0);
		}

		sortingAttribute = "";

		//Reads in the nodes and paths
		Input.parse(args[0]);

		//Prints nodes and paths to the screen
		Input.printNodes(nodes);
		Input.printPaths(paths);

		//Calculate normalized coordinates
		Coordinates.calculateNormalizedCoordinates(nodes);

		//Create unique colors for each path
		pathColors = WindowContent.createColors(paths);

		//Creating a window object
		launch(args);
	}

	//Standard JavaFX method
	public void start(Stage stage) throws Exception {
		stage.setTitle("Graphical Visualization");

	    BorderPane root = new BorderPane();

		Scene scene = new Scene(root,WindowContent.defaultWidth,WindowContent.defaultHeight);
		stage.setScene(scene);

		titleLabel = new Label("");
		mainLabel = new Label("");
		Pane textPane = new Pane();
		textPane.getChildren().addAll(titleLabel,mainLabel);

		leftPane = new ScrollPane();
		leftPane.setPrefWidth(0.15*WindowContent.defaultWidth);
		leftPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		leftPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		leftPane.setContent(textPane);

		drawPane = new Pane();
		ScrollPane middlePane = new ScrollPane();
		middlePane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		middlePane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		middlePane.setContent(drawPane);

		rightPane = new ScrollPane();
		rightPane.setPrefWidth(0.15*WindowContent.defaultWidth);
		rightPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		rightPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		MenuBar menuBar = new MenuBar();
	    Menu displayMenu = new Menu("Display");
	    menuBar.getMenus().add(displayMenu);

		sortMenu = Sort.makeSortMenu();
	    displayMenu.getItems().add(sortMenu);

		root.setTop(menuBar);
		root.setLeft(leftPane);
		root.setCenter(middlePane);
		root.setRight(rightPane);

		stage.show();

	    menuBarHeight = menuBar.getHeight();

	    scene.heightProperty().addListener(WindowContent.getResizeListener(scene));
	    scene.widthProperty().addListener(WindowContent.getResizeListener(scene));

	    WindowContent.drawAll();
	}
}
