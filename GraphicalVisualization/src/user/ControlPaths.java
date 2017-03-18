package user;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import model.Executer;
import model.Path;
import output.Graph;
import output.Mode;

public class ControlPaths {
	public static CheckBox[] checkboxes;
	public static Label checkboxtitlelabel;

	public static void createCheckboxes()
	{
		String[] routes = getRoutes();
		int noOfCheckboxes = routes.length + 2;
		checkboxes = new CheckBox[noOfCheckboxes];

		checkboxtitlelabel = new Label("Paths shown");
		checkboxtitlelabel.setFont(new Font(Graph.titleLabelSize));

		checkboxes[0] = new CheckBox("Show all");
		checkboxes[0].setSelected(true);
		checkboxes[0].selectedProperty().addListener(getCheckBoxListener(0));

		checkboxes[1] = new CheckBox("Show none");
		checkboxes[1].setSelected(false);
		checkboxes[1].selectedProperty().addListener(getCheckBoxListener(1));

		for(int i=2;i<noOfCheckboxes;i++)
		{
			checkboxes[i] = new CheckBox(routes[i-2]);
			checkboxes[i].setSelected(true);
			checkboxes[i].selectedProperty().addListener(getCheckBoxListener(i));
		}
	}

	public static void updateRightPane()
	{
		Executer.rightPane.getChildren().clear();
		Zoom.addZoomSlider();

		if(Executer.sortingAttribute.equals(""))
		{
			int noOfCheckboxes = checkboxes.length;

			checkboxtitlelabel.setLayoutX(0.025*Graph.defaultWidth);
			checkboxtitlelabel.setLayoutY(0.18*Graph.defaultHeight);
			checkboxes[0].setLayoutX(0.01*Graph.defaultWidth);
			checkboxes[1].setLayoutX(0.01*Graph.defaultWidth);
			checkboxes[0].setLayoutY(0.25*Graph.defaultHeight);
			checkboxes[1].setLayoutY(0.25*Graph.defaultHeight+25);

			Executer.rightPane.getChildren().addAll(checkboxtitlelabel,checkboxes[0],checkboxes[1]);

			for(int i=2;i<noOfCheckboxes;i++)
			{
				checkboxes[i].setLayoutX(0.01*Graph.defaultWidth);
				checkboxes[i].setLayoutY(0.25*Graph.defaultHeight+25*(i+0.5));
				Executer.rightPane.getChildren().add(checkboxes[i]);
			}
		}
		else
		{
			int noOfPaths = Executer.paths.size();
			int attributeNo = Executer.pathAttributeNames.indexOf(Executer.sortingAttribute);
			ArrayList<String> pathAttributes = Sort.getDistinctAttributes(Executer.sortingAttribute);
			String attribute;
			int noOfPathAttributes = pathAttributes.size();
			int checkboxcounter = 0;
			Path path;

			checkboxtitlelabel.setLayoutX(0.025*Graph.defaultWidth);
			checkboxtitlelabel.setLayoutY(0.18*Graph.defaultHeight);
			checkboxes[0].setLayoutX(0.01*Graph.defaultWidth);
			checkboxes[1].setLayoutX(0.01*Graph.defaultWidth);
			checkboxes[0].setLayoutY(0.25*Graph.defaultHeight);
			checkboxes[1].setLayoutY(0.25*Graph.defaultHeight+25);

			Executer.rightPane.getChildren().addAll(checkboxtitlelabel,checkboxes[0],checkboxes[1]);

			for(int i=0;i<noOfPathAttributes;i++)
			{
				attribute = pathAttributes.get(i);

				for(int j=0;j<noOfPaths;j++)
				{
					path = Executer.paths.get(j);

					if(path.getPathAttributes().get(attributeNo).equals(attribute))
					{
						checkboxes[j+2].setLayoutX(0.01*Graph.defaultWidth);
						checkboxes[j+2].setLayoutY(0.25*Graph.defaultHeight+25*((i+1)*0.5+checkboxcounter+2));
						Executer.rightPane.getChildren().add(checkboxes[j+2]);
						checkboxcounter ++;
					}
				}
			}
		}
	}

	public static Slider[] createColorSliders(int pathno)
	{
		double[] color = new double[3];
		color[0] = Executer.pathColors[pathno][0];
		color[0] = color[0]/255;
		color[1] = Executer.pathColors[pathno][1];
		color[1] = color[1]/255;
		color[2] = Executer.pathColors[pathno][2];
		color[2] = color[2]/255;

		Slider[] colorsliders = new Slider[3];

		for(int i=0;i<3;i++)
		{
			colorsliders[i] = new Slider(0,1,color[i]);
			colorsliders[i].setLayoutX(0.01*Graph.defaultWidth);
			colorsliders[i].setLayoutY(0.1*Graph.defaultHeight+40*i);
			colorsliders[i].setMinorTickCount(1);
			colorsliders[i].setMajorTickUnit(1);
			colorsliders[i].setShowTickLabels(true);
			colorsliders[i].setShowTickMarks(true);
			colorsliders[i].valueProperty().addListener(getColorsliderListener(i,pathno));
		}

		colorsliders[0].setLabelFormatter(getColorSliderLabels("red"));
		colorsliders[1].setLabelFormatter(getColorSliderLabels("green"));
		colorsliders[2].setLabelFormatter(getColorSliderLabels("blue"));

		return colorsliders;
	}

	public static Slider[] createColorSliders(int[] nodeno)
	{
		double[] color = new double[3];
		color[0] = Executer.circleColors[nodeno[0]][nodeno[1]][nodeno[2]][0];
		color[0] = color[0]/255;
		color[1] = Executer.circleColors[nodeno[0]][nodeno[1]][nodeno[2]][1];
		color[1] = color[1]/255;
		color[2] = Executer.circleColors[nodeno[0]][nodeno[1]][nodeno[2]][2];
		color[2] = color[2]/255;

		Slider[] colorsliders = new Slider[3];

		for(int i=0;i<3;i++)
		{
			colorsliders[i] = new Slider(0,1,color[i]);
			colorsliders[i].setLayoutX(0.01*Graph.defaultWidth);
			colorsliders[i].setLayoutY(0.1*Graph.defaultHeight+40*i);
			colorsliders[i].setMinorTickCount(1);
			colorsliders[i].setMajorTickUnit(1);
			colorsliders[i].setShowTickLabels(true);
			colorsliders[i].setShowTickMarks(true);
			colorsliders[i].valueProperty().addListener(getColorsliderListener(i,nodeno));
		}

		colorsliders[0].setLabelFormatter(getColorSliderLabels("red"));
		colorsliders[1].setLabelFormatter(getColorSliderLabels("green"));
		colorsliders[2].setLabelFormatter(getColorSliderLabels("blue"));

		return colorsliders;
	}

	public static void colorizeOption(int pathno)
	{
		Slider[] colorsliders = createColorSliders(pathno);
		Label colortitlelabel = new Label("Color");
		colortitlelabel.setFont(new Font(Graph.titleLabelSize));
		colortitlelabel.setLayoutX(0.045*Graph.defaultWidth);
		colortitlelabel.setLayoutY(0.05*Graph.defaultHeight);

		Executer.rightPane.getChildren().clear();
		Executer.rightPane.getChildren().addAll(colortitlelabel,colorsliders[0],colorsliders[1],colorsliders[2]);
	}

	public static void colorizeOption(int[] nodeno)
	{
		Slider[] colorsliders = createColorSliders(nodeno);
		Label colortitlelabel = new Label("Color");
		colortitlelabel.setFont(new Font(Graph.titleLabelSize));
		colortitlelabel.setLayoutX(0.045*Graph.defaultWidth);
		colortitlelabel.setLayoutY(0.05*Graph.defaultHeight);

		Executer.rightPane.getChildren().clear();
		Executer.rightPane.getChildren().addAll(colortitlelabel,colorsliders[0],colorsliders[1],colorsliders[2]);
	}

	public static Menu makeColorMenu()
	{
		Menu colorMenu = new Menu("Color");
		Menu resetColorMenu = new Menu("Reset");
		colorMenu.getItems().add(resetColorMenu);

		MenuItem nodeColorItem = new MenuItem("Nodes");
		MenuItem pathColorItem = new MenuItem("Paths");
		resetColorMenu.getItems().addAll(nodeColorItem,pathColorItem);

		nodeColorItem.setOnAction(resetNodesAction());
		pathColorItem.setOnAction(resetPathsAction());

		return colorMenu;
	}

	private static String[] getRoutes()
	{
		ArrayList<Path> paths = Executer.paths;
		ArrayList<int[]> nodes;
		int[] Nodes = new int[2];
		int noOfPaths = paths.size();
		int noOfEdges;
		String[] routes = new String[noOfPaths];
		String route;

		for(int i=0;i<noOfPaths;i++)
		{
			nodes = paths.get(i).getNodes();
			noOfEdges = nodes.size();
			route = "";

			for(int j=0;j<noOfEdges;j++)
			{
				Nodes = nodes.get(j);
				route = route + Nodes[0] + "-" + Nodes[1] + " ";
			}

			routes[i] = route;
		}

		return routes;
	}

	private static ChangeListener<Boolean> getCheckBoxListener(int checkboxcount)
	{
		ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>()
		{
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldvalue, Boolean newvalue) {
				if(checkboxcount == 0)
				{
					int noOfCheckboxes = checkboxes.length;

					if(newvalue)
					{
						checkboxes[1].setSelected(false);

						for(int i=2;i<noOfCheckboxes;i++)
						{
							checkboxes[i].setSelected(true);
						}
					}
				}
				else if(checkboxcount == 1)
				{
					int noOfCheckboxes = checkboxes.length;

					if(newvalue)
					{
						checkboxes[0].setSelected(false);

						for(int i=2;i<noOfCheckboxes;i++)
						{
							checkboxes[i].setSelected(false);
						}
					}
				}
				else
				{
					if(Executer.nodeMode.isSelected())
					{
						if(newvalue)
							checkboxes[1].setSelected(false);
						else
							checkboxes[0].setSelected(false);
						Mode.drawPaths();
					}
					else
					{
						if(newvalue)
							checkboxes[1].setSelected(false);
						else
							checkboxes[0].setSelected(false);
						drawPath();
					}
				}
			}
		};

		return changeListener;
	}

	private static void drawPath()
	{
		Graph.updateCircleColors();
		Graph.drawAll();
	}

	private static ChangeListener<Number> getColorsliderListener(int colorindex,int pathno)
	{
		ChangeListener<Number> changeListener = new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> observableValue, Number o, Number n)
			{
				int noOfLines = Executer.edgelines[pathno].length;
				int noOfArcs = Executer.edgearcs[pathno].length;
				Executer.pathColors[pathno][colorindex] = (int) (n.doubleValue()*255);
				int[] color = Executer.pathColors[pathno];

				for(int i=0;i<noOfLines;i++)
				{
					Executer.edgelines[pathno][i].setStroke(Color.rgb(color[0],color[1],color[2]));
					Executer.linearrowpolygons[pathno][i].setFill(Color.rgb(color[0],color[1],color[2]));
				}

				for(int i=0;i<noOfArcs;i++)
				{
					Executer.edgearcs[pathno][i].setStroke(Color.rgb(color[0],color[1],color[2]));
					Executer.arrowpolygons[pathno][i].setFill(Color.rgb(color[0],color[1],color[2]));
				}
			}
		};

		return changeListener;
	}

	private static ChangeListener<Number> getColorsliderListener(int colorindex,int[] nodeno)
	{
		ChangeListener<Number> changeListener = new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> observableValue, Number o, Number n)
			{
				int[] color = Executer.circleColors[nodeno[0]][nodeno[1]][nodeno[2]];
				color[colorindex] = (int) (n.doubleValue() * 255);
				Executer.circleColors[nodeno[0]][nodeno[1]][nodeno[2]] = color;
				Executer.nodecircles[nodeno[0]][nodeno[1]][nodeno[2]].setStroke(Color.rgb(color[0],color[1],color[2]));
			}
		};

		return changeListener;
	}

	private static EventHandler<ActionEvent> resetNodesAction()
	{
		EventHandler<ActionEvent> reset = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent act)
			{
				Executer.circleColors = Graph.createRedColors(Graph.noOfScreensX,Graph.noOfScreensY);
				Graph.drawAll();
			}
		};

		return reset;
	}

	private static EventHandler<ActionEvent> resetPathsAction()
	{
		EventHandler<ActionEvent> reset = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent a)
			{
				Executer.pathColors = Graph.createColors();
				Graph.drawAll();
			}
		};

		return reset;
	}

	private static StringConverter<Double> getColorSliderLabels(String string) {
		StringConverter<Double> output = new StringConverter<Double>() {
            public String toString(Double n) {
            	if(n<0.5)
            	{
            		return "0";
            	}
            	else
            	{
            		return string;
            	}
            }

			public Double fromString(String arg0) {
				return null;
			}
		};

		return output;
	}
}
