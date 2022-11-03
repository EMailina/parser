/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.IOException;

/**
 *
 * @author Erik
 */
public class Parser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        PersonsGenerator dataGenerator = new PersonsGenerator();
        dataGenerator.generatePersonEntityData();
        dataGenerator.generateRealEstateAgents();
        dataGenerator.generateSoldProperty();
        
    }
    
}
