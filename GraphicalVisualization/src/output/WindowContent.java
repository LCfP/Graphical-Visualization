package output;

import model.Path;
import model.Node;
import model.Executer;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class WindowContent
{
	//Draw all elements on the canvas
	public static void drawAll(Canvas textcanvas,Pane pane,ArrayList<Node> nodes,ArrayList<Path> paths)
	{
        Coordinates.adjustVirtualCoordinates(nodes);

        drawNodes(textcanvas,pane,nodes);
        int noOfPaths = paths.size();
        int count = 0;

        for(int i=0;i<noOfPaths;i++)
        {
        	count += ArcDraw.drawArcs(textcanvas,pane,count,paths.get(i));
        }
	}


	//Draws all nodes as red circles
	public static void drawNodes(Canvas textcanvas,Pane pane,ArrayList<Node> nodes)
	{
		int NoNodes = nodes.size();
		double nodeRadius;
		double[][] virtualcoordinates = Coordinates.getVirtualCoordinates(nodes);
		GraphicsContext gc = textcanvas.getGraphicsContext2D();

        nodeRadius = Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/120;

        for(int i=0;i<NoNodes;i++)
        {
        	Executer.nodecircles[i] = new Circle(virtualcoordinates[0][i],virtualcoordinates[1][i],nodeRadius);
        	Executer.nodecircles[i].setFill(Color.WHITE);
        	Executer.nodecircles[i].setStrokeWidth(Math.min(Executer.defaultHeight, 0.8*Executer.defaultWidth)/200);

        	Executer.nodecircles[i].setOnMouseEntered(Mouse.MouseOnCircleEnter(gc,pane));
        	Executer.nodecircles[i].setOnMouseExited(Mouse.MouseOnCircleExit(gc,pane));
        	Executer.nodecircles[i].setOnMouseClicked(Mouse.MouseclickOnCircle(gc, pane));
        	Executer.nodecircles[i].setStroke(Color.RED);
        	
        	pane.getChildren().add(Executer.nodecircles[i]);
        }
	}

	public static int[] inventColor(int index)
	{
		int[] basiccolors = new int[3];

		int sqrtindex = (int) (Math.sqrt(index+1)+1);

		basiccolors[0] = ((int)(0.5*index/sqrtindex))*255/sqrtindex;
		basiccolors[1] = ((int)(index/sqrtindex))*255/sqrtindex;
		basiccolors[2] = ((index+1)%sqrtindex)*255/(sqrtindex-1);

		return basiccolors;
	}


	public static int[] convertToRGB(int pixel)
	{
		int[] color = new int[3];

		color[0] = (int) ((Math.pow(256,3) + pixel) / 65536);
		color[1] = (int) (((Math.pow(256,3) + pixel) / 256 ) % 256 );
		color[2] = (int) ((Math.pow(256,3) + pixel) % 256);

		return color;
	}
}
