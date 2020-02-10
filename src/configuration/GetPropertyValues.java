package configuration;

import java.io.*;
import java.util.Properties;

public class GetPropertyValues {
    String result = "";
    InputStream inputStream;

    public String getPropValues(String input) throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "configuration/config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // get the property value and print it out
            result = prop.getProperty(input);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

    public void setPropValues(String key, String input) throws IOException {
        File configFile = new File("src/configuration/config.properties");
        FileWriter writer = new FileWriter(configFile);
        Properties props = new Properties();
        props.setProperty(key, input);
        props.store(writer, "host settings");
        writer.close();
    }
}
