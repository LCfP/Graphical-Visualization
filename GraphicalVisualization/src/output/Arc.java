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
	public static void drawArcs(GraphicsContext gc, ArrayList<Edge> edges, ArrayList<Integer> copies)
	{
		int NoOfEdges = edges.size();
		double[][] coordinates = new double[2][2*NoOfEdges];

		for(int i=0;i<NoOfEdges;i++)
        {
        	coordinates[0][2*i]=edges.get(i).getNode1().getXcoordinate();
        	coordinates[0][2*i+1]=edges.get(i).getNode2().getXcoordinate();
        	coordinates[1][2*i]=edges.get(i).getNode1().getYcoordinate();
        	coordinates[1][2*i+1]=edges.get(i).getNode2().getYcoordinate();
        }

        double[][] adjustedcoordinates = Coordinates.transformCoordinates(coordinates);

		//adjusted coordinates of the nodes
        double tempx1;
        double tempy1;
        double tempx2;
        double tempy2;

        for(int i=0;i<NoOfEdges;i++)
        {
        	tempx1 = adjustedcoordinates[0][2*i];
        	tempy1 = adjustedcoordinates[1][2*i];
        	tempx2 = adjustedcoordinates[0][2*i+1];
        	tempy2 = adjustedcoordinates[1][2*i+1];

        	if(tempx1 > tempx2)
        	{
        		if(tempy1 > tempy2)
        		{
        			if ((tempy1 - tempy2) > (tempx1 - tempx2))
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),0);
        			}
        			else
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),1);
        			}
        		}
        		else//tempy1 < tempy2 and tempx1 > tempx2
        		{
        			if ((tempy2 - tempy1) > (tempx1 - tempx2))
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),2);
        			}
        			else
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),3);
        			}
        		}
        	}
        	else//tempx1 < tempx2
        	{
        		if(tempy1 > tempy2)
        		{
        			if ((tempy1 - tempy2) > (tempx2 - tempx1))
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),4);
        			}
        			else
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),5);
        			}
        		}
        		else//tempy1 < tempy2 and tempx1 < tempx2
        		{
        			if ((tempy2 - tempy1) > (tempx2 - tempx1))
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),6);
        			}
        			else
        			{
        				draw(gc,tempx1,tempx2,tempy1,tempy2,copies.get(i),7);
        			}
        		}
        	}
        }
	}

	private static void draw(GraphicsContext gc,double x1,double x2,double y1,double y2,int copies,int mode)
	{
		//parameters of the circle arc
		double circleRadius;
		double circleAngle;
		double circleX;
		double circleY;

		//Additional angles needed
		double alpha;
		double beta;
		
		double[]arrowX = new double[3];
		double[]arrowY = new double[3];
		double arrowwidth = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;
		double nodemiddle = Math.min(Executer.defaultHeight,Executer.defaultWidth)/120;

		gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/400);

		switch(mode){
		case 0:
		{
			alpha = Math.toDegrees(Math.atan((y1 - y2) / (x1 - x2)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y2 - y1)/(Math.sin(Math.toRadians(beta))-Math.sin(Math.toRadians(beta+circleAngle)));
    		    circleX = x1 + circleRadius*(-Math.cos(Math.toRadians(beta)) - 1) ;
    		    circleY = y1 + circleRadius*(Math.sin(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,beta, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+beta)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+beta)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+beta));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+beta));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+beta));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+beta));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);
    	    }

			break;
		}
		case 1:
		{
			alpha = Math.toDegrees(Math.atan((x1 - x2) / (y1 - y2)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y1 - y2)/(Math.sin(Math.toRadians(90-beta))-Math.sin(Math.toRadians(90-beta-circleAngle)));
    		    circleX = x2 + circleRadius*(-Math.sin(Math.toRadians(beta)) - 1) ;
    		    circleY = y2 + circleRadius*(Math.cos(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,90-beta-circleAngle, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+90-beta-circleAngle)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+90-beta-circleAngle)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+90-beta-circleAngle));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+90-beta-circleAngle));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+90-beta-circleAngle));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+90-beta-circleAngle));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);
    	    }

			break;
		}
		case 2:
		{
			alpha = Math.toDegrees(Math.atan((y2 - y1) / (x1 - x2)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y2 - y1)/(Math.sin(Math.toRadians(180-beta-circleAngle))-Math.sin(Math.toRadians(180-beta)));
    		    circleX = x2 + circleRadius*(Math.cos(Math.toRadians(beta)) - 1) ;
    		    circleY = y2 + circleRadius*(Math.sin(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,180-beta-circleAngle, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+180-beta-circleAngle)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+180-beta-circleAngle)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+180-beta-circleAngle));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+180-beta-circleAngle));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+180-beta-circleAngle));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+180-beta-circleAngle));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);
    	    }

			break;
		}
		case 3:
		{
			alpha = Math.toDegrees(Math.atan((x1 - x2) / (y2 - y1)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y1 - y2)/(Math.sin(Math.toRadians(90+beta+circleAngle))-Math.sin(Math.toRadians(90+beta)));
    		    circleX = x1 + circleRadius*(Math.sin(Math.toRadians(beta)) - 1) ;
    		    circleY = y1 + circleRadius*(Math.cos(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,90+beta, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+90+beta)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+90+beta)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+90+beta));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+90+beta));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+90+beta));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+90+beta));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);
    	    }

			break;
		}
		case 4:
		{
			alpha = Math.toDegrees(Math.atan((y1 - y2) / (x2 - x1)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y2 - y1)/(Math.sin(Math.toRadians(-beta-circleAngle))-Math.sin(Math.toRadians(-beta)));
    		    circleX = x2 + circleRadius*(- Math.cos(Math.toRadians(beta)) - 1) ;
    		    circleY = y2 + circleRadius*(- Math.sin(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,360-beta-circleAngle, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+360-beta-circleAngle)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+360-beta-circleAngle)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+360-beta-circleAngle));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+360-beta-circleAngle));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+360-beta-circleAngle));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+360-beta-circleAngle));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);
    	    }

			break;
		}
		case 5:
		{
			alpha = Math.toDegrees(Math.atan((x2 - x1) / (y1 - y2)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y1 - y2)/(Math.sin(Math.toRadians(270+beta+circleAngle))-Math.sin(Math.toRadians(270+beta)));
    		    circleX = x1 + circleRadius*(-Math.sin(Math.toRadians(beta)) - 1) ;
    		    circleY = y1 + circleRadius*(-Math.cos(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,270+beta, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+270+beta)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+270+beta)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+270+beta));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+270+beta));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+270+beta));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+270+beta));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);
    	    }

			break;
		}
		case 6:
		{
			alpha = Math.toDegrees(Math.atan((y2 - y1) / (x2 - x1)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y2 - y1)/(Math.sin(Math.toRadians(180+beta))-Math.sin(Math.toRadians(180+beta+circleAngle)));
    		    circleX = x1 + circleRadius*(Math.cos(Math.toRadians(beta)) - 1) ;
    		    circleY = y1 + circleRadius*(-Math.sin(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,180+beta, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+180+beta)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+180+beta)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+180+beta));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+180+beta));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+180+beta));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+180+beta));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);
    	    }

			break;
		}
		case 7:
		{
			alpha = Math.toDegrees(Math.atan((x2 - x1) / (y2 - y1)));

			for(int j=0;j<copies;j++){
				beta = j*(90-alpha)/(copies);
    		    circleAngle = 180 - 2*alpha - 2*beta;
    		    circleRadius = (y1 - y2)/(Math.sin(Math.toRadians(-90-beta))-Math.sin(Math.toRadians(-90-beta-circleAngle)));
    		    circleX = x2 + circleRadius*(Math.sin(Math.toRadians(beta)) - 1) ;
    		    circleY = y2 + circleRadius*(-Math.cos(Math.toRadians(beta)) - 1);

    		    gc.strokeArc(circleX+nodemiddle, circleY+nodemiddle, 2*circleRadius, 2*circleRadius,270-beta-circleAngle, circleAngle,ArcType.OPEN);
    		    
    		    arrowX[0] = circleX+nodemiddle+circleRadius*(1+Math.cos(Math.toRadians(0.7*circleAngle+270-beta-circleAngle)));
    		    arrowY[0] = circleY+nodemiddle+circleRadius*(1-Math.sin(Math.toRadians(0.7*circleAngle+270-beta-circleAngle)));
    		    arrowX[1] = circleX+nodemiddle+circleRadius+(circleRadius+arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+270-beta-circleAngle));
    		    arrowY[1] = circleY+nodemiddle+circleRadius-(circleRadius+arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+270-beta-circleAngle));
    		    arrowX[2] = circleX+nodemiddle+circleRadius+(circleRadius-arrowwidth)*Math.cos(Math.toRadians(0.67*circleAngle+270-beta-circleAngle));
    		    arrowY[2] = circleY+nodemiddle+circleRadius-(circleRadius-arrowwidth)*Math.sin(Math.toRadians(0.67*circleAngle+270-beta-circleAngle));
    		        		    
    		    gc.fillPolygon(arrowX,arrowY,3);   		    
    	    }

			break;
		}
		default:
		{
			System.out.println("default case reached");
		}
		}
	}
}
