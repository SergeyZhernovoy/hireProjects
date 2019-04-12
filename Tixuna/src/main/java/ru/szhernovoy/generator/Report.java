package ru.szhernovoy.generator;/**
 * Created by szhernovoy on 21.02.2017.
 */

import ru.szhernovoy.generator.entity.Column;
import ru.szhernovoy.generator.entity.ConfigReport;
import ru.szhernovoy.generator.utils.ShareStringDataReport;
import ru.szhernovoy.generator.utils.StringDataReport;
import ru.szhernovoy.generator.service.Validator;

import java.util.List;

public class Report {

    private Validator validator;
    private ConfigReport config;
    private List<StringDataReport> data;
    private String title;
    private int currentLine;
    private String textReport;

    public Report(Validator validator, ConfigReport config, List<StringDataReport> data) {
        this.validator = validator;
        this.config = config;
        this.data = data;
    }

    public String generateTitle(){
        String result = this.title;
        if(this.title == null){
            StringBuilder builder = new StringBuilder();
            builder.append("|");
            for(Column column : this.config.getColumns()){
                builder.append(" ");
                builder.append(column.getTitle());
                builder.append(setSpace(column.getTitle().length(),column.getWidth()," "));
                builder.append("|");
            }
            builder.append("\n");
            this.title = builder.toString();
            result = this.title;
        }
        currentLine++;
        return result;
    }

    public String setSpace(int title, int width, String symb){
        StringBuilder builder = new StringBuilder();
        int count = width - title + 1;
        while(count > 0){
            builder.append(symb);
            count--;
        }
        return builder.toString();
    }

    public String generateString(StringDataReport dataString){

        ShareStringDataReport stringDataReport = new ShareStringDataReport(dataString,this.config);
        stringDataReport.execute();
        StringBuilder builder = new StringBuilder();
        int widthNum = this.config.getColumns().get(0).getWidth();
        int widthDate = this.config.getColumns().get(1).getWidth();
        int widthFio = this.config.getColumns().get(2).getWidth();

        builder.append(setSpace(0,this.config.getPage().getWidth(),"-"));
        for (int index = 0; index < stringDataReport.getNumber().size();index++){

            if(currentLine == this.config.getPage().getHeight()) {
                currentLine = 0;
                builder.append("~\n");
                builder.append(this.generateTitle());
            }

            builder.append("|");
            builder.append(" ");
            String var = stringDataReport.getNumber().get(index);
            builder.append(var);
            builder.append(setSpace(var.length(),widthNum," "));

            builder.append("|");
            builder.append(" ");
            var = stringDataReport.getDate().get(index);
            builder.append(var);
            builder.append(setSpace(var.length(),widthDate," "));

            builder.append("|");
            builder.append(" ");
            var = stringDataReport.getFio().get(index);
            builder.append(var);
            builder.append(setSpace(var.length(),widthFio," "));
            builder.append("|");
            builder.append("\n");
            currentLine++;
        }
        return builder.toString();
    }

    public void generateReport(){

       StringBuilder mainText = new StringBuilder();
       mainText.append(this.generateTitle());
       for (StringDataReport stringDataReport : this.data){
           mainText.append(generateString(stringDataReport));
       }
       this.textReport = mainText.toString();
    }

    public void save(){

    }

}
