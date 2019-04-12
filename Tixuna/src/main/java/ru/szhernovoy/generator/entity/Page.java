package ru.szhernovoy.generator.entity;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by szhernovoy on 22.02.2017.
 */

@XmlType(propOrder = {"width","height"}, name = "page")
public class Page {
    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
