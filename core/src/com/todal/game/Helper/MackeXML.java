package com.todal.game.Helper;


import com.badlogic.gdx.Gdx;
import com.todal.game.MyGdxGame;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.todal.game.MyGdxGame.FILE_PLAYER;
import static com.todal.game.MyGdxGame.FILE_SPACE_SHIP;

public class MackeXML {

    public static void  createDocument(String handle) throws IOException {

        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("player");
            root.addElement("Name").addText("player");
            root.addElement("money").addText("5000");
            root.addElement("planetCount").addText("20");

            // print the document to System.out
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            //writer.write(document);

            // Save the contents to a file
            XMLWriter fileWriter = new XMLWriter(
                    new FileOutputStream(handle + FILE_PLAYER), format);
            fileWriter.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  createSpaceship(String handle) throws IOException {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("ship");
            root.addElement("Name").addText("Tado-044");
            root.addElement("pos").addAttribute("x", "4965.5073")
                    .addAttribute("y", "5799.1235");
            root.addElement("target").addAttribute("x", "5013.96")
                    .addAttribute("y", "5764.547");
            Element slot = root.addElement("SLOT0");
            slot.addElement("ModuleType").addText("COKPIT");
            slot.addElement("Name").addText("Cocpit");
            slot.addElement("Capacity").addText("4");
            Element fill = slot.addElement("Fill");
            fill.addElement("cargo")
                    .addAttribute("cargotype","PERSON")
                    .addAttribute("fill","1");

            // print the document to System.out
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            //writer.write(document);

            // Save the contents to a file
            XMLWriter fileWriter = new XMLWriter(
                    new FileOutputStream(handle + FILE_SPACE_SHIP), format);
            fileWriter.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMoney(double money){

        Document document = null;

        try {
            File inputFile = new File(MyGdxGame.getLoclaPath() + FILE_PLAYER);
            SAXReader reader = new SAXReader();
            document = reader.read(inputFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();

        // save new target
        for (int i = 0; i < elements.size(); i++) {
            if ("money".equals(elements.get(i).getName())){
                elements.get(i).setText(Double.toString(money));
            }
        }
        try{
            // print the document to System.out
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePlounetCount(int counter){

        Document document = null;

        try {
            File inputFile = new File(MyGdxGame.getLoclaPath() + FILE_PLAYER);
            SAXReader reader = new SAXReader();
            document = reader.read(inputFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();

        // save new target
        for (int i = 0; i < elements.size(); i++) {
            if ("planetCount".equals(elements.get(i).getName())){
                elements.get(i).setText(Integer.toString(counter));
            }
        }
        try{
            // print the document to System.out
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newTarget(float x, float y) {
        
        Document document = null;

        try {
            File inputFile = new File(MyGdxGame.getLoclaPath() + FILE_SPACE_SHIP);
            SAXReader reader = new SAXReader();
            document = reader.read(inputFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();

            // save new target
            for (int i = 0; i < elements.size(); i++) {
                if ("target".equals(elements.get(i).getName())){
                    elements.get(i).attributes().get(0).setValue(Float.toString(x));
                    elements.get(i).attributes().get(1).setValue(Float.toString(y));
                }
            }
            try{
            // print the document to System.out
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newPosition(float x, float y) {

        Document document = null;

        try {
            File inputFile = new File(MyGdxGame.getLoclaPath() + FILE_SPACE_SHIP);
            SAXReader reader = new SAXReader();
            document = reader.read(inputFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();

        // save new target
        for (int i = 0; i < elements.size(); i++) {
            if ("pos".equals(elements.get(i).getName())){
                elements.get(i).attributes().get(0).setValue(Float.toString(x));
                elements.get(i).attributes().get(1).setValue(Float.toString(y));
            }
        }
        try{
            // print the document to System.out
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
