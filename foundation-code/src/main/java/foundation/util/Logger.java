package foundation.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;

public class Logger {

    public static void log(String message, LoggerLevels level) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/applog.log", true))) {
            writer.write("[" + level.name() + "]- " + message + " " + level.name() + " occurred at " + LocalDateTime.now()+ "\n");

        } catch (IOException i) {

            System.err.println("Could not access log file");
        }
    }
}
