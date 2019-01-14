package com.todal.game.Helper;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.todal.game.GameObjekts.SpaceObjekt.Planet;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.GameScreen;
import com.todal.game.Screenns.Hud.Hud;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.omg.CORBA.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CreateXmlFile {



    public static void crateSpace(MyGdxGame game, GameScreen screen, Hud hud) {

        List<Planet> planets; //= new ArrayList<Planet>();

        CreateSpace createSpace1 = new CreateSpace();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("Planets");
            doc.appendChild(rootElement);
            planets = createSpace1.Create(game, hud);

            for (int z=1;z<=planets.size()-1;z++) {


                            String path = planets.get(z).getPath();

                            // supercars element
                            Element planeta = doc.createElement("planet");
                            rootElement.appendChild(planeta);

                            Node nanme = doc.createElement("name");
                            nanme.appendChild(doc.createTextNode(planets.get(z).getSpaceObjectName()));
                            planeta.appendChild(nanme);

                            Node ntexture = doc.createElement("texture");
                            ntexture.appendChild(doc.createTextNode(path));
                            planeta.appendChild(ntexture);

                            // Create a new Node with the given tag name
                            Node nposx = doc.createElement("posx");
                            float x = planets.get(z).getX();
                            nposx.appendChild(doc.createTextNode(Float.toString(x)));
                            planeta.appendChild(nposx);

                            Node nposy = doc.createElement("posy");
                            float y = planets.get(z).getY();
                            nposy.appendChild(doc.createTextNode(Float.toString(y)));
                            planeta.appendChild(nposy);

                            Node nrot = doc.createElement("rot");
                            float rRotation = planets.get(z).getRotationSpeed();
                            nrot.appendChild(doc.createTextNode(Float.toString(rRotation)));
                            planeta.appendChild(nrot);

                Node nTitan = doc.createElement("CargoType1");
                String sTitan = planets.get(z).wares.get(0).getCargoType().name();
                double rTitan = planets.get(z).wares.get(0).getPrice();
                ((Element) nTitan).setAttribute( "cargotype", sTitan);
                ((Element) nTitan).setAttribute( "price", Double.toString(rTitan));
                planeta.appendChild(nTitan);

                Node nRgafen = doc.createElement("CargoType2");
                String sGrafen = planets.get(z).wares.get(1).getCargoType().name();
                double rGrafen = planets.get(z).wares.get(1).getPrice();
                ((Element) nRgafen).setAttribute("cargotype", sGrafen);
                ((Element) nRgafen).setAttribute("price", (Double.toString(rGrafen)));
                planeta.appendChild(nRgafen);

                Node nWoter = doc.createElement("CargoType3");
                String sWater = planets.get(z).wares.get(2).getCargoType().name();
                double rWoter = planets.get(z).wares.get(2).getPrice();
                ((Element) nWoter).setAttribute ("cargotype", sWater);
                ((Element) nWoter).setAttribute ("price", (Double.toString(rWoter)));
                planeta.appendChild(nWoter);

                Node nFuell = doc.createElement("CargoType4");
                double rFuell = planets.get(z).wares.get(3).getPrice();
                ((Element) nFuell).setAttribute("cargotype", "FUEL");
                ((Element) nFuell).setAttribute("price", (Double.toString(rFuell)));
                planeta.appendChild(nFuell);

            }

            // write the content into xml file

            String localDir = Gdx.files.getLocalStoragePath();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File((localDir + MyGdxGame.FILE_PLANETS)));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
