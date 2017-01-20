package model;

import java.util.ArrayList;

public class Edge
{
	private Node Node1;
	private Node Node2;

	//if direction is true, than the edge has direction from Node 1 to Node 2
	private boolean direction;

	//attributes of the edge
	private ArrayList<String> Attributes;
	private ArrayList<String> AttributeNames;

	//counts the number of preceding edges between Node 1 and Node 2
	private int virtualordering;

	//the total number of this edge that exists
	private int totalcopies;

	//class constructor
	public Edge(Node node1, Node node2, boolean dir,int ordering)
	{
		Node1 = node1;
		Node2 = node2;
		direction = dir;
		virtualordering = ordering;
		totalcopies = 0;
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);
	}

	//deep copy class constructor
	public Edge(Edge edge)
	{
		ArrayList<String> edgeAttributes = edge.getAttributes();
		ArrayList<String> edgeAttributeNames = edge.getAttributeNames();
		Attributes = new ArrayList<String>(0);
		AttributeNames = new ArrayList<String>(0);
		int noOfAttributes = edgeAttributes.size();
		int noOfAttributeNames = edgeAttributeNames.size();
		Node1 = new Node(edge.getNode1());
		Node2 = new Node(edge.getNode2());
		direction = edge.getDirection();
		virtualordering = edge.getVirtualordering();
		totalcopies = edge.getTotalcopies();

		for(int i=0;i<noOfAttributes;i++)
		{
			Attributes.add(edgeAttributes.get(i));
		}

		for(int i=0;i<noOfAttributeNames;i++)
		{
			AttributeNames.add(edgeAttributeNames.get(i));
		}
	}

	//official equals method
	public boolean equals(Object obj)
	{
		if (obj==null)
		{
			return false;
		}

		Edge edge = (Edge) obj;

		return equals(edge);
	}

	//equals method
	public boolean equals(Edge edge)
	{
		if(edge.getNode1().equals(Node1) & edge.getNode2().equals(Node2) & edge.getDirection()==direction
				& edge.getVirtualordering()==virtualordering & edge.getTotalcopies()==totalcopies
				& edge.getAttributes().equals(Attributes) & edge.getAttributeNames().equals(AttributeNames))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//set virtual ordering
	public void setVirtualordering(int ordering)
	{
		virtualordering = ordering;
	}

	//set total copies
	public void setTotalcopies(int copies)
	{
		totalcopies = copies;
	}

	//add attribute
	public void addAttribute(String attribute)
	{
		Attributes.add(attribute);
	}

	public void addAttributeName(String attributeName)
	{
		AttributeNames.add(attributeName);
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

	//return elements
	public Node getNode1(){
		return Node1;
	}

	public Node getNode2(){
		return Node2;
	}

	public boolean getDirection()
	{
		return direction;
	}

	public int getVirtualordering() {
		return virtualordering;
	}

	public int getTotalcopies()
	{
		return totalcopies;
	}

	//returns Attributes
	public ArrayList<String> getAttributes()
	{
		return Attributes;
	}

	public ArrayList<String> getAttributeNames()
	{
		return AttributeNames;
	}
}
