package agentcmd;

import org.junit.Test; 
import static org.junit.Assert.*;

/**
 * A JUnit test case for the XenStoreClient class.
 * @author Dan Prince
 */
public class XenStoreClientTest {

	@Test
	public void read() {

		try {
			XenStoreClient client = new XenStoreClient("echo");	
			assertTrue(client.read("blah").contains("blah"));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void dir() {

		try {
			XenStoreClient client = new XenStoreClient("echo");	
			assertEquals("blah", client.dir("blah")[1]);
			assertEquals(2, client.dir("blah").length);
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
