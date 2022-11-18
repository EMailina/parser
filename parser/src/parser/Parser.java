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
        //dataGenerator.generateSchoolEvidence();
        
        //UniqueValues u = new UniqueValues();
        //u.exportStreets();
        
    }
    
}
