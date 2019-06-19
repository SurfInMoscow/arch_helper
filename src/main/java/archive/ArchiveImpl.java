package archive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArchiveImpl {

    public void makeZIP(Path src) {
        try {
            Files.walkFileTree(src, new FileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanUp(Path src) {
        try {
            Files.walkFileTree(src, new FileVisitorToDelete());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
