package output;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import model.Executer;
import model.Node;
import model.Path;

public class ArcDraw
{

	//method that draws circle arcs between nodes
	public static int[][] drawArcs(Path path,int screenNo,int[][] edgeCount)
	{
		ArrayList<Node[]> nodes = path.getNodes();
		int pathindex = Executer.paths.indexOf(path);
		int[] color = Executer.pathColors[pathindex];
		int NoOfEdges = nodes.size();
		double[][] coordinates = Executer.normalizedCoordinates;
		double[] coordinates1 = new double[2];
		double[] coordinates2 = new double[2];
		int node1;
		int node2;

		ArrayList<Line> lines = new ArrayList<Line>(0);
		ArrayList<Polygon> linepolygons = new ArrayList<Polygon>(0);
		ArrayList<Arc> arcs = new ArrayList<Arc>(0);
		ArrayList<Polygon> arcpolygons = new ArrayList<Polygon>(0);

        double[] measures = WindowContent.getScreenMeasures();
        double initX = measures[0] * (screenNo%WindowContent.noOfScreensX);
        double initY = measures[1] * (screenNo/WindowContent.noOfScreensX);



        for(int i=0;i<NoOfEdges;i++)
        {
        	node1 = nodes.get(i)[0].getNumber();
        	node2 = nodes.get(i)[1].getNumber();

        	coordinates1[0] = initX + measures[0] * coordinates[0][node1];
        	coordinates1[1] = initY + measures[1] * coordinates[1][node1];
        	coordinates2[0] = initX + measures[0] * coordinates[0][node2];
        	coordinates2[1] = initY + measures[1] * coordinates[1][node2];

        	if(edgeCount[node1][node2] == 0 & edgeCount[node2][node1] == 0)
        	{
        	 	lines.add(createStraightArc(color,coordinates1[0],coordinates2[0],
        	 			coordinates1[1],coordinates2[1]));
	        	linepolygons.add(createLinePolygon(color,coordinates1[0],coordinates2[0],
        	 			coordinates1[1],coordinates2[1]));
	        	edgeCount[node1][node2] ++;
        	}
        	else
        	{
        		if(coordinates1[1] >= coordinates2[1])
	        	{
	        		if(coordinates1[0] <= coordinates2[0])
	        		{
	        			arcs.add(createCircleArc(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
	            	 			coordinates1[1],coordinates2[1],0));
	        			arcpolygons.add(createArcPolygon(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
	            	 			coordinates1[1],coordinates2[1],0));
	        			edgeCount[node1][node2] ++;
	        		}
	        		else
	        		{
	        			arcs.add(createCircleArc(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
	            	 			coordinates1[1],coordinates2[1],1));
	        			arcpolygons.add(createArcPolygon(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
	            	 			coordinates1[1],coordinates2[1],1));
	        			edgeCount[node1][node2] ++;
	        		}
	        	}
	        	else
	        	{
	        		if(coordinates1[1] < coordinates2[1])
		        	{
		        		if(coordinates1[0] > coordinates2[0])
		        		{
	            			arcs.add(createCircleArc(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
		            	 			coordinates1[1],coordinates2[1],2));
	            			arcpolygons.add(createArcPolygon(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
		            	 			coordinates1[1],coordinates2[1],2));
	            			edgeCount[node1][node2] ++;
	            		}
	            		else
	            		{
	            			arcs.add(createCircleArc(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
		            	 			coordinates1[1],coordinates2[1],3));
	            			arcpolygons.add(createArcPolygon(edgeCount[node1][node2],color,coordinates1[0],coordinates2[0],
		            	 			coordinates1[1],coordinates2[1],3));
	            			edgeCount[node1][node2] ++;
	            		}
	            	}
	        	}
        	}
        }

        incorporateShapes(pathindex,lines,arcs,linepolygons,arcpolygons);

        return edgeCount;
	}

	private static Line createStraightArc(int[] color,double x1,double x2,double y1,double y2)
	{
		Pane drawPane = Executer.drawPane;
		Line line = new Line();

		line.setStartX(x1);
		line.setEndX(x2);
		line.setStartY(y1);
		line.setEndY(y2);
		line.setFill(null);
		line.setStroke(Color.rgb(color[0],color[1],color[2]));
		line.setStrokeWidth(WindowContent.defaultArcWidth);
		line.setOnMouseEntered(Mouse.MouseOnLineEnter(drawPane));
		line.setOnMouseExited(Mouse.MouseOnEdgeExit(drawPane));
		line.setOnMouseClicked(Mouse.MouseclickOnLine(drawPane));

		return line;
	}

	private static Polygon createLinePolygon(int[] color,double x1,double x2,double y1,double y2)
	{
		Pane drawPane = Executer.drawPane;
		Polygon polygon;
		double[] arrowcoordinates = new double[6];
		double arrowangle = Math.toRadians(25);
		double alpha;
		double distance;
		double[] d = new double[2];
		double l;


		arrowcoordinates[0] = x1+0.7*(x2-x1);
		arrowcoordinates[1] = y1+0.7*(y2-y1);

		if(x1 < x2)
		{
			alpha = Math.atan((y1-y2)/(x2-x1));
		}
		else if (x1==x2)
		{
			if(y1 > y2)
			{
				alpha = Math.toRadians(90);
			}
			else
			{
				alpha = -Math.toRadians(90);
			}
		}
		else
		{
			alpha = Math.toRadians(180) + Math.atan((y1-y2)/(x2-x1));
		}

		distance = 0.03*Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		d[0] = arrowcoordinates[0] - distance * Math.cos(-alpha);
		d[1] = arrowcoordinates[1] - distance * Math.sin(-alpha);
		l = distance * Math.tan(arrowangle);

		arrowcoordinates[2] = d[0] + l * Math.cos(Math.toRadians(90)-alpha);
		arrowcoordinates[3] = d[1] + l * Math.sin(Math.toRadians(90)-alpha);
		arrowcoordinates[4] = d[0] - l * Math.cos(Math.toRadians(90)-alpha);
		arrowcoordinates[5] = d[1] - l * Math.sin(Math.toRadians(90)-alpha);

		polygon = new Polygon(arrowcoordinates);
		polygon.setFill(Color.rgb(color[0],color[1],color[2]));
		polygon.setOnMouseEntered(Mouse.MouseOnArrowEnter(drawPane));
		polygon.setOnMouseExited(Mouse.MouseOnEdgeExit(drawPane));
		polygon.setOnMouseClicked(Mouse.MouseclickOnArrow(drawPane));

		return polygon;
	}

	private static Arc createCircleArc(int count,int[] color,double x1,double x2,double y1,double y2,int mode)
	{
		Pane drawPane = Executer.drawPane;
		Arc arc;
		double[] arcpars = getCircleArcParameters(count,x1,x2,y1,y2,mode);

		arc = new Arc(arcpars[4],arcpars[5],arcpars[2],arcpars[2],arcpars[3]-180,2*arcpars[0]);
		arc.setType(ArcType.OPEN);
		arc.setFill(null);
		arc.setStroke(Color.rgb(color[0],color[1],color[2]));
		arc.setStrokeWidth(WindowContent.defaultArcWidth);
		arc.setOnMouseEntered(Mouse.MouseOnEdgeEnter(drawPane));
		arc.setOnMouseExited(Mouse.MouseOnEdgeExit(drawPane));
		arc.setOnMouseClicked(Mouse.MouseclickOnEdge(drawPane));

		return arc;
	}

	private static Polygon createArcPolygon(int count,int[] color,double x1,double x2,double y1,double y2,int mode)
	{
		Pane drawPane = Executer.drawPane;
		Polygon polygon;
		double[] arcpars = getCircleArcParameters(count,x1,x2,y1,y2,mode);

		//arrow parameters
		double[]arrowcoordinates = new double[6];
		double arrowwidth = WindowContent.defaultArrowWidth;

		arrowcoordinates[0] = arcpars[4]+arcpars[2]*Math.cos(Math.toRadians(arcpars[3]-180+0.7*2*arcpars[0]));
		arrowcoordinates[1] = arcpars[5]-arcpars[2]*Math.sin(Math.toRadians(arcpars[3]-180+0.7*2*arcpars[0]));
		arrowcoordinates[2] = arcpars[4]+(arcpars[2]+arrowwidth)*Math.cos(Math.toRadians(arcpars[3]-180+0.67*2*arcpars[0]));
		arrowcoordinates[3] = arcpars[5]-(arcpars[2]+arrowwidth)*Math.sin(Math.toRadians(arcpars[3]-180+0.67*2*arcpars[0]));
		arrowcoordinates[4] = arcpars[4]+(arcpars[2]-arrowwidth)*Math.cos(Math.toRadians(arcpars[3]-180+0.67*2*arcpars[0]));
		arrowcoordinates[5] = arcpars[5]-(arcpars[2]-arrowwidth)*Math.sin(Math.toRadians(arcpars[3]-180+0.67*2*arcpars[0]));

		polygon = new Polygon(arrowcoordinates);
		polygon.setFill(Color.rgb(color[0],color[1],color[2]));
		polygon.setOnMouseEntered(Mouse.MouseOnArrowEnter(drawPane));
		polygon.setOnMouseExited(Mouse.MouseOnEdgeExit(drawPane));
		polygon.setOnMouseClicked(Mouse.MouseclickOnArrow(drawPane));

		return polygon;
	}

	private static double[] getCircleArcParameters(int count,double x1,double x2,double y1,double y2,int mode)
	{
		int order;
		int dif;

		//parameters of the circle arc
		double[] output = new double[6];

		if(count == 0)
		{
			order = -1;
			dif = 0;
			output[0] = 30;
		}
		else
		{
			order = (int) (Math.log(count+0.9)/Math.log(2));
			dif = count - (int) (Math.pow(2,order));
			output[0] = (Math.pow(2,order + 1)-2*dif-1)*30/(Math.pow(2,order + 1));
		}

		output[1] = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		output[2] = 0.5 * output[1] / Math.cos(Math.toRadians(90-output[0]));

		switch(mode){
		case 0:
		{
			if(x2==x1)
			{
				output[3] = 180 - output[0];
			}
			else
			{
				output[3] = 90 - output[0] + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			}
			break;
		}
		case 1:
		{
			output[3] = 270 - output[0] + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			break;
		}
		case 2:
		{
			output[3] = 270 - output[0] + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			break;
		}
		case 3:
		{
			if(x2==x1)
			{
				output[3] = - output[0];
			}
			else
			{
				output[3] = 90 - output[0] + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			}
			break;
		}
		default:
		{
			output[3] = 0;
			System.out.println("Warning(Arc): default case reached");
		}
		}

		output[4] = x1 + output[2] * Math.cos(Math.toRadians(output[3]));
		output[5] = y1 + output[2] * -Math.sin(Math.toRadians(output[3]));

		return output;
	}

	private static void incorporateShapes(int pathindex,ArrayList<Line> lines,ArrayList<Arc> arcs,ArrayList<Polygon> linepolygons,ArrayList<Polygon> arcpolygons)
	{
		int noOfLines = lines.size();
		int noOfArcs = arcs.size();
		Pane drawPane = Executer.drawPane;

		Executer.edgelines[pathindex] = new Line[noOfLines];
		Executer.edgearcs[pathindex] = new Arc[noOfArcs];
		Executer.linearrowpolygons[pathindex] = new Polygon[noOfLines];
		Executer.arrowpolygons[pathindex] = new Polygon[noOfArcs];

		for(int i=0;i<noOfLines;i++)
		{
			Executer.edgelines[pathindex][i] = lines.get(i);
			Executer.linearrowpolygons[pathindex][i] = linepolygons.get(i);
			drawPane.getChildren().addAll(Executer.edgelines[pathindex][i],Executer.linearrowpolygons[pathindex][i]);
		}

		for(int i=0;i<noOfArcs;i++)
		{
			Executer.edgearcs[pathindex][i] = arcs.get(i);
			Executer.arrowpolygons[pathindex][i] = arcpolygons.get(i);
			drawPane.getChildren().addAll(Executer.edgearcs[pathindex][i],Executer.arrowpolygons[pathindex][i]);
		}
	}
}
