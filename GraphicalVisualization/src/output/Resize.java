package output;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Executer;

public class Resize {

	public static ChangeListener<? super Number> getListener(Scene scene,Pane drawPane)
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


}
