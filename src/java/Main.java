import agentcmd.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {

		logger.info("AgentCmd Started.");
		PropertyConfigurator.configure("log4j.properties");

		String tmpDir = System.getProperty("java.io.tmpdir");
		int sleepSec=PropUtil.getIntProperty("interval", 120);
		int sleepMilli=PropUtil.getIntProperty("interval", 120)*1000;
		logger.info("Script check interval: "+sleepSec+ " seconds.");

		try {
			logger.info("Working Directory: " +new java.io.File( "." ).getCanonicalPath());
			String csId = IdParser.parseId(); // obtain the Cloud Server ID
			logger.info("Cloud Server Id: " + csId);
			String batFile = tmpDir + File.separator + csId + ".bat";
		
			ICommandSource cmdSource = CommandSourceFactory.getCommandSource();

			while (true) {

				String data = cmdSource.getScript(csId);

				if (data != null) {
					logger.info("Running script.");
					logger.debug("Script: " + data);
					agentcmd.Util.writeData(data, batFile);

					ByteArrayOutputStream os = new ByteArrayOutputStream();
					CommandRunner cr = new CommandRunner(os);
					cr.runCommand(new String[]{"cmd.exe", "/c", csId+".bat", csId}, new java.util.HashMap<String,String>(), tmpDir);
					logger.info("Script run completed.");

					logger.info("Archiving script output.");
					cmdSource.archiveOutput(csId, new String(os.toByteArray()));
					cmdSource.deleteScript(csId);
					logger.info("Script deleted.");
				}

				Thread.sleep(sleepMilli);
			}
		} catch (java.lang.InterruptedException ie) {
			System.out.println("Exiting.");
		} catch (Exception e) {
			logger.error("Fatal Exception: " + e.getMessage(), e);
			e.printStackTrace();
			System.exit(1);
		}

	}

}
