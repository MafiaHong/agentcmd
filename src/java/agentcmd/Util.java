package agentcmd;

import java.io.*;
import org.apache.log4j.Logger;

/**
 * @author Dan Prince
 */
public class Util {

	private static Logger logger = Logger.getLogger(Util.class);

	public static void writeData(String data, String filename)
		throws IOException {

		FileWriter writer = new FileWriter(filename);
		writer.write(data);
		writer.close();

	}

}
