package output;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Executer;
import user.ControlPaths;

public class Mode {
	public static ChangeListener<Boolean> getNodeModeListener()
	{
		ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>()
		{
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldvalue, Boolean newvalue)
			{
				if(newvalue)
				{
					Executer.drawPane.getChildren().clear();

					ControlPaths.checkboxes[1].setSelected(true);
					ControlPaths.checkboxes[2].setSelected(true);

					Graph.drawNodes(1);
				}
				else
				{
					Executer.drawPane.getChildren().clear();
					Graph.drawAll();
				}
			}
		};
		return changeListener;
	}

	public static void drawPath(boolean newvalue,int checkboxcount)
	{
		int noOfEdges;

		if(newvalue)
		{
			noOfEdges = Executer.paths.get(checkboxcount-2).getNodes().size();
			ControlPaths.checkboxes[1].setSelected(false);

			for(int i=0;i<noOfEdges;i++)
			{
				Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][0] = 0;
				Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][1] = 150;
				Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][2] = 0;
				Executer.nodecircles[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0].setStroke(Color.rgb(
						Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][0],
						Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][1],
						Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][2]));
			}
		}
		else
		{
			noOfEdges = Executer.paths.get(checkboxcount-2).getNodes().size();
			ControlPaths.checkboxes[0].setSelected(false);

			for(int i=0;i<noOfEdges;i++)
			{
				Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][0] = 255;
				Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][1] = 0;
				Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][2] = 0;
				Executer.nodecircles[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0].setStroke(Color.rgb(
						Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][0],
						Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][1],
						Executer.circleColors[Executer.paths.get(checkboxcount-2).getNodes().get(i)[0]][0][0][2]));
			}
		}
	}
}
