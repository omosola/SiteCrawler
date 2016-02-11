import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author: Omosola Odetunde
 * Date Created: 9/3/2013
 * Last Updated: 9/5/2013
 * 
 * Adapted from the following Basic Example Code:
 * https://code.google.com/p/crawler4j/source/browse/src/test/java/edu/uci/ics/crawler4j/examples/basic/BasicCrawlController.java
 **/

public class SiteCrawlerController {
	public static void main(String[] args) throws Exception
	{
		if (args.length != 2)
		{
			System.out.println("Needed paramters: ");
			System.out.println("\t rootFolder (it will contain intermediate crawl data)");
			System.out.println("\t numberOfCrawlers (number of concurrent threads)");
			System.exit(1);
		}
		
		/*
		 * crawlStorageFolder is a folder where intermediate crawl data is
		 * stored
		 */
		String crawlStorageFolder = args[0];
		
		/*
		 * numberOfCrawlers shows the number of concurrent threads that should
		 * be initiated for crawling
		 */
		int numberOfCrawlers = Integer.parseInt(args[1]);
		
		CrawlConfig config = new CrawlConfig();
		
		config.setCrawlStorageFolder(crawlStorageFolder);
		
		/*
		 * Be polite: Make sure that we don't send more than 1 request per
		 * second (1000 milliseconds between requests)
		 */
		config.setPolitenessDelay(400);
		
		/*
		 * You can set the maximum crawl depth here. The default value is -1
		 * for unlimited depth
		 */
		config.setMaxDepthOfCrawling(-1);
		
		/*
		 * You can set the maximum number of pages to crawl. The default value
		 * is -1 for unlimited number of pages
		 */
		config.setMaxPagesToFetch(1000);
		
		/*
		 * Do you need to set a proxy? If so, you can use:
		 * config.setProxyHost("proxyserver.example.com");
		 * config.setProxyPort(8080);
		 * 
		 * If your proxy also needs authentication:
		 * config.setProxyUsername(username); config.getProxyPassword(password);
		 */
		
		/*
		 * This config parameter can be used to set your crawl to be resumable
		 * (meaning that you can resume the crawl from a previously
		 * interrupted/crashed crawl). Note: if you enable resuming feature and
		 * want to start a fresh crawl, you need to delete the contents of
		 * rootFolder manually.
		 */
		config.setResumableCrawling(false);
		
		/*
		 * Instantiate the controller for this crawl
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		String seedUrl = "http://www.google.com";
		controller.addSeed(seedUrl);
		
		/*
		 * Start the crawl. This is a blocking operation, meaning your code
		 * will reach the line after this only when crawling is finished
		 */
		
		System.out.println("Crawling up to 1000 pages starting from " + seedUrl);
		controller.start(SiteCrawler.class, numberOfCrawlers);

		List<Object> localData = controller.getCrawlersLocalData();
		if (localData != null && localData.get(0) instanceof SiteTreeWrapper) {
			String siteTreeFilename = "logger.txt";
			SiteTreeWrapper siteTree = (SiteTreeWrapper)localData.get(0);
			System.out.println("Saving crawl tree to " + siteTreeFilename);
			siteTree.save(siteTreeFilename);
		}
	}
}
