package configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import Model.GOLSim;
import Model.Simulation;
import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Purpose: Creates an xml parser that can either parse a file with a cell grid or without one
 * It creates a hashMap of the parameters in the file
 * Assumptions: Assumes that the xml has the right tags for each simulation
 * Dependencies: Dependent on the simulation method createInitialGridFromFile
 * xml_parser parser = new xml_parser();
 * This creates the parser and automatically takes care of creating the hashMap
 */
public class xml_parser {
    private HashMap<String, ArrayList<String>> sims = new HashMap<>();
    final static ArrayList<String> fireParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "probCatch", "percentBurning"));
    final static ArrayList<String> percParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentEmpty", "percentBlocked"));
    final static ArrayList<String> GOLParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentAlive"));
    final static ArrayList<String> segParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "probSatisfy", "percentX", "percentO"));
    final static ArrayList<String> predPreyParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentFish", "percentSharks", "breedThreshFish", "breedThreshShark", "defaultSharkEnergy", "defaultFishEnergy"));
    final static ArrayList<String> sugarParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "defaultCapacity", "defaultMetabolism", "defaultSugar", "sugarRate", "percentAgent", "percentSugarFull", "percentSugarHalf", "percentSugarZero"));
    final static ArrayList<String> RPSParams = new ArrayList<String>(Arrays.asList("grid_width", "grid_height", "percentRock","percentScissors","threshold"));
    private Simulation sim;
    private String fileType;
    private static String[][] currentGrid;
    private static Document currentDocument;
    private static HashMap<String,Double> paramHashMap;

    public xml_parser()
    {
        fileType = "";
    }

    /**
     * Purpose: Reads files without cell grid and generates hashMap
     * Assumptions: Assumes that the file has the right tags
     * @param file -> file selected from project library
     * @return HashMap<String,Double>
     */
    public HashMap<String,Double> readFile(String file) {
        fileType = file;
        addSimsToHashMap();
        HashMap<String,Double> paramMap = new HashMap<>();
        try {
            File inputFile = new File("data/"+file);
            createDocument(inputFile);
            for(String s: sims.get(file)) {
                paramMap.putIfAbsent(s,Double.parseDouble(currentDocument.getDocumentElement().getElementsByTagName(s).item(0).getTextContent()));
            }
        } catch (NullPointerException e) {
            showError("Wrong formatting of XML");
        }
        return paramMap;
    }


    /**
     * Purpose: Reads custom files and parses them
     * Takes in file path and reduces the file name to its original variant
     * From this it loads the rules of that variant within the doc through the use of a hashmap
     * Assumptions: Assumes that the file has the right tags
     * @param file -> file selected from project library (file explorer)
     * @return HashMap<String,Double>
     */
    public HashMap<String,Double> readSavedFile(File file) {
        addSimsToHashMap();
        String fileName = file.toString().replaceAll("[0-9]", "");
        fileName = fileName.replace('\\', '/');
        String[] paths = fileName.split("/");
        fileType= paths[paths.length-1];
        HashMap<String,Double> paramMap = new HashMap<>();
        paramHashMap = paramMap;
        try {
            createDocument(file);
            setUpParamHashMap();
            setUpCells();
            sim = new GOLSim(new HashMap<String,Double>());
            sim.createInitialGridFromFile(currentGrid);
        } catch (XMLException e) {
            showError(e.getMessage());
        }
        return paramMap;
    }

    /**
     * Purpose: Gets the current sim from the parsing of the xml file
     * Assumptions: Assumes that the sim variable has been correctly instantiated
     * @return Simulation
     */
    public Simulation getSim()
    {
        return sim;
    }

    /**
     * Purpose: Gets the name of the file (file type)
     * Assumptions: Assumes the fileType has been initialized
     * @return String
     */
    public String getFileType()
    {
        return fileType;
    }

    private void showError(String mes)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mes);
        alert.showAndWait();
    }

    private void addSimsToHashMap() {
        sims.putIfAbsent("pred_prey.xml",predPreyParams);
        sims.putIfAbsent("fire.xml",fireParams);
        sims.putIfAbsent("percolate.xml",percParams);
        sims.putIfAbsent("game_of_life.xml",GOLParams);
        sims.putIfAbsent("segregation.xml",segParams);
        sims.putIfAbsent("sugar.xml",sugarParams);
        sims.putIfAbsent("rps.xml",RPSParams);
    }

    private void createDocument(File inputFile)
    {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            currentDocument = doc;
        } catch (NullPointerException | IOException | SAXException  | ParserConfigurationException e) {
            showError("Wrong formatting of XML");
        }
    }

    private void setUpParamHashMap()
    {
        for(String s: sims.get(fileType))
        {
            double paramValue = Double.parseDouble(currentDocument.getDocumentElement().getElementsByTagName(s).item(0).getTextContent());
            if(paramValue<0)
            {
                showError("XML contains inappropriate value.");
            }
            paramHashMap.putIfAbsent(s,paramValue);
        }
    }

    private void setUpCells()
    {
        int rows = (int)(paramHashMap.get("grid_height")*10)/10;
        int cols = (int)(paramHashMap.get("grid_width")*10)/10;
        String[][] grid = new String[rows][cols];
        currentGrid = grid;
        for(int i  = 0; i<rows;i++)
        {
            for(int j  = 0; j<cols;j++)
            {
                grid[i][j] = currentDocument.getDocumentElement().getElementsByTagName("cell"+i+""+j).item(0).getTextContent();
            }
        }
    }
}