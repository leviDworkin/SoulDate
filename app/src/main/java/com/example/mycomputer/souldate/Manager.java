package com.example.mycomputer.souldate;

public class Manager {
    private String name, report;

    public  Manager(){}
    public Manager(String name, String report){
        this.name = name;
        this.report = report;
    }
    public Manager(Manager other){
        this.name = other.name;
        this.report = other.report;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
