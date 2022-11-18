/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import static javax.management.Query.value;

/**
 *
 * @author Erik
 */
public class UniqueValues {

    private static final String COMMA = ";";

    public UniqueValues() {
    }

    public void exportStreets() throws FileNotFoundException, IOException {
        Scanner scanner1 = new Scanner(new File("invalid_zipcodes.csv"));
        scanner1.useDelimiter("\\n");
        ArrayList<String> zipcodes = new ArrayList<>();
        Random random = new Random();
        ArrayList<String> zipcodesOuky = new ArrayList<>();
        zipcodesOuky.add("10001");
        while (scanner1.hasNextLine()) {
            String line1 = scanner1.nextLine();
            zipcodes.add(line1);
        }

        FileWriter file = new FileWriter("streets.csv");
        int id = 1;

        Scanner scanner = new Scanner(new File("ny.csv"));
        String[] line;
        ArrayList<String> map = new ArrayList<>();
        //   while (ihospitalizationsFile.hasNextLine()) {
        String help = "10001";
        scanner.useDelimiter(";|\\n");
        String code;
        String line1 = scanner.nextLine();
        int i = 0;
        while (scanner.hasNext()) {
            line1 = scanner.nextLine();
            i++;
            line = line1.split(";");
            if (line.length > 0) {
                if (!line[0].equals("(Undisclosed Address)")) {

                    String street = line[0];
                    if (line.length < 2) {
                        code = help;
                    } else {
                        code = line[1];
                    }

                    if (!map.contains(street)) {
                        file.append(String.valueOf(id));
                        file.append(COMMA);
                        file.append(street);
                        file.append(COMMA);
                        if (zipcodes.contains(code)) {

                            code = zipcodesOuky.get(random.nextInt(zipcodesOuky.size()));

                        }

                        file.append(code);
                        zipcodesOuky.add(code);
                        file.append(COMMA);
                        id++;
                        file.append("\n");
                        map.add(street);
                    }
                } else {
                    System.out.println("x");
                }
            }
        }

        file.flush();

        file.close();

    }
}
