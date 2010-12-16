package agentcmd;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * @author Dan Prince
 */
public class CommandRunner {

	private static Logger logger = Logger.getLogger(CommandRunner.class);

	private PrintStream outStream = System.out;

	public CommandRunner() {
	}

	public CommandRunner(java.io.OutputStream os) {
		this.outStream = new PrintStream(os, true);
	}

	public boolean runCommand(String[] cmdarr, Map<String, String> newEnv, String workdir) throws InterruptedException, IOException {

		Map oldEnv = System.getenv();
		String[] envArr = new String[oldEnv.size() + newEnv.size()];
		int i = 0;

		Iterator keys = newEnv.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			String value = newEnv.get(key);
			envArr[i] = key+"="+value;
			i++;
		}

		keys = oldEnv.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			String value = (String)oldEnv.get(key);
			envArr[i] = key+"="+value;
			i++;
		}

		Process process = Runtime.getRuntime().exec(cmdarr, envArr, new File(workdir));

		ProcessLogger errorLogger = new ProcessLogger(process.getErrorStream());
		ProcessLogger inLogger = new ProcessLogger(process.getInputStream());

		Thread terr = new Thread(errorLogger);
		terr.start();
		Thread tout = new Thread(inLogger);
		tout.start();

		process.waitFor();
		terr.join();
		tout.join();
		if (process.exitValue() > 0) {
			outStream.flush();
			return false;
		}

		outStream.flush();
		return true;

	}

	class ProcessLogger implements Runnable {

		InputStream in;

		public ProcessLogger(InputStream in) {
			this.in = in;
		}

		public void run() {

			try {
				InputStreamReader reader = new InputStreamReader(in);
				BufferedReader buffReader = new BufferedReader(reader);
				String line=null;
				while ((line = buffReader.readLine()) != null) {
					outStream.println(line);
				}
			} catch (IOException e) {
				logger.error("IOException in process logger.", e);
				e.printStackTrace();
			}

		}
	}

}
