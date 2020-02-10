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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.*;

public class xml_creator {

    private static double num = Math.random() * 1000;
    private static String xmlFilePath = "";
    private Simulation sim;

    public xml_creator(Simulation sim) {
        this.sim = sim;
        createGrid(sim);
    }

    public static void createGrid(Simulation sim) {
        try {
            xmlFilePath = String.format("data/%s%.0f.xml",sim.getName(), num);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("title");
            root.appendChild(document.createTextNode(String.format("%s.xml",sim.getName())));
            document.appendChild(root);

            Element author = document.createElement("author");
            author.appendChild(document.createTextNode("Vineet Alaparthi"));
            root.appendChild(author);

            Element grid_width = document.createElement("grid_width");
            grid_width.appendChild(document.createTextNode(sim.getCols() + ""));
            root.appendChild(grid_width);

            Element grid_height = document.createElement("grid_height");
            grid_height.appendChild(document.createTextNode(sim.getRows() + ""));
            root.appendChild(grid_height);

            for(Map.Entry<String,Double> entry: sim.getParams().entrySet())
            {
                Element param = document.createElement(entry.getKey());
                param.appendChild(document.createTextNode(entry.getValue() + ""));
                root.appendChild(param);
            }


            Element grid_config = document.createElement("grid_config");
            root.appendChild(grid_config);

            for (int i = 0; i < sim.getRows(); i++) {
                Element row = document.createElement("row"+i);
                grid_config.appendChild(row);
                for (int j = 0; j < sim.getCols(); j++) {
                    Element cell = document.createElement("cell" + i+""+j);
                    cell.appendChild(document.createTextNode(sim.getCell(i,j)));
                    row.appendChild(cell);
                }
            }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(xmlFilePath));

                transformer.transform(domSource, streamResult);

                System.out.println("Done creating XML File");

        } catch(TransformerConfigurationException e){
            e.printStackTrace();
        } catch(TransformerException e){
            e.printStackTrace();
        } catch(ParserConfigurationException e){
            e.printStackTrace();
        }
    }
}

