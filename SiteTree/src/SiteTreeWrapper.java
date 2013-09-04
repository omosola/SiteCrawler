import java.io.*;
import java.util.*;

public class SiteTreeWrapper {
	private SiteNode root;
	private HashMap<String, SiteNode> nameMap;
	
	public SiteTreeWrapper()
	{
		this.root = null;
		this.nameMap = new HashMap<String, SiteNode>();
	}
	
	/* ADD AND REMOVE NODES */
	public void createNode(String url, String parentUrl)
	{
		if (getNode(url) != null) return;
		
		SiteNode child = new SiteNode(url);
		SiteNode parent = getNode(parentUrl);
		if (parent == null)
		{
			setRoot(child);
		}
		else
		{
			parent.addChild(child);
		}
		
		this.nameMap.put(url, child);
	}
	
	/* SETTERS AND GETTERS */
	public SiteNode getNode(String url)
	{
		return this.nameMap.get(url);
	}
	
	public SiteNode getRoot()
	{
		return this.root;
	}
	
	/* QUERY METHODS */
	public int size()
	{
		return this.nameMap.size();
	}
	
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		string.append(this.root + " -> " + this.root.getChildren());
		for (SiteNode child: root.getChildren()) {
			string.append("\n" + child + " -> " + child.getChildren());
		}
		return string.toString();
	}
	
	/* STORAGE METHODS (SAVING AND LOADING) */
	public void save(String treeFileName)
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(treeFileName));
			write(bw, this.root);
			bw.close();
			System.out.println("Done writing to file!");
		} catch (IOException e) {
			System.out.println("Error while attempting to load your file: " + e.getMessage());
		}
	}
	
	private void write(BufferedWriter bw, SiteNode parent)
	{
		if (parent == null) return;
		else {
			try {
				bw.write("\n" + parent.getUrlPath());
				bw.write(" -> ");			
				Set<SiteNode> children = parent.getChildren();
				
				// write parent-child relationship for this parent node
				for (SiteNode child: children) {
					bw.write(child.getUrlPath() + ",");
				}
				
				// now descend into children nodes
				// to build up their parent->children relationship strings
				for (SiteNode child: children) {
					write(bw, child);
				}
			} catch (IOException e) {
				System.out.println("Error while writing tree for " + parent.getUrlPath() + " Msg: " + e.getMessage());
				return;
			}
		}
	}
	
	public void load(String treeFileName)
	{
		try {
			String line;
			
			BufferedReader br = new BufferedReader(new FileReader(treeFileName));
			while ((line = br.readLine()) != null) {
				parseLine(line);
			}
			br.close();
			System.out.println("Done loading from file.");
		} catch (IOException e) {
			System.out.println("Error while attempting to load your file: " + e.getMessage());
		}
	}
	
	// parses the line from loading
	// and turns it into parent and child nodes
	// which are added to the site tree
	private void parseLine(String line)
	{
		String[] relation = line.split(" -> ");
		if (relation.length != 2) return;
		// if relation.length == 1
		// this node has no children,
		// it must have already been added as the child
		// of some parent node. No action needs to be taken.
		String parentUrl = relation[0];
		String[] childUrls = relation[1].split(",");
		
		if (getNode(parentUrl) == null) {
			createNode(parentUrl, null);
		}
		
		for (String childUrl: childUrls) {
			createNode(childUrl, parentUrl);
		}
	}
	
	/* PRIVATE HELPER METHODS */
	private void setRoot(SiteNode node)
	{
		this.root = node;
	}
}