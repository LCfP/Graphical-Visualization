package user;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import model.Executer;
import model.Node;
import model.Path;
import output.Graph;

public class Mouse {
	private static boolean clickedstate = false;
	private static boolean nodeclicked = false;
	private static boolean pathclicked = false;
	private static int[] nodeindex;
	private static int pathindex;

	private static int getNodeNo(Circle circle)
	{
    	int noOfNodes = Executer.nodes.size();
    	int noOfScreensX = Graph.noOfScreensX;
    	int noOfScreensY = Graph.noOfScreensY;

    	Circle[][][] circles = Executer.nodecircles;
    	int nodeno = -1;

    	for(int i=0;i<noOfNodes;i++)
    	{
        	for(int x=0;x<noOfScreensX;x++)
        	{
	        	for(int y=0;y<noOfScreensY;y++)
	        	{
	        		try
	        		{
		        		if(circles[i][x][y].equals(circle))
		        		{
		        			nodeno = i;
		        			break;
		        		}
	        		}
	        		catch(NullPointerException e){}
	        	}
        	}
    	}

    	if(nodeno == -1)
    	{
    		System.out.println("Error: could not find node");
    		System.exit(0);
    	}

    	return(nodeno);
	}

	private static int getPathNo(Arc arc)
	{
		int pathno = - 1;
		Arc[][] arcs = Executer.edgearcs;
		int noOfPaths = Executer.paths.size();
		int noOfEdges;

    	for(int i=0;i<noOfPaths;i++)
    	{
    		noOfEdges = arcs[i].length;

    		for(int j=0;j<noOfEdges;j++)
    		{
    			if(arcs[i][j].equals(arc))
    			{
    				pathno = i;
    				break;
    			}
    		}
        }

    	if(pathno == -1)
        {
        	System.out.println("Error: could not find path");
        	System.exit(0);
        }

    	return(pathno);
	}

	private static int getPathNo(Polygon polygon)
	{
		int pathno = - 1;
		Polygon[][] linepolygons = Executer.linearrowpolygons;
		Polygon[][] arcpolygons = Executer.arrowpolygons;
		int noOfPaths = Executer.paths.size();
		int noOfEdges;

    	for(int i=0;i<noOfPaths;i++)
    	{
    		noOfEdges = linepolygons[i].length;

    		for(int j=0;j<noOfEdges;j++)
    		{
    			if(linepolygons[i][j].equals(polygon))
    			{
    				pathno = i;
    				break;
    			}
    		}

    		noOfEdges = arcpolygons[i].length;

    		for(int j=0;j<noOfEdges;j++)
    		{
    			if(arcpolygons[i][j].equals(polygon))
    			{
    				pathno = i;
    				break;
    			}
    		}
        }

    	if(pathno == -1)
        {
        	System.out.println("Error: could not find path");
        	System.exit(0);
        }

    	return(pathno);
	}

	private static int getPathNo(Line line)
	{
		int pathno = - 1;
		Line[][] lines = Executer.edgelines;
		int noOfPaths = Executer.paths.size();
		int noOfEdges;

    	for(int i=0;i<noOfPaths;i++)
    	{
    		noOfEdges = lines[i].length;

    		for(int j=0;j<noOfEdges;j++)
    		{
    			if(lines[i][j].equals(line))
    			{
    				pathno = i;
    				break;
    			}
    		}
        }

    	if(pathno == -1)
        {
        	System.out.println("Error: could not find path");
        	System.exit(0);
        }

    	return(pathno);
	}

	private static void addCircleListeners()
	{
		int noOfScreensX = Graph.noOfScreensX;
		int noOfScreensY = Graph.noOfScreensY;
		int noOfNodes = Executer.nodes.size();
		Circle[][][] circles = Executer.nodecircles;

        for(int x=0;x<noOfScreensX;x++)
        {
        	for(int y=0;y<noOfScreensY;y++)
            {
        		for(int i=0;i<noOfNodes;i++)
        		{
        			try
        			{
        				circles[i][x][y].setOnMouseEntered(MouseOnCircleEnter());
        				circles[i][x][y].setOnMouseExited(MouseOnCircleExit());
        			}
	        		catch(NullPointerException e){}
        		}
            }
        }
	}

	private static void removeCircleListeners()
	{
		int noOfScreensX = Graph.noOfScreensX;
		int noOfScreensY = Graph.noOfScreensY;
		int noOfNodes = Executer.nodes.size();
		Circle[][][] circles = Executer.nodecircles;

        for(int x=0;x<noOfScreensX;x++)
        {
        	for(int y=0;y<noOfScreensY;y++)
            {
        		for(int i=0;i<noOfNodes;i++)
        		{
        			try
        			{
        				circles[i][x][y].setOnMouseEntered(null);
        				circles[i][x][y].setOnMouseExited(null);
        			}
	        		catch(NullPointerException e){}
        		}
            }
        }
	}

	private static void addArcAndPolygonListeners()
	{
		Arc[][] arcs = Executer.edgearcs;
		Line[][] lines = Executer.edgelines;
		Polygon[][] linearrows = Executer.linearrowpolygons;
		Polygon[][] arcarrows = Executer.arrowpolygons;

		int noOfPaths = Executer.paths.size();
		int noOfEdges;

		for(int i=0;i<noOfPaths;i++)
		{
			noOfEdges = arcs[i].length;

			for(int j=0;j<noOfEdges;j++)
			{
				arcs[i][j].setOnMouseEntered(MouseOnEdgeEnter());
				arcs[i][j].setOnMouseExited(MouseOnEdgeExit());
				arcarrows[i][j].setOnMouseEntered(MouseOnArrowEnter());
				arcarrows[i][j].setOnMouseExited(MouseOnArrowExit());
			}

			noOfEdges = lines[i].length;

			for(int j=0;j<noOfEdges;j++)
			{
				lines[i][j].setOnMouseEntered(MouseOnLineEnter());
				lines[i][j].setOnMouseExited(MouseOnLineExit());
				linearrows[i][j].setOnMouseEntered(MouseOnArrowEnter());
				linearrows[i][j].setOnMouseExited(MouseOnArrowExit());
			}
		}
	}

	private static void removeArcAndPolygonListeners()
	{
		Arc[][] arcs = Executer.edgearcs;
		Line[][] lines = Executer.edgelines;
		Polygon[][] linearrows = Executer.linearrowpolygons;
		Polygon[][] arcarrows = Executer.arrowpolygons;

		int noOfPaths = Executer.paths.size();
		int noOfEdges;

		System.out.println(noOfPaths);
		for(int i=0;i<noOfPaths;i++)
		{
			noOfEdges = arcs[i].length;

			for(int j=0;j<noOfEdges;j++)
			{
				arcs[i][j].setOnMouseEntered(null);
				arcs[i][j].setOnMouseExited(null);
				arcarrows[i][j].setOnMouseEntered(null);
				arcarrows[i][j].setOnMouseExited(null);
			}

			noOfEdges = lines[i].length;

			for(int j=0;j<noOfEdges;j++)
			{
				lines[i][j].setOnMouseEntered(null);
				lines[i][j].setOnMouseExited(null);
				linearrows[i][j].setOnMouseEntered(null);
				linearrows[i][j].setOnMouseExited(null);
			}
		}
	}

	private static void emphasizePath(int pathno)
	{
		Arc arc;
		Line line;
		Polygon linepolygon;
		Polygon arcpolygon;
		int noOfEdges = Executer.edgearcs[pathno].length;
		double[][] arrowcoordinates = new double[3][2];

        for(int i=0;i<noOfEdges;i++)
        {
        	arc = Executer.edgearcs[pathno][i];
        	arc.setStrokeWidth(4*Graph.defaultArcWidth);
        	arcpolygon = Executer.arrowpolygons[pathno][i];
        	arcpolygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+2*Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
        	arcpolygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+2*Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
        	arcpolygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-2*Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
        	arcpolygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-2*Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		}

        noOfEdges = Executer.edgelines[pathno].length;

        for(int i=0;i<noOfEdges;i++)
        {
        	line = Executer.edgelines[pathno][i];
        	line.setStrokeWidth(4*Graph.defaultArcWidth);
        	linepolygon = Executer.linearrowpolygons[pathno][i];

        	arrowcoordinates[0][0] = 0.5 * (linepolygon.getPoints().get(2) + linepolygon.getPoints().get(4));
        	arrowcoordinates[0][1] = 0.5 * (linepolygon.getPoints().get(3) + linepolygon.getPoints().get(5));
        	arrowcoordinates[1][0] = arrowcoordinates[0][0] - linepolygon.getPoints().get(0);
        	arrowcoordinates[1][1] = arrowcoordinates[0][1] - linepolygon.getPoints().get(1);
        	arrowcoordinates[2][0] = -arrowcoordinates[1][1];
        	arrowcoordinates[2][1] = arrowcoordinates[1][0];

        	linepolygon.getPoints().set(2,arrowcoordinates[0][0] + 2 * arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
        	linepolygon.getPoints().set(3,arrowcoordinates[0][1] + 2 * arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
        	linepolygon.getPoints().set(4,arrowcoordinates[0][0] - 2 * arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
        	linepolygon.getPoints().set(5,arrowcoordinates[0][1] - 2 * arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
		}
	}

	private static void resetCircle()
	{
		Circle[][][] circles = Executer.nodecircles;
		int alength = circles.length;
		int blength;
		int clength;

		if(nodeclicked)
		{
			Executer.nodecircles[nodeindex[0]][nodeindex[1]][nodeindex[2]].setRadius(Graph.defaultCircleRadius);
			Executer.nodecircles[nodeindex[0]][nodeindex[1]][nodeindex[2]].setStrokeWidth(Graph.defaultCircleWidth);
		}
		else
		{
			for(int a=0;a<alength;a++)
			{
				blength = circles[a].length;

				for(int b=0;b<blength;b++)
				{
					clength = circles[a][b].length;

					for(int c=0;c<clength;c++)
					{
						Executer.nodecircles[a][b][c].setRadius(Graph.defaultCircleRadius);
						Executer.nodecircles[a][b][c].setStrokeWidth(Graph.defaultCircleWidth);
					}
				}
			}
		}
	}

	private static void resetArcsAndPolygons()
	{
		Arc arc;
		Line line;
		Polygon linepolygon;
		Polygon arcpolygon;
		double[][] arrowcoordinates = new double[3][2];
		int noOfPaths = Executer.paths.size();
		int noOfEdges;

		if(pathclicked)
		{
			noOfEdges = Executer.edgearcs[pathindex].length;

			for(int i=0;i<noOfEdges;i++)
			{
				arc = Executer.edgearcs[pathindex][i];
        		arc.setStrokeWidth(Graph.defaultArcWidth);
        		arcpolygon = Executer.arrowpolygons[pathindex][i];
        		arcpolygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
        		arcpolygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
        		arcpolygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
        		arcpolygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
			}

			noOfEdges = Executer.edgelines[pathindex].length;

			for(int i=0;i<noOfEdges;i++)
			{
				line = Executer.edgelines[pathindex][i];
        		line.setStrokeWidth(Graph.defaultArcWidth);
        		linepolygon = Executer.linearrowpolygons[pathindex][i];

        	   	arrowcoordinates[0][0] = 0.5 * (linepolygon.getPoints().get(2) + linepolygon.getPoints().get(4));
            	arrowcoordinates[0][1] = 0.5 * (linepolygon.getPoints().get(3) + linepolygon.getPoints().get(5));
            	arrowcoordinates[1][0] = arrowcoordinates[0][0] - linepolygon.getPoints().get(0);
            	arrowcoordinates[1][1] = arrowcoordinates[0][1] - linepolygon.getPoints().get(1);
            	arrowcoordinates[2][0] = -arrowcoordinates[1][1];
            	arrowcoordinates[2][1] = arrowcoordinates[1][0];

            	linepolygon.getPoints().set(2,arrowcoordinates[0][0] + arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
            	linepolygon.getPoints().set(3,arrowcoordinates[0][1] + arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
            	linepolygon.getPoints().set(4,arrowcoordinates[0][0] - arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
            	linepolygon.getPoints().set(5,arrowcoordinates[0][1] - arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
    		}
		}
		else
		{
			for(int j=0;j<noOfPaths;j++)
			{
				noOfEdges = Executer.edgearcs[j].length;

				for(int i=0;i<noOfEdges;i++)
				{
					arc = Executer.edgearcs[j][i];
	        		arc.setStrokeWidth(Graph.defaultArcWidth);
	        		arcpolygon = Executer.arrowpolygons[j][i];
	        		arcpolygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	        		arcpolygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	        		arcpolygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	        		arcpolygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
				}

				noOfEdges = Executer.edgelines[j].length;

				for(int i=0;i<noOfEdges;i++)
				{
					line = Executer.edgelines[j][i];
	        		line.setStrokeWidth(Graph.defaultArcWidth);
	        		linepolygon = Executer.linearrowpolygons[j][i];

	        	   	arrowcoordinates[0][0] = 0.5 * (linepolygon.getPoints().get(2) + linepolygon.getPoints().get(4));
	            	arrowcoordinates[0][1] = 0.5 * (linepolygon.getPoints().get(3) + linepolygon.getPoints().get(5));
	            	arrowcoordinates[1][0] = arrowcoordinates[0][0] - linepolygon.getPoints().get(0);
	            	arrowcoordinates[1][1] = arrowcoordinates[0][1] - linepolygon.getPoints().get(1);
	            	arrowcoordinates[2][0] = -arrowcoordinates[1][1];
	            	arrowcoordinates[2][1] = arrowcoordinates[1][0];

	            	linepolygon.getPoints().set(2,arrowcoordinates[0][0] + arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
	            	linepolygon.getPoints().set(3,arrowcoordinates[0][1] + arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
	            	linepolygon.getPoints().set(4,arrowcoordinates[0][0] - arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
	            	linepolygon.getPoints().set(5,arrowcoordinates[0][1] - arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
	    		}
			}
		}
	}

	private static void resetArcsAndPolygons(int pathno)
	{
		Arc arc;
		Polygon arcpolygon;
		Line line;
		Polygon linepolygon;
		double[][] arrowcoordinates = new double[3][2];

		int noOfEdges = Executer.edgearcs[pathno].length;

		for(int i=0;i<noOfEdges;i++)
		{
			arc = Executer.edgearcs[pathno][i];
    		arc.setStrokeWidth(Graph.defaultArcWidth);
    		arcpolygon = Executer.arrowpolygons[pathno][i];
    		arcpolygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
    		arcpolygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
    		arcpolygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-Graph.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
    		arcpolygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-Graph.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		}

		noOfEdges = Executer.edgelines[pathno].length;

		for(int i=0;i<noOfEdges;i++)
		{
			line = Executer.edgelines[pathno][i];
    		line.setStrokeWidth(Graph.defaultArcWidth);
    		linepolygon = Executer.linearrowpolygons[pathno][i];

    	   	arrowcoordinates[0][0] = 0.5 * (linepolygon.getPoints().get(2) + linepolygon.getPoints().get(4));
        	arrowcoordinates[0][1] = 0.5 * (linepolygon.getPoints().get(3) + linepolygon.getPoints().get(5));
        	arrowcoordinates[1][0] = arrowcoordinates[0][0] - linepolygon.getPoints().get(0);
        	arrowcoordinates[1][1] = arrowcoordinates[0][1] - linepolygon.getPoints().get(1);
        	arrowcoordinates[2][0] = -arrowcoordinates[1][1];
        	arrowcoordinates[2][1] = arrowcoordinates[1][0];

        	linepolygon.getPoints().set(2,arrowcoordinates[0][0] + arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
        	linepolygon.getPoints().set(3,arrowcoordinates[0][1] + arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
        	linepolygon.getPoints().set(4,arrowcoordinates[0][0] - arrowcoordinates[2][0] * Math.tan(Math.toRadians(25)));
        	linepolygon.getPoints().set(5,arrowcoordinates[0][1] - arrowcoordinates[2][1] * Math.tan(Math.toRadians(25)));
		}
	}

	private static void printNodeText(int nodeno){
    	Node node = Executer.nodes.get(nodeno);
    	ArrayList<String> attributeNames = node.getAttributeNames();
    	ArrayList<String> attributes = node.getAttributes();
    	int noOfAttributes = attributeNames.size();
    	String maintext;

    	maintext = "No: "+node.getNumber()+"\nCoordinates:\n("
	        	+node.getXcoordinate()+","+node.getYcoordinate()+")\n";

    	for(int i=0;i<noOfAttributes;i++)
    	{
    		maintext = maintext +"\n"+ attributeNames.get(i) +": "+ attributes.get(i);
    	}

    	Executer.titleLabel.setText("Node");
    	Executer.mainLabel.setText(maintext);
	}

	private static void printPathText(int pathno)
	{
        String maintext = "";
        Path path = Executer.paths.get(pathno);
        ArrayList<Node[]> nodes = path.getNodes();
    	ArrayList<String> pathAttributeNames = Executer.pathAttributeNames;
    	ArrayList<String> pathAttributes = path.getPathAttributes();
    	int noOfPathAttributes = pathAttributeNames.size();
    	ArrayList<String> edgeAttributeNames = Executer.edgeAttributeNames;
    	ArrayList<String[]> edgeAttributes = path.getEdgeAttributes();
    	int noOfEdges = edgeAttributes.size();
    	int noOfEdgeAttributes = edgeAttributeNames.size();

    	for(int i=0;i<noOfPathAttributes;i++)
    	{
    		maintext = maintext + pathAttributeNames.get(i) +": "+ pathAttributes.get(i) +"\n";
    	}

    	maintext = maintext + "\nRoutes:";

        for(int i=0;i<noOfEdges;i++)
        {
        	maintext = maintext +"\n"+ nodes.get(i)[0].getNumber()+"-"+nodes.get(i)[1].getNumber() +"\n";

        	for(int j=0;j<noOfEdgeAttributes;j++)
        	{
        		maintext = maintext + edgeAttributeNames.get(j) +": "+ edgeAttributes.get(i)[j] +"\n";
        	}
        }

        Executer.titleLabel.setText("Path");
        Executer.mainLabel.setText(maintext);
	}

	private static int[] findNodeIndex(Circle circle)
	{
		Circle[][][] circles = Executer.nodecircles;
		int[] output = new int[3];
		int alength = circles.length;
		int blength;
		int clength;

		for(int a=0;a<alength;a++)
		{
			blength = circles[a].length;

			for(int b=0;b<blength;b++)
			{
				clength = circles[a][b].length;

				for(int c=0;c<clength;c++)
				{
					if(circle.equals(circles[a][b][c]))
					{
						output[0] = a;
						output[1] = b;
						output[2] = c;
					}
				}
			}
		}

		return output;
	}

	private static void adjustState(int pathno)
	{
    	if(pathno == pathindex & clickedstate)
    	{
    		Executer.titleLabel.setText("");
    		Executer.mainLabel.setText("");

    		resetArcsAndPolygons();
    		addCircleListeners();
    		addArcAndPolygonListeners();

        	clickedstate = false;
        	pathclicked = false;
        	pathindex = -1;
    	}
    	else if(clickedstate)
    	{
    		resetCircle();
    		resetArcsAndPolygons();

    		emphasizePath(pathno);

    		nodeclicked = false;
        	pathclicked = true;
        	pathindex = pathno;
        	nodeindex = null;
    	}
    	else
    	{
    		removeCircleListeners();
    		removeArcAndPolygonListeners();
    		emphasizePath(pathno);

    		clickedstate = true;
        	pathclicked = true;
        	pathindex = pathno;
    	}
	}

	public static EventHandler<MouseEvent> MouseOnCircleEnter(){
		EventHandler<MouseEvent> mouseDraggedOnCircleEntered = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Circle circle = (Circle) t.getSource();
	        	int nodeno = getNodeNo(circle);

	        	printNodeText(nodeno);

	        	circle.setRadius(2*Graph.defaultCircleRadius);
	        	circle.setStrokeWidth(2*Graph.defaultCircleWidth);
	        }
		};

		return (mouseDraggedOnCircleEntered);
	}

	public static EventHandler<MouseEvent> MouseOnCircleExit(){
		EventHandler<MouseEvent> mouseDraggedOnCircleExited = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Circle circle = (Circle) (t.getSource());

	        	Executer.titleLabel.setText("");
	        	Executer.mainLabel.setText("");

	        	circle.setRadius(Graph.defaultCircleRadius);
	        	circle.setStrokeWidth(Graph.defaultCircleWidth);
	        }
		};

		return(mouseDraggedOnCircleExited);
	}

	public static EventHandler<MouseEvent> MouseclickOnCircle(){
		EventHandler<MouseEvent> mouseclickOnCircle = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Label Title = Executer.titleLabel;
	        	Label Main = Executer.mainLabel;
	        	Circle circle = (Circle) t.getSource();
	        	boolean clicked = false;
	        	int nodeno = getNodeNo(circle);

	        	if(circle.getRadius()==(2*Graph.defaultCircleRadius))
	        	{
	        		clicked = true;
	        	}

	        	if(clicked&clickedstate)
	        	{
	        		Title.setText("");
	        		Main.setText("");

		        	resetCircle();

		        	addCircleListeners();
		        	addArcAndPolygonListeners();

		        	clickedstate = false;
		        	nodeclicked = false;
		        	nodeindex = null;
	        	}
	        	else if(clickedstate)
	        	{
	        		resetCircle();
	        		resetArcsAndPolygons();

		        	circle.setRadius(2*Graph.defaultCircleRadius);
		        	circle.setStrokeWidth(2*Graph.defaultCircleWidth);

		        	pathclicked = false;
		        	nodeclicked = true;
		        	nodeindex = findNodeIndex(circle);
		        	pathindex = -1;
	        	}
	        	else
	        	{
	        		removeCircleListeners();
	        		removeArcAndPolygonListeners();

	        		circle.setRadius(2*Graph.defaultCircleRadius);
		        	circle.setStrokeWidth(2*Graph.defaultCircleWidth);

	        		clickedstate = true;
		        	nodeclicked = true;
		        	nodeindex = findNodeIndex(circle);
	        	}

	        	printNodeText(nodeno);
	        }
		};

		return(mouseclickOnCircle);
	}

	public static EventHandler<MouseEvent> MouseOnEdgeEnter(){
		EventHandler<MouseEvent> mouseOnEdgeEnter = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc = (Arc) (t.getSource());
	        	int pathno = getPathNo(arc);

	            printPathText(pathno);
	            emphasizePath(pathno);
	        }
		};

		return(mouseOnEdgeEnter);
	}

	public static EventHandler<MouseEvent> MouseOnEdgeExit(){
		EventHandler<MouseEvent> mouseOnEdgeExit = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc = (Arc) (t.getSource());
	        	int pathno = getPathNo(arc);

	        	Executer.titleLabel.setText("");
	        	Executer.mainLabel.setText("");

	        	resetArcsAndPolygons(pathno);
	        }
		};

		return(mouseOnEdgeExit);
	}

	public static EventHandler<MouseEvent> MouseOnArrowExit(){
		EventHandler<MouseEvent> mouseOnEdgeExit = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Polygon polygon = (Polygon) (t.getSource());
	        	int pathno = getPathNo(polygon);

	        	Executer.titleLabel.setText("");
	        	Executer.mainLabel.setText("");

	        	resetArcsAndPolygons(pathno);
	        }
		};

		return(mouseOnEdgeExit);
	}

	public static EventHandler<MouseEvent> MouseOnLineExit(){
		EventHandler<MouseEvent> mouseOnEdgeExit = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Line line = (Line) (t.getSource());
	        	int pathno = getPathNo(line);

	        	Executer.titleLabel.setText("");
	        	Executer.mainLabel.setText("");

	        	resetArcsAndPolygons(pathno);
	        }
		};

		return(mouseOnEdgeExit);
	}

	public static EventHandler<MouseEvent> MouseOnLineEnter(){
		EventHandler<MouseEvent> mouseOnEdgeEnter = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Line line = (Line) (t.getSource());
	        	int pathno = getPathNo(line);

	            printPathText(pathno);
	            emphasizePath(pathno);
	        }
		};

		return(mouseOnEdgeEnter);
	}

	public static EventHandler<MouseEvent> MouseOnArrowEnter(){
		EventHandler<MouseEvent> mouseOnArrowEnter = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Polygon polygon = (Polygon) (t.getSource());
	        	int pathno = getPathNo(polygon);

	            printPathText(pathno);
	            emphasizePath(pathno);
	        }
		};

		return(mouseOnArrowEnter);
	}

	public static EventHandler<MouseEvent> MouseclickOnArrow(){
		EventHandler<MouseEvent> mouseclickOnPolygon = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Polygon polygon = (Polygon) t.getSource();
	        	int pathno = getPathNo(polygon);
	        	adjustState(pathno);

	        	printPathText(pathno);
	        }
		};

		return(mouseclickOnPolygon);
	}

	public static EventHandler<MouseEvent> MouseclickOnEdge(){
		EventHandler<MouseEvent> mouseclickOnArc = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc = (Arc) t.getSource();
	        	int pathno = getPathNo(arc);
	        	adjustState(pathno);

	        	printPathText(pathno);
	        }
		};

		return(mouseclickOnArc);
	}

	public static EventHandler<MouseEvent> MouseclickOnLine(){
		EventHandler<MouseEvent> mouseclickOnLine = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Line line = (Line) t.getSource();
	        	int pathno = getPathNo(line);
	        	adjustState(pathno);

	        	printPathText(pathno);
	        }
		};

		return(mouseclickOnLine);
	}
}
