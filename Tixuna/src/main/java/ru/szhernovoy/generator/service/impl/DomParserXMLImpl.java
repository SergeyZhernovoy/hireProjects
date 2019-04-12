package ru.szhernovoy.generator.service.impl;/**
 * Created by szhernovoy on 22.02.2017.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.szhernovoy.generator.entity.Column;
import ru.szhernovoy.generator.entity.ConfigReport;
import ru.szhernovoy.generator.entity.Page;
import ru.szhernovoy.generator.service.ParserXML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


public class DomParserXMLImpl implements ParserXML {

    private String filename;

    public DomParserXMLImpl(String filename) {
        this.filename = filename;
    }

    @Override
    public ConfigReport getConfiguration() {

        ConfigReport configReport = new ConfigReport();
        try(InputStream in = new FileInputStream(this.filename)) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("page");
            for(int i = 0; i < nList.getLength(); i++){
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) node;
                    Page page = new Page();
                    page.setWidth(Integer.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()));
                    page.setHeight(Integer.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent()));
                    configReport.setPage(page);
                }
            }
            NodeList cList = doc.getElementsByTagName("column");
            List<Column> columnList = new LinkedList<>();
            for(int i = 0; i < cList.getLength(); i++){
                Node node = cList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) node;
                    Column newColumn = new Column();
                    newColumn.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
                    newColumn.setWidth(Integer.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()));
                    columnList.add(newColumn);
                }
            }

            configReport.setColumns(columnList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return configReport;
    }

}
