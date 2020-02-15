package configuration;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Model.Simulation;
import javafx.scene.control.Alert;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.*;

public class xml_creator {

    private static double num = Math.random() * 1000;
    private static String xmlFilePath = "";
    private static Simulation sim;
    private static Element currentRoot;
    private static Document currentDocument;

    public xml_creator(Simulation sim) {
        this.sim = sim;
        createGrid(sim);
    }

    private static void createGrid(Simulation sim) {
            Document document = createDocandInitialTags();
            createParams();
            createCells();
            createTransformer(document);
    }

    private static void showError(String mes)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mes);
        alert.showAndWait();
    }

    private static void createTransformer(Document document)
    {
        try{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        transformer.transform(domSource, streamResult);
        } catch(TransformerConfigurationException e){
            showError(e.getMessage());
        }catch(TransformerException e){
            showError(e.getMessage());
        }
    }

    private static Document createDocandInitialTags() {
        try{
        xmlFilePath = String.format("data/%s%.0f.xml", sim.getName(), num);
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        currentDocument = document;
        Element root = document.createElement("title");
        currentRoot = root;
        root.appendChild(document.createTextNode(String.format("%s.xml", sim.getName())));
        document.appendChild(root);

        Element author = document.createElement("author");
        author.appendChild(document.createTextNode("Vineet Alaparthi"));
        root.appendChild(author);
        }
            catch(ParserConfigurationException e){
            showError(e.getMessage());
        }
        return currentDocument;
    }

    private static void createParams() {
        for(Map.Entry<String,Double> entry: sim.getParams().entrySet())
        {
            Element param = currentDocument.createElement(entry.getKey());
            param.appendChild(currentDocument.createTextNode(entry.getValue() + ""));
            currentRoot.appendChild(param);
        }
    }

    private static void createCells()
    {
        Element grid_config = currentDocument.createElement("grid_config");
        currentRoot.appendChild(grid_config);

        for (int i = 0; i < sim.getRows(); i++) {
            Element row = currentDocument.createElement("row"+i);
            grid_config.appendChild(row);
            for (int j = 0; j < sim.getCols(); j++) {
                Element cell = currentDocument.createElement("cell" + i+""+j);
                cell.appendChild(currentDocument.createTextNode(sim.getCell(i,j)));
                row.appendChild(cell);
            }
        }
    }
}

