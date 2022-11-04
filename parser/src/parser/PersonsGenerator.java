package parser;

import entity.AgentEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zuzana Žillová
 */
public class PersonsGenerator {

    private final int COUNT_OF_STREETS = 62381;
    private final int RANGE_OF_BUILDING = 9999;
    private final int RANGE_OF_PROPERTY = 119999;
    
    private final int COUNT_OF_CITY_AREAS = 22;

    private final int COUNT_OF_PERSONS = 200000;
    private final int COUNT_OF_AGENTS = 10000;
    private final int COUNT_OF_SOLD_PROPERTY = 250000;

    ArrayList<String> surnames = new ArrayList<>();
    ArrayList<String> nameFemale = new ArrayList<>();
    ArrayList<String> nameMale = new ArrayList<>();
    ArrayList<String> idNumbers = new ArrayList<>();
    ArrayList<String> numbers = new ArrayList<>();
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> agentIds = new ArrayList<>();

    ArrayList<AgentEntity> agents = new ArrayList<>();
    ArrayList<String> compositeKeysSold = new ArrayList<>();
    
    HashMap<String, Float> prices = new HashMap<>();

    Random rand = new Random();
    
    public void generateSchoolEvidence() throws IOException{
        
        FileWriter writer = new FileWriter("export/r_school_evidence.unl");
        for (int i = 0; i < COUNT_OF_CITY_AREAS; i++) {
            
            
            String id = "" + (i + 1);
            String primarySchools = "" + randBetween(65, 95);
            String elementarySchools = "" + randBetween(20, 40);
            String middleSchools = "" + randBetween(3, 9);
            String hightSchools = "" + randBetween(80, 100);
            
            writer.append(id);
            writer.append(";");
            writer.append(primarySchools);
            writer.append(";");
            writer.append(elementarySchools);
            writer.append(";");
            writer.append(middleSchools);
            writer.append(";");
            writer.append(hightSchools);
            writer.append(";");
            writer.append("\n");
            
        }
        
        writer.flush();
        writer.close();
        
    }

    public void generateSoldProperty() throws IOException {

        loadPrices();
        String date;
        String agent;
        String customer;
        String property;
        Float price;

        FileWriter writer = new FileWriter("export/r_sold_property.unl");

        System.out.println("generateSoldProperty");
        for (int i = 0; i < COUNT_OF_SOLD_PROPERTY; i++) {
            
            if (i % 10000 == 0) {
                System.out.println("" + i);
            }

            String compositeKey;
            do {
                AgentEntity agentEntity = agents.get(rand.nextInt(agents.size()));

                date = generateDateOfSold(agentEntity);
                agent = "" + agentEntity.getId();
                customer = idNumbers.get(rand.nextInt(idNumbers.size()));
                property = "" + (rand.nextInt(RANGE_OF_PROPERTY) + 1);

                int percent = rand.nextInt(7)+1;
                float percents = (float)percent/100;
                
                price = prices.get(property);
                
                if (price == null) {
                    System.out.println("");
                }
                
                if (rand.nextBoolean()) { // kupene nadcenu 
                    price += (price * percents);
                } else {                  // kupene podcenou
                    price -= (price * percents);
                }
                

                compositeKey = date + agent + customer + property;
            } while (compositeKeysSold.contains(compositeKey));

            writer.append(date);
            writer.append(";");
            writer.append(agent);
            writer.append(";");
            writer.append(customer);
            writer.append(";");
            writer.append(property);
            writer.append(";");
            writer.append(""+price);
            writer.append(";");
            writer.append("\n");
        }
        
        writer.flush();
        writer.close();

    }

    private String generateDateOfSold(AgentEntity agent) {

        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(agent.getFrom().getYear(), agent.getTo() != null ? agent.getTo().getYear() : 2022);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        OffsetDateTime date = gc.getTime().toInstant().atOffset(ZoneOffset.ofHours(2));

        if (agent.getTo() != null) {
            if (date.isAfter(agent.getTo())) {
                date = agent.getTo();
            }
        }

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        String dateStr = "";

        if (day < 10) {
            dateStr += "0" + day;
        } else {
            dateStr += "" + day;
        }

        dateStr += ".";

        if (month < 10) {
            dateStr += "0" + month;
        } else {
            dateStr += "" + month;
        }

        dateStr += ".";
        dateStr += date.getYear();

        return dateStr;
    }

    public void generateRealEstateAgents() throws IOException {

        FileWriter writer = new FileWriter("export/r_real_estate_agent.unl");

        for (int i = 0; i < COUNT_OF_AGENTS; i++) {

            int id = i + 1;
            String numberId;
            do {
                numberId = idNumbers.get(rand.nextInt(idNumbers.size()));
            } while (agentIds.contains(numberId));

            GregorianCalendar gc = new GregorianCalendar();

            int year = randBetween(2000, 2021);
            gc.set(gc.YEAR, year);
            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
            gc.set(gc.DAY_OF_YEAR, dayOfYear);

            OffsetDateTime dateFrom = gc.getTime().toInstant().atOffset(ZoneOffset.ofHours(2));
            int day = dateFrom.getDayOfMonth();
            int month = dateFrom.getMonthValue();

            String dateFromStr = "";

            if (day < 10) {
                dateFromStr += "0" + day;
            } else {
                dateFromStr += "" + day;
            }

            dateFromStr += ".";

            if (month < 10) {
                dateFromStr += "0" + month;
            } else {
                dateFromStr += "" + month;
            }

            dateFromStr += ".";
            dateFromStr += dateFrom.getYear();

            String dateToStr = null;
            OffsetDateTime dateTo = null;

            if (rand.nextDouble() < 0.1) {

                int yearTo = randBetween(year, 2021);
                gc.set(gc.YEAR, yearTo);
                int dayOfYearTo = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
                gc.set(gc.DAY_OF_YEAR, dayOfYearTo);

                dateTo = gc.getTime().toInstant().atOffset(ZoneOffset.ofHours(2));
                day = dateTo.getDayOfMonth();
                month = dateTo.getMonthValue();

                if (day < 10) {
                    dateToStr = "0" + day;
                } else {
                    dateToStr = "" + day;
                }

                dateToStr += ".";

                if (month < 10) {
                    dateToStr += "0" + month;
                } else {
                    dateToStr += "" + month;
                }

                dateToStr += ".";
                dateToStr += dateTo.getYear();
            }

            agents.add(new AgentEntity(id, dateFrom, dateTo));

            writer.append("" + id);
            writer.append(";");
            writer.append(dateFromStr);
            writer.append(";");

            if (dateToStr != null) {
                writer.append(dateToStr);
            } else {
                System.out.println("");
            }

            writer.append(";");
            writer.append(numberId);
            writer.append(";");
            writer.append("\n");
        }

        writer.flush();
        writer.close();

    }

    public void generatePersonEntityData() throws IOException {

        loadSurnames();
        loadNames();

        FileWriter writer = new FileWriter("export/r_person.unl");

        for (int i = 0; i < COUNT_OF_PERSONS; i++) {

            if (i % 5000 == 0) {
                System.out.println("" + i);
            }

            boolean female = rand.nextBoolean();
            String firstName;

            if (female) {
                firstName = nameFemale.get(rand.nextInt(nameFemale.size()));
            } else {
                firstName = nameMale.get(rand.nextInt(nameMale.size()));
            }
            String lastName = surnames.get(rand.nextInt(surnames.size()));

            String email = null;
            String number = null;

            if (rand.nextDouble() < 0.7) {
                number = generatePhoneNumber();
            }

            if (rand.nextDouble() < 0.7) {
                email = generateEmail(firstName, lastName);
            }

            String idNumber = generateIdNumber(female);

            writer.append(idNumber);
            writer.append(";");
            writer.append(firstName);
            writer.append(";");
            writer.append(lastName);
            writer.append(";");
            if (email != null) {
                writer.append(email);
            }
            writer.append(";");
            if (number != null) {
                writer.append(number);
            }
            writer.append(";");
            writer.append("" + (rand.nextInt(RANGE_OF_BUILDING) + 1));
            writer.append(";");
            writer.append("" + (rand.nextInt(COUNT_OF_STREETS) + 1));
            writer.append(";");
            writer.append("\n");

        }

        writer.flush();
        writer.close();

    }

    private String generateEmail(String firstName, String lastName) {

        String email = "";
        do {

            float random = rand.nextFloat();
            if (random < 0.3) {
                email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@gmail.com";
            } else if (random < 0.6) {
                email = lastName.toLowerCase() + "." + firstName.toLowerCase() + "@gmail.com";
            } else if (random < 0.8) {
                email = firstName.toLowerCase() + rand.nextInt(100) + "@gmail.com";
            } else {
                email = lastName.toLowerCase() + rand.nextInt(100) + "@gmail.com";
            }

        } while (emails.contains(email));

        numbers.add(email);

        return email;
    }

    private String generateIdNumber(boolean female) {

        GregorianCalendar gc = new GregorianCalendar();

        String idNumber;
        do {
            int year = randBetween(1940, 2000);
            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
            gc.set(gc.YEAR, year);
            gc.set(gc.DAY_OF_YEAR, dayOfYear);

            String yearStr = "" + year;
            idNumber = "" + yearStr.substring(2, 4);

            OffsetDateTime date = gc.getTime().toInstant().atOffset(ZoneOffset.ofHours(2));
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();

            if (female) {
                month += 50;
            }

            if (month < 10) {
                idNumber += "0" + month;
            } else {
                idNumber += "" + month;
            }

            if (day < 10) {
                idNumber += "0" + day;
            } else {
                idNumber += "" + day;
            }

            idNumber += "/";
            idNumber += "" + rand.nextInt(10);
            idNumber += "" + rand.nextInt(10);
            idNumber += "" + rand.nextInt(10);
            idNumber += "" + rand.nextInt(10);
        } while (idNumbers.contains(idNumber));

        idNumbers.add(idNumber);

        return idNumber;

    }

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

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private void loadSurnames() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("data/surnames.csv"));

            scanner.useDelimiter(",|\\n");

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                String txt = "" + data[0].charAt(0);
                txt += data[0].substring(1, data[0].length()).toLowerCase();
                surnames.add(txt);
            }

            System.out.println("Surnames was loaded.");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersonsGenerator.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadNames() {

        Scanner scanner;
        try {
            scanner = new Scanner(new File("data/names.csv"));

            scanner.useDelimiter(",|\\n");

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data[0].equals("M")) {
                    nameMale.add(data[1]);
                } else {
                    nameFemale.add(data[1]);
                }
            }

            System.out.println("First names was loaded.");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersonsGenerator.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadPrices(){
        Scanner scanner;
        try {
            scanner = new Scanner(new File("data/prices.csv"));

            scanner.useDelimiter(",|\\n");

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                prices.put(data[0], Float.valueOf(data[1]));
                
            }

            System.out.println("Prices loaded.");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersonsGenerator.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
