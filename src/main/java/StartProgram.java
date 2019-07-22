import archive.ArchiveImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.WorkingData;

import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.ConsoleHelper.readFromConsole;
import static utils.RegexDate.dateMatch;
import static utils.RegexDate.parseDate;
import static utils.WorkingData.AMHLIVE1;
import static utils.WorkingData.AMHLIVE1_PP;

public class StartProgram {
    private static final Logger logger = LoggerFactory.getLogger(StartProgram.class);

    public static void main(String[] args) throws Exception {

        System.out.println("Input data of archive in format dd/mm/yyyy:");
        String dateString = "";
        do {
            dateString = readFromConsole();
        } while (!dateMatch(dateString));
        System.out.println(dateString + " - succeed! Date is correct.\n");

        WorkingData.inputDate = parseDate(dateString);
        logger.info("date {} - correct", dateString);
        System.out.println("-----------------------------------------------\n");

        System.out.print("Proccessing...");

        ArchiveImpl impl = new ArchiveImpl();
        proccessZIP(impl, AMHLIVE1, dateString, 1);
        proccessZIP(impl, AMHLIVE1_PP, dateString, 3);
    }

    private static void proccessZIP(ArchiveImpl impl, String pathStr, String dateString, int instance) throws Exception {
        logger.info("start proccessZIP for {}", pathStr);
        int count = impl.makeZIP(Paths.get(pathStr), dateString, instance);
        logger.info("finish proccessZIP for {} - {} files!", pathStr, count);
        System.out.print("Done - " + count + " files!\n");
        System.out.println("-----------------------------------------------\n");
        decisionToDelete(Paths.get(pathStr), impl);
    }

    private static void decisionToDelete(Path src, ArchiveImpl impl) {
        System.out.println("Do you want to delete files from source? y/n");
        String decString = "";
        do {
            decString = readFromConsole();
        } while (!decString.equals("y") && !decString.equals("n"));
        switch (decString) {
            case "y":
                logger.info("decision to delete - {}", decString);
                impl.cleanUp(src);
                System.out.print("Done!\n");
                System.out.println("Exit program.");
                break;
            case "n":
                logger.info("decision to delete - {}", decString);
                System.out.println("Exit program.");
                break;
        }
    }
}
