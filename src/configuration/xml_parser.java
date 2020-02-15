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


    /*
    Reads custom files and parses them
    Takes in file path and reduces the file name to its original variant
    From this it loads the rules of that variant within the doc
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

    public Simulation getSim()
    {
        return sim;
    }

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