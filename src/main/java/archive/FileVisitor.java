package archive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.FileVisitResult.CONTINUE;
import static utils.LookupCreation.lookupCreationDate;
import static utils.WorkingData.*;

public class FileVisitor extends SimpleFileVisitor<Path> {
    private ZipOutputStream zout;
    private int count = 0;

    public FileVisitor(String dateString, int instance) throws FileNotFoundException {
        String[] strings = dateString.split("/");
        FileOutputStream fos;
        switch (instance) {
            case 1:
                fos = new FileOutputStream(AMHLIVE1_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_1" + ".zip");
                this.zout = new ZipOutputStream(fos);
                break;
            case 2:
                fos = new FileOutputStream(AMHLIVE2_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_2" + ".zip");
                this.zout = new ZipOutputStream(fos);
                break;
            case 3:
                fos = new FileOutputStream(AMHLIVE1_PP_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_1_PP" + ".zip");
                this.zout = new ZipOutputStream(fos);
                break;
            case 4:
                fos = new FileOutputStream(AMHLIVE2_PP_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_2_PP" + ".zip");
                this.zout = new ZipOutputStream(fos);
                break;
        }
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        String filename = path.toFile().getAbsolutePath();
        if (lookupCreationDate(attrs)) {
            try (FileInputStream fis = new FileInputStream(filename)) {
                ZipEntry entry = new ZipEntry(path.toFile().getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                count++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        zout.close();
        return CONTINUE;
    }

    public int getCount() {
        return count;
    }
}
