package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class containing the methods for saving the gamestate into a file and Loading in from a file
 */
public class SaveHandler {
    /**
     * Saves the game into a file
     * @param mit the Gamestate to be saved
     * @param neven The name of the file to
     */
    public  void save(hexGame mit, String neven){
        Document dom;
        Element e;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("Save");

            // create data elements and place them under root
            for(int i=0;i<5;i++) {
                e = dom.createElement("Line");
                for(int j =0; j<6; j++) {
                    Element c = dom.createElement("Cell");
                    c.appendChild(dom.createTextNode(String.valueOf(mit.getCell(i,j))));
                    e.appendChild(c);
                }
                rootEle.appendChild(e);
            }
            rootEle.setAttribute("Score",String.valueOf(mit.getScore()));


            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file

                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream("Save/"+neven+".xml")));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    /**
     * Loads the game
     * @param mit the name of the file to load from
     * @return a {@code hexGame} object
     */
    public hexGame load(String mit){
        hexGame temp =new hexGame();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(mit);

            NodeList nList = doc.getElementsByTagName("Save");
            Node root =nList.item(0);
            Element eElement = (Element) root;

            temp.setScore(Integer.parseInt(eElement.getAttribute("Score")));
            NodeList lines =((Element) root).getElementsByTagName("Line");
            for (int i=0;i<5;i++) {
                Element line = (Element) lines.item(i);
                NodeList lineNode = line.getElementsByTagName("Cell");
                for (int j = 0; j<6; j++){
                     int n= Integer.parseInt(lineNode.item(j).getTextContent());
                    temp.setCell(i,j,n);
                }
            }


            } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            }
        return temp;
    }
}