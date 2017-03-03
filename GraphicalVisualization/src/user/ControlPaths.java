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

	public static void createCheckboxes()
	{
		Pane checkboxPane = new Pane();
		String[] routes = getRoutes();
		int noOfCheckboxes = routes.length;
		checkboxes = new CheckBox[noOfCheckboxes+2];
		Executer.rightPane.setContent(checkboxPane);

		Label checkboxtitlelabel = new Label("Paths shown");
		checkboxtitlelabel.setFont(new Font(Graph.titleLabelSize));
		checkboxtitlelabel.setLayoutX(0.03*Graph.defaultWidth);
		checkboxtitlelabel.setLayoutY(0.05*Graph.defaultHeight);
		checkboxPane.getChildren().add(checkboxtitlelabel);

		checkboxes[0] = new CheckBox("Show all");
		checkboxes[0].setSelected(true);
		checkboxes[0].setLayoutX(0.01*Graph.defaultWidth);
		checkboxes[0].setLayoutY(0.1*Graph.defaultHeight);
		checkboxes[0].selectedProperty().addListener(getCheckBoxListener(0));
		checkboxPane.getChildren().add(checkboxes[0]);

		checkboxes[1] = new CheckBox("Show none");
		checkboxes[1].setSelected(false);
		checkboxes[1].setLayoutX(0.01*Graph.defaultWidth);
		checkboxes[1].setLayoutY(0.1*Graph.defaultHeight+25);
		checkboxes[1].selectedProperty().addListener(getCheckBoxListener(1));
		checkboxPane.getChildren().add(checkboxes[1]);

		for(int i=0;i<noOfCheckboxes;i++)
		{
			checkboxes[i+2] = new CheckBox(routes[i]);
			checkboxes[i+2].setSelected(true);
			checkboxes[i+2].setLayoutX(0.01*Graph.defaultWidth);
			checkboxes[i+2].setLayoutY(0.1*Graph.defaultHeight+25*(i+2.5));
			checkboxes[i+2].selectedProperty().addListener(getCheckBoxListener(i+2));
			checkboxPane.getChildren().add(checkboxes[i+2]);
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
