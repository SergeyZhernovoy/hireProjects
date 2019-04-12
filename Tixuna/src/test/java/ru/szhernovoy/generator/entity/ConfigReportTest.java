package ru.szhernovoy.generator.entity;

import org.junit.Assert;
import org.junit.Test;
import ru.szhernovoy.generator.service.ParserXML;
import ru.szhernovoy.generator.service.impl.JaxbParserXMLImpl;

import static org.hamcrest.core.Is.is;

/**
 * Created by szhernovoy on 21.02.2017.
 */
public class ConfigReportTest {

    private ParserXML parser;

    @Test
    public void whenParsingXMLFileShouldGetList() throws Exception {
        String first = getClass().getClassLoader().getResource("settings.xml").getPath();
        this.parser = new JaxbParserXMLImpl(first);
        ConfigReport configReport = parser.getConfiguration();
        Assert.assertThat(true,is(configReport.getPage().getWidth() > 0));
        Assert.assertThat(true,is(configReport.getPage().getHeight() > 0));
        Assert.assertThat(true,is(configReport.getColumns().size() > 0));
    }

}