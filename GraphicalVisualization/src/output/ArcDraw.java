package output;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import model.Edge;
import model.Executer;
import model.Path;

public class ArcDraw
{

	//method that draws circle arcs between nodes
	public static int drawArcs(Canvas textcanvas,Pane pane,int count,Path path)
	{
		ArrayList<Edge> edges = path.getEdges();
		int[] color = path.getColor();
		int NoOfEdges = edges.size();
		double[][] node1coordinates = new double[2][NoOfEdges];
		double[][] node2coordinates = new double[2][NoOfEdges];

		for(int i=0;i<NoOfEdges;i++)
        {
        	node1coordinates[0][i] = edges.get(i).getNode1().getVirtualXcoordinate();
        	node2coordinates[0][i] = edges.get(i).getNode2().getVirtualXcoordinate();
        	node1coordinates[1][i] = edges.get(i).getNode1().getVirtualYcoordinate();
        	node2coordinates[1][i] = edges.get(i).getNode2().getVirtualYcoordinate();
        }

        double tempx1;
        double tempy1;
        double tempx2;
        double tempy2;

        for(int i=0;i<NoOfEdges;i++)
        {
        	tempx1 = node1coordinates[0][i];
        	tempy1 = node1coordinates[1][i];
        	tempx2 = node2coordinates[0][i];
        	tempy2 = node2coordinates[1][i];

        	if(tempy1 >= tempy2)
        	{
        		if(tempx1 <= tempx2)
        		{
        			drawCircleArc(textcanvas,pane,count+i,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),0);
        		}
        		else//tempy1 >= tempy2 and tempx1 > tempx2
        		{
        			drawCircleArc(textcanvas,pane,count+i,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),1);
        		}
        	}
        	else
        	{
            	if(tempy1 < tempy2)
            	{
            		if(tempx1 > tempx2)
            		{
            			drawCircleArc(textcanvas,pane,count+i,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),2);
            		}
            		else//tempy1 < tempy2 and tempx1 <= tempx2
            		{
            			drawCircleArc(textcanvas,pane,count+i,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),3);
            		}
            	}
        	}
        }

        return(NoOfEdges);
	}

	private static void drawCircleArc(Canvas textcanvas,Pane pane,int count,int[] color,double x1,double x2,double y1,double y2,int ordering,int totalcopies,int mode)
	{
		//parameters of the circle arc
		double angleCircleArc = (30 *(totalcopies+1-ordering)) / (totalcopies+1);
		double nodesDistance = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		double circleRadius;
		double circleX;
		double circleY;
		double angleHorAxis_Radius;

		//arrow parameters
		double[]arrowcoordinates = new double[6];
		double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
        GraphicsContext gc = textcanvas.getGraphicsContext2D();

		circleRadius = 0.5 * nodesDistance / Math.cos(Math.toRadians(90-angleCircleArc));

		switch(mode){
		case 0:
		{
			if(x2==x1)
			{
				angleHorAxis_Radius = 180 - angleCircleArc;
			}
			else
			{
				angleHorAxis_Radius = 90 - angleCircleArc + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			}
			break;
		}
		case 1:
		{
			angleHorAxis_Radius = 270 - angleCircleArc + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			break;
		}
		case 2:
		{
			angleHorAxis_Radius = 270 - angleCircleArc + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			break;
		}
		case 3:
		{
			if(x2==x1)
			{
				angleHorAxis_Radius = - angleCircleArc;
			}
			else
			{
				angleHorAxis_Radius = 90 - angleCircleArc + Math.toDegrees(Math.atan((y1-y2)/(x2-x1)));
			}
			break;
		}
		default:
		{
			angleHorAxis_Radius = 0;
			System.out.println("Warning(Arc): default case reached");
		}
		}

		circleX = x1 + circleRadius * Math.cos(Math.toRadians(angleHorAxis_Radius));
		circleY = y1 + circleRadius * -Math.sin(Math.toRadians(angleHorAxis_Radius));

		Executer.edgearcs[count] = new Arc(circleX, circleY, circleRadius, circleRadius,angleHorAxis_Radius-180,2*angleCircleArc);
		Executer.edgearcs[count].setType(ArcType.OPEN);
		Executer.edgearcs[count].setFill(null);
		Executer.edgearcs[count].setStroke(Color.rgb(color[0],color[1],color[2]));
		Executer.edgearcs[count].setStrokeWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);
		Executer.edgearcs[count].setOnMouseEntered(Mouse.MouseOnEdgeEnter(gc, pane));
		Executer.edgearcs[count].setOnMouseExited(Mouse.MouseOnEdgeExit(gc, pane));
		Executer.edgearcs[count].setOnMouseClicked(Mouse.MouseclickOnEdge(gc, pane));

		pane.getChildren().add(Executer.edgearcs[count]);

		arrowcoordinates[0] = circleX+circleRadius*Math.cos(Math.toRadians(angleHorAxis_Radius-180+0.7*2*angleCircleArc));
		arrowcoordinates[1] = circleY-circleRadius*Math.sin(Math.toRadians(angleHorAxis_Radius-180+0.7*2*angleCircleArc));
		arrowcoordinates[2] = circleX+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		arrowcoordinates[3] = circleY-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		arrowcoordinates[4] = circleX+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		arrowcoordinates[5] = circleY-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		//gc.fillPolygon(arrowX,arrowY,3);

		Executer.arrowpolygons[count] = new Polygon(arrowcoordinates);
		Executer.arrowpolygons[count].setFill(Color.rgb(color[0],color[1],color[2]));
		Executer.arrowpolygons[count].setOnMouseEntered(Mouse.MouseOnArrowEnter(gc, pane));
		Executer.arrowpolygons[count].setOnMouseExited(Mouse.MouseOnArrowExit(gc, pane));
		Executer.arrowpolygons[count].setOnMouseClicked(Mouse.MouseclickOnArrow(gc, pane));
		
		pane.getChildren().add(Executer.arrowpolygons[count]);
	}
}
