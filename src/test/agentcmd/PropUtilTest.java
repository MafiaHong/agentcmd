package agentcmd;

import org.junit.Test; 
import static org.junit.Assert.*;

/**
 * A JUnit test case for the XenStoreClient class.
 * @author Dan Prince
 */
public class PropUtilTest {

	@Test
	public void getProperty() {

		try {
			assertEquals("test", PropUtil.getProperty("cloudfiles.username"));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void getPropertyWithDefault() {

		try {
			assertEquals("test", PropUtil.getProperty("cloudfiles.username", "zz"));
			assertEquals("zz", PropUtil.getProperty("cloudfiles.nothere", "zz"));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void getIntProperty() {

		try {
			assertEquals(30, PropUtil.getIntProperty("interval"));
			assertEquals(-1, PropUtil.getIntProperty("blahblah"));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}


}
