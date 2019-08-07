package archive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.StandardOpenOption.READ;
import static utils.LookupCreation.lookupCreationDate;
import static utils.WorkingData.*;

public class FileVisitor extends SimpleFileVisitor<Path> {
    private static final Logger logger = LoggerFactory.getLogger(FileVisitor.class);
    private FileOutputStream fos = null;
    private ZipOutputStream zout;
    private WritableByteChannel chOut;
    private static final ByteBuffer BUFF = ByteBuffer.allocate(2048);
    private int count = 0;

    public FileVisitor(String dateString, int instance) throws Exception {
        String[] strings = dateString.split("/");
        String pathZIP;
        switch (instance) {
            case 1:
                pathZIP = AMHLIVE1_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_1" + ".zip";
                createZipOutputStream(pathZIP);
                break;
            case 2:
                pathZIP = AMHLIVE2_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_2" + ".zip";
                createZipOutputStream(pathZIP);
                break;
            case 3:
                pathZIP = AMHLIVE1_PP_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_1_PP" + ".zip";
                createZipOutputStream(pathZIP);
                break;
            case 4:
                pathZIP = AMHLIVE2_PP_ZIP + "/" + strings[2] + strings[1] + strings[0] + "_2_PP" + ".zip";
                createZipOutputStream(pathZIP);
                break;
        }
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        String filename = path.toFile().getAbsolutePath();
        if (lookupCreationDate(attrs)) {
            try (FileChannel chIn = FileChannel.open(path.toAbsolutePath(), READ)) {
                ZipEntry entry = new ZipEntry(path.toFile().getName());
                zout.putNextEntry(entry);
                logger.debug("added new entry - {}", entry.getName());
                chOut = Channels.newChannel(zout);
                while (chIn.read(BUFF) != -1){
                    BUFF.flip();
                    chOut.write(BUFF);
                    BUFF.compact();
                }
                zout.closeEntry();
                count++;
            }
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        logger.info("Streams are closed.");
        chOut.close();
        zout.close();
        fos.close();
        return CONTINUE;
    }

    private void createZipOutputStream(String pathZIP) throws Exception {
        if (!Files.exists(Paths.get(pathZIP))) {
            logger.info("created stream for {}", pathZIP);
            fos = new FileOutputStream(pathZIP);
            this.zout = new ZipOutputStream(fos);
        } else {
            logger.warn("Archive is already created! Be careful while input date.");
            throw new IllegalStateException("Archive is already created! Be careful while input date.");
        }
    }

    public int getCount() {
        return count;
    }
}
