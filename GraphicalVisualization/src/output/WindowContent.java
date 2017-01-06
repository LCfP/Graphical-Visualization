package output;

import model.Path;
import model.Node;
import model.Executer;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WindowContent
{
	//Draw all elements on the canvas
	public static void drawAll(GraphicsContext GC,ArrayList<Node> nodes,ArrayList<Path> paths)
	{
        Coordinates.adjustVirtualCoordinates(nodes);

        drawNodes(GC,nodes);
        int noOfPaths = paths.size();

        for(int i=0;i<noOfPaths;i++)
        {
        	int[] color = inventColor(i);
        	Arc.drawArcs(GC,color,paths.get(i).getEdges());
        }
	}


	//Draws all nodes as red circles
	public static void drawNodes(GraphicsContext GC,ArrayList<Node> nodes)
	{
		int NoNodes = nodes.size();
		double nodeDiameter;
		double[][] virtualcoordinates = Coordinates.getVirtualCoordinates(nodes);

		GC.setFill(Color.GREEN);


        //nodes adjust in size when screensize is adjust
        GC.setFont(new Font(Executer.defaultHeight/40));
        GC.setLineWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/300);
        nodeDiameter = Math.min(Executer.defaultHeight, Executer.defaultWidth)/60;

        for(int i=0;i<NoNodes;i++)
        {
        	if(nodes.get(i).isDepot())
        	{
        		GC.setStroke(Color.rgb(180,0,255));
        	}
        	else
        	{
        		GC.setStroke(Color.RED);
        	}

        	GC.strokeOval(virtualcoordinates[0][i],virtualcoordinates[1][i], nodeDiameter, nodeDiameter);
        	GC.fillText(String.valueOf(i),virtualcoordinates[0][i],virtualcoordinates[1][i]+Executer.defaultHeight/25);
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
}
