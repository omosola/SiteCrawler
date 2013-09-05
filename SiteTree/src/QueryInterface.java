import java.io.Console;

/**
 * @author: Omosola Odetunde
 * Date Created: 9/4/2013
 * Last Updated: 9/5/2013
 * 
 **/

public class QueryInterface
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Needed paramters: ");
			System.out.println("\t siteTreeFilename (filename of file containing save site tree data)");
			System.exit(1);
		}
		
		/*
		 * siteTreeFilename is a file where site tree data is saved
		 * after the site has been crawled. contains
		 * parent/child relationships between site nodes 
		 */
		String siteTreeFilename = args[0];
		
		SiteTreeWrapper siteTree = new SiteTreeWrapper();
		siteTree.load(siteTreeFilename);
		
		Console c = System.console();
		if (c == null) return;
		
		String input;
		
		while (true)
		{
			input = c.readLine("Enter a command\n");
			// break user input on whitespaces
			String[] commandAndArgs = input.split(" ");
			String command = commandAndArgs[0];
			System.out.println();
			
			switch (command) {
				case "childrenOf":
				{
					// TODO: Put in input check!
					if (commandAndArgs.length != 2) break;
					SiteNode node = siteTree.getNode(commandAndArgs[1]);
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						
						System.out.print("> ");
						for (SiteNode child : node.getChildren())
						{
							System.out.println(child);
						}
					}
					System.out.println();
					break;
				}
				case "parentOf":
				{
					// TODO: Put in input check!
					if (commandAndArgs.length != 2) break;
					SiteNode node = siteTree.getNode(commandAndArgs[1]);
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						System.out.println("> " + node.getParent());
					}
					System.out.println();
					break;
				}
				case "pathTo":
				{
					// TODO: Put in input check!
					if (commandAndArgs.length != 2) break;
					SiteNode node = siteTree.getNode(commandAndArgs[1]);
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						System.out.print("> ");
						for (SiteNode ancestor : node.getAncestors())
						{
							System.out.println(ancestor);
						}
					}
					System.out.println();
					break;
				}
				case "reachableFrom":
				{
					// TODO: Put in input check!
					if (commandAndArgs.length != 2) break;
					String nodeUrl = commandAndArgs[1];
					SiteNode node;
					if (nodeUrl.equals("."))
					{
						// get information from the root node
						node = siteTree.getRoot();
						System.out.println(node);
					}
					else 
					{
						node = siteTree.getNode(commandAndArgs[1]);
					}
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						System.out.print("> ");
						for (SiteNode descendant : node.getDescendants())
						{
							System.out.println(descendant);
						}
					}
					System.out.println();
					break;
				}
				case "exit":
				{
					System.out.println("Exiting");
					return;
				}
				default: {
					System.out.println("Command not recognized, choose from reachableFrom/pathTo/childrenOf/parentOf <urlPath> OR exit\n");
				}
			}
		}
	}
}
