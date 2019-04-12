package ru.szhernovoy.generator.utils;/**
 * Created by szhernovoy on 22.02.2017.
 */

public class StringDataReport {

    private String number;
    private String date;
    private String fio;

    public StringDataReport() {
    }

    public StringDataReport(StringDataReport stringDataReport) {
        this.number = stringDataReport.getNumber();
        this.date = stringDataReport.getDate();
        this.fio = stringDataReport.getFio();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

}
