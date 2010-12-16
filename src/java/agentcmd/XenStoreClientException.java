package agentcmd;

/**
 * @author Dan Prince
 */
class XenStoreClientException extends Exception {

    private static final long serialVersionUID = -3032333035119313321L;

	private String progOutput = "";

    public XenStoreClientException() {
        super();
    }

    public XenStoreClientException(String msg, String output) {
        super(msg);
		this.progOutput = progOutput;
    }

	public String getOutput() {
		return progOutput;
	}

}
