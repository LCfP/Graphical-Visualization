package user;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Executer;
import model.Node;
import model.Path;
import output.Graph;

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
		checkboxtitlelabel.setLayoutX(0.025*Graph.defaultWidth);
		checkboxtitlelabel.setLayoutY(0.18*Graph.defaultHeight);
		Executer.rightPane.getChildren().add(checkboxtitlelabel);

		checkboxes[0] = new CheckBox("Show all");
		checkboxes[0].setSelected(true);
		checkboxes[0].setLayoutX(0.01*Graph.defaultWidth);
		checkboxes[0].setLayoutY(0.25*Graph.defaultHeight);
		checkboxes[0].selectedProperty().addListener(getCheckBoxListener(0));
		Executer.rightPane.getChildren().add(checkboxes[0]);

		checkboxes[1] = new CheckBox("Show none");
		checkboxes[1].setSelected(false);
		checkboxes[1].setLayoutX(0.01*Graph.defaultWidth);
		checkboxes[1].setLayoutY(0.25*Graph.defaultHeight+25);
		checkboxes[1].selectedProperty().addListener(getCheckBoxListener(1));
		Executer.rightPane.getChildren().add(checkboxes[1]);

		for(int i=2;i<noOfCheckboxes;i++)
		{
			checkboxes[i] = new CheckBox(routes[i-2]);
			checkboxes[i].setSelected(true);
			checkboxes[i].setLayoutX(0.01*Graph.defaultWidth);
			checkboxes[i].setLayoutY(0.25*Graph.defaultHeight+25*(i+0.5));
			checkboxes[i].selectedProperty().addListener(getCheckBoxListener(i));
			Executer.rightPane.getChildren().add(checkboxes[i]);
		}
	}

	public static void updateRightPane()
	{
		if(Executer.sortingAttribute.equals(""))
		{
			int noOfCheckboxes = checkboxes.length;

			checkboxtitlelabel.setLayoutY(0.18*Graph.defaultHeight);
			checkboxes[0].setLayoutY(0.25*Graph.defaultHeight);
			checkboxes[1].setLayoutY(0.25*Graph.defaultHeight+25);

			for(int i=2;i<noOfCheckboxes;i++)
			{
				checkboxes[i].setLayoutY(0.25*Graph.defaultHeight+25*(i+0.5));
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
			
			checkboxtitlelabel.setLayoutY(0.18*Graph.defaultHeight);
			checkboxes[0].setLayoutY(0.25*Graph.defaultHeight);
			checkboxes[1].setLayoutY(0.25*Graph.defaultHeight+25);

			for(int i=0;i<noOfPathAttributes;i++)
			{
				attribute = pathAttributes.get(i);
				
				for(int j=0;j<noOfPaths;j++)
				{
					path = Executer.paths.get(j);
					
					if(path.getPathAttributes().get(attributeNo).equals(attribute))
					{
						checkboxes[j+2].setLayoutY(0.25*Graph.defaultHeight+25*((i+1)*0.5+checkboxcounter+2));
						checkboxcounter ++;
					}
				}
			}
		}
	}

	private static String[] getRoutes()
	{
		ArrayList<Path> paths = Executer.paths;
		ArrayList<Node[]> nodes;
		Node[] Nodes = new Node[2];
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
				route = route + Nodes[0].getNumber() + "-" + Nodes[1].getNumber() + " ";
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
				Pane drawPane = Executer.drawPane;
				int noOfLines;
				int noOfArcs;

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
					if(newvalue)
					{
						noOfLines = Executer.edgelines[checkboxcount-2].length;
						checkboxes[1].setSelected(false);

						for(int i=0;i<noOfLines;i++)
						{
							drawPane.getChildren().addAll(Executer.edgelines[checkboxcount-2][i],Executer.linearrowpolygons[checkboxcount-2][i]);
						}

						noOfArcs = Executer.edgearcs[checkboxcount-2].length;

						for(int i=0;i<noOfArcs;i++)
						{
							drawPane.getChildren().addAll(Executer.edgearcs[checkboxcount-2][i],Executer.arrowpolygons[checkboxcount-2][i]);
						}
					}
					else
					{
						noOfLines = Executer.edgelines[checkboxcount-2].length;
						checkboxes[0].setSelected(false);

						for(int i=0;i<noOfLines;i++)
						{
							drawPane.getChildren().remove(Executer.edgelines[checkboxcount-2][i]);
							drawPane.getChildren().remove(Executer.linearrowpolygons[checkboxcount-2][i]);
						}

						noOfArcs = Executer.edgearcs[checkboxcount-2].length;

						for(int i=0;i<noOfArcs;i++)
						{
							drawPane.getChildren().remove(Executer.edgearcs[checkboxcount-2][i]);
							drawPane.getChildren().remove(Executer.arrowpolygons[checkboxcount-2][i]);
						}
					}
				}
			}
		};

		return changeListener;
	}
}
