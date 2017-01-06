package output;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import model.Executer;

public class Resize {

	public static ChangeListener<? super Number> getListener(Scene scene,GraphicsContext gc)
	{
		final ChangeListener<Number> listener = new ChangeListener<Number>()
		{

			//Resets the canvas sizes and redraws the nodes and paths
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				Executer.defaultHeight = scene.getHeight();
				Executer.defaultWidth = scene.getWidth();

				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
				gc.getCanvas().setHeight(scene.getHeight());
				gc.getCanvas().setWidth(scene.getWidth());

				WindowContent.drawAll(gc, Executer.nodes, Executer.paths);
			}

		};

		return listener;
	}


}
