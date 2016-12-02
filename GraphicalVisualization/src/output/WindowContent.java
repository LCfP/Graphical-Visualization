package output;

import model.Path;
import model.Node;
import model.Edge;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WindowContent
{

	//Draws all nodes as red circles
	public static void drawNodes(GraphicsContext GC,ArrayList<Node> nodes)
	{
		int NoNodes = nodes.size();
        double[] xcoordinates = new double[NoNodes];
        double[] ycoordinates = new double[NoNodes];
        double[] adjustedxcoordinates = new double[NoNodes];
        double[] adjustedycoordinates = new double[NoNodes];

        for(int i=0;i<NoNodes;i++){
        	xcoordinates[i] = nodes.get(i).getXcoordinate();
        	ycoordinates[i] = nodes.get(i).getYcoordinate();
        }

        adjustedxcoordinates = Coordinates.transformXCoordinates(xcoordinates);
        adjustedycoordinates = Coordinates.transformYCoordinates(ycoordinates);

		GC.setFill(Color.GREEN);
        GC.setStroke(Color.RED);
        GC.setLineWidth(3);

        for(int i=0;i<NoNodes;i++){
        	GC.strokeOval(adjustedxcoordinates[i],adjustedycoordinates[i], 10, 10);
        	GC.fillText(String.valueOf(i+1),adjustedxcoordinates[i],adjustedycoordinates[i]+25);
        }
	}

	//Draws all paths as blue lines
	public static void drawPaths(GraphicsContext GC,ArrayList<Path> paths)
	{
		int NoPaths = paths.size();
		ArrayList<Edge> TempEdges = new ArrayList<Edge>(0);

		for(int i=0;i<NoPaths;i++)
		{
			TempEdges.addAll(paths.get(i).getEdges());
		}

		int NoEdges = TempEdges.size();
		double[] xcoordinates = new double[2*NoEdges];
        double[] ycoordinates = new double[2*NoEdges];
        double[] adjustedxcoordinates = new double[2*NoEdges];
        double[] adjustedycoordinates = new double[2*NoEdges];

        for(int i=0;i<NoEdges;i++)
        {
        	xcoordinates[2*i]=TempEdges.get(i).getNode1().getXcoordinate();
        	xcoordinates[2*i+1]=TempEdges.get(i).getNode2().getXcoordinate();
        	ycoordinates[2*i]=TempEdges.get(i).getNode1().getYcoordinate();
        	ycoordinates[2*i+1]=TempEdges.get(i).getNode2().getYcoordinate();
        }

        adjustedxcoordinates = Coordinates.transformXCoordinates(xcoordinates);
        adjustedycoordinates = Coordinates.transformYCoordinates(ycoordinates);

        GC.setFill(Color.GREEN);
        GC.setStroke(Color.BLUE);
        GC.setLineWidth(2);

        for(int i=0;i<NoEdges;i++)
        {
        	GC.strokeLine(adjustedxcoordinates[2*i]+5,adjustedycoordinates[2*i]+5,adjustedxcoordinates[2*i+1]+5,adjustedycoordinates[2*i+1]+5);
        }
	}






}
