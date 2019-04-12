package ru.szhernovoy.generator.service.impl;

import ru.szhernovoy.generator.utils.StringDataReport;
import ru.szhernovoy.generator.service.DataParser;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by szhernovoy on 22.02.2017.
 */


public class DataReportImpl implements DataParser {

    private String filename;
    public DataReportImpl(String filename) {
        this.filename = filename;
    }

    @Override
    public List<StringDataReport> getAllData() {

        List<StringDataReport> data = new ArrayList<>();
        try {
            BufferedReader reader = getContent();
            String sCurrentLine;
            while((sCurrentLine = reader.readLine()) != null){
               data.add(getStringData(sCurrentLine));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public BufferedReader getContent() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.filename), Charset.forName("UTF-16")));
        return reader;
    }

    public StringDataReport getStringData(String data){
        StringTokenizer stringTokenizer = new StringTokenizer(data);
        StringBuilder builder = new StringBuilder();
        StringDataReport stringData = new StringDataReport();
        stringData.setNumber(stringTokenizer.nextToken());
        stringData.setDate(stringTokenizer.nextToken());
        while(stringTokenizer.hasMoreTokens()){
            builder.append(stringTokenizer.nextToken());
            if(stringTokenizer.hasMoreTokens()){
                builder.append(" ");
            }
        }
        stringData.setFio(builder.toString());
        return stringData;
    }

}
