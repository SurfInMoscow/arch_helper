package archive;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.WorkingData;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static utils.RegexDate.parseDate;

public class ArchiveImplTest {

    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        Files.deleteIfExists(Paths.get("src/test/resources/data/test1.txt"));
        Files.deleteIfExists(Paths.get("src/test/resources/data/test2.txt"));
        Files.deleteIfExists(Paths.get("src/test/resources/data/test3.txt"));
        String str = String.valueOf(LocalDate.now().getYear()) + String.valueOf(LocalDate.now().getMonthValue()) + String.valueOf(LocalDate.now().getDayOfMonth()) + "_1.zip";
        Files.deleteIfExists(Paths.get("src/test/resources/zip/" + str));
        Files.createFile(Paths.get("src/test/resources/data/test1.txt"));
        Files.createFile(Paths.get("src/test/resources/data/test2.txt"));
        Files.createFile(Paths.get("src/test/resources/data/test3.txt"));
        WorkingData data = new WorkingData();
        Field amhlive1 = WorkingData.class.getDeclaredField("AMHLIVE1");
        amhlive1.setAccessible(true);
        amhlive1.set(data, "src/test/resources");
        Field amhlive1_zip = WorkingData.class.getDeclaredField("AMHLIVE1_ZIP");
        amhlive1_zip.setAccessible(true);
        amhlive1_zip.set(data, "src/test/resources/zip");
    }

    @Test
    public void makeZIP() throws Exception {
        ArchiveImpl impl = new ArchiveImpl();
        Assert.assertEquals(3, impl.makeZIP(Paths.get("src/test/resources/data"), dateSetUp(), 1));
    }

    @Test
    public void cleanUp() {
        ArchiveImpl impl = new ArchiveImpl();
        dateSetUp();
        impl.cleanUp(Paths.get("src/test/resources/data"));
        Assert.assertFalse(Files.exists(Paths.get("src/test/resources/data/test3.txt")));
    }

    private String dateSetUp() {
        LocalDate date = LocalDate.now();
        String dateStr = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
        WorkingData.inputDate = parseDate(dateStr);
        return dateStr;
    }
}