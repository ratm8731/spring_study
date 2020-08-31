package com.example.demo.jpa.entity;

import java.io.Serializable;

public class AgentSummary implements Serializable {
    private String name;
    private Integer count;
    private String softwareName;

    public AgentSummary(String name, int count, String softwareName) {
        this.name = name;
        this.count = count;
        this.softwareName = softwareName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AgentSummary{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", softwareName='" + softwareName + '\'' +
                '}';
    }
}
