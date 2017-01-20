package model;

import java.util.ArrayList;

public class Node
{
	//Node has an x and y coordinate and a number
	private double realxcoordinate;
	private double realycoordinate;
	private double[][] virtualxcoordinates;
	private double[][] virtualycoordinates;
	private int number;

	//attributes of the node
	private ArrayList<String> Attributes;
	private ArrayList<String> AttributeNames;

	//Main class constructor
	public Node(double x, double y,int no)
	{
		realxcoordinate = x;
		realycoordinate = y;
		virtualxcoordinates = new double[1][1];
		virtualycoordinates = new double[1][1];
		virtualxcoordinates[0][0] = x;
		virtualycoordinates[0][0] = y;
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
		int[] noOfVirtualCoordinates = new int[2];
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);
		double[][] nodeVirtualXcoordinates = node.getVirtualXcoordinates();
		double[][] nodeVirtualYcoordinates = node.getVirtualYcoordinates();

		realxcoordinate = node.getRealXcoordinate();
		realycoordinate = node.getRealYcoordinate();

		noOfVirtualCoordinates[0] = nodeVirtualXcoordinates.length;
		noOfVirtualCoordinates[1] = nodeVirtualXcoordinates[0].length;
		virtualxcoordinates = new double[noOfVirtualCoordinates[0]][noOfVirtualCoordinates[1]];
		virtualycoordinates = new double[noOfVirtualCoordinates[0]][noOfVirtualCoordinates[1]];

		for(int x=0;x<noOfVirtualCoordinates[0];x++)
		{
			for(int y=0;y<noOfVirtualCoordinates[1];y++)
			{
				virtualxcoordinates[x][y] = nodeVirtualXcoordinates[x][y];
				virtualycoordinates[x][y] = nodeVirtualYcoordinates[x][y];
			}
		}

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
	public boolean equals(Node node){
		double[][] nodeVirtualXcoordinates = node.getVirtualXcoordinates();
		double[][] nodeVirtualYcoordinates = node.getVirtualYcoordinates();
		int[] noOfVirtualCoordinates = new int[2];
		boolean virtualCoordinatesTest = true;
		noOfVirtualCoordinates[0] = nodeVirtualXcoordinates.length;
		noOfVirtualCoordinates[1] = nodeVirtualXcoordinates[0].length;

		if(noOfVirtualCoordinates[0] == virtualxcoordinates.length & noOfVirtualCoordinates[1] == virtualxcoordinates[0].length)
		{
			for(int x=0;x<noOfVirtualCoordinates[0];x++)
			{
				if(!virtualCoordinatesTest)
				{
					break;
				}

				for(int y=0;y<noOfVirtualCoordinates[1];y++)
				{
					if(virtualxcoordinates[x][y] != nodeVirtualXcoordinates[x][y])
					{
						virtualCoordinatesTest = false;
						break;
					}
					if(virtualycoordinates[x][y] != nodeVirtualYcoordinates[x][y])
					{
						virtualCoordinatesTest = false;
						break;
					}
				}
			}
		}
		else
		{
			virtualCoordinatesTest = false;
		}

		if(node.getRealXcoordinate()==realxcoordinate & node.getRealYcoordinate()==realycoordinate
				& virtualCoordinatesTest & node.getNumber()==number
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

	//sets virtual coordinates
	public void setVirtualXcoordinates(double[][] newX)
	{
		int[] noOfNewCoordinates = new int[2];
		noOfNewCoordinates[0] = newX.length;
		noOfNewCoordinates[1] = newX[0].length;

		virtualxcoordinates = new double[noOfNewCoordinates[0]][noOfNewCoordinates[1]];

		for(int x=0;x<noOfNewCoordinates[0];x++)
		{
			for(int y=0;y<noOfNewCoordinates[1];y++)
			{
				virtualxcoordinates[x][y] = newX[x][y];
			}
		}
	}

	public void setVirtualYcoordinates(double[][] newY)
	{
		int[] noOfNewCoordinates = new int[2];
		noOfNewCoordinates[0] = newY.length;
		noOfNewCoordinates[1] = newY[0].length;

		virtualycoordinates = new double[noOfNewCoordinates[0]][noOfNewCoordinates[1]];

		for(int x=0;x<noOfNewCoordinates[0];x++)
		{
			for(int y=0;y<noOfNewCoordinates[1];y++)
			{
				virtualycoordinates[x][y] = newY[x][y];
			}
		}
	}

	public int noOfVirtualCoordinates()
	{
		int no = 0;

		for(int x=0;x<virtualxcoordinates.length;x++)
		{
			for(int y=0;y<virtualxcoordinates[0].length;y++)
			{
				if(virtualxcoordinates[x][y] != 0)
				{
					no++;
				}
			}
		}

		return(no);
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
	public double[][] getVirtualXcoordinates()
	{
		return virtualxcoordinates;
	}

	public double[][] getVirtualYcoordinates()
	{
		return virtualycoordinates;
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
