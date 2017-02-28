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
        	coordinates[0][i]=nodes.get(i).getXcoordinate();
        	coordinates[1][i]=nodes.get(i).getYcoordinate();
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

	//calculates the normalized coordinates on 1x1 grid and the aspect ratio
	public static void calculateNormalizedCoordinates(ArrayList<Node> nodes)
	{
		double[][] coordinates = getRealCoordinates(nodes);
		int noOfNodes = nodes.size();

		double minx = Minimum(coordinates[0]);
		double miny = Minimum(coordinates[1]);
		double maxx = Maximum(coordinates[0]);
		double maxy = Maximum(coordinates[1]);

		double xmargin = 0.5*(maxy - miny) * Math.tan(Math.toRadians(30));
		double ymargin = 0.5*(maxx - minx) * Math.tan(Math.toRadians(30));

		double xwidth = 2*xmargin + maxx - minx;
		double ywidth = 2*ymargin + maxy - miny;
		double width = Math.max(xwidth,ywidth);

		double[][] normalizedCoordinates = new double[2][noOfNodes];

		//keeping the aspect ratio
		for(int i=0;i<noOfNodes;i++)
		{
			normalizedCoordinates[0][i] = (coordinates[0][i] - minx + xmargin) / width;
			normalizedCoordinates[1][i] = (maxy - coordinates[1][i] + ymargin) / width;
		}

		Executer.normalizedCoordinates = normalizedCoordinates;
		Executer.aspectratio = xwidth / ywidth;

		checkNodeOverlap(nodes);
	}

	//changes the virtual coordinates when 2 nodes overlap such that they are both visible
	public static void checkNodeOverlap(ArrayList<Node> nodes)
	{
		boolean horizontal = true;
		boolean unique = false;
		boolean next = false;
		int noOfNodes = nodes.size();
		double[][] normalizedcoordinates = Executer.normalizedCoordinates;
		double minimumDistanceSquared = 1/Math.pow(45,2);
		double distanceSquared;

		while(!unique)
		{
			unique = true;
			next = false;

			for(int i=0;i<(noOfNodes-1);i++)
			{
				if(next)
				{
					break;
				}
				else
				{
					for(int j=(i+1);j<noOfNodes;j++)
					{
						distanceSquared = Math.pow(normalizedcoordinates[0][i]-normalizedcoordinates[0][j],2)+
								Math.pow(normalizedcoordinates[1][i]-normalizedcoordinates[1][j],2);
						if(distanceSquared < minimumDistanceSquared)
						{
							if(horizontal)
							{
								normalizedcoordinates[0][j] = normalizedcoordinates[0][i] + 1.2*Math.sqrt(minimumDistanceSquared);

								horizontal = false;
								unique = false;
								next =  true;
								break;
							}
							else
							{
								normalizedcoordinates[1][j] = normalizedcoordinates[1][i] + 1.2*Math.sqrt(minimumDistanceSquared);

								horizontal = true;
								unique = false;
								next =  true;
								break;
							}
						}
					}
				}
			}
		}

		Executer.normalizedCoordinates = normalizedcoordinates;
	}
}
