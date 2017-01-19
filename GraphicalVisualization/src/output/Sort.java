package output;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import model.Executer;
import model.Path;

public class Sort {

	public static void makeSortMenu(Pane drawPane)
	{
	    Path path;
		MenuItem menuItem;
		Menu sortMenu;
		ArrayList<String> attributeNames;
		int noOfAttributes;

		Executer.sortMenu = new Menu("Sort by");

		sortMenu = Executer.sortMenu;
		menuItem = new MenuItem("All");
		sortMenu.getItems().add(menuItem);
		menuItem.setOnAction(sortByAll(drawPane));

	    if(Executer.paths.size()>0)
	    {
	    	path = Executer.paths.get(0);
	    	attributeNames = path.getAttributeNames();
	    	noOfAttributes = attributeNames.size();

	    	if(noOfAttributes>0)
	    	{
	    		for(int i=0;i<noOfAttributes;i++)
	    		{
		    		menuItem = new MenuItem(attributeNames.get(i));
		    		sortMenu.getItems().add(menuItem);
		    		menuItem.setOnAction(sortBy(drawPane));
	    		}
	    	}
	    }
	}

	public static EventHandler<ActionEvent> sortByAll(Pane drawPane)
	{
		EventHandler<ActionEvent> sortClick = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent a)
			{
				Executer.attributeName = "";
				WindowContent.drawAll(drawPane,Executer.nodes,Executer.paths);
			}
		};

		return(sortClick);
	}

	public static EventHandler<ActionEvent> sortBy(Pane drawPane)
	{
		EventHandler<ActionEvent> sortClick = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent a)
			{
					MenuItem menuItem = (MenuItem) (a.getSource());
					Executer.attributeName = menuItem.getText();
					WindowContent.drawAll(drawPane,Executer.nodes,Executer.paths);
			}
		};

		return(sortClick);
	}

	public static int getAttributeNo(String attributeName)
	{
		int attributeNo =-1;
		Path path = Executer.paths.get(0);
		ArrayList<String> attributeNames = path.getAttributeNames();
		int noOfAttributes = attributeNames.size();

		for(int i=0;i<noOfAttributes;i++)
		{
			if(attributeName.equals(attributeNames.get(i)))
			{
				attributeNo = i;
				break;
			}
		}

		return(attributeNo);
	}

	public static ArrayList<String> getDistinctAttributes(String attributeName)
	{
		ArrayList<String> attributes = new ArrayList<String>(0);
		ArrayList<Path> paths = Executer.paths;
		String attribute;
		int noOfPaths = paths.size();
		int attributeNo = paths.get(0).getAttributeNames().indexOf(attributeName);

		for(int i=0;i<noOfPaths;i++)
		{
			attribute = paths.get(i).getAttributes().get(attributeNo);
			if(!attributes.contains(attribute))
			{
				attributes.add(attribute);
			}
		}

		return(attributes);
	}
}
