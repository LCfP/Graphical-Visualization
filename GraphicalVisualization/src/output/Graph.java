package output;

import model.Path;
import user.Mouse;
import user.Sort;
import user.Zoom;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

public class Graph
{
	//default screensizes
	public static double defaultWidth = 1200;
	public static double defaultHeight = 900;

	public static int noOfScreensX = 1;
	public static int noOfScreensY = 1;

	public static double graphwidth = 0.7;

	public static double defaultCircleRadius = Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/120;
	public static double defaultCircleWidth = Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/200;
	public static double defaultArrowWidth = Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/120;
	public static double defaultArcWidth = Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/400;

	public static double graphLabelSize = Math.min(graphwidth*defaultWidth/noOfScreensX,defaultHeight/noOfScreensY)/20;
	public static double titleLabelSize = Math.min(graphwidth*defaultWidth,Graph.defaultHeight)/40;
	public static double mainLabelSize = Math.min(graphwidth*defaultWidth,Graph.defaultHeight)/60;

	public static void updateDefaultSizes(Scene scene)
	{
		defaultHeight = scene.getHeight();
		defaultWidth = scene.getWidth();
		updateOtherSizes();
	}

	public static void updateOtherSizes()
	{
		defaultCircleRadius = Zoom.zoom * Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/120;
		defaultCircleWidth = Zoom.zoom * Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/200;
		defaultArrowWidth = Zoom.zoom * Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/120;
		defaultArcWidth = Zoom.zoom * Math.min(defaultHeight/noOfScreensY,graphwidth*defaultWidth/noOfScreensX)/400;
		graphLabelSize = Math.min(graphwidth*defaultWidth/noOfScreensX,defaultHeight/noOfScreensY)/20;
		titleLabelSize = Math.min(graphwidth*defaultWidth,defaultHeight)/40;
		mainLabelSize = Math.min(graphwidth*defaultWidth,defaultHeight)/60;
		Executer.titleLabel.setFont(new Font(titleLabelSize));
		Executer.titleLabel.setLayoutX(0.03*defaultWidth);
		Executer.titleLabel.setLayoutY(0.05*defaultHeight);
		Executer.mainLabel.setFont(new Font(mainLabelSize));
		Executer.mainLabel.setLayoutX(0.01*defaultWidth);
		Executer.mainLabel.setLayoutY(0.1*defaultHeight);
	}

	//Draw all elements on the canvas
	public static void drawAll()
	{
		ArrayList<String> attributes;
		int attributeNo;
		int noOfAttributes;
        Pane drawPane = Executer.drawPane;
        Label[] labels;
        double[] screenmeasures;
        String sortingAttribute = Executer.sortingAttribute;

        drawPane.getChildren().clear();

		if(sortingAttribute.equals(""))
		{
			attributes = null;
			attributeNo = -1;
			noOfAttributes = 1;

			noOfScreensX = 1;
			noOfScreensY = 1;

			Graph.updateOtherSizes();

			labels = new Label[1];
			labels[0] = new Label(" All paths");
			labels[0].setFont(new Font(graphLabelSize));
			labels[0].setLayoutX(0);
			labels[0].setLayoutY(0);
			drawPane.getChildren().add(labels[0]);
		}
		else
		{
			attributes = Sort.getDistinctAttributes(sortingAttribute);
			attributeNo = Executer.pathAttributeNames.indexOf(sortingAttribute);
			noOfAttributes = attributes.size();
			labels = new Label[noOfAttributes];

			noOfScreensX = (int) (Math.sqrt(noOfAttributes - 1)+1);
			noOfScreensY = (int) ((noOfAttributes - 1) / noOfScreensX + 1);

			Graph.updateOtherSizes();

			screenmeasures = getScreenMeasures();

			for(int i=0;i<noOfAttributes;i++)
			{
				labels[i] = new Label(" " + sortingAttribute + " = " + attributes.get(i));
				labels[i].setFont(new Font(graphLabelSize));
				labels[i].setLayoutX((i%noOfScreensX)*Zoom.zoom*screenmeasures[0]);
				labels[i].setLayoutY((i/noOfScreensX)*Zoom.zoom*screenmeasures[1]);
				drawPane.getChildren().add(labels[i]);
			}
		}

		drawNodes(noOfAttributes);
		makePaths(noOfAttributes,attributeNo,attributes);
	}

	//Draws all nodes as red circles
	private static void drawNodes(int noOfScreens)
	{
		Pane drawPane = Executer.drawPane;
		ArrayList<Node> nodes = Executer.nodes;
		int NoNodes = nodes.size();
		double[][] coordinates = Executer.normalizedCoordinates;
        double[] screenmeasures = getScreenMeasures();
        double initX;
        double initY;
		Circle[][][] circles = new Circle[NoNodes][noOfScreensX][noOfScreensY];
		int[] color;

		Executer.titleLabel.setText("");
		Executer.mainLabel.setText("");

        for(int x=0;x<noOfScreensX;x++)
        {
        	for(int y=0;y<noOfScreensY;y++)
            {
        		if((noOfScreensX*y+x)<noOfScreens)
        		{
                    for(int i=0;i<NoNodes;i++)
                    {
                        initX = x*Zoom.zoom*screenmeasures[0];
                        initY = y*Zoom.zoom*screenmeasures[1];

                        color = Executer.circleColors[i][x][y];

                    	circles[i][x][y] = new Circle(initX+Zoom.zoom*screenmeasures[0]*coordinates[0][i],initY+
                    			Zoom.zoom*screenmeasures[1]*coordinates[1][i],defaultCircleRadius);
                    	circles[i][x][y].setFill(Color.WHITE);
                    	circles[i][x][y].setStrokeWidth(defaultCircleWidth);

                    	circles[i][x][y].setOnMouseEntered(Mouse.MouseOnCircleEnter());
                    	circles[i][x][y].setOnMouseExited(Mouse.MouseOnCircleExit());
                    	circles[i][x][y].setOnMouseClicked(Mouse.MouseclickOnCircle());
                    	circles[i][x][y].setStroke(Color.rgb(color[0],color[1],color[2]));

                    	drawPane.getChildren().add(circles[i][x][y]);
                    }
        		}
            }
        }

    	Executer.nodecircles = circles;
	}

	private static void makePaths(int noOfAttributes,int attributeNo,ArrayList<String> attributes)
	{
		ArrayList<Path> paths = Executer.paths;
		ArrayList<Node> nodes = Executer.nodes;
		int noOfPaths = paths.size();
		int noOfNodes = nodes.size();
		int[][][] edgeCount = new int[noOfAttributes][noOfNodes][noOfNodes];
		Path path;

		Executer.edgearcs = new Arc[noOfPaths][];
		Executer.arrowpolygons = new Polygon[noOfPaths][];
		Executer.edgelines = new Line[noOfPaths][];
		Executer.linearrowpolygons = new Polygon[noOfPaths][];

		for(int j=0;j<noOfPaths;j++)
		{
			path = paths.get(j);

			for(int i=0;i<noOfAttributes;i++)
			{
				if(noOfAttributes == 1)
				{
					edgeCount[i] = ArcDraw.makeArcs(path,i,edgeCount[i]);
				}
				else if(path.getPathAttributes().get(attributeNo).equals(attributes.get(i)))
				{
					edgeCount[i] = ArcDraw.makeArcs(path,i,edgeCount[i]);
				}
			}
		}
	}

	public static ChangeListener<? super Number> getResizeListener(Scene scene)
	{
		final ChangeListener<Number> listener = new ChangeListener<Number>()
		{

			//Resets the canvas sizes and redraws the nodes and paths
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

				Graph.updateDefaultSizes(scene);

				Executer.drawPane.getChildren().clear();
				Executer.leftScrollPane.setPrefWidth(0.5*(1-graphwidth)*defaultWidth);
				Executer.rightScrollPane.setPrefWidth(0.5*(1-graphwidth)*defaultWidth);
				Graph.drawAll();
			}

		};

		return listener;
	}

	public static int[][] createColors()
	{
		ArrayList<Path> paths = Executer.paths;
		int noOfPaths = paths.size();
		int[][] colors = new int[noOfPaths][3];

		for(int i=0;i<noOfPaths;i++)
		{
			colors[i] = inventColor(i);
		}

		return colors;
	}

	public static int[][][][] createRedColors()
	{
		int noOfNodes = Executer.nodes.size();
		int[][][][] colors = new int[noOfNodes][1][1][3];

		for(int i=0;i<noOfNodes;i++)
		{
			colors[i][0][0][0] = 255;
			colors[i][0][0][1] = 0;
			colors[i][0][0][2] = 0;
		}

		return colors;
	}

	public static void updateCircleColors()
	{
		int[][][][] newColors;
		int noOfGraphs;
		int noOfNodes = Executer.nodes.size();
		int noOfXScreens;
		int noOfYScreens;

		if(Executer.sortingAttribute.equals(""))
		{
			newColors = createRedColors();
		}
		else
		{
			noOfGraphs = Sort.getDistinctAttributes(Executer.sortingAttribute).size();
			noOfXScreens = (int) (Math.sqrt(noOfGraphs-1)+1);
			noOfYScreens = noOfGraphs/noOfXScreens+1;
			newColors = new int[noOfNodes][noOfXScreens][noOfYScreens][3];

			for(int a=0;a<noOfNodes;a++)
			{
				for(int b=0;b<noOfXScreens;b++)
				{
					for(int c=0;c<noOfYScreens;c++)
					{
						newColors[a][b][c][0] = 255;
						newColors[a][b][c][1] = 0;
						newColors[a][b][c][2] = 0;
					}
				}
			}
		}

		Executer.circleColors = newColors;
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

	public static double[] getScreenMeasures()
	{
		double[] measures = new double[2];
		measures[0] = Math.min(graphwidth*Graph.defaultWidth/noOfScreensX,
        		(Graph.defaultHeight - Executer.menuBarHeight)/noOfScreensY/Executer.aspectratio);
        measures[1] = Math.min(graphwidth*Graph.defaultWidth/noOfScreensX*Executer.aspectratio,
        		(Graph.defaultHeight - Executer.menuBarHeight)/noOfScreensY);

        return measures;
	}
}
