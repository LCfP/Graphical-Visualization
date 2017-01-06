package model;

public class Node
{
	//Node has an x and y coordinate and a number
	private double realxcoordinate;
	private double realycoordinate;
	private double virtualxcoordinate;
	private double virtualycoordinate;
	private int number;
	private boolean depot;

	//Main class constructor
	public Node(double x, double y,int no,boolean dep)
	{
		realxcoordinate = x;
		realycoordinate = y;
		virtualxcoordinate = x;
		virtualycoordinate = y;
		number = no;
		depot = dep;
	}

	//Deep copy constructor
	public Node(Node node)
	{
		realxcoordinate = node.getRealXcoordinate();
		realycoordinate = node.getRealYcoordinate();
		virtualxcoordinate = node.getVirtualXcoordinate();
		virtualycoordinate = node.getVirtualYcoordinate();
		number = node.getNumber();
		depot = node.isDepot();
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
		if(node.getRealXcoordinate()==realxcoordinate & node.getRealYcoordinate()==realycoordinate
				& node.getVirtualXcoordinate()==virtualxcoordinate
				& node.getVirtualYcoordinate()==virtualycoordinate & node.getNumber()==number
				& node.isDepot() == depot)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//sets virtual coordinates
	public void setVirtualXcoordinate(double newX)
	{
		virtualxcoordinate = newX;
	}

	public void setVirtualYcoordinate(double newY)
	{
		virtualycoordinate = newY;
	}

	//Gives the real coordinates
	public double getRealXcoordinate()
	{
		return realxcoordinate;
	}

	public double getRealYcoordinate()
	{
		return realycoordinate;
	}

	//Gives the virtual coordinates
	public double getVirtualXcoordinate()
	{
		return virtualxcoordinate;
	}

	public double getVirtualYcoordinate()
	{
		return virtualycoordinate;
	}

	public int getNumber()
	{
		return number;
	}

	public boolean isDepot()
	{
		return depot;
	}
}
