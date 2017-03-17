package output;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import model.Executer;

public class Export {
	public static EventHandler<ActionEvent> getExportListener()
	{
		EventHandler<ActionEvent> exportClick = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent a)
			{
		        Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd_HH_mm_ss");
		        WritableImage image = Executer.drawPane.snapshot(new SnapshotParameters(), null);
		        File file = new File("image_" + sdf.format(cal.getTime())+".png");

		        try {
		            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		        } catch (IOException e) {}
			}
		};

		return(exportClick);
	}
}
