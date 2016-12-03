package output;

import java.util.ArrayList;

import model.Executer;
import model.Node;

public class Coordinates {

	//Transforms the coordinates of the nodes to coordinates on the screen preserving the aspect ratio
	public static double[][] transformCoordinates(double[][] coordinates)
	{
		double xmin = Minimum(coordinates[0]);
		double xmax = Maximum(coordinates[0]);
		double ymin = Minimum(coordinates[1]);
		double ymax = Maximum(coordinates[1]);

		double width = Executer.defaultWidth;
		double height = Executer.defaultHeight;
		double unitlength;
		double margin;

		int length = coordinates[0].length;

		double[][] newCoordinates = new double[2][length];

		if((xmax-xmin)/width - (ymax-ymin)/height > 0)
		{
			unitlength = 0.9*width/(xmax-xmin);
			margin = 0.05*width;
		}
		else
		{
			unitlength = 0.9*height/(ymax-ymin);
			margin = 0.05*height;
		}

		newCoordinates[0] = adjustCoordinates(coordinates[0],xmin,margin,unitlength,true);
		newCoordinates[1] = adjustCoordinates(coordinates[1],ymax,margin,unitlength,false);

		return(newCoordinates);
	}

	//gets coordinates from the nodes
	public static double[][] getCoordinates(ArrayList<Node> nodes) {
		int length = nodes.size();
		double[][] coordinates = new double[2][length];

		for(int i=0;i<length;i++)
        {
        	coordinates[0][i]=nodes.get(i).getXcoordinate();
        	coordinates[1][i]=nodes.get(i).getYcoordinate();
        }

		return coordinates;
	}

	//adjusts the coordinates such that they can be drawn on the canvas
	private static double[] adjustCoordinates(double[] coordinates,double ref,double margin,double aspectratio,boolean x)
	{
		int length = coordinates.length;
		double[] newCoordinates = new double[length];

		if(x)
		{
			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = margin + (coordinates[i]-ref)*aspectratio;
			}
		}
		else
		{
			for(int i=0;i<length;i++)
			{
				newCoordinates[i] = margin + (ref-coordinates[i])*aspectratio;
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
