package ru.szhernovoy.generator;

import org.junit.Test;
import ru.szhernovoy.generator.entity.ConfigReport;
import ru.szhernovoy.generator.utils.StringDataReport;
import ru.szhernovoy.generator.service.DataParser;
import ru.szhernovoy.generator.service.Validator;
import ru.szhernovoy.generator.service.impl.DataReportImpl;
import ru.szhernovoy.generator.service.impl.JaxbParserXMLImpl;

import java.util.List;

/**
 * Created by admin on 23.02.2017.
 */
public class ReportTest {
    @Test
    public void generateTitle() throws Exception {
        String first = getClass().getClassLoader().getResource("settings.xml").getPath();
        String second =getClass().getClassLoader().getResource("source-data.tsv").getPath(); ;
        String third = "example-report.txt";
        ConfigReport configReport = new JaxbParserXMLImpl(first).getConfiguration();
        Validator validator = new Validator(new String[]{first,second,third});
        DataParser dataReport = new DataReportImpl(second);
        List<StringDataReport> stringDataReports = dataReport.getAllData();
        Report report = new Report(validator,configReport,stringDataReports);
        System.out.println(report.generateTitle());


    }

    @Test
    public void setSpace() throws Exception {
        String first = getClass().getClassLoader().getResource("settings.xml").getPath();
        String second =getClass().getClassLoader().getResource("source-data.tsv").getPath(); ;
        String third = "example-report.txt";
        ConfigReport configReport = new JaxbParserXMLImpl(first).getConfiguration();
        Validator validator = new Validator(new String[]{first,second,third});
        DataParser dataReport = new DataReportImpl(second);
        List<StringDataReport> stringDataReports = dataReport.getAllData();
        Report report = new Report(validator,configReport,stringDataReports);
    }

}