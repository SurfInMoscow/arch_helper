package utils;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static utils.WorkingData.*;

public class LookupCreation {

    public static boolean lookupCreationDate(BasicFileAttributes attrs) {
        Objects.requireNonNull(attrs);
        FileTime fileTime = attrs.creationTime();
        long date = fileTime.toMillis();
        Instant instant = Instant.ofEpochMilli(date);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate fld = LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());

        return fld.getYear() == getWorkingData().inputDate().getYear() && fld.getMonthValue() == getWorkingData().inputDate().getMonthValue()
                && fld.getDayOfMonth() == getWorkingData().inputDate().getDayOfMonth();
    }

}
