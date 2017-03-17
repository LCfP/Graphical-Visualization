package model;
import input.Input;
import output.Coordinates;
import output.Export;
import output.Graph;
import output.Mode;
import user.ControlPaths;
import user.Sort;
import user.Zoom;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
	public static int[][][][] circleColors;

	public static Circle[][][] nodecircles;
	public static Arc[][] edgearcs;
	public static Polygon[][] arrowpolygons;
	public static Line[][] edgelines;
	public static Polygon[][] linearrowpolygons;

	public static Menu sortMenu;
	public static String sortingAttribute;
	public static double menuBarHeight;
	public static CheckMenuItem nodeMode;

	public static Label titleLabel;
	public static Label mainLabel;

	public static Pane drawPane;
	public static ScrollPane leftScrollPane;
	public static ScrollPane rightScrollPane;
	public static Pane rightPane;

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
		pathColors = Graph.createColors();

		circleColors = Graph.createRedColors();

		//Creating a window object
		launch(args);
	}

	//Standard JavaFX method
	public void start(Stage stage) throws Exception {
		stage.setTitle("Graphical Visualization");

	    BorderPane root = new BorderPane();

		Scene scene = new Scene(root,Graph.defaultWidth,Graph.defaultHeight);
		stage.setScene(scene);

		titleLabel = new Label("");
		mainLabel = new Label("");

		Pane textPane = new Pane();
		textPane.getChildren().addAll(titleLabel,mainLabel);

		leftScrollPane = new ScrollPane();
		leftScrollPane.setPrefWidth(0.5*(1-Graph.graphwidth)*Graph.defaultWidth);
		leftScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		leftScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		leftScrollPane.setContent(textPane);

		drawPane = new Pane();
		ScrollPane middlePane = new ScrollPane();
		middlePane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		middlePane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		middlePane.setContent(drawPane);
		drawPane.setOnScroll(null);

		rightScrollPane = new ScrollPane();
		rightScrollPane.setPrefWidth(0.5*(1-Graph.graphwidth)*Graph.defaultWidth);
		rightScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		rightScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		rightPane = new Pane();
		rightScrollPane.setContent(rightPane);

		Zoom.createZoomSlider();
		Zoom.addZoomSlider();
		ControlPaths.createCheckboxes();
		ControlPaths.updateRightPane();

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
	    Menu displayMenu = new Menu("Display");
	    Menu modeMenu = new Menu("Mode");

	    menuBar.getMenus().add(fileMenu);
	    menuBar.getMenus().add(displayMenu);
	    menuBar.getMenus().add(modeMenu);

		MenuItem export = new MenuItem("Export");
		fileMenu.getItems().add(export);
		export.setOnAction(Export.getExportListener());

	    Menu colorMenu = ControlPaths.makeColorMenu();
		sortMenu = Sort.makeSortMenu();
	    displayMenu.getItems().addAll(colorMenu,sortMenu);

	    nodeMode = new CheckMenuItem("Node");
	    nodeMode.setSelected(false);
	    nodeMode.selectedProperty().addListener(Mode.getNodeModeListener());
	    modeMenu.getItems().add(nodeMode);

		root.setTop(menuBar);
		root.setLeft(leftScrollPane);
		root.setCenter(middlePane);
		root.setRight(rightScrollPane);

		stage.show();

	    menuBarHeight = menuBar.getHeight();

	    scene.heightProperty().addListener(Graph.getResizeListener(scene));
	    scene.widthProperty().addListener(Graph.getResizeListener(scene));

	    Graph.drawAll();
	}
}
