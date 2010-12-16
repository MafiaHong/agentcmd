package agentcmd;

import java.util.regex.*;

import org.apache.log4j.Logger;

/** 
 * Utility to parse the cloud server ID (slice) from an IXenStoreClient.
 * @author Dan Prince
 */
public class IdParser {

	private static Logger logger = Logger.getLogger(IdParser.class);

	private IdParser() {
	}

	public static String parseId(IXenStoreClient client) throws Exception {
        final Pattern pattern = Pattern.compile(".*slice\":\"([\\w]*).*" , Pattern.MULTILINE);

		String[] networking = client.dir("vm-data/networking");
		String interface1 = client.read("vm-data/networking/"+networking[0]);

		Matcher matcher = pattern.matcher(interface1);

		if (matcher.find()) {
			return matcher.group(1);
		} else {
			throw new Exception("Failed to parse ID/slice.");
		}

	}

	public static String parseId() throws Exception {

		IXenStoreClient client = new XenStoreClient();
		return parseId(client);

	}

}
