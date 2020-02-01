package cellsociety;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class xml_parser {

    public static void main(String[] args) {

        try {
            File inputFile = new File("Resources\\game_of_life.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("cell_config");
            System.out.println("----------------------------");
            NodeList grid_size = doc.getDocumentElement().getElementsByTagName("grid_size");
            Element grid_size_ele = (Element) grid_size.item(0);
            int grid_size_int = Integer.parseInt(grid_size_ele.getTextContent());

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    for (int i = 0; i<grid_size_int; i++){
                        System.out.println(eElement
                                .getElementsByTagName("c"+String.valueOf(i+1))
                                .item(0)
                                .getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}