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
			assertEquals("60", PropUtil.getProperty("interval"));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void getPropertyWithDefault() {

		try {
			assertEquals("60", PropUtil.getProperty("interval", "zz"));
			assertEquals("zz", PropUtil.getProperty("blahblah", "zz"));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void getIntProperty() {

		try {
			assertEquals(60, PropUtil.getIntProperty("interval"));
			assertEquals(-1, PropUtil.getIntProperty("blahblah"));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}


}
