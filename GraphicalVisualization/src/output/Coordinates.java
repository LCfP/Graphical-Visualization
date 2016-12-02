package output;

import model.Executer;

public class Coordinates {

	//Transforms the coordinates of the nodes to coordinates on the screen preserving the aspect ratio
	public static double[][] transformCoordinates(double[] xcoordinates,double[]ycoordinates)
	{
		double xmin = Minimum(xcoordinates);
		double xmax = Maximum(xcoordinates);
		double ymin = Minimum(ycoordinates);
		double ymax = Maximum(ycoordinates);

		System.out.println(xmin);
		System.out.println(ymin);
		System.out.println(xmax);
		System.out.println(ymax);

		int width = Executer.defaultWidth;
		int height = Executer.defaultHeight;
		double aspectratio;
		double margin;

		int length = xcoordinates.length;

		double[][] newCoordinates = new double[2][length];

		if((xmax-xmin)/width - (ymax-ymin)/height > 0)
		{
			aspectratio = width/(xmax-xmin);
			margin = width;
		}
		else
		{
			aspectratio = height/(ymax-ymin);
			margin = height;
		}

		System.out.println(aspectratio);

		newCoordinates[0] = adjustCoordinates(xcoordinates,xmin,margin,aspectratio,true);
		newCoordinates[1] = adjustCoordinates(ycoordinates,ymin,margin,aspectratio,false);

		return(newCoordinates);
	}

	//adjusts the coordinates such that they can be drawn on the canvas
	private static double[] adjustCoordinates(double[] coordinates,double min,double margin,double aspectratio,boolean x)
	{
		int length = coordinates.length;
		double[] newCoordinates = new double[length];

		if(x)
		{
			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = 0.05*margin + 0.9*(coordinates[i]-min)*aspectratio;
			}
		}
		else
		{
			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = 0.95*margin - 0.9*(coordinates[i]-min)*aspectratio;
			}
		}

		return newCoordinates;

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
}
