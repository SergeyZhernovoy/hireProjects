package ru.szhernovoy.generator.utils;

import ru.szhernovoy.generator.entity.ConfigReport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 24.02.2017.
 */


public class ShareStringDataReport {

    private StringDataReport source;
    private ConfigReport configReport;

    private List<String> number;
    private List<String> date;
    private List<String> fio;


    public ShareStringDataReport(StringDataReport source, ConfigReport configReport) {
        this.source = new StringDataReport(source);
        this.configReport = configReport;
    }

    public void execute(){

        int width = this.configReport.getColumns().get(0).getWidth();
        this.number = fillingColumnLine(this.convertStringToArrayWithSpaceDelimetr(this.source.getNumber(),width), width);

        width = this.configReport.getColumns().get(1).getWidth();
        this.date = fillingColumnLine(this.convertStringToArrayWithSpaceDelimetr(this.source.getDate(),width), width);

        width = this.configReport.getColumns().get(2).getWidth();
        this.fio = fillingColumnLine(this.convertStringToArrayWithSpaceDelimetr(this.source.getFio(),width), width);

        this.bindingSpaceToList();

    }

    public String[] convertStringToArrayWithSpaceDelimetr(String str, int width){

        String arrWords[] = str.split(" ");
        List<String> resultWords = new ArrayList<>();

        for(String string : arrWords){
            if(string.length() <= width){
                resultWords.add(string);
            } else {
                char[] chars = string.toCharArray();
                int current = width;
                for(int index = 0; index < chars.length;){
                    String chunk = new String(chars,index,current);
                    resultWords.add(chunk);
                    index+=current;
                    if(index+current > string.length()){
                        current = string.length() - index;
                    }
                }
            }
        }
        arrWords = resultWords.toArray(new String[resultWords.size()]);
        return arrWords;
    }

    public List<String> fillingColumnLine(String[] arrWords, int width){

        List<String> arrPhrases = new ArrayList<>();
        StringBuilder stringBuffer = new StringBuilder();
        int cnt = 0;
        int index = 0;
        int length = arrWords.length;

        while (index != length) {

            if (cnt + arrWords[index].length() <= width) {
                cnt += arrWords[index].length() + 1;
                stringBuffer.append(arrWords[index]).append(" ");
                index++;
            } else {
                arrPhrases.add(stringBuffer.toString());   // Добавляем фразу в коллекцию
                stringBuffer = new StringBuilder();
                cnt = 0;                                   // Обнуляем счётчик
            }
        }
        if (stringBuffer.length() > 0) {
            arrPhrases.add(stringBuffer.toString());       // Забираем "остатки"
        }

        return arrPhrases;

    }

    public void bindingSpaceToList(){

        int sizeNum  = this.number.size();
        int sizeData = this.date.size();
        int sizeFio  = this.fio.size();

        int max  = sizeNum > sizeData ? sizeNum : sizeData;
        max = max > sizeFio ? max : sizeFio;


        while(sizeNum < max){
            this.number.add(" ");
        }

        while(sizeNum < max){
            this.date.add(" ");
        }

        while(sizeFio < max){
            this.fio.add(" ");
        }

    }

    public List<String> getNumber() {
        return number;
    }

    public List<String> getDate() {
        return date;
    }

    public List<String> getFio() {
        return fio;
    }
}
