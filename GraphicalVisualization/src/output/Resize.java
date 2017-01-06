package output;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import model.Executer;

public class Resize {

	public static ChangeListener<? super Number> getListener(Scene scene,Canvas textcanvas,Pane pane)
	{
		final ChangeListener<Number> listener = new ChangeListener<Number>()
		{

			//Resets the canvas sizes and redraws the nodes and paths
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

				Executer.defaultHeight = scene.getHeight();
				Executer.defaultWidth = scene.getWidth();
				
				pane.getChildren().clear();
				GraphicsContext gc = textcanvas.getGraphicsContext2D();

				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
				textcanvas.setHeight(scene.getHeight());
				textcanvas.setWidth(0.2*scene.getWidth());
				
				pane.getChildren().clear();

				WindowContent.drawAll(textcanvas,pane, Executer.nodes, Executer.paths);
			}

		};

		return listener;
	}


}
