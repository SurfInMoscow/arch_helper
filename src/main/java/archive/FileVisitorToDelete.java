package archive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static utils.LookupCreation.lookupCreationDate;

public class FileVisitorToDelete extends SimpleFileVisitor<Path> {
    private static final Logger logger = LoggerFactory.getLogger(FileVisitorToDelete.class);

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (lookupCreationDate(attrs)) {
            logger.debug("delete file");
            Files.deleteIfExists(file);
        }
        return CONTINUE;
    }
}
