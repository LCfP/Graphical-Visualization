package output;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import model.Edge;
import model.Executer;
import model.Node;
import model.Path;

public class Mouse {
	private static boolean clickedstate = false;

	public static int getNodeNo(Circle circle)
	{
    	int noOfNodes = Executer.nodes.size();
    	int noOfScreensX = WindowContent.noOfScreensX;
    	int noOfScreensY = WindowContent.noOfScreensY;

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

	public static int[] getPathNo(Arc arc)
	{
		int pathno = - 1;
		int arccounter = 0;
		int[] returnvalues = new int[2];
		int noOfPaths = Executer.paths.size();
		int noOfEdges;
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
		Arc[][][] arcs = Executer.edgearcs;

    	for(int i=0;i<noOfPaths;i++)
    	{
    		noOfEdges = Executer.paths.get(i).getEdges().size();
    		for(int j=0;j<noOfEdges;j++)
    		{
    			for(int x=0;x<noOfScreensX;x++)
	            {
	            	for(int y=0;y<noOfScreensY;y++)
	                {
	            		try
	            		{
			            	if(arc.equals(arcs[arccounter+j][x][y]))
			            	{
			            		pathno = i;
			            		break;
			            	}
	            		}
	            		catch(NullPointerException e){}
	                }
	            }
    		}

    		if(pathno != -1)
    		{
    			break;
    		}

    		arccounter += noOfEdges;
        }

    	if(pathno == -1)
        {
        	System.out.println("Error: could not find path");
        	System.exit(0);
        }

    	returnvalues[0] = pathno;
    	returnvalues[1] = arccounter;

    	return(returnvalues);
	}

	public static int[] getPathNo(Polygon polygon)
	{
		int pathno = - 1;
		int arccounter = 0;
		int[] returnvalues = new int[2];
		int noOfPaths = Executer.paths.size();
		int noOfEdges;
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
		Polygon[][][] polygons = Executer.arrowpolygons;

    	for(int i=0;i<noOfPaths;i++)
    	{
    		noOfEdges = Executer.paths.get(i).getEdges().size();
    		for(int j=0;j<noOfEdges;j++)
    		{
    			for(int x=0;x<noOfScreensX;x++)
	            {
	            	for(int y=0;y<noOfScreensY;y++)
	                {
	            		try
	            		{
			            	if(polygon.equals(polygons[arccounter+j][x][y]))
			            	{
			            		pathno = i;
			            		break;
			            	}
	            		}
	            		catch(NullPointerException e){}
	                }
	            }
    		}

    		if(pathno != -1)
    		{
    			break;
    		}

    		arccounter += noOfEdges;
        }

    	if(pathno == -1)
        {
        	System.out.println("Error: could not find path");
        	System.exit(0);
        }

    	returnvalues[0] = pathno;
    	returnvalues[1] = arccounter;

    	return(returnvalues);
	}

	public static void addCircleListeners(Pane drawPane)
	{
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
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
        				circles[i][x][y].setOnMouseEntered(MouseOnCircleEnter(drawPane));
        				circles[i][x][y].setOnMouseExited(MouseOnCircleExit(drawPane));
        			}
	        		catch(NullPointerException e){}
        		}
            }
        }
	}

	public static void removeCircleListeners()
	{
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
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

	public static void addArcPolygonListeners(Pane drawPane)
	{
		Arc arc;
		Polygon polygon;
		ArrayList<Edge> edges;
		int noOfPaths = Executer.paths.size();
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
		int arccounter = 0;

		for(int i=0;i<noOfPaths;i++)
		{
			edges = Executer.paths.get(i).getEdges();
			for(int j=0;j<edges.size();j++)
			{
	            for(int x=0;x<noOfScreensX;x++)
	            {
	            	for(int y=0;y<noOfScreensY;y++)
	                {
	            		try
	            		{
	        				arc = Executer.edgearcs[arccounter+j][x][y];
		    	        	arc.setOnMouseEntered(MouseOnEdgeEnter(drawPane));
		    	        	arc.setOnMouseExited(MouseOnEdgeExit(drawPane));
		    	        	polygon = Executer.arrowpolygons[arccounter+j][x][y];
		    	        	polygon.setOnMouseEntered(MouseOnArrowEnter(drawPane));
		    	        	polygon.setOnMouseExited(MouseOnEdgeExit(drawPane));
	        			}
		        		catch(NullPointerException e){}
	                }
	            }
			}

			arccounter += edges.size();
		}
	}

	public static void removeArcPolygonListeners()
	{
		Arc arc;
		Polygon polygon;
		ArrayList<Edge> edges;
		int noOfPaths = Executer.paths.size();
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
		int arccounter = 0;

		for(int i=0;i<noOfPaths;i++)
		{
			edges = Executer.paths.get(i).getEdges();
			for(int j=0;j<edges.size();j++)
			{
	            for(int x=0;x<noOfScreensX;x++)
	            {
	            	for(int y=0;y<noOfScreensY;y++)
	                {
	            		try
	            		{
	        				arc = Executer.edgearcs[arccounter+j][x][y];
		    	        	arc.setOnMouseEntered(null);
		    	        	arc.setOnMouseExited(null);
		    	        	polygon = Executer.arrowpolygons[arccounter+j][x][y];
		    	        	polygon.setOnMouseEntered(null);
		    	        	polygon.setOnMouseExited(null);
	        			}
		        		catch(NullPointerException e){}
	                }
	            }
			}

			arccounter += edges.size();
		}
	}

	public static void emphasizePath(int pathno,int arccounter)
	{
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
		int noOfEdges = Executer.paths.get(pathno).getEdges().size();
		Arc arc;
		Polygon polygon;

        for(int i=0;i<noOfEdges;i++)
        {
			for(int x=0;x<noOfScreensX;x++)
            {
            	for(int y=0;y<noOfScreensY;y++)
                {
            		try
            		{
    	            	arc = (Arc) (Executer.edgearcs[arccounter+i][x][y]);
    	            	arc.setStrokeWidth(4*WindowContent.defaultArcWidth);
    	            	polygon = (Polygon) (Executer.arrowpolygons[arccounter+i][x][y]);
    	            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+2*WindowContent.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
    	            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+2*WindowContent.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
    	            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-2*WindowContent.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
    	            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-2*WindowContent.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
            		}
            		catch(NullPointerException e){}
	            }
            }
        }
	}

	public static void resetCircles()
	{
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
		int noOfNodes = Executer.nodes.size();
		Circle[][][] circles = Executer.nodecircles;

		for(int i=0;i<noOfNodes;i++)
		{
            for(int x=0;x<noOfScreensX;x++)
            {
            	for(int y=0;y<noOfScreensY;y++)
                {
            		try
            		{
	            		circles[i][x][y].setRadius(WindowContent.defaultCircleRadius);
	            		circles[i][x][y].setStrokeWidth(WindowContent.defaultCircleWidth);
            		}
            		catch(NullPointerException e){}
                }
            }
		}
	}

	public static void resetArcsPolygons()
	{
		Arc arc;
		Polygon polygon;
		ArrayList<Edge> edges;
		int noOfPaths = Executer.paths.size();
		int noOfScreensX = WindowContent.noOfScreensX;
		int noOfScreensY = WindowContent.noOfScreensY;
		int arccounter = 0;

		for(int i=0;i<noOfPaths;i++)
		{
			edges = Executer.paths.get(i).getEdges();
			for(int j=0;j<edges.size();j++)
    		{
				for(int x=0;x<noOfScreensX;x++)
	            {
	            	for(int y=0;y<noOfScreensY;y++)
	                {
	            		try
	            		{
		            		arc = Executer.edgearcs[arccounter+j][x][y];
		            		arc.setStrokeWidth(WindowContent.defaultArcWidth);
		            		polygon = Executer.arrowpolygons[arccounter+j][x][y];
		            		polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+WindowContent.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            		polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+WindowContent.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            		polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-WindowContent.defaultArrowWidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            		polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-WindowContent.defaultArrowWidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            		}
	            		catch(NullPointerException e){}
	            	}
	            }
    		}

			arccounter += edges.size();
		}
	}

	public static void printNodeText(int nodeno){
    	Node node = Executer.nodes.get(nodeno);
    	ArrayList<String> attributeNames = node.getAttributeNames();
    	ArrayList<String> attributes = node.getAttributes();
    	int noOfAttributes = attributeNames.size();
    	Label Title = Executer.titleLabel;
    	Label Main = Executer.mainLabel;
    	String maintext;
    	
    	maintext = "No: "+node.getNumber()+"\nCoordinates:\n("
	        	+node.getRealXcoordinate()+","+node.getRealYcoordinate()+")\n";
    	
    	for(int i=0;i<noOfAttributes;i++)
    	{
    		maintext = maintext +"\n"+ attributeNames.get(i) +": "+ attributes.get(i);
    	}

    	Title.setText("Node");
    	Title.setFont(new Font(Math.min(WindowContent.defaultWidth/30,WindowContent.defaultHeight/20)));
    	Title.setLayoutX(0.07*WindowContent.defaultWidth);
    	Title.setLayoutY(0.03*WindowContent.defaultHeight+Executer.menuBarHeight);
    	
    	Main.setText(maintext);
    	Main.setMaxWidth(0.18*WindowContent.defaultWidth);
    	Main.setMaxHeight(0.85*WindowContent.defaultHeight-Executer.menuBarHeight);
    	Main.setFont(new Font(Math.min(WindowContent.defaultWidth/60,WindowContent.defaultHeight/40)));
    	Main.setLayoutX(0.02*WindowContent.defaultWidth);
    	Main.setLayoutY(0.15*WindowContent.defaultHeight+Executer.menuBarHeight);
	}

	public static void printPathText(int pathno)
	{
    	Label Title = Executer.titleLabel;
    	Label Main = Executer.mainLabel;
        String maintext = "";
        Edge edge;
        Path path = Executer.paths.get(pathno);
    	ArrayList<String> attributeNames = path.getAttributeNames();
    	ArrayList<String> attributes = path.getAttributes();
    	int noOfAttributes = attributeNames.size();
    	ArrayList<String> edgeAttributeNames = path.getEdges().get(0).getAttributeNames();
    	ArrayList<String> edgeAttributes;
    	int noOfEdgeAttributes = edgeAttributeNames.size();
    
    	for(int i=0;i<noOfAttributes;i++)
    	{
    		maintext = maintext + attributeNames.get(i) +": "+ attributes.get(i) +"\n";
    	}
    	
    	maintext = maintext + "\nRoutes:";
    	
        for(int i=0;i<path.getEdges().size();i++)
        {
        	edge = path.getEdges().get(i);
        	maintext = maintext +"\n"+ edge.getNode1().getNumber()+"-"+edge.getNode2().getNumber() +"\n";
        	edgeAttributes = edge.getAttributes();
        	
        	for(int j=0;j<noOfEdgeAttributes;j++)
        	{
        		maintext = maintext + edgeAttributeNames.get(j) +": "+ edgeAttributes.get(j) +"\n";
        	}
        }
        
    	Title.setText("Path");
    	Title.setFont(new Font(Math.min(WindowContent.defaultWidth/30,WindowContent.defaultHeight/20)));
    	Title.setLayoutX(0.07*WindowContent.defaultWidth);
    	Title.setLayoutY(0.03*WindowContent.defaultHeight+Executer.menuBarHeight);

    	Main.setText(maintext);
    	Main.setMaxWidth(0.18*WindowContent.defaultWidth);
    	Main.setMaxHeight(0.85*WindowContent.defaultHeight-Executer.menuBarHeight);
    	Main.setFont(new Font(Math.min(WindowContent.defaultWidth/60,WindowContent.defaultHeight/40)));
    	Main.setLayoutX(0.02*WindowContent.defaultWidth);
    	Main.setLayoutY(0.15*WindowContent.defaultHeight+Executer.menuBarHeight);
	}

	public static EventHandler<MouseEvent> MouseOnCircleEnter(Pane drawPane){
		EventHandler<MouseEvent> mouseDraggedOnCircleEntered = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Circle circle = (Circle) t.getSource();
	        	int nodeno = getNodeNo(circle);

	        	printNodeText(nodeno);

	        	circle.setRadius(2*WindowContent.defaultCircleRadius);
	        	circle.setStrokeWidth(2*WindowContent.defaultCircleWidth);
	        }
		};

		return (mouseDraggedOnCircleEntered);
	}

	public static EventHandler<MouseEvent> MouseOnCircleExit(Pane drawPane){
		EventHandler<MouseEvent> mouseDraggedOnCircleExited = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Circle[][][] circles = Executer.nodecircles;
	        	Circle circle = (Circle) (t.getSource());
	        	int noOfNodes = Executer.nodecircles.length;
	        	int nodeNo = -1;
	        	int nodeX = -1;
	        	int nodeY = -1;
	        	int noOfScreensX = WindowContent.noOfScreensX;
	        	int noOfScreensY = WindowContent.noOfScreensY;

	        	Executer.titleLabel.setText("");
	        	Executer.mainLabel.setText("");

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
				        			nodeNo = i;
				        			nodeX = x;
				        			nodeY = y;
				        			break;
				        		}
			        		}
			        		catch(NullPointerException e){}
			        	}
		        	}
	        	}

	        	circle = circles[nodeNo][nodeX][nodeY];
	        	circle.setRadius(WindowContent.defaultCircleRadius);
	        	circle.setStrokeWidth(WindowContent.defaultCircleWidth);
	        }
		};

		return(mouseDraggedOnCircleExited);
	}

	public static EventHandler<MouseEvent> MouseclickOnCircle(Pane drawPane){
		EventHandler<MouseEvent> mouseclickOnCircle = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Label Title = Executer.titleLabel;
	        	Label Main = Executer.mainLabel;
	        	Circle circle = (Circle) t.getSource();
	        	boolean clicked = false;
	        	int nodeno = getNodeNo(circle);

	        	if(circle.getRadius()==(2*WindowContent.defaultCircleRadius))
	        	{
	        		clicked = true;
	        	}

	        	if(clicked&clickedstate)
	        	{
	        		Title.setText("");
	        		Main.setText("");

		        	circle.setRadius(WindowContent.defaultCircleRadius);
		        	circle.setStrokeWidth(WindowContent.defaultCircleWidth);

		        	addCircleListeners(drawPane);
		        	addArcPolygonListeners(drawPane);

		        	clickedstate = false;
	        	}
	        	else
	        	{
		        	if(clickedstate)
		        	{
		        		resetCircles();
		        		resetArcsPolygons();

			        	circle.setRadius(2*WindowContent.defaultCircleRadius);
			        	circle.setStrokeWidth(2*WindowContent.defaultCircleWidth);
		        	}
		        	else
		        	{
		        		removeCircleListeners();
		        		removeArcPolygonListeners();

		        		clickedstate = true;
		        	}

		        	printNodeText(nodeno);
	        	}
	        }
		};

		return(mouseclickOnCircle);
	}

	public static EventHandler<MouseEvent> MouseOnEdgeEnter(Pane drawPane){
		EventHandler<MouseEvent> mouseOnEdgeEnter = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc = (Arc) (t.getSource());
	        	int[] patharcno = getPathNo(arc);
	        	int pathno = patharcno [0];
	        	int arccounter = patharcno[1];

	            printPathText(pathno);
	            emphasizePath(pathno,arccounter);
	        }
		};

		return(mouseOnEdgeEnter);
	}

	public static EventHandler<MouseEvent> MouseOnEdgeExit(Pane drawPane){
		EventHandler<MouseEvent> mouseOnEdgeExit = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Executer.titleLabel.setText("");
	        	Executer.mainLabel.setText("");

	        	resetArcsPolygons();
	        }
		};

		return(mouseOnEdgeExit);
	}

	public static EventHandler<MouseEvent> MouseOnArrowEnter(Pane drawPane){
		EventHandler<MouseEvent> mouseOnArrowEnter = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Polygon polygon = (Polygon) (t.getSource());
	        	int[] patharcno = getPathNo(polygon);
	        	int pathno = patharcno [0];
	        	int arccounter = patharcno[1];

	            printPathText(pathno);
	            emphasizePath(pathno,arccounter);
	        }
		};

		return(mouseOnArrowEnter);
	}

	public static EventHandler<MouseEvent> MouseclickOnArrow(Pane drawPane){
		EventHandler<MouseEvent> mouseclickOnPolygon = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Polygon polygon = (Polygon) t.getSource();
	        	boolean clicked = false;
	        	int[] patharcno = getPathNo(polygon);
	        	int pathno = patharcno [0];
	        	int arccounter = patharcno[1];
	            double distance = Math.sqrt(Math.pow(polygon.getPoints().get(2)-polygon.getPoints().get(4), 2)
	            		+Math.pow(polygon.getPoints().get(3)-polygon.getPoints().get(5), 2));

	        	if(Math.abs(distance - 4*WindowContent.defaultArrowWidth) < 0.01)
	        	{
	        		clicked = true;
	        	}

	        	if(clicked&clickedstate)
	        	{
	        		Executer.titleLabel.setText("");
	        		Executer.mainLabel.setText("");

	        		resetArcsPolygons();
	        		addCircleListeners(drawPane);
	        		addArcPolygonListeners(drawPane);

		        	clickedstate = false;
	        	}
	        	else
	        	{
		        	if(clickedstate)
		        	{
		        		resetCircles();
		        		resetArcsPolygons();
		        		emphasizePath(pathno,arccounter);
		        	}
		        	else
		        	{
		        		removeCircleListeners();
		        		removeArcPolygonListeners();

		        		clickedstate = true;
		        	}

		        	printPathText(pathno);
	        	}
	        }
		};

		return(mouseclickOnPolygon);
	}

	public static EventHandler<MouseEvent> MouseclickOnEdge(Pane drawPane){
		EventHandler<MouseEvent> mouseclickOnArc = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc = (Arc) t.getSource();
	        	boolean clicked = false;
	        	int[] patharcno = getPathNo(arc);
	        	int pathno = patharcno [0];
	        	int arccounter = patharcno[1];

	        	if(arc.getStrokeWidth()==(4*WindowContent.defaultArcWidth))
	        	{
	        		clicked = true;
	        	}

	        	if(clicked&clickedstate)
	        	{
	        		Executer.titleLabel.setText("");
	        		Executer.mainLabel.setText("");

	        		resetArcsPolygons();
	        		addCircleListeners(drawPane);
	        		addArcPolygonListeners(drawPane);

		        	clickedstate = false;
	        	}
	        	else
	        	{
		        	if(clickedstate)
		        	{
		        		resetCircles();
		        		resetArcsPolygons();
		        		emphasizePath(pathno,arccounter);
		        	}
		        	else
		        	{
		        		removeCircleListeners();
		        		removeArcPolygonListeners();

		        		clickedstate = true;
		        	}

		        	printPathText(pathno);
	        	}
	        }
		};

		return(mouseclickOnArc);
	}
}
