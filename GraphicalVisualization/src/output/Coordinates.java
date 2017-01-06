package output;

import java.util.ArrayList;

import model.Executer;
import model.Node;

public class Coordinates 
{

	//gets real coordinates from the nodes
	public static double[][] getRealCoordinates(ArrayList<Node> nodes) {
		int length = nodes.size();
		double[][] coordinates = new double[2][length];

		for(int i=0;i<length;i++)
        {
        	coordinates[0][i]=nodes.get(i).getRealXcoordinate();
        	coordinates[1][i]=nodes.get(i).getRealYcoordinate();
        }

		return coordinates;
	}

	//gets virtual coordinates from the nodes
	public static double[][] getVirtualCoordinates(ArrayList<Node> nodes) {
		int length = nodes.size();
		double[][] coordinates = new double[2][length];

		for(int i=0;i<length;i++)
        {
        	coordinates[0][i]=nodes.get(i).getVirtualXcoordinate();
        	coordinates[1][i]=nodes.get(i).getVirtualYcoordinate();
        }

		return coordinates;
	}

	//Calculates the minimum of an array
	private static double Minimum(double[] array)
	{
		double min = array[0];
		int arraylength = array.length;

		for(int i=0;i<arraylength;i++)
		{
			if(array[i]<min)
			{
				min = array[i];
			}
		}

		return min;
	}

	//Calculates the maximum of an array
	private static double Maximum(double[] array)
	{
		double max = array[0];
		int arraylength = array.length;

		for(int i=0;i<arraylength;i++)
		{
			if(array[i]>max)
			{
				max = array[i];
			}
		}

		return max;
	}

	//adjusts the virtual coordinates of the nodes such that they can be drawn on the canvas
	public static void adjustVirtualCoordinates(ArrayList<Node> nodes)
	{
		double[][] coordinates = getRealCoordinates(nodes);
		int noOfNodes = nodes.size();

		double minx = Minimum(coordinates[0]);
		double miny = Minimum(coordinates[1]);
		double maxx = Maximum(coordinates[0]);
		double maxy = Maximum(coordinates[1]);

		double xmargin = (maxy - miny) * (1 - Math.cos(Math.toRadians(30)));
		double ymargin = (maxx - minx) * (1 - Math.cos(Math.toRadians(30)));

		double width = Executer.defaultWidth;
		double height = Executer.defaultHeight;

		for(int i=0;i<noOfNodes;i++)
		{
			//keeping the aspect ratio
			if((maxx-minx+2*xmargin)>(maxy-miny+2*ymargin))
			{
				nodes.get(i).setVirtualXcoordinate(width*(-minx+xmargin+coordinates[0][i])/(maxx-minx+2*xmargin));
				nodes.get(i).setVirtualYcoordinate(height*(maxy+ymargin-coordinates[1][i])/(maxx-minx+2*xmargin));
			}
			else
			{
				nodes.get(i).setVirtualXcoordinate(width*(-minx+xmargin+coordinates[0][i])/(maxy-miny+2*ymargin));
				nodes.get(i).setVirtualYcoordinate(height*(maxy+ymargin-coordinates[1][i])/(maxy-miny+2*ymargin));
			}
		}

		checkNodeOverlap(nodes);
	}

	//changes the virtual coordinates when 2 nodes overlap such that they are both visible
	public static void checkNodeOverlap(ArrayList<Node> nodes)
	{
		double[][] coordinates = getVirtualCoordinates(nodes);
		int noOfNodes = nodes.size();
		double minimumDistance = 1.5 * Math.min(Executer.defaultHeight, Executer.defaultWidth) / 60;
		double newX;
		double newY;

		for(int i=0;i<(noOfNodes-1);i++)
		{
			for(int j=i;j<noOfNodes;j++)
			{
				if(Math.abs(coordinates[0][i]-coordinates[0][j])<minimumDistance
						& Math.abs(coordinates[1][i]-coordinates[1][j])<minimumDistance)
				{
					newX = 0.5 * (coordinates[0][i] + coordinates[0][j]);
					newY = 0.5 * (coordinates[1][i] + coordinates[1][j]);

					nodes.get(i).setVirtualXcoordinate(newX-0.5*minimumDistance);
					nodes.get(i).setVirtualYcoordinate(newY);
					nodes.get(j).setVirtualXcoordinate(newX+0.5*minimumDistance);
					nodes.get(j).setVirtualYcoordinate(newY);
				}
			}
		}
	}
}
