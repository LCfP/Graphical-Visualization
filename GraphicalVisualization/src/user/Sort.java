package user;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import model.Executer;
import model.Path;
import output.Graph;

public class Sort {

	public static Menu makeSortMenu()
	{
		MenuItem menuItem;
		Menu sortMenu;
		ArrayList<String> attributeNames;
		int noOfAttributes;

		sortMenu = new Menu("Sort by");

		menuItem = new MenuItem("All");
		sortMenu.getItems().add(menuItem);
		menuItem.setOnAction(sortByAll());

	    if(Executer.paths.size()>0)
	    {
	    	attributeNames = Executer.pathAttributeNames;
	    	noOfAttributes = attributeNames.size();

	    	if(noOfAttributes>0)
	    	{
	    		for(int i=0;i<noOfAttributes;i++)
	    		{
		    		menuItem = new MenuItem(attributeNames.get(i));
		    		sortMenu.getItems().add(menuItem);
		    		menuItem.setOnAction(sortBy());
	    		}
	    	}
	    }

	    return sortMenu;
	}

	public static EventHandler<ActionEvent> sortByAll()
	{
		EventHandler<ActionEvent> sortClick = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent a)
			{
				Executer.sortingAttribute = "";
				Graph.drawAll();
			}
		};

		return(sortClick);
	}

	public static EventHandler<ActionEvent> sortBy()
	{
		EventHandler<ActionEvent> sortClick = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent a)
			{
					MenuItem menuItem = (MenuItem) (a.getSource());
					Executer.sortingAttribute = menuItem.getText();
					Graph.drawAll();
			}
		};

		return(sortClick);
	}

	public static int getAttributeNo(String attributeName)
	{
		int attributeNo =-1;
		ArrayList<String> attributeNames = Executer.pathAttributeNames;
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
		int attributeNo = Executer.pathAttributeNames.indexOf(attributeName);

		for(int i=0;i<noOfPaths;i++)
		{
			attribute = paths.get(i).getPathAttributes().get(attributeNo);
			if(!attributes.contains(attribute))
			{
				attributes.add(attribute);
			}
		}

		return(attributes);
	}
}
