package configuration;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.*;

public class xml_creator {

    public static final String xmlFilePath = "Resources/game_of_life.xml";


    public static void main(String argv[]) {

        try {

            String grid = "9";
            int gridnum = 9;

            ArrayList<String> options = new ArrayList<String>();
            options.add("alive");
            options.add("dead");

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("title");
            root.appendChild(document.createTextNode("Game of Life xml file"));
            document.appendChild(root);

            Element author = document.createElement("author");
            author.appendChild(document.createTextNode("Vineet Alaparthi"));
            root.appendChild(author);

            Element grid_size = document.createElement("grid_size");
            grid_size.appendChild(document.createTextNode(grid));
            root.appendChild(grid_size);

            Element cell_config = document.createElement("cell_config");
            root.appendChild(cell_config);

            for (int i = 0; i<gridnum; i++){
                Element tempcell = document.createElement("c" + String.valueOf(i+1));
                tempcell.appendChild(document.createTextNode(options.get((int)Math.round(Math.random()))));
                cell_config.appendChild(tempcell);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
