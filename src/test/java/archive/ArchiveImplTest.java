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
        Files.deleteIfExists(Paths.get("src/test/resources/data_pp/test1_pp.txt"));
        Files.deleteIfExists(Paths.get("src/test/resources/data_pp/test2_pp.txt"));
        Files.deleteIfExists(Paths.get("src/test/resources/data_pp/test3_pp.txt"));
        String str = String.valueOf(LocalDate.now().getYear()) + String.valueOf(LocalDate.now().getMonthValue()) + String.valueOf(LocalDate.now().getDayOfMonth()) + "_1.zip";
        String str_pp = String.valueOf(LocalDate.now().getYear()) + String.valueOf(LocalDate.now().getMonthValue()) + String.valueOf(LocalDate.now().getDayOfMonth()) + "_1_PP.zip";
        Files.deleteIfExists(Paths.get("src/test/resources/zip/" + str));
        Files.deleteIfExists(Paths.get("src/test/resources/zip/" + str_pp));
        Files.createFile(Paths.get("src/test/resources/data/test1.txt"));
        Files.createFile(Paths.get("src/test/resources/data/test2.txt"));
        Files.createFile(Paths.get("src/test/resources/data/test3.txt"));
        Files.createFile(Paths.get("src/test/resources/data_pp/test1_pp.txt"));
        Files.createFile(Paths.get("src/test/resources/data_pp/test2_pp.txt"));
        Files.createFile(Paths.get("src/test/resources/data_pp/test3_pp.txt"));
        WorkingData data = new WorkingData();
        Field amhlive1 = WorkingData.class.getDeclaredField("AMHLIVE1");
        Field amhlive1_pp = WorkingData.class.getDeclaredField("AMHLIVE1_PP");
        amhlive1.setAccessible(true);
        amhlive1.set(data, "src/test/resources");
        amhlive1_pp.setAccessible(true);
        amhlive1_pp.set(data,"src/test/resources");
        Field amhlive1_zip = WorkingData.class.getDeclaredField("AMHLIVE1_ZIP");
        Field amhlive1_pp_zip = WorkingData.class.getDeclaredField("AMHLIVE1_PP_ZIP");
        amhlive1_zip.setAccessible(true);
        amhlive1_zip.set(data, "src/test/resources/zip");
        amhlive1_pp_zip.setAccessible(true);
        amhlive1_pp_zip.set(data, "src/test/resources/zip");
    }

    @Test
    public void makeZIP() throws Exception {
        ArchiveImpl impl = new ArchiveImpl();
        Assert.assertEquals(3, impl.makeZIP(Paths.get("src/test/resources/data"), dateSetUp(), 1));
        Assert.assertEquals(3, impl.makeZIP(Paths.get("src/test/resources/data_pp"), dateSetUp(), 3));
    }

    @Test
    public void cleanUp() {
        ArchiveImpl impl = new ArchiveImpl();
        dateSetUp();
        impl.cleanUp(Paths.get("src/test/resources/data"));
        impl.cleanUp(Paths.get("src/test/resources/data_pp"));
        Assert.assertFalse(Files.exists(Paths.get("src/test/resources/data/test3.txt")));
        Assert.assertFalse(Files.exists(Paths.get("src/test/resources/data_pp/test3.txt")));
    }

    private String dateSetUp() {
        LocalDate date = LocalDate.now();
        String dateStr = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
        WorkingData.inputDate = parseDate(dateStr);
        return dateStr;
    }
}