package com.todal.game.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlWriter;
import com.todal.game.GameObjekts.SpaceObjekt.Ware;
import com.todal.game.MyGdxGame;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class ModifiedXML {


    private static Document doc;

    public static void setDocument(String file) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void writeDocument(String file) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean writePositionToXml(float x, float y) {

        setDocument(((Gdx.files.internal(MyGdxGame.FILE_SPACE_SHIP)).path()));
        //setDocument((((MyGdxGame.FILE_SPACE_SHIP))));

        Node ship = doc.getFirstChild();
        Node ship1 = doc.getElementsByTagName("pos").item(0);
        NamedNodeMap attr = ship1.getAttributes();
        Node PosX = attr.getNamedItem("x");
        PosX.setTextContent(Float.toString(x));
        Node PosY = attr.getNamedItem("y");
        PosY.setTextContent(Float.toString(y));

        writeDocument(Gdx.files.local(MyGdxGame.FILE_SPACE_SHIP).path());

        return true;

    }

    public static Boolean writeTargetPositionToXml(float x, float y) {

        String DIR = Gdx.files.getLocalStoragePath();

        FileHandle handle = Gdx.files.local(MyGdxGame.FILE_SPACESHIP);
        if (handle.exists()){
            System.out.println("JestJestJest");
        }

        setDocument(((Gdx.files.local(MyGdxGame.FILE_SPACE_SHIP)).file().getPath()));

        //Node ship = doc.getFirstChild();
        Node ship1 = doc.getElementsByTagName("target").item(0);
        NamedNodeMap attr = ship1.getAttributes();
        Node targetX = attr.getNamedItem("x");
        targetX.setTextContent(Float.toString(x));
        Node targetY = attr.getNamedItem("y");
        targetY.setTextContent(Float.toString(y));

        writeDocument( Gdx.files.local(MyGdxGame.FILE_SPACE_SHIP).path());

        return true;

    }
    public static Boolean writeMoneyToXml(double m) {

        setDocument(MyGdxGame.getLoclaPath() + MyGdxGame.FILE_PLAYER);
        //Node ship = doc.getFirstChild();
        Node player = doc.getElementsByTagName("money").item(0);
        player.setTextContent(Double.toString(m));

        writeDocument(MyGdxGame.getLoclaPath() + MyGdxGame.FILE_PLAYER);
        return true;
    }

    public static Boolean writePlanetCountTripToXml(int m) {


        setDocument(MyGdxGame.getLoclaPath() + MyGdxGame.FILE_PLAYER);
        //Node ship = doc.getFirstChild();
        Node player = doc.getElementsByTagName("planetCount").item(0);
        player.setTextContent(Integer.toString(m));

        writeDocument(MyGdxGame.getLoclaPath() + MyGdxGame.FILE_PLAYER);
        return true;
    }

    public static Boolean writeNewPriceToXml(String planetName, List<Ware> wares) {

        setDocument(MyGdxGame.FILE_PLANETS);

        NodeList planets = doc.getElementsByTagName("planet");
        for (int i = 0; i < planets.getLength(); i++) {
            Node planet = planets.item(i);
            if (planet.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) planet;
                if (getTagValue("name",e).equals(planetName)){
                    NodeList nodes = planet.getChildNodes();
                    for (int j = 0; j < nodes.getLength(); j++) {
                        Node element = nodes.item(j);
                        for (int it=0; it < wares.size() ;it++){
                        if (wares.get(it).getCargoType().name().equals(element.getAttributes())) {
                            element.setTextContent(Double.toString(wares.get(it).getPrice()));
                        }
                       }
                    }
                    writeDocument(MyGdxGame.FILE_PLANETS);
                    break;
                }
            }
        }
        return true;
    }

    public static Boolean writeNewFillToXml(int index, double fill){

        StringWriter writer = new StringWriter();
        XmlWriter xml = new XmlWriter(writer);

        try {
            xml.element("SLOT" + index).
                    element("Fill").
                    element("cargo").attribute("FUEL", fill)
                    .pop().
                    pop().
                    pop();

            FileHandle handle = Gdx.files.internal(MyGdxGame.FILE_SPACE_SHIP);
            //handle.writeString(writer.toString(),false);
            System.out.println(writer);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private
    static String getTagValue(String s, Element e) {
        NodeList nl = e.getElementsByTagName(s)
                .item(0)
                .getChildNodes();
                Node n = (Node) nl.item(0);
        return
                n.getNodeValue();
    }
}
