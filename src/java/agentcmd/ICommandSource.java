package agentcmd;

import java.util.Properties;

/**
 * @author Dan Prince
 */
public interface ICommandSource {

	public String getScript(String scriptId) throws Exception;

    public boolean deleteScript(String scriptId) throws Exception;

	public void archiveOutput(String scriptId, String stdout) throws Exception;

}
