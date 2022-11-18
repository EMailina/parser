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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Erik
 */
public class PropertyReader {

    private static final String COMMA = ";";
    private int id = 1;
    private int idObject = 1;
    private ArrayList<String> appraisers = new ArrayList<>();

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void export() throws FileNotFoundException, IOException, ParseException {
        appraisers.add("Broker Provided");
        appraisers.add("Reality TX");
        appraisers.add("VIP Reality");
        appraisers.add("United Real Estate");
        appraisers.add("Agent provided");
        appraisers.add("NY reality");
        appraisers.add("New home reality");
        appraisers.add("Zillow reality");
        FileWriter file = new FileWriter("parking.unl");
        FileWriter fileZuzka = new FileWriter("prices.csv");
        FileWriter fileNested = new FileWriter("nested.unl");
        Random random = new Random();
        int id = 1;

        Scanner scanner = new Scanner(new File("properties2.csv"));
        String[] line;
        ArrayList<String> map = new ArrayList<>();

        String help = "10001";
        //scanner.useDelimiter(";|\\n");
        scanner.useDelimiter("~");
        String code;
        String line1 = scanner.next();
        int i = 0;

        while (scanner.hasNext()) {
            line1 = scanner.next();
            i++;
            line = line1.split(";");

            int parking = 0;
            int garage = 0;
            double chance = random.nextDouble();
            if (chance < 0.03) {
                garage = 2;
            } else if (chance < 0.07) {
                parking = 1;
                garage = 1;
            } else if (chance < 0.1) {
                parking = 2;
            } else if (chance < 0.2) {
                parking = 1;
            }

            //parking
            file.append(String.valueOf(id));
            file.append(COMMA);
            file.append(String.valueOf(garage));
            file.append(COMMA);
            file.append(String.valueOf(parking));
            file.append(COMMA);
            file.append("\n");

            if (line.length < 18) {
                break;
            }
            Double x = Double.parseDouble(line[line.length - 2].replace(',', '.'));

            Building b = new Building(id, id, Integer.valueOf(line[line.length - 3]), x, Integer.valueOf(line[line.length - 1]), garage);
            ArrayList<Double> prices = getPrices(b);

            //prices
            fileZuzka.append(String.valueOf(id));
            fileZuzka.append(",");
            fileZuzka.append(df.format(prices.get(prices.size() - 1)).replace(",", "."));
            fileZuzka.append("\n");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            //System.out.println();

            for (Double price : prices) {
                fileNested.append(String.valueOf(idObject));
                fileNested.append(COMMA);
                fileNested.append(String.valueOf(id));
                fileNested.append(COMMA);
                fileNested.append(formatter.format(generateDate()));
                fileNested.append(COMMA);
                fileNested.append(appraisers.get(random.nextInt(appraisers.size() - 1)));
                fileNested.append(COMMA);
                fileNested.append(df.format(price));
                fileNested.append(COMMA);
                fileNested.append("\n");
                idObject++;

            }

            id++;
        }

        fileNested.flush();
        fileNested.close();
        fileZuzka.flush();
        fileZuzka.close();
        file.flush();
        file.close();

    }

    public ArrayList<Double> getPrices(Building b) {
        ArrayList<Double> prices = new ArrayList<>();
        Random random = new Random();
        int count = 1;
        double chance = 0;
        double price = 150000 + random.nextInt(50000);

        price += 25000 * b.getCountBathrooms();
        price += 20000 * b.getCountBedrooms();
        price += 70000 * b.getGarage();

        price += 100000 * b.getCountStories() - 1;
        price += random.nextDouble();

        prices.add(price);

        do {

            price = price + random.nextInt(15000);
            price += random.nextDouble();
            count++;
            chance = random.nextDouble();
            prices.add(price);
        } while (chance < 0.7 && count < 2);

        return prices;
    }

    public Double getPriceNear(ArrayList<Double> prices) {
        Double price = prices.get(prices.size() - 1);
        Random random = new Random();
        if (random.nextBoolean() == true) {
            price += random.nextInt(5000);
        } else {
            price -= random.nextInt(5000);
        }
        return price;
    }

    public LocalDate generateDate() {

        long minDay = LocalDate.of(2000, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return randomDate;

    }
}
