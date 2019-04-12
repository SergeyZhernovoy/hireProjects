package ru.szhernovoy.generator.service.impl;

import org.junit.Assert;
import org.junit.Test;
import ru.szhernovoy.generator.utils.StringDataReport;

import java.util.List;

import static org.hamcrest.core.Is.is;

/**
 * Created by szhernovoy on 22.02.2017.
 */
public class DataReportImplTest {
    @Test
    public void afterParsingWeGetListStringData() throws Exception {
        String first = getClass().getClassLoader().getResource("source-data.tsv").getPath();
        DataReportImpl dataReport = new DataReportImpl(first);
        List<StringDataReport> stringDataReports = dataReport.getAllData();
        for(StringDataReport str : stringDataReports){
            System.out.println(" num:"+str.getNumber() + " data:"+ str.getDate() + " fio:"+str.getFio());
        }

    }

    @Test
    public void whenParsingStringWeShouldGetStringData() throws Exception {
        String first = "";
        DataReportImpl dataReport = new DataReportImpl(first);
        //String test = "1\t25/11\tПавлов Дмитрий";
        String test = "5\t29/11/2009\tЮлианна-Оксана Сухово-Кобылина";
        StringDataReport stringDataReport = dataReport.getStringData(test);
        Assert.assertThat("5",is(stringDataReport.getNumber()));
        Assert.assertThat("29/11/2009",is(stringDataReport.getDate()));
        Assert.assertThat("Юлианна-Оксана Сухово-Кобылина",is(stringDataReport.getFio()));
    }

}