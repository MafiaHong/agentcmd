package agentcmd;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Dan Prince
 */
public class PropUtil {

	private static Logger logger = Logger.getLogger(PropUtil.class);

	private final static String propFile = "agentcmd.properties";

	private static Properties props = null;

	private static void loadProperties() throws IOException {

		props = new Properties();
        FileInputStream in = new FileInputStream(propFile);
        props.load(in);    
        in.close();
		logger.error("Properties loaded.");

	}

	public static String getProperty(String key) {
		try {
			if (props == null) {
				loadProperties();
			}
			return props.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Failed to load properties file: " + e.getMessage());
			return null;
		}
	}

    public static String getProperty(String key, String defaultVal) {
        String val = getProperty(key);
        if (val == null) {
            return defaultVal;
        } else {
            return val;
		}
    }

    public static int getIntProperty(String key) {
        String val = getProperty(key);
        if (val == null) {
            logger.error("Error parsing property as number: " + key);
            return -1;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            logger.error("Error parsing property as number: " + key);
            return -1;
        }
    }

    public static int getIntProperty(String key, int defaultVal) {
        int val = getIntProperty(key);
        if (val == -1) {
            return defaultVal;
        } else {
            return val;
		}
    }

}
