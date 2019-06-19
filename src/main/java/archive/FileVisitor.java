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

public class FileVisitor extends SimpleFileVisitor<Path> {
    private ZipOutputStream zout;

    public FileVisitor() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("/Users/vorobyev/Documents/output.zip");
        this.zout = new ZipOutputStream(fos);
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
}
