package utils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDate {

    private static String datePattern = "(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";

    public static boolean dateMatch(String str) {
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static LocalDate parseDate(String str) {
        String[] strings = str.split("/");
        return LocalDate.of(Integer.parseInt(strings[2]), Integer.parseInt(strings[1]), Integer.parseInt(strings[0]));
    }
}
