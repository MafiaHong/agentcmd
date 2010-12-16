package agentcmd;

import com.rackspacecloud.client.cloudfiles.*;
import java.io.*;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author Dan Prince
 */
public class CloudFilesCommandSource implements ICommandSource {

    private static Logger logger = Logger.getLogger(CloudFilesCommandSource.class);

	private String username = "";
	private String password = "";
	private String container = "";
	private FilesClient client = null;

	public CloudFilesCommandSource() throws Exception {
		this.username = PropUtil.getProperty("cloudfiles.username");
		this.password = PropUtil.getProperty("cloudfiles.api_key");
		this.container = PropUtil.getProperty("cloudfiles.container");
        client = new FilesClient(username, password);
		if (!client.login()) {
			logger.error("Failed to login to Cloud Files. Please specify the correct username and password in agentcmd.properties. Exiting.");
			throw new Exception("Failed to login to Cloud Files.");
		}
	}

	public String getScript(String scriptId) throws IOException {
		try {
			byte[] bytes = client.getObject(container, scriptId);
			return new String(bytes);
		} catch (FilesNotFoundException fe) {
			logger.info("No data found for server: " + scriptId);
			return null;
		}
	}

	public boolean deleteScript(String scriptId) throws IOException {
		try {
			client.deleteObject(container, scriptId);
			return true;
		} catch (FilesNotFoundException fe) {
			logger.error("Failed to delete script: " + scriptId, fe);
			return false;
		}
	}

	public void archiveOutput(String scriptId, String data) throws Exception {
		String path = System.getProperty("java.io.tmpdir") + File.separator + scriptId + ".out";
		agentcmd.Util.writeData(data, path);
		File f = new File(path);
		client.storeObject(container, f, "text/plain");
		f.delete();
	}

}
