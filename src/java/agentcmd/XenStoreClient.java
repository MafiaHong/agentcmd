package agentcmd;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

/** 
 * Wrapper around the xenstore_client.exe binary.
 * @author Dan Prince
 */
public class XenStoreClient
		implements IXenStoreClient {

	private static Logger logger = Logger.getLogger(XenStoreClient.class);

	private static String cmd = "c:\\Progra~1\\Citrix\\XenTools\\xenstore_client.exe";

	public XenStoreClient() {
	}

	public XenStoreClient(String customCmd) {
		this.cmd = customCmd;
	}

	public String read(String path) throws XenStoreClientException {
		return runCmd(new String[]{cmd, "read", path});
	}

	public String[] dir(String path) throws XenStoreClientException {
		String data = runCmd(new String[]{cmd, "dir", path});
		return data.split("\\W");
	}

	private String runCmd(String[] args) throws XenStoreClientException {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			CommandRunner cr = new CommandRunner(os);
			java.util.Map<String, String> map = new java.util.HashMap<String, String>();
			String tmpdir = System.getProperty("java.io.tmpdir");
			if (cr.runCommand(args, map, tmpdir)) {
				return new String(os.toByteArray());
			} else {
				throw new XenStoreClientException("Failed to run XenStoreClient command.", new String(os.toByteArray()));
			}
		} catch (Exception e) {
				throw new XenStoreClientException("Failed to run XenStoreClient command: " + e.getMessage(), "");
		}
	}

}
