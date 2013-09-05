import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author: Omosola Odetunde
 * Date Created: 9/3/2013
 * Last Updated: 9/4/2013
 * 
 * Adapted from the following Basic Example Code:
 * https://code.google.com/p/crawler4j/source/browse/src/test/java/edu/uci/ics/crawler4j/examples/basic/BasicCrawler.java
 **/

public class SiteCrawler extends WebCrawler
{
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	private SiteTreeWrapper siteTree;
	
	public SiteCrawler()
	{
		siteTree = new SiteTreeWrapper();
	}
	
	/**
	 * This function is called by the controller to get the local
	 * data of this crawler when job is finished 
	 */
	@Override
	public SiteTreeWrapper getMyLocalData()
	{
		return siteTree;
	}
	
	/**
	 * This function is called by the controller before finishing
	 * the job. You can put whatever stuff you need here.
	 */
	@Override
	public void onBeforeExit()
	{
		dumpMyData();
	}
	
	/**
	 * 
	 */
	private void dumpMyData()
	{
		// Anything you want to write into a text file or
		// printed on screen about the crawl
	}
	
	/**
	 * Contains logic for crawling the site. Determines which 
	 * pages should be visited
	 *
	 **/
	@Override
	public boolean shouldVisit(WebURL url)
	{
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.startsWith("http://rc.live.test.cheggnet.com/");
	}
	
	/**
	 * This function if called when a page is fetched and ready to process
	 * by your program.
	 **/
	@Override
	public void visit(Page page)
	{	
		WebURL url = page.getWebURL();
		
		String childUrl = url.getURL();
		String parentUrl = url.getParentUrl();
		if (childUrl.endsWith("/")) {
			// remove the trailing forward slash, just to keep
			// format consistent across site urls added to the tree
			childUrl = childUrl.substring(0, childUrl.length() - 1);
		}
		if (parentUrl != null && parentUrl.endsWith("/")) {
			parentUrl = parentUrl.substring(0, parentUrl.length() - 1);			
		}
		siteTree.createNode(childUrl, parentUrl);
		System.out.print(".");
	}
}
