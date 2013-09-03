import java.util.*;


public class SiteTree {
	private SiteNode root;
	private HashMap<SiteNode, Set<SiteNode>> tree;
	private HashMap<String, SiteNode> nameMap;
	
	public SiteTree()
	{
		root = null;
		//tree = new HashMap<SiteNode, Set<SiteNode>>();
		nameMap = new HashMap<String, SiteNode>();
	}
	
	// returns the SiteNode matching this
	// url path
	public SiteNode get(String urlPath)
	{
		return nameMap.get(urlPath);
	}
	
	public void setRoot(SiteNode node)
	{
		root = node;
	}
	
	public void add(SiteNode siteNode)
	{
		if (root == null) {
			root = siteNode;
		}
		
		nameMap.put(siteNode.getUrlPath(), siteNode);
	}
	
	/**
	 * 
	 * @param urlPath
	 */
	public void add(String urlPath)
	{
		SiteNode node = new SiteNode(urlPath);
		add(node);
	}
	
	public void remove(SiteNode siteNode)
	{
		//tree.remove(siteNode);
		nameMap.remove(siteNode.getUrlPath());
	}
	
	public void remove(String urlPath)
	{
		nameMap.remove(urlPath);
	}
	
	public SiteNode getRoot()
	{
		return root;
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
		
		SiteNode siteNode = siteTree.get("my/profile");
		if (siteNode != null)
			System.out.println(siteNode.getDescendants());
	}
	
	@Override
	public String toString() {
		return nameMap.toString();
	}
}
