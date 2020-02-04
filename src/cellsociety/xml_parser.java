package cellsociety;

import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class xml_parser {

    public static void main(String[] args)
    {

    }
    public static HashMap<String,Double> readPredPreyFile() {
        HashMap<String,Double> paramMap = new HashMap<>();
        try {
            File inputFile = new File("Resources/pred_prey.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            paramMap.putIfAbsent("gridWidth",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("grid_width").item(0).getTextContent()));
            paramMap.putIfAbsent("gridHeight",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("grid_height").item(0).getTextContent()));
            paramMap.putIfAbsent("percentFish",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("percentFish").item(0).getTextContent()));
            paramMap.putIfAbsent("percentSharks",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("percentSharks").item(0).getTextContent()));
            paramMap.putIfAbsent("breedThreshFish",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("breedThreshFish").item(0).getTextContent()));
            paramMap.putIfAbsent("breedThreshShark",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("breedThreshShark").item(0).getTextContent()));
            paramMap.putIfAbsent("defaultSharkEnergy",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("defaultSharkEnergy").item(0).getTextContent()));
            paramMap.putIfAbsent("defaultFishEnergy",Double.parseDouble(doc.getDocumentElement().getElementsByTagName("defaultFishEnergy").item(0).getTextContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramMap;
    }
}