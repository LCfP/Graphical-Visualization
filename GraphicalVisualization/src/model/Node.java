package model;

import java.util.ArrayList;

public class Node
{
	//Node has an x and y coordinate and a number
	private double xcoordinate;
	private double ycoordinate;
	private int number;

	//attributes of the node
	private ArrayList<String> Attributes;
	private ArrayList<String> AttributeNames;

	//Main class constructor
	public Node(double x, double y,int no)
	{
		xcoordinate = x;
		ycoordinate = y;
		number = no;
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);
	}

	//Deep copy constructor
	public Node(Node node)
	{
		ArrayList<String> nodeAttributes = node.getAttributes();
		ArrayList<String> nodeAttributeNames = node.getAttributeNames();
		int noOfAttributes = nodeAttributes.size();
		int noOfAttributeNames = nodeAttributeNames.size();
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);

		xcoordinate = node.getXcoordinate();
		ycoordinate = node.getYcoordinate();

		number = node.getNumber();

		for(int i=0;i<noOfAttributes;i++)
		{
			Attributes.add(nodeAttributes.get(i));
		}

		for(int i=0;i<noOfAttributeNames;i++)
		{
			AttributeNames.add(nodeAttributeNames.get(i));
		}
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
	public boolean equals(Node node)
	{
		if(node.getXcoordinate()==xcoordinate & node.getYcoordinate()==ycoordinate & node.getNumber()==number
			& node.getAttributes().equals(Attributes) & node.getAttributeNames().equals(AttributeNames))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void addAttribute(String attribute)
	{
		Attributes.add(attribute);
	}

	public void addAttributeName(String attributename)
	{
		AttributeNames.add(attributename);
	}

	//remove attributes
	public void removeAttributes()
	{
		Attributes = new ArrayList<String>(0);
	}

	public void removeAttributeNames()
	{
		AttributeNames = new ArrayList<String>(0);
	}

	//Gives the real coordinates
	public double getXcoordinate()
	{
		return xcoordinate;
	}

	public double getYcoordinate()
	{
		return ycoordinate;
	}

	public int getNumber()
	{
		return number;
	}

	//Gives the attributes
	public ArrayList<String> getAttributes()
	{
		return Attributes;
	}

	public ArrayList<String> getAttributeNames()
	{
		return AttributeNames;
	}
}
