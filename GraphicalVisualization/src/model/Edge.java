package model;

public class Edge
{
	private Node Node1;
	private Node Node2;

	//if direction is true, than the edge has direction from Node 1 to Node 2
	private boolean direction;

	//class constructor
	public Edge(Node node1, Node node2, boolean dir)
	{
		Node1 = node1;
		Node2 = node2;
		direction = dir;
	}

	//deep copy class constructor
	public Edge(Edge edge)
	{
		Node1 = new Node(edge.getNode1());
		Node2 = new Node(edge.getNode2());
		direction = edge.getDirection();
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
		if(edge.getNode1().equals(Node1) & edge.getNode2().equals(Node2) & edge.getDirection()==direction)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//this method is a superset of equals and gives true is the same nodes are in both edges regardless of the direction
	public boolean equivalent(Edge edge)
	{
		if((edge.getNode1().equals(Node1) & edge.getNode2().equals(Node2)) | (edge.getNode1().equals(Node2) & edge.getNode2().equals(Node1)))
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
		return("Node 1: "+Node1.toString()+", Node 2: "+Node2.toString()+", Direction: "+String.valueOf(direction));
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
}
