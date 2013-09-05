import java.io.Console;


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
		do
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
					if (commandAndArgs.length != 2) return;
					SiteNode node = siteTree.getNode(commandAndArgs[1]);
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						for (SiteNode child : node.getChildren())
						{
							System.out.println(child);
						}
					}
				};
				case "parentOf":
				{
					// TODO: Put in input check!
					if (commandAndArgs.length != 2) return;
					SiteNode node = siteTree.getNode(commandAndArgs[1]);
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						System.out.println(node.getParent());
					}
				};
				case "pathTo":
				{
					// TODO: Put in input check!
					if (commandAndArgs.length != 2) return;
					SiteNode node = siteTree.getNode(commandAndArgs[1]);
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						System.out.println(node.getAncestors());
					}
				};
				case "reachableFrom":
				{
					// TODO: Put in input check!
					if (commandAndArgs.length != 2) return;
					SiteNode node = siteTree.getNode(commandAndArgs[1]);
					if (node == null)
					{
						System.out.println("This url is not within the site tree.");
					}
					else
					{
						System.out.println(node.getDescendants());
					}
				}
				default: {
					System.out.println();
				}
			}
		} while(!input.toLowerCase().equals("exit"));
	}
}
