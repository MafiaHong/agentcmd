package agentcmd;

/** 
 * Interface for a wrapper around the xenstore_client.exe binary.
 * @author Dan Prince
 */
public interface IXenStoreClient {

	public String read(String path) throws XenStoreClientException;

	public String[] dir(String path) throws XenStoreClientException;

}
