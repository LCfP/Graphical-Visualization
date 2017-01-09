package output;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
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

	public static EventHandler<MouseEvent> MouseOnCircleEnter(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseDraggedOnCircleEntered = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Circle circle = (Circle) t.getSource();
	        	Node node;
	        	String depot;

	        	int nodeno = -1;
	        	int noOfNodes = Executer.nodes.size();

	        	for(int i=0;i<noOfNodes;i++)
	        	{
	        		if(Executer.nodecircles[i].equals(circle))
	        		{
	        			nodeno = i;
	        			break;
	        		}
	        	}

	        	if(nodeno == -1)
	        	{
	        		System.out.println("Error: could not find node");
	        		System.exit(0);
	        	}

	        	node = Executer.nodes.get(nodeno);

	        	if(node.isDepot())
	        	{
	        		depot = "yes";
	        	}
	        	else
	        	{
	        		depot = "no";
	        	}

	        	gc.setFont(new Font(Executer.defaultHeight/20));
	        	gc.fillText("Node",60,60);
	        	gc.setFont(new Font(Executer.defaultHeight/40));
	        	gc.fillText("No: "+node.getNumber()+"\nDepot: "+depot+"\nCoordinates:\n("
	        	+node.getRealXcoordinate()+","+node.getRealYcoordinate()+")",10,100);

	        	circle.setRadius(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/60);
	        	circle.setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/100);
	        }
		};

		return (mouseDraggedOnCircleEntered);
	}

	public static EventHandler<MouseEvent> MouseOnCircleExit(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseDraggedOnCircleExited = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Circle circle;

	        	gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

	        	int noOfNodes = Executer.nodes.size();

	        	for(int i=0;i<noOfNodes;i++)
	        	{
	        		circle = (Circle)(pane.getChildren().get(i));
	            	circle.setRadius(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/120);
	            	circle.setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/200);
	        	}
	        }
		};

		return(mouseDraggedOnCircleExited);
	}

	public static EventHandler<MouseEvent> MouseOnEdgeEnter(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseOnEdgeEnter = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc = (Arc) (t.getSource());
	        	Polygon polygon;
	        	ArrayList<Edge> edges;

	            int pathno = -1;
	            int noOfNodes = Executer.nodes.size();
	            int noOfPaths = Executer.paths.size();
	            int arccounter = 0;
	            double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;

	        	for(int i=0;i<noOfPaths;i++)
	        	{
	        		edges = Executer.paths.get(i).getEdges();
	        		for(int j=0;j<edges.size();j++)
	        		{
		            	if(arc.equals(Executer.edgearcs[arccounter+j]))
		            	{
		            		pathno = i;
		            		break;
		            	}
	        		}

	        		if(pathno != -1)
	        		{
	        			break;
	        		}

	        		arccounter += edges.size();
	            }

	        	if(pathno == -1)
	            {
	            	System.out.println("Error: could not find path");
	            	System.exit(0);
	            }

	            Path path = Executer.paths.get(pathno);
	            String route = "Routes:\n";
	            String edge;

	            for(int i=0;i<path.getEdges().size();i++)
	            {
	            	edge = path.getEdges().get(i).getNode1().getNumber()+"-"+path.getEdges().get(i).getNode2().getNumber()+"\n";
	            	route = route + edge;
	            }
	            route = route + "Costs: "+path.getCosts();

	            gc.setFont(new Font(Executer.defaultHeight/20));
	            gc.fillText("Path",60,60);
	            gc.setFont(new Font(Executer.defaultHeight/40));
	            gc.fillText(route,10,100);

	            edges = Executer.paths.get(pathno).getEdges();

	            for(int i=0;i<edges.size();i++)
	            {
	            	arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+i)));
	            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/100);
	            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+i)+1));
	            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            }
	        }
		};

		return(mouseOnEdgeEnter);
	}

	public static EventHandler<MouseEvent> MouseOnEdgeExit(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseOnEdgeExit = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	ArrayList<Edge> edges;
	        	int noOfNodes = Executer.nodes.size();
	        	int noOfPaths = Executer.paths.size();
	        	int arccounter = 0;
	            double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
	        	Arc arc;
	        	Polygon polygon;

	        	gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

	        	for(int i=0;i<noOfPaths;i++)
	        	{
	        		edges = Executer.paths.get(i).getEdges();
	        		for(int j=0;j<edges.size();j++)
	        		{
	        			arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
	        			arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
	        	       	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
		            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	        		}

	        		arccounter += edges.size();
	        	}
	        }
		};

		return(mouseOnEdgeExit);
	}

	public static EventHandler<MouseEvent> MouseOnArrowEnter(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseOnArrowEnter = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc;
	        	Polygon polygon = (Polygon) (t.getSource());
	        	ArrayList<Edge> edges;

	            int pathno = -1;
	            int noOfNodes = Executer.nodes.size();
	            int noOfPaths = Executer.paths.size();
	            int arccounter = 0;
	            double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;

	        	for(int i=0;i<noOfPaths;i++)
	        	{
	        		edges = Executer.paths.get(i).getEdges();
	        		for(int j=0;j<edges.size();j++)
	        		{
		            	if(polygon.equals(Executer.arrowpolygons[arccounter+j]))
		            	{
		            		pathno = i;
		            		break;
		            	}
	        		}

	        		if(pathno != -1)
	        		{
	        			break;
	        		}

	        		arccounter += edges.size();
	            }

	        	if(pathno == -1)
	            {
	            	System.out.println("Error: could not find path");
	            	System.exit(0);
	            }

	            Path path = Executer.paths.get(pathno);
	            String route = "Routes:\n";
	            String edge;

	            for(int i=0;i<path.getEdges().size();i++)
	            {
	            	edge = path.getEdges().get(i).getNode1().getNumber()+"-"+path.getEdges().get(i).getNode2().getNumber()+"\n";
	            	route = route + edge;
	            }
	            route = route + "Costs: "+path.getCosts();

	            gc.setFont(new Font(Executer.defaultHeight/20));
	            gc.fillText("Path",60,60);
	            gc.setFont(new Font(Executer.defaultHeight/40));
	            gc.fillText(route,10,100);

	            edges = Executer.paths.get(pathno).getEdges();

	            for(int i=0;i<edges.size();i++)
	            {
	            	arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+i)));
	            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/100);
	            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+i)+1));
	            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	            }
	        }
		};

		return(mouseOnArrowEnter);
	}

	public static EventHandler<MouseEvent> MouseOnArrowExit(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseOnArrowExit = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	ArrayList<Edge> edges;
	        	int noOfNodes = Executer.nodes.size();
	        	int noOfPaths = Executer.paths.size();
	        	int arccounter = 0;
	            double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
	        	Arc arc;
	        	Polygon polygon;

	        	gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

	        	for(int i=0;i<noOfPaths;i++)
	        	{
	        		edges = Executer.paths.get(i).getEdges();
	        		for(int j=0;j<edges.size();j++)
	        		{
	        			arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
	        			arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
	        	       	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
		            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
	        		}

	        		arccounter += edges.size();
	        	}
	        }
		};

		return(mouseOnArrowExit);
	}
	
	public static EventHandler<MouseEvent> MouseclickOnArrow(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseclickOnPolygon = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc;
	        	Path path;
	        	Polygon polygon = (Polygon) t.getSource();
	        	Circle circle;
	        	ArrayList<Edge> edges;
	        	boolean clicked = false;
	        	
	            int pathno = -1;
	            int noOfNodes = Executer.nodes.size();
	            int noOfPaths = Executer.paths.size();
	            int arccounter = 0;
	            double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
	            String route = "Routes:\n";
	            String edge;
	            double distance = Math.sqrt(Math.pow(polygon.getPoints().get(2)-polygon.getPoints().get(4), 2)
	            		+Math.pow(polygon.getPoints().get(3)-polygon.getPoints().get(5), 2));

	        	if(Math.abs(distance - 4*arrowwidth) < 0.01)
	        	{
	        		clicked = true;
	        	}
	        	
	        	for(int i=0;i<noOfPaths;i++)
	        	{
	        		edges = Executer.paths.get(i).getEdges();
	        		for(int j=0;j<edges.size();j++)
	        		{
		            	if(polygon.equals(Executer.arrowpolygons[arccounter+j]))
		            	{
		            		pathno = i;
		            		break;
		            	}
	        		}

	        		if(pathno != -1)
	        		{
	        			break;
	        		}

	        		arccounter += edges.size();
	            }

	        	if(pathno == -1)
	            {
	            	System.out.println("Error: could not find path");
	            	System.exit(0);
	            }

	            path = Executer.paths.get(pathno);

	            for(int i=0;i<path.getEdges().size();i++)
	            {
	            	edge = path.getEdges().get(i).getNode1().getNumber()+"-"+path.getEdges().get(i).getNode2().getNumber()+"\n";
	            	route = route + edge;
	            }
	            route = route + "Costs: "+path.getCosts();	        	

	        	if(clicked&clickedstate)
	        	{
	        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
	        				        	
		        	edges = Executer.paths.get(pathno).getEdges();
		        	
		            for(int i=0;i<edges.size();i++)
		            {
		            	arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+i)));
		            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
		            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+i)+1));
		            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            }
		        	
	        		for(int i=0;i<noOfNodes;i++)
	        		{
	        			circle = (Circle) (pane.getChildren().get(i));
	    	        	circle.setOnMouseEntered(MouseOnCircleEnter(gc,pane));
	    	        	circle.setOnMouseExited(MouseOnCircleExit(gc,pane));
	        		}
	        		
	        		arccounter = 0;
	        		for(int i=0;i<noOfPaths;i++)
	        		{
	        			edges = Executer.paths.get(i).getEdges();
	        			for(int j=0;j<edges.size();j++)
	        			{
	        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
		    	        	arc.setOnMouseEntered(MouseOnEdgeEnter(gc,pane));
		    	        	arc.setOnMouseExited(MouseOnEdgeExit(gc,pane));
		    	        	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
		    	        	polygon.setOnMouseEntered(MouseOnArrowEnter(gc,pane));
		    	        	polygon.setOnMouseExited(MouseOnArrowExit(gc,pane));
	        			}

	        			arccounter += edges.size();
	        		}
		        	
		        	clickedstate = false;
	        	}
	        	else
	        	{
		        	if(clickedstate)
		        	{
		        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		        		      		
		        		for(int i=0;i<noOfNodes;i++)
		        		{
		        			circle = (Circle) (pane.getChildren().get(i));
		    	        	circle.setRadius(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/120);
		    	        	circle.setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/200);
		    	        }
		        		
		        		arccounter = 0;
		        		for(int i=0;i<noOfPaths;i++)
		        		{
		        			edges = Executer.paths.get(i).getEdges();
		        			if(i==pathno)
		        			{
			        			for(int j=0;j<edges.size();j++)
			        			{
			        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
					            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/100);
					            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
					            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
			        			}
		        			}
		        			else
		        			{
			        			for(int j=0;j<edges.size();j++)
			        			{
			        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
					            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
					            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
					            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
			        			}
		        			}

		        			arccounter += edges.size();
		        		}
		        	}
		        	else
		        	{
		        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		        		
		        		for(int i=0;i<noOfNodes;i++)
		        		{
		        			circle = (Circle) (pane.getChildren().get(i));
		    	        	circle.setOnMouseEntered(null);
		    	        	circle.setOnMouseExited(null);
		    	        }
		        		
		        		arccounter = 0;
		        		for(int i=0;i<noOfPaths;i++)
		        		{
		        			edges = Executer.paths.get(i).getEdges();
		        			for(int j=0;j<edges.size();j++)
		        			{
		        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
			    	        	arc.setOnMouseEntered(null);
			    	        	arc.setOnMouseExited(null);
			    	        	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
			    	        	polygon.setOnMouseEntered(null);
			    	        	polygon.setOnMouseExited(null);
		        			}

		        			arccounter += edges.size();
		        		}
		        		
		        		clickedstate = true;
		        	}

		            gc.setFont(new Font(Executer.defaultHeight/20));
		            gc.fillText("Path",60,60);
		            gc.setFont(new Font(Executer.defaultHeight/40));
		            gc.fillText(route,10,100);
	        	}
	        }
		};

		return(mouseclickOnPolygon);
	}
	
	public static EventHandler<MouseEvent> MouseclickOnEdge(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseclickOnArc = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Arc arc = (Arc) t.getSource();
	        	Path path;
	        	Polygon polygon;
	        	Circle circle;
	        	ArrayList<Edge> edges;
	        	boolean clicked = false;
	        	
	            int pathno = -1;
	            int noOfNodes = Executer.nodes.size();
	            int noOfPaths = Executer.paths.size();
	            int arccounter = 0;
	            double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
	            String route = "Routes:\n";
	            String edge;

	        	if(arc.getStrokeWidth()==(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/100))
	        	{
	        		clicked = true;
	        	}
	        	
	        	for(int i=0;i<noOfPaths;i++)
	        	{
	        		edges = Executer.paths.get(i).getEdges();
	        		for(int j=0;j<edges.size();j++)
	        		{
		            	if(arc.equals(Executer.edgearcs[arccounter+j]))
		            	{
		            		pathno = i;
		            		break;
		            	}
	        		}

	        		if(pathno != -1)
	        		{
	        			break;
	        		}

	        		arccounter += edges.size();
	            }

	        	if(pathno == -1)
	            {
	            	System.out.println("Error: could not find path");
	            	System.exit(0);
	            }

	            path = Executer.paths.get(pathno);

	            for(int i=0;i<path.getEdges().size();i++)
	            {
	            	edge = path.getEdges().get(i).getNode1().getNumber()+"-"+path.getEdges().get(i).getNode2().getNumber()+"\n";
	            	route = route + edge;
	            }
	            route = route + "Costs: "+path.getCosts();	        	

	        	if(clicked&clickedstate)
	        	{
	        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
	        				        	
		        	edges = Executer.paths.get(pathno).getEdges();
		        	
		            for(int i=0;i<edges.size();i++)
		            {
		            	arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+i)));
		            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
		            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+i)+1));
		            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
		            }
		        	
	        		for(int i=0;i<noOfNodes;i++)
	        		{
	        			circle = (Circle) (pane.getChildren().get(i));
	    	        	circle.setOnMouseEntered(MouseOnCircleEnter(gc,pane));
	    	        	circle.setOnMouseExited(MouseOnCircleExit(gc,pane));
	        		}
	        		
	        		arccounter = 0;
	        		for(int i=0;i<noOfPaths;i++)
	        		{
	        			edges = Executer.paths.get(i).getEdges();
	        			for(int j=0;j<edges.size();j++)
	        			{
	        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
		    	        	arc.setOnMouseEntered(MouseOnEdgeEnter(gc,pane));
		    	        	arc.setOnMouseExited(MouseOnEdgeExit(gc,pane));
		    	        	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
		    	        	polygon.setOnMouseEntered(MouseOnArrowEnter(gc,pane));
		    	        	polygon.setOnMouseExited(MouseOnArrowExit(gc,pane));
	        			}

	        			arccounter += edges.size();
	        		}
		        	
		        	clickedstate = false;
	        	}
	        	else
	        	{
		        	if(clickedstate)
		        	{
		        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		        		      		
		        		for(int i=0;i<noOfNodes;i++)
		        		{
		        			circle = (Circle) (pane.getChildren().get(i));
		    	        	circle.setRadius(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/120);
		    	        	circle.setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/200);
		    	        }
		        		
		        		arccounter = 0;
		        		for(int i=0;i<noOfPaths;i++)
		        		{
		        			edges = Executer.paths.get(i).getEdges();
		        			if(i==pathno)
		        			{
			        			for(int j=0;j<edges.size();j++)
			        			{
			        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
					            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/100);
					            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
					            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-2*arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-2*arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
			        			}
		        			}
		        			else
		        			{
			        			for(int j=0;j<edges.size();j++)
			        			{
			        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
					            	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
					            	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
					            	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					            	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
			        			}
		        			}

		        			arccounter += edges.size();
		        		}
		        	}
		        	else
		        	{
		        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		        		
		        		for(int i=0;i<noOfNodes;i++)
		        		{
		        			circle = (Circle) (pane.getChildren().get(i));
		    	        	circle.setOnMouseEntered(null);
		    	        	circle.setOnMouseExited(null);
		    	        }
		        		
		        		arccounter = 0;
		        		for(int i=0;i<noOfPaths;i++)
		        		{
		        			edges = Executer.paths.get(i).getEdges();
		        			for(int j=0;j<edges.size();j++)
		        			{
		        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
			    	        	arc.setOnMouseEntered(null);
			    	        	arc.setOnMouseExited(null);
			    	        	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
			    	        	polygon.setOnMouseEntered(null);
			    	        	polygon.setOnMouseExited(null);
		        			}

		        			arccounter += edges.size();
		        		}
		        		
		        		clickedstate = true;
		        	}

		            gc.setFont(new Font(Executer.defaultHeight/20));
		            gc.fillText("Path",60,60);
		            gc.setFont(new Font(Executer.defaultHeight/40));
		            gc.fillText(route,10,100);
	        	}
	        }
		};

		return(mouseclickOnArc);
	}
	
	public static EventHandler<MouseEvent> MouseclickOnCircle(GraphicsContext gc,Pane pane){
		EventHandler<MouseEvent> mouseclickOnCircle = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent t)
	        {
	        	Circle circle = (Circle) t.getSource();
	        	Arc arc;
	        	Polygon polygon;
	        	Node node;
	        	ArrayList<Edge> edges;
	        	String depot;
	        	boolean clicked = false;

	        	double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
	        	int nodeno = -1;
	        	int noOfNodes = Executer.nodes.size();
	        	int noOfPaths = Executer.paths.size();
	        	int arccounter = 0;
	        	
	        	if(circle.getRadius()==(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/60))
	        	{
	        		clicked = true;
	        	}

	        	for(int i=0;i<noOfNodes;i++)
	        	{
	        		if(Executer.nodecircles[i].equals(circle))
	        		{
	        			nodeno = i;
	        			break;
	        		}
	        	}

	        	if(nodeno == -1)
	        	{
	        		System.out.println("Error: could not find node");
	        		System.exit(0);
	        	}

	        	node = Executer.nodes.get(nodeno);
	        	
	        	if(clicked&clickedstate)
	        	{
	        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
	        		
		        	circle.setRadius(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/120);
		        	circle.setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/200);
		        	
	        		for(int i=0;i<noOfNodes;i++)
	        		{
	        			circle = (Circle) (pane.getChildren().get(i));
	    	        	circle.setOnMouseEntered(MouseOnCircleEnter(gc,pane));
	    	        	circle.setOnMouseExited(MouseOnCircleExit(gc,pane));
	        		}
	        		
	        		for(int i=0;i<noOfPaths;i++)
	        		{
	        			edges = Executer.paths.get(i).getEdges();
	        			for(int j=0;j<edges.size();j++)
	        			{
	        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
		    	        	arc.setOnMouseEntered(MouseOnEdgeEnter(gc,pane));
		    	        	arc.setOnMouseExited(MouseOnEdgeExit(gc,pane));
		    	        	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
		    	        	polygon.setOnMouseEntered(MouseOnArrowEnter(gc,pane));
		    	        	polygon.setOnMouseExited(MouseOnArrowExit(gc,pane));
	        			}

	        			arccounter += edges.size();
	        		}
	        		
		        	clickedstate = false;
	        	}
	        	else
	        	{
		        	if(node.isDepot())
		        	{
		        		depot = "yes";
		        	}
		        	else
		        	{
		        		depot = "no";
		        	}
		        	
		        	if(clickedstate)
		        	{
		        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		        		
		        		for(int i=0;i<noOfNodes;i++)
		        		{
		        			circle = (Circle) (pane.getChildren().get(i));
		    	        	circle.setRadius(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/120);
		    	        	circle.setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/200);
		        		}
		        		
		        		arccounter=0;
		        		for(int i=0;i<noOfPaths;i++)
		        		{
		        			edges = Executer.paths.get(i).getEdges();
		        			for(int j=0;j<edges.size();j++)
			        		{
			        			arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
					           	arc.setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
					           	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
					           	polygon.getPoints().set(2,arc.getCenterX()+(arc.getRadiusX()+arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					           	polygon.getPoints().set(3,arc.getCenterY()-(arc.getRadiusX()+arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					           	polygon.getPoints().set(4,arc.getCenterX()+(arc.getRadiusX()-arrowwidth)*Math.cos(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
					           	polygon.getPoints().set(5,arc.getCenterY()-(arc.getRadiusX()-arrowwidth)*Math.sin(Math.toRadians(arc.getStartAngle()+0.67*arc.getLength())));
			        		}

		        			arccounter += edges.size();
		        		}
		        		
		        		circle = (Circle) (pane.getChildren().get(nodeno));
			        	circle.setRadius(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/60);
			        	circle.setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/100);
		        	}
		        	else
		        	{
		        		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		        		
		        		for(int i=0;i<noOfNodes;i++)
		        		{
		    	        	Executer.nodecircles[i].setOnMouseEntered(null);
		    	        	Executer.nodecircles[i].setOnMouseExited(null);
		        		}
		        		
		        		arccounter = 0;
		        		for(int i=0;i<noOfPaths;i++)
		        		{
		        			edges = Executer.paths.get(i).getEdges();
		        			for(int j=0;j<edges.size();j++)
		        			{
		        				arc = (Arc) (pane.getChildren().get(noOfNodes+2*(arccounter+j)));
			    	        	arc.setOnMouseEntered(null);
			    	        	arc.setOnMouseExited(null);
			    	        	polygon = (Polygon) (pane.getChildren().get(noOfNodes+2*(arccounter+j)+1));
			    	        	polygon.setOnMouseEntered(null);
			    	        	polygon.setOnMouseExited(null);
		        			}

		        			arccounter += edges.size();
		        		}
		        		
		        		clickedstate = true;
		        	}

		        	gc.setFont(new Font(Executer.defaultHeight/20));
		        	gc.fillText("Node",60,60);
		        	gc.setFont(new Font(Executer.defaultHeight/40));
		        	gc.fillText("No: "+node.getNumber()+"\nDepot: "+depot+"\nCoordinates:\n("
		        	+node.getRealXcoordinate()+","+node.getRealYcoordinate()+")",10,100);
	        	}
	        }
		};

		return(mouseclickOnCircle);
	}
}
