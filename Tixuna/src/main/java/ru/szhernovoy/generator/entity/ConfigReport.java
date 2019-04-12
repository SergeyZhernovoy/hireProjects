package ru.szhernovoy.generator.entity;/**
 * Created by szhernovoy on 21.02.2017.
 */


import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "settings")
@XmlType(propOrder = {"page","columns"})
public class ConfigReport {

    private Page page;
    private List<Column> columns = new LinkedList<>();


    public Page getPage() {
        return page;
    }

    @XmlElement
    public void setPage(Page page) {
        this.page = page;
    }


    public List<Column> getColumns() {
        return columns;
    }

    @XmlElementWrapper
    @XmlElement(name = "column")
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

}
