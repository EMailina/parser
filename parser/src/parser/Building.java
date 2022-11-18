/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Erik
 */
public class Building {
    
    private int id;
    private int id_property;
    private int countBedrooms;
    private double countBathrooms;
    private int countStories;
    private int garage;

    public Building(int id, int id_property, int countBedrooms, double countBathrooms, int countStories, int garage) {
        this.id = id;
        this.id_property = id_property;
        this.countBedrooms = countBedrooms;
        this.countBathrooms = countBathrooms;
        this.countStories = countStories;
        this.garage = garage;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_property() {
        return id_property;
    }

    public void setId_property(int id_property) {
        this.id_property = id_property;
    }



    
    
    
    public int getCountBedrooms() {
        return countBedrooms;
    }

    public void setCountBedrooms(int countBedrooms) {
        this.countBedrooms = countBedrooms;
    }

    public double getCountBathrooms() {
        return countBathrooms;
    }

    public void setCountBathrooms(double countBathrooms) {
        this.countBathrooms = countBathrooms;
    }

    public int getCountStories() {
        return countStories;
    }

    public void setCountStories(int countStories) {
        this.countStories = countStories;
    }

    public int getGarage() {
        return garage;
    }

    public void setGarage(int garage) {
        this.garage = garage;
    }
    
    
}
