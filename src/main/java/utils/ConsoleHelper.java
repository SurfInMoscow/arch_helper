package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleHelper.class);

    public static String readFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text = "";
        try {
            text = reader.readLine();
        } catch (IOException e) {
            e.getMessage();
        }
        logger.info("input {} - string", text);
        return text.toLowerCase();
    }

    public static int checkInstance(String str) {
        return Integer.parseInt(str);
    }
}
