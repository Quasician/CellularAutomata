package configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import Model.GOLSim;
import Model.Simulation;
import org.w3c.dom.Document;

public class xml_parser {
    private HashMap<String, ArrayList<String>> sims = new HashMap<>();
    final static ArrayList<String> fireParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "probCatch", "percentBurning"));
    final static ArrayList<String> percParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentEmpty", "percentBlocked"));
    final static ArrayList<String> GOLParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentAlive"));
    final static ArrayList<String> segParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "probSatisfy", "percentX", "percentO"));
    final static ArrayList<String> predPreyParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentFish", "percentSharks", "breedThreshFish", "breedThreshShark", "defaultSharkEnergy", "defaultFishEnergy"));
    final static ArrayList<String> sugarParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "defaultCapacity", "defaultMetabolism", "defaultSugar", "sugarRate", "percentAgent", "percentSugarFull", "percentSugarHalf", "percentSugarZero"));
    private Simulation sim;
    private String fileType;

    public xml_parser()
    {
        fileType = "";
    }

    public HashMap<String,Double> readFile(String file) {
        fileType = file;
        sims.putIfAbsent("pred_prey.xml",predPreyParams);
        sims.putIfAbsent("fire.xml",fireParams);
        sims.putIfAbsent("percolate.xml",percParams);
        sims.putIfAbsent("game_of_life.xml",GOLParams);
        sims.putIfAbsent("segregation.xml",segParams);
        sims.putIfAbsent("sugar.xml",sugarParams);
        HashMap<String,Double> paramMap = new HashMap<>();
        try {
            File inputFile = new File("data/"+file);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            for(String s: sims.get(file))
            {
                paramMap.putIfAbsent(s,Double.parseDouble(doc.getDocumentElement().getElementsByTagName(s).item(0).getTextContent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramMap;
    }
    public HashMap<String,Double> readSavedFile(File file) {
        sims.putIfAbsent("pred_prey.xml",predPreyParams);
        sims.putIfAbsent("fire.xml",fireParams);
        sims.putIfAbsent("percolate.xml",percParams);
        sims.putIfAbsent("game_of_life.xml",GOLParams);
        sims.putIfAbsent("segregation.xml",segParams);
        sims.putIfAbsent("sugar.xml",sugarParams);
        String fileName = file.toString().replaceAll("[0-9]", "");
        String[] paths = fileName.split("\\\\");
        fileType= paths[paths.length-1];
        HashMap<String,Double> paramMap = new HashMap<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            for(String s: sims.get(fileType))
            {
                System.out.println(s);
                paramMap.putIfAbsent(s,Double.parseDouble(doc.getDocumentElement().getElementsByTagName(s).item(0).getTextContent()));
            }
            int rows = (int)(paramMap.get("grid_height")*10)/10;
            int cols = (int)(paramMap.get("grid_width")*10)/10;
            String[][] grid = new String[rows][cols];
            for(int i  = 0; i<rows;i++)
            {
                for(int j  = 0; j<cols;j++)
                {
                    grid[i][j] = doc.getDocumentElement().getElementsByTagName("cell"+i+""+j).item(0).getTextContent();
                }
            }
            sim = new GOLSim(new HashMap<String,Double>());
            sim.createInitialGridFromFile(grid);
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