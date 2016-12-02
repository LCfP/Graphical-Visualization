package output;

import model.Executer;

public class Coordinates {

	//Transforms the x-coordinates of the nodes to x-coordinates on the screen
	public static double[] transformXCoordinates(double[] coordinates)
	{
		double min;
		double max;
		int length = coordinates.length;
		double[] newCoordinates = new double[length];

		min = max = coordinates[0];

		if(length>0)
		{
			for(int i=1;i<length;i++)
			{
				if(coordinates[i]<min)
				{
					min=coordinates[i];
				}

				if(coordinates[i]>max)
				{
					max=coordinates[i];
				}
			}

			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = 0.05*Executer.defaultWidth + 0.9*Executer.defaultWidth*(coordinates[i]-min)/(max-min);
			}
		}
		else{
			newCoordinates[0] = 0;
		}

		return(newCoordinates);
	}

	//Transforms the y-coordinates of the nodes to y-coordinates on the screen
	public static double[] transformYCoordinates(double[] coordinates)
	{
		double min;
		double max;
		int length = coordinates.length;
		double[] newCoordinates = new double[length];

		min = max = coordinates[0];

		if(length>0)
		{
			for(int i=1;i<length;i++)
			{
				if(coordinates[i]<min)
				{
					min=coordinates[i];
				}

				if(coordinates[i]>max)
				{
					max=coordinates[i];
				}
			}

			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = 0.95*Executer.defaultHeigth - 0.9*Executer.defaultHeigth*(coordinates[i]-min)/(max-min);
			}
		}
		else{
			newCoordinates[0] = 0;
		}

		return(newCoordinates);
	}
}
