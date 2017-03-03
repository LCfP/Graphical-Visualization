package user;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import model.Executer;
import output.Graph;

public class Zoom {
	public static Slider zoomslider;
	public static double zoom = 1;

	public static void createZoomSlider()
	{
		Label zoomLabel = new Label("Zoom");
		zoomLabel.setFont(new Font(Graph.titleLabelSize));
		zoomLabel.setLayoutX(0.045*Graph.defaultWidth);
		zoomLabel.setLayoutY(0.05*Graph.defaultHeight);

		zoomslider = new Slider(1,10,1);
		zoomslider.setLayoutX(0.01*Graph.defaultWidth);
		zoomslider.setLayoutY(0.1*Graph.defaultHeight);
		zoomslider.setMinorTickCount(1);
		zoomslider.setMajorTickUnit(9);
		zoomslider.setShowTickLabels(true);
		zoomslider.setShowTickMarks(true);
		zoomslider.setLabelFormatter(new StringConverter<Double>() {
            public String toString(Double n) {
            	if(n<5.5)
            	{
            		return "-";
            	}
            	else
            	{
            		return "+";
            	}
            }

			public Double fromString(String arg0) {
				return null;
			}
		});
		zoomslider.valueProperty().addListener(zoomsliderListener());
		Executer.rightPane.getChildren().addAll(zoomLabel,zoomslider);
	}

	private static ChangeListener<Number> zoomsliderListener()
	{
		ChangeListener<Number> changeListener = new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				zoom = newValue.doubleValue();
				Graph.drawAll();
			}
		};

		return changeListener;
	}
}
