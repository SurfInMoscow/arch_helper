package archive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArchiveImpl {

    public int makeZIP(Path src, String dateString, int instance) {
        try {
            FileVisitor fileVisitor = new FileVisitor(dateString, instance);
            Files.walkFileTree(src, fileVisitor);
            return fileVisitor.getCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void cleanUp(Path src) {
        try {
            Files.walkFileTree(src, new FileVisitorToDelete());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
