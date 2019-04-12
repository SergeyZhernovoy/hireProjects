package ru.szhernovoy.generator.entity;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by szhernovoy on 22.02.2017.
 */

@XmlType(propOrder = {"width","title"},name = "column")
public class Column {

    private int width;

    private String title;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
