package model;

public class Node
{
	//Node has an x and y coordinate and a number
	private double xcoordinate;
	private double ycoordinate;
	private int number;

	//Main class constructor
	public Node(double x, double y,int no)
	{
		xcoordinate = x;
		ycoordinate = y;
		number = no;
	}

	//Deep copy constructor
	public Node(Node node)
	{
		xcoordinate = node.getXcoordinate();
		ycoordinate = node.getYcoordinate();
		number = node.getNumber();
	}

	//official equals method
	public boolean equals(Object obj)
	{
		if (obj==null)
		{
			return false;
		}
			
		Node node = (Node) obj;
			
		return equals(node);
	}
		
	//Equals method
	public boolean equals(Node node){
		if(node.getXcoordinate()==xcoordinate & node.getYcoordinate()==ycoordinate & node.getNumber()==number)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//toString method
		public String toString()
		{
			return("Number: "+String.valueOf(number)+", X-coordinate: "+String.valueOf(xcoordinate)+", Y-coordinate: "+String.valueOf(ycoordinate));
		}

	//Gives the coordinates
	public double getXcoordinate()
	{
		return xcoordinate;
	}

	public double getYcoordinate()
	{
		return ycoordinate;
	}

	public int getNumber(){
		return number;
	}
}
