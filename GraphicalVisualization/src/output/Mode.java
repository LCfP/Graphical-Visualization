package output;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.Executer;
import model.Node;
import model.Path;
import user.ControlPaths;
import user.Mouse;
import user.Zoom;

public class Mode {
	public static ChangeListener<Boolean> getNodeModeListener()
	{
		ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>()
		{
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldvalue, Boolean newvalue)
			{
				if(newvalue)
				{
					Executer.sortingAttribute = "";
					ControlPaths.updateRightPane();
					ControlPaths.checkboxes[1].setSelected(true);
					ControlPaths.checkboxes[2].setSelected(true);
				}
				else
				{
					Graph.updateCircleColors();
					Graph.drawAll();
				}
			}
		};
		return changeListener;
	}

	public static void drawPaths()
	{
		Pane drawPane = Executer.drawPane;
		int noOfCheckboxes = ControlPaths.checkboxes.length;
		int noOfScreens = 0;
		int noOfScreensX;
		int noOfScreensY;
		double[] screenmeasures = new double[2];
		ArrayList<Label> labels = new ArrayList<Label>(0);
		Label label;

		ArrayList<Node> nodes = Executer.nodes;
		ArrayList<Path> paths = Executer.paths;
		ArrayList<Integer> pathNos = new ArrayList<Integer>(0);
		ArrayList<Integer> nodeNos;
		int NoNodes = nodes.size();
		double[][] coordinates = Executer.normalizedCoordinates;
        double initX;
        double initY;
		Circle[][][] circles;
		int[] color;

		Executer.titleLabel.setText("");
		Executer.mainLabel.setText("");
		drawPane.getChildren().clear();

		for(int i=2;i<noOfCheckboxes;i++)
		{
			if(ControlPaths.checkboxes[i].isSelected())
			{
				noOfScreens++;
				pathNos.add(i-2);
				label = new Label(ControlPaths.checkboxes[i].getText());
				labels.add(label);
			}
		}

		if(noOfScreens == 0)
		{
			noOfScreensX = 1;
			noOfScreensY = 1;
		}
		else
		{
			noOfScreensX = (int) Math.ceil(Math.sqrt(noOfScreens));
			noOfScreensY = (int) Math.ceil(((double) (noOfScreens))/((double) (noOfScreensX)));
		}
		
		Graph.noOfScreensX = noOfScreensX;
		Graph.noOfScreensY = noOfScreensY;
		Graph.updateOtherSizes();

		screenmeasures[0] = Math.min(Graph.graphwidth*Graph.defaultWidth/noOfScreensX,
        		(Graph.defaultHeight - Executer.menuBarHeight)/noOfScreensY/Executer.aspectratio);
		screenmeasures[1] = Math.min(Graph.graphwidth*Graph.defaultWidth/noOfScreensX*Executer.aspectratio,
        		(Graph.defaultHeight - Executer.menuBarHeight)/noOfScreensY);

		circles = new Circle[NoNodes][noOfScreensX][noOfScreensY];
		Executer.circleColors = Graph.createRedColors(noOfScreensX, noOfScreensY);

		for(int y=0;y<noOfScreensY;y++)
        {
			for(int x=0;x<noOfScreensX;x++)
			{
        		if((noOfScreensX*y+x)<noOfScreens)
        		{
        			labels.get(noOfScreensX*y+x).setFont(new Font(Graph.graphLabelSize));
        			labels.get(noOfScreensX*y+x).setLayoutX(x*Zoom.zoom*screenmeasures[0]);
        			labels.get(noOfScreensX*y+x).setLayoutY(y*Zoom.zoom*screenmeasures[1]);
        			drawPane.getChildren().add(labels.get(noOfScreensX*y+x));

        			nodeNos = new ArrayList<Integer>(0);

        			for(int i=0;i<paths.get(pathNos.get(x+y*noOfScreensX)).getNodes().size();i++)
        				nodeNos.add(paths.get(pathNos.get(x+y*noOfScreensX)).getNodes().get(i)[0]);

                    for(int i=0;i<NoNodes;i++)
                    {
                        initX = x*Zoom.zoom*screenmeasures[0];
                        initY = y*Zoom.zoom*screenmeasures[1];


                        if(nodeNos.contains(i))
                        {
                        	Executer.circleColors[i][x][y][0] = 0;
                        	Executer.circleColors[i][x][y][1] = 150;
                        	Executer.circleColors[i][x][y][2] = 0;
                        }
                        else
                        {
                        	Executer.circleColors[i][x][y][0] = 255;
                        	Executer.circleColors[i][x][y][1] = 0;
                        	Executer.circleColors[i][x][y][2] = 0;
                        }

                        color = Executer.circleColors[i][x][y];

                    	circles[i][x][y] = new Circle(initX+Zoom.zoom*screenmeasures[0]*coordinates[0][i],initY+
                    			Zoom.zoom*screenmeasures[1]*coordinates[1][i],Graph.defaultCircleRadius);
                    	circles[i][x][y].setFill(Color.WHITE);
                    	circles[i][x][y].setStrokeWidth(Graph.defaultCircleWidth);

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
}
