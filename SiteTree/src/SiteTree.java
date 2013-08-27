import java.util.*;


public class SiteTree {
	private SiteNode root;
	private HashMap<SiteNode, Set<SiteNode>> tree;
	
	public SiteTree()
	{
		root = null;
		tree = new HashMap<SiteNode, Set<SiteNode>>();
	}
	
	// returns the SiteNode matching this
	// url path
	public Set<SiteNode> get(String urlPath)
	{
		// search by urlPath
		SiteNode siteNode = new SiteNode(urlPath);
		return tree.get(siteNode);
	}
	
	public void add(SiteNode siteNode)
	{
		tree.put(siteNode, siteNode.getChildren());
		//System.out.println(tree);
	}
	
	public void remove(SiteNode siteNode)
	{
		tree.remove(siteNode);
	}
	
	public static void main(String[] args)
	{
		SiteTree siteTree = new SiteTree();

		// hard code in a few nodes, just to test
		SiteNode node1 = new SiteNode(".");
		SiteNode node2 = new SiteNode("my/profile");
		SiteNode node3 = new SiteNode("my/questions-and-answers");
		SiteNode node4 = new SiteNode("my/books");
		SiteNode node5 = new SiteNode("my/account/history");
		SiteNode node6 = new SiteNode("homework-help");
		SiteNode node7 = new SiteNode("sell-textbooks");
		SiteNode node8 = new SiteNode("u/okeefe-okeefe");
		
		// add parent child relationships
		
		node1.addChild(node2);
		node1.addChild(node3);
		node1.addChild(node4);
		node2.addChild(node3);
		node2.addChild(node8);
		node3.addChild(node5);
		node4.addChild(node7);
		node8.addChild(node6);
		
		siteTree.add(node1);
		siteTree.add(node2);
		siteTree.add(node3);
		siteTree.add(node4);
		siteTree.add(node5);
		siteTree.add(node6);
		siteTree.add(node7);
		siteTree.add(node8);
		
		System.out.println(node3.getDescendants());
		System.out.println(node2.getAncestors());
		System.out.println(node1.getAncestors());
		System.out.println(node1.getDescendants());
	}
}
