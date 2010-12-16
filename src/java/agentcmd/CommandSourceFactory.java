package agentcmd;

import org.apache.log4j.Logger;

/**
 * @author Dan Prince
 */
public class CommandSourceFactory {

	private static Logger logger = Logger.getLogger(CommandSourceFactory.class);

	public static ICommandSource getCommandSource() throws Exception {
	
		String csType = PropUtil.getProperty("commandSourceType", "cloudfiles");

		if (csType == "cloudfiles") {
			logger.info("Using cloud files command source.");
			return new CloudFilesCommandSource();
		} else {
			throw new Exception("Invalid command source type specified.");
		}
	
	}

}
