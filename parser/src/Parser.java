/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Erik
 */
public class Parser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        //PropertyReader u = new PropertyReader();
        //u.export();
        UniqueValues u = new UniqueValues();
        u.exportStreets();
    }

    ArrayList<String> numbers = new ArrayList<>();

    private String generatePhoneNumber() {

        String phone = "";
        do {
            phone = "(";
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                phone += random.nextInt(10);

            }
            phone += ")";
            for (int i = 0; i < 3; i++) {
                phone += random.nextInt(10);

            }
            phone += "-";
            for (int i = 0; i < 4; i++) {
                phone += random.nextInt(10);

            }

        } while (numbers.contains(phone));
        numbers.add(phone);

        return phone;
    }

}
