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
	public static double[][][][] getVirtualCoordinates(ArrayList<Node> nodes) {
		int length = nodes.size();
		int noOfScreensX = nodes.get(0).getVirtualXcoordinates().length;
		int noOfScreensY = nodes.get(0).getVirtualXcoordinates()[0].length;
		double[][][][] coordinates = new double[2][length][noOfScreensX][noOfScreensY];

		for(int i=0;i<length;i++)
        {
        	coordinates[0][i]=nodes.get(i).getVirtualXcoordinates();
        	coordinates[1][i]=nodes.get(i).getVirtualYcoordinates();
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
	public static void adjustVirtualCoordinates(ArrayList<Node> nodes,int noOfScreensX,int noOfScreensY,int noOfScreens)
	{
		double[][] coordinates = getRealCoordinates(nodes);
		int noOfNodes = nodes.size();
		double aspectratio;

		double minx = Minimum(coordinates[0]);
		double miny = Minimum(coordinates[1]);
		double maxx = Maximum(coordinates[0]);
		double maxy = Maximum(coordinates[1]);

		double xmargin = (maxy - miny) * (1 - Math.cos(Math.toRadians(30)));
		double ymargin = (maxx - minx) * (1 - Math.cos(Math.toRadians(30)));

		double width = 0.8 * WindowContent.defaultWidth  / noOfScreensX ;
		double height = (WindowContent.defaultHeight - Executer.menuBarHeight ) / noOfScreensY;

		double[][] virtualXCoordinates = new double[noOfScreensX][noOfScreensY];
		double[][] virtualYCoordinates = new double[noOfScreensX][noOfScreensY];

		//keeping the aspect ratio
		if(((maxx - minx + 2*xmargin)/width) > ((maxy - miny + 2*ymargin)/height))
		{
			aspectratio = (maxx - minx + 2*xmargin)/width;

			for(int i=0;i<noOfNodes;i++)
			{
				for(int x=0;x<noOfScreensX;x++)
				{
					for(int y=0;y<noOfScreensY;y++)
					{
						if((noOfScreensX*y+x)<noOfScreens)
						{
							virtualXCoordinates[x][y] = 0.2*WindowContent.defaultWidth+width*x+(coordinates[0][i]+xmargin-minx)/aspectratio;
							virtualYCoordinates[x][y] = Executer.menuBarHeight+height*y+ymargin+(maxy+ymargin-coordinates[1][i])/aspectratio;
						}
					}
				}

				nodes.get(i).setVirtualXcoordinates(virtualXCoordinates);
				nodes.get(i).setVirtualYcoordinates(virtualYCoordinates);
			}
		}
		else
		{
			aspectratio = (maxy - miny + 2*ymargin)/height;
			for(int i=0;i<noOfNodes;i++)
			{
				for(int x=0;x<noOfScreensX;x++)
				{
					for(int y=0;y<noOfScreensY;y++)
					{
						if((noOfScreensX*y+x)<noOfScreens)
						{
							virtualXCoordinates[x][y] = 0.2*WindowContent.defaultWidth+width*x+xmargin+(coordinates[0][i]+ymargin-minx)/aspectratio;
							virtualYCoordinates[x][y] = Executer.menuBarHeight+height*y+ymargin+(maxy+ymargin-coordinates[1][i])/aspectratio;
						}
					}
				}

				nodes.get(i).setVirtualXcoordinates(virtualXCoordinates);
				nodes.get(i).setVirtualYcoordinates(virtualYCoordinates);
			}
		}

		checkNodeOverlap(nodes,noOfScreens);
	}

	//changes the virtual coordinates when 2 nodes overlap such that they are both visible
	public static void checkNodeOverlap(ArrayList<Node> nodes,int noOfScreens)
	{
		int noOfNodes = nodes.size();
		double[][][][] virtualcoordinates = getVirtualCoordinates(nodes);
		int noOfScreensX = virtualcoordinates[0][0].length;
		int noOfScreensY = virtualcoordinates[0][0][0].length;
		double minimumDistance = 1.5 * Math.min(WindowContent.defaultHeight, 0.8*WindowContent.defaultWidth) / 60 / noOfScreensX;
		double[][] newX1 = new double[noOfScreensX][noOfScreensY];
		double[][] newY1 = new double[noOfScreensX][noOfScreensY];
		double[][] newX2 = new double[noOfScreensX][noOfScreensY];
		double[][] newY2 = new double[noOfScreensX][noOfScreensY];

		for(int i=0;i<(noOfNodes-1);i++)
		{
			for(int j=(i+1);j<noOfNodes;j++)
			{
				if(Math.abs(virtualcoordinates[0][i][0][0]-virtualcoordinates[0][j][0][0])<minimumDistance
						& Math.abs(virtualcoordinates[1][i][0][0]-virtualcoordinates[1][j][0][0])<minimumDistance)
				{
					for(int x=0;x<noOfScreensX;x++)
					{
						for(int y=0;y<noOfScreensY;y++)
						{
							if((noOfScreensX*y+x)<noOfScreens)
							{
								newX1[x][y] = 0.5 * (virtualcoordinates[0][i][x][y] + virtualcoordinates[0][j][x][y])-0.5*minimumDistance;
								newX2[x][y] = 0.5 * (virtualcoordinates[0][i][x][y] + virtualcoordinates[0][j][x][y])+0.5*minimumDistance;
								newY1[x][y] = 0.5 * (virtualcoordinates[1][i][x][y] + virtualcoordinates[1][j][x][y]);
								newY2[x][y] = 0.5 * (virtualcoordinates[1][i][x][y] + virtualcoordinates[1][j][x][y]);
							}
						}
					}

					nodes.get(i).setVirtualXcoordinates(newX1);
					nodes.get(i).setVirtualYcoordinates(newY1);
					nodes.get(j).setVirtualXcoordinates(newX2);
					nodes.get(j).setVirtualYcoordinates(newY2);
				}
			}
		}
	}
}
