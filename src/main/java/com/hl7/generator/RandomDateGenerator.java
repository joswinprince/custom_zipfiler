package com.hl7.generator;
import java.time.LocalDate;
import java.util.Random;

public class RandomDateGenerator {
    public static void generateDOB() {
        // Set the range of years (1940 to 1960)
        int minYear = 1940;
        int maxYear = 1960;

        // Create a Random object
        Random random = new Random();

        // Generate a random year between 1940 and 1960
        int randomYear = minYear + random.nextInt(maxYear - minYear + 1);

        // Generate a random month (1 to 12)
        int randomMonth = 1 + random.nextInt(12);

        // Generate a random day (1 to 28/30/31 depending on the month)
        int randomDay = 1 + random.nextInt(getMaxDayOfMonth(randomYear, randomMonth));

        // Format the random date
        String randomDate = String.format("%04d%02d%02d", randomYear, randomMonth, randomDay);

        System.out.println("Random date: " + randomDate);
    }

    // Method to get the maximum day of the month for a given year and month
    private static int getMaxDayOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1).lengthOfMonth();
    }
}
