package agentcmd;

import org.junit.Test; 
import static org.junit.Assert.*;

/**
 * A JUnit test case for the IdParser class.
 * @author Dan Prince
 */
public class IdParserTest {

	private class TestXenStoreClient implements IXenStoreClient {

		TestXenStoreClient() {
		}

		public String read(String path) throws XenStoreClientException {
			return "{\"label\":\"public\",\"ips\":[{\"netmask\":\"255.255.255.0\",\"enabled\":\"1\",\"ip\":\"184.106.160.82\"}],\"mac\":\"40:40:4e:04:ae:48\",\"gateway\":\"184.106.160.1\",\"slice\":\"446952\",\"dns\":[\"173.203.4.8\",\"173.203.4.9\"]}";
		}

		public String[] dir(String path) throws XenStoreClientException {
			return new String[]{"40404e04ae48", "4040088ccf41"};
		}

	}

	@Test
	public void parseId() {

		try {
			IXenStoreClient client = new TestXenStoreClient();	
			assertEquals("446952", IdParser.parseId(client));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
