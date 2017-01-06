package output;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import model.Edge;
import model.Executer;

public class Arc
{

	//method that draws circle arcs between nodes
	public static void drawArcs(GraphicsContext gc,int[] color,ArrayList<Edge> edges)
	{
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
        			drawCircleArc(gc,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),0);
        		}
        		else//tempy1 >= tempy2 and tempx1 > tempx2
        		{
        			drawCircleArc(gc,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),1);
        		}
        	}
        	else
        	{
            	if(tempy1 < tempy2)
            	{
            		if(tempx1 > tempx2)
            		{
            			drawCircleArc(gc,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),2);
            		}
            		else//tempy1 < tempy2 and tempx1 <= tempx2
            		{
            			drawCircleArc(gc,color,tempx1,tempx2,tempy1,tempy2,edges.get(i).getVirtualordering(),edges.get(i).getTotalcopies(),3);
            		}
            	}
        	}
        }
	}

	private static void drawCircleArc(GraphicsContext gc,int[] color,double x1,double x2,double y1,double y2,int ordering,int totalcopies,int mode)
	{
		//parameters of the circle arc
		double nodemiddle = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
		double angleCircleArc = (30 *(totalcopies+1-ordering)) / (totalcopies+1);
		double nodesDistance = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		double circleRadius;
		double circleX;
		double circleY;
		double angleHorAxis_Radius;

		//arrow parameters
		double[]arrowX = new double[3];
		double[]arrowY = new double[3];
		double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;



		gc.setFill(Color.rgb(color[0],color[1],color[2]));
        gc.setStroke(Color.rgb(color[0],color[1],color[2]));
        gc.setLineWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);

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

		circleX = x1 + circleRadius * (Math.cos(Math.toRadians(angleHorAxis_Radius)) - 1);
		circleY = y1 + circleRadius * (-Math.sin(Math.toRadians(angleHorAxis_Radius)) - 1);

		gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,angleHorAxis_Radius-180,2*angleCircleArc,ArcType.OPEN);

		arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(angleHorAxis_Radius-180+0.7*2*angleCircleArc)));
		arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(angleHorAxis_Radius-180+0.7*2*angleCircleArc)));
		arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(angleHorAxis_Radius-180+0.67*2*angleCircleArc));
		gc.fillPolygon(arrowX,arrowY,3);
	}
}
