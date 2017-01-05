package output;

import model.Path;
import model.Node;
import model.Edge;
import model.Executer;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WindowContent
{
	public static void drawAll(GraphicsContext GC,ArrayList<Node> nodes,ArrayList<Path> paths)
	{

	}


	//Draws all nodes as red circles
	public static void drawNodes(GraphicsContext GC,ArrayList<Node> nodes)
	{
		int NoNodes = nodes.size();
		double nodeDiameter;
        double[][] adjustedcoordinates;

        adjustedcoordinates = Coordinates.transformCoordinates(Coordinates.getCoordinates(nodes));

		GC.setFill(Color.GREEN);
        GC.setStroke(Color.RED);

        //nodes adjust in size when screensize is adjust
        GC.setFont(new Font(Executer.defaultHeight/40));
        GC.setLineWidth(Math.min(Executer.defaultHeight, Executer.defaultWidth)/300);
        nodeDiameter = Math.min(Executer.defaultHeight, Executer.defaultWidth)/60;

        for(int i=0;i<NoNodes;i++){
        	GC.strokeOval(adjustedcoordinates[0][i],adjustedcoordinates[1][i], nodeDiameter, nodeDiameter);
        	GC.fillText(String.valueOf(i+1),adjustedcoordinates[0][i],adjustedcoordinates[1][i]+Executer.defaultHeight/25);
        }
	}

	//Draws all paths as blue lines
	public static void drawPaths(GraphicsContext GC,ArrayList<Path> paths)
	{
		int NoOfPaths = paths.size();
		int NoOfEdges;
		Path tempPath;
		Edge tempEdge;

		//arraylist of unique edges
		ArrayList<Edge> edges = new ArrayList<Edge>(0);

		//arraylist with the number copies of the unique edges
		ArrayList<Integer> copiesOfEdges = new ArrayList<Integer>(0);

		for(int i=0;i<NoOfPaths;i++)
		{
			tempPath = paths.get(i);
			NoOfEdges = tempPath.getEdges().size();

			for(int j=0;j<NoOfEdges;j++)
			{
				tempEdge = tempPath.getEdges().get(j);

				if(edges.contains(tempEdge))
				{
					copiesOfEdges.set(edges.indexOf(tempEdge),copiesOfEdges.get(edges.indexOf(tempEdge))+1);
				}
				else
				{
					copiesOfEdges.add(1);
					edges.add(tempEdge);
				}
			}
		}

        Arc.drawArcs(GC, edges, copiesOfEdges);
	}
}
