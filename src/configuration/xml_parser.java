package configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import Model.Simulation;
import org.w3c.dom.Document;

public class xml_parser {
    private static HashMap<String, ArrayList<String>> sims = new HashMap<>();
    final static ArrayList<String> fireParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "probCatch", "percentBurning"));
    final static ArrayList<String> percParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentEmpty", "percentBlocked"));
    final static ArrayList<String> GOLParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentAlive"));
    final static ArrayList<String> segParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "probSatisfy", "percentX", "percentO"));
    final static ArrayList<String> predPreyParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentFish", "percentSharks", "breedThreshFish", "breedThreshShark", "defaultSharkEnergy", "defaultFishEnergy"));
    final static ArrayList<String> sugarParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "defaultCapacity", "defaultMetabolism", "defaultSugar", "sugarRate", "percentAgent", "percentSugarFull", "percentSugarHalf", "percentSugarZero"));
    private static Simulation sim;
    private static String fileType;

    public static void main(String[] args)
    {

    }
    public static HashMap<String,Double> readFile(String file) {
        fileType = file;
        sims.putIfAbsent("pred_prey.xml",predPreyParams);
        sims.putIfAbsent("fire.xml",fireParams);
        sims.putIfAbsent("percolate.xml",percParams);
        sims.putIfAbsent("game_of_life.xml",GOLParams);
        sims.putIfAbsent("segregation.xml",segParams);
        sims.putIfAbsent("sugar.xml",sugarParams);
        HashMap<String,Double> paramMap = new HashMap<>();
        try {
            File inputFile = new File("Resources/"+file);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            for(String s: sims.get(file))
            {
                paramMap.putIfAbsent(s,Double.parseDouble(doc.getDocumentElement().getElementsByTagName(s).item(0).getTextContent()));
            }
            String[][] grid = new String[(int)(paramMap.get("grid_height")*10)/10][(int)(paramMap.get("grid_width")*10)/10];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramMap;
    }
    public static HashMap<String,Double> readSavedFile(File file) {
        sims.putIfAbsent("pred_prey.xml",predPreyParams);
        sims.putIfAbsent("fire.xml",fireParams);
        sims.putIfAbsent("percolate.xml",percParams);
        sims.putIfAbsent("game_of_life.xml",GOLParams);
        sims.putIfAbsent("segregation.xml",segParams);
        sims.putIfAbsent("sugar.xml",sugarParams);
        String fileName = file.toString().replaceAll("[0-9]", "");
        HashMap<String,Double> paramMap = new HashMap<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            for(String s: sims.get(fileName))
            {
                paramMap.putIfAbsent(s,Double.parseDouble(doc.getDocumentElement().getElementsByTagName(s).item(0).getTextContent()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramMap;
    }

    public Simulation getSim()
    {
        return sim;
    }

    public String getFileType()
    {
        return fileType;
    }
}