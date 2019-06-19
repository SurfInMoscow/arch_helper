package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    public static String readFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text = "";
        try {
            text = reader.readLine();
        } catch (IOException e) {
            e.getMessage();
        }
        return text;
    }

    public static int checkInstance(String str) {
        return Integer.parseInt(str);
    }
}
