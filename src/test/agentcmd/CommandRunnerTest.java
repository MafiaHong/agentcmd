package agentcmd;

import org.junit.Test; 
import org.junit.BeforeClass; 
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

/**
 * A JUnit test case for the CommandRunner class.
 * @author Dan Prince
 */
public class CommandRunnerTest {

	private static String trueCmd = null;
	private static String falseCmd = null;
	private static String tmpDir = null;
	private static String envCmd = null;

	@BeforeClass
	public static void testInit() {
		if (java.io.File.separatorChar == '\\') {
			trueCmd = "ECHO";
			falseCmd = "ASDF";
			envCmd = "SET";
			tmpDir = System.getProperty("java.io.tmpdir");
		} else {
			trueCmd = "/bin/true";
			falseCmd = "/bin/false";
			envCmd = "env";
			tmpDir = "/tmp";
		}
	}

	@Test
	public void runTrueCommand() {

		try {
			CommandRunner cr = new CommandRunner();
			assertEquals(true, cr.runCommand(new String[]{trueCmd}, new java.util.HashMap<String,String>(), tmpDir));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void runFalseCommand() {

		try {
			CommandRunner cr = new CommandRunner();
			assertEquals(false, cr.runCommand(new String[]{falseCmd}, new java.util.HashMap<String,String>(), tmpDir));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void captureStdout() {

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			CommandRunner cr = new CommandRunner(os);
			assertEquals(true, cr.runCommand(new String[]{"echo", "blah"}, new java.util.HashMap<String,String>(), tmpDir));
			assertEquals("blah\n", new String(os.toByteArray()));
			
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void captureStderr() {

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			CommandRunner cr = new CommandRunner(os);
			assertEquals(false, cr.runCommand(new String[]{"man", "zz"}, new java.util.HashMap<String,String>(), tmpDir));
			if (java.io.File.separatorChar == '\\') {
				assertTrue(new String(os.toByteArray()).contains("is not recognized"));
			} else {
				assertEquals("No manual entry for zz\n", new String(os.toByteArray()));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void envVariableSet() {

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			CommandRunner cr = new CommandRunner(os);
			java.util.Map<String, String> map = new java.util.HashMap<String, String>();
			map.put("BLAH", "ASDF");
			cr.runCommand(new String[]{envCmd}, map, tmpDir);
			assertTrue(new String(os.toByteArray()).contains("ASDF"));
			
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
