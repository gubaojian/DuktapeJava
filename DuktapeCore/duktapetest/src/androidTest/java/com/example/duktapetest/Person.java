package com.example.duktapetest;

import java.util.List;
import java.util.Map;

/**
 * Created by furture on 16/8/15.
 */
public class Person {

    private String name;

    private List<String> loves;


    private String[] years;

    private boolean isChina;


    private Map<String,String> sites;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLoves() {
        return loves;
    }

    public void setLoves(List<String> loves) {
        this.loves = loves;
    }

    public String[] getYears() {
        return years;
    }

    public void setYears(String[] years) {
        this.years = years;
    }

    public boolean isChina() {
        return isChina;
    }

    public void setChina(boolean china) {
        isChina = china;
    }

    public Map<String, String> getSites() {
        return sites;
    }

    public void setSites(Map<String, String> sites) {
        this.sites = sites;
    }
}
