package ru.szhernovoy.generator.service.impl;/**
 * Created by szhernovoy on 22.02.2017.
 */

import ru.szhernovoy.generator.entity.ConfigReport;
import ru.szhernovoy.generator.service.ParserXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class JaxbParserXMLImpl implements ParserXML {

    private String filename;

    public JaxbParserXMLImpl(String filename) {
        this.filename = filename;
    }

    @Override
    public ConfigReport getConfiguration() {
        JAXBContext context = null;
        ConfigReport configReport = null;
        try(InputStream in = new FileInputStream(this.filename)) {
            context = JAXBContext.newInstance(ConfigReport.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            configReport = (ConfigReport) unmarshaller.unmarshal(in);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configReport;
    }
}
