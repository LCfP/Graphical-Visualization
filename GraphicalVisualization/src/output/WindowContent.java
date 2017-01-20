package output;

import model.Path;
import model.Node;
import model.Executer;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

public class WindowContent
{
	//default screensizes
	public static double defaultWidth = 1200;
	public static double defaultHeight = 900;

	public static int noOfScreensX = 1;
	public static int noOfScreensY = 1;
	public static double defaultCircleRadius = Math.min(defaultHeight/noOfScreensY, 0.8*defaultWidth/noOfScreensX)/120;
	public static double defaultCircleWidth = Math.min(defaultHeight/noOfScreensY, 0.8*defaultWidth/noOfScreensX)/200;
	public static double defaultArrowWidth = Math.min(defaultHeight/noOfScreensY, 0.8*defaultWidth/noOfScreensX)/120;
	public static double defaultArcWidth = Math.min(defaultHeight/noOfScreensY,defaultWidth/noOfScreensX)/400;

	public static void updateDefaultSizes(Scene scene)
	{
		defaultHeight = scene.getHeight();
		defaultWidth = scene.getWidth();
		updateOtherSizes();
	}

	public static void updateOtherSizes()
	{
		noOfScreensX = Executer.nodes.get(0).getVirtualXcoordinates().length;
		noOfScreensY = Executer.nodes.get(0).getVirtualXcoordinates()[0].length;
		defaultCircleRadius = Math.min(defaultHeight/noOfScreensY, 0.8*defaultWidth/noOfScreensX)/120;
		defaultCircleWidth = Math.min(defaultHeight/noOfScreensY, 0.8*defaultWidth/noOfScreensX)/200;
		defaultArrowWidth = Math.min(defaultHeight/noOfScreensY, 0.8*defaultWidth/noOfScreensX)/120;
		defaultArcWidth = Math.min(defaultHeight/noOfScreensY,defaultWidth/noOfScreensX)/400;
	}

	//Draw all elements on the canvas
	public static void drawAll(Pane drawPane,ArrayList<Node> nodes,ArrayList<Path> paths)
	{
		Path path;
		ArrayList<String> attributes;
		int count = 0;
		int attributeNo;
		int noOfAttributes;
		int noOfPaths = paths.size();
        int edgecounter = 0;

		for(int i=0;i<paths.size();i++)
		{
			edgecounter += paths.get(i).getEdges().size();
		}

		if(Executer.attributeName.equals(""))
		{
			Label allLabel = new Label("All paths");

			Coordinates.adjustVirtualCoordinates(nodes,1,1,1);
			WindowContent.updateOtherSizes();
			drawNodes(drawPane,nodes);

			allLabel.setFont(new Font(Math.min(defaultWidth/30,defaultHeight/20)));
			allLabel.setLayoutX(0.2*defaultWidth);
			allLabel.setLayoutY(Executer.menuBarHeight);
			drawPane.getChildren().add(allLabel);

			Executer.edgearcs = new Arc[edgecounter][1][1];
			Executer.arrowpolygons = new Polygon[edgecounter][1][1];

	        for(int i=0;i<noOfPaths;i++)
	        {
	        	count += ArcDraw.drawArcs(drawPane,count,paths.get(i),0,0);
	        }
		}
		else
		{
			attributes = Sort.getDistinctAttributes(Executer.attributeName);
			attributeNo = Sort.getAttributeNo(Executer.attributeName);
			noOfAttributes = attributes.size();
			noOfScreensX = (int) (Math.sqrt(noOfAttributes - 1)+1);
			noOfScreensY = (int) ((noOfAttributes - 1) / noOfScreensX + 1);
			Label[] labels = new Label[noOfAttributes];

			Coordinates.adjustVirtualCoordinates(Executer.nodes,noOfScreensX,noOfScreensY,noOfAttributes);
			WindowContent.updateOtherSizes();
			drawNodes(drawPane,nodes);

			for(int i=0;i<noOfAttributes;i++)
			{
				labels[i] = new Label(Executer.attributeName+" = "+attributes.get(i));
				labels[i].setFont(new Font(Math.min(defaultWidth/30/noOfScreensX,defaultHeight/20/noOfScreensY)));
				labels[i].setLayoutX(0.2*defaultWidth+(i%noOfScreensX)*0.8*defaultWidth/noOfScreensX);
				labels[i].setLayoutY(Executer.menuBarHeight+(i/noOfScreensY)*(defaultHeight-Executer.menuBarHeight)/noOfScreensY);
				drawPane.getChildren().add(labels[i]);
			}

			Executer.edgearcs = new Arc[edgecounter][noOfScreensX][noOfScreensY];
			Executer.arrowpolygons = new Polygon[edgecounter][noOfScreensX][noOfScreensY];

			for(int j=0;j<noOfPaths;j++)
			{
				for(int i=0;i<noOfAttributes;i++)
				{
					path = paths.get(j);

					if(path.getAttributes().get(attributeNo).equals(attributes.get(i)))
					{
						count += ArcDraw.drawArcs(drawPane,count,path,i%noOfScreensX,i/noOfScreensX);
					}
				}
			}
		}
	}


	//Draws all nodes as red circles
	public static void drawNodes(Pane drawPane,ArrayList<Node> nodes)
	{
		int noOfScreens = nodes.get(0).noOfVirtualCoordinates();
		int NoNodes = nodes.size();
		double[][][][] virtualcoordinates = Coordinates.getVirtualCoordinates(nodes);
		int noOfScreensX = virtualcoordinates[0][0].length;
		int noOfScreensY = virtualcoordinates[0][0][0].length;
		Circle[][][] circles = new Circle[NoNodes][noOfScreensX][noOfScreensY];

		drawPane.getChildren().clear();
		Executer.titleLabel.setText("");
		Executer.mainLabel.setText("");
		drawPane.getChildren().add(Executer.titleLabel);
		drawPane.getChildren().add(Executer.mainLabel);

        for(int x=0;x<noOfScreensX;x++)
        {
        	for(int y=0;y<noOfScreensY;y++)
            {
        		if((noOfScreensX*y+x)<noOfScreens)
        		{
                    for(int i=0;i<NoNodes;i++)
                    {
                    	circles[i][x][y] = new Circle(virtualcoordinates[0][i][x][y],virtualcoordinates[1][i][x][y],defaultCircleRadius);
                    	circles[i][x][y].setFill(Color.WHITE);
                    	circles[i][x][y].setStrokeWidth(defaultCircleWidth);

                    	circles[i][x][y].setOnMouseEntered(Mouse.MouseOnCircleEnter(drawPane));
                    	circles[i][x][y].setOnMouseExited(Mouse.MouseOnCircleExit(drawPane));
                    	circles[i][x][y].setOnMouseClicked(Mouse.MouseclickOnCircle(drawPane));
                    	circles[i][x][y].setStroke(Color.RED);

                    	drawPane.getChildren().add(circles[i][x][y]);
                    }
        		}
            }
        }

    	Executer.nodecircles = circles;
	}

	public static ChangeListener<? super Number> getResizeListener(Scene scene,Pane drawPane)
	{
		final ChangeListener<Number> listener = new ChangeListener<Number>()
		{

			//Resets the canvas sizes and redraws the nodes and paths
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

				WindowContent.updateDefaultSizes(scene);

				drawPane.getChildren().clear();

				Executer.titleLabel = new Label("");
				Executer.mainLabel = new Label("");
				drawPane.getChildren().addAll(Executer.titleLabel,Executer.mainLabel);
				WindowContent.drawAll(drawPane, Executer.nodes, Executer.paths);
			}

		};

		return listener;
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
