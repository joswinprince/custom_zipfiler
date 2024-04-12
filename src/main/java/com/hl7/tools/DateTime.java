package com.hl7.tools;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    public static String getDateTime() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        System.out.println("Current Date: " + currentDate);

        // Get the current time
        LocalTime currentTime = LocalTime.now();
        System.out.println("Current Time: " + currentTime);

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current Date and Time: " + currentDateTime);

        // Format date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = currentDate.format(dateFormatter);
        String formattedTime = currentTime.format(timeFormatter);
        String formattedDateTime = currentDateTime.format(dateTimeFormatter);

        System.out.println("Formatted Date: " + formattedDate);
        System.out.println("Formatted Time: " + formattedTime);
        System.out.println("Formatted Date and Time: " + formattedDateTime);
        return formattedDateTime;
    }
}
