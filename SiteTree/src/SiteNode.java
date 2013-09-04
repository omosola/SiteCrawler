import java.util.*;

/**
 * @author: Omosola Odetunde
 * Date Created: 8/26/2013
 * Last Updated: 8/26/2013
 * 
 * Adapted from the following Basic Example Code:
 * https://code.google.com/p/crawler4j/source/browse/src/test/java/edu/uci/ics/crawler4j/examples/basic/BasicCrawler.java
 **/

public class SiteNode {
	private SiteNode parent = null;
	private Set<SiteNode> children = new HashSet<SiteNode>();
	private String urlPath;
	// add attribute for SVN Info
	
	public SiteNode(String urlPath)
	{
		this.urlPath = urlPath;
	}
	
	public void addChild(SiteNode child)
	{
		child.setParent((SiteNode)this);
		this.children.add(child);
	}
	
	public void removeChild(SiteNode child)
	{
		this.children.remove(child);
	}
	
	public Set<SiteNode> getChildren()
	{
		return this.children;
	}
	
	public void setParent(SiteNode parent)
	{
		this.parent = parent;
	}
	
	public SiteNode getParent()
	{
		return this.parent;
	}
	
	public Set<SiteNode> getDescendants()
	{
		Set<SiteNode> descendants = new HashSet<SiteNode>();
		collectDescendants(this, descendants);
		return descendants;
	}
	
	private void collectDescendants(SiteNode parent, Set<SiteNode> descendants)
	{
		for (SiteNode child : parent.getChildren())
		{
			descendants.add(child);
			collectDescendants(child, descendants);
		}
	}
	
	public Set<SiteNode> getAncestors()
	{
		Set<SiteNode> ancestors = new HashSet<SiteNode>();
		collectAncestors(this, ancestors);
		return ancestors;
	}
	
	private void collectAncestors(SiteNode child, Set<SiteNode> ancestors)
	{
		SiteNode parent = child.getParent();
		if (parent != null)
		{	
			ancestors.add(parent);
			collectAncestors(child.getParent(), ancestors);
		}
	}
	
	public String getUrlPath()
	{
		return this.urlPath;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (obj == null || !(obj instanceof SiteNode)) return false;
		SiteNode other = (SiteNode)obj;
		return this.urlPath.equals(other.urlPath);
	}
	
	@Override
	public int hashCode()
	{
		// http://stackoverflow.com/questions/113511/hash-code-implementation
		int result = 15;
		return 37 * result + this.urlPath.hashCode();
	}
	
	@Override
	public String toString()
	{
		return this.urlPath;
	}
}
