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

    private void setPropValues(String key, String input) throws IOException {
        File configFile = new File("src/configuration/config.properties");
        FileWriter writer = new FileWriter(configFile);
        Properties props = new Properties();
        props.setProperty("buttonNormal", "Normal");
        props.setProperty("buttonSlow", "Slow");
        props.setProperty("buttonStep", "Step");
        props.setProperty("buttonSeg", "Segregation");
        props.setProperty("buttonGol", "Game of Life");
        props.setProperty("buttonPerc", "Percolation");
        props.setProperty("buttonFire", "Fire");
        props.setProperty("buttonPP", "Pred Prey");
        props.setProperty("buttonSugar", "Sugar");
        props.setProperty("buttonLoadSim", "Load Custom Sim");
        props.setProperty("buttonEnter", "Enter");
        props.setProperty("buttonStart", "Start");
        props.setProperty("buttonSave", "Save");
        props.setProperty("buttonBack", "Back");
        props.setProperty("buttonRPS", "Rock,Paper,Scissors");
        props.setProperty("buttonAnt", "Ant");
        props.setProperty(key, input);
        props.store(writer, "host settings");
        writer.close();
    }
}
