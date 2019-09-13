import archive.ArchiveImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.WorkingData;

import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.ConsoleHelper.checkInstance;
import static utils.ConsoleHelper.readFromConsole;
import static utils.RegexDate.dateMatch;
import static utils.RegexDate.parseDate;
import static utils.WorkingData.*;

public class StartProgram {
    private static final Logger logger = LoggerFactory.getLogger(StartProgram.class);

    public static void main(String[] args) throws Exception {

        System.out.println("Choose your instanse to archive:" + "\n" +
                "1. amhlive1;" + "\n" +
                "2. amhlive2;" + "\n" +
                "3. amhlive1_pp;" + "\n" +
                "4. amhlive2_pp;" + "\n" +
                "Press 1-4 -> Enter...");

        int number = 0;
        String numberString = ""; //use it for working with input text from console.
        do {
            numberString = readFromConsole();
            number = checkInstance(numberString);
            if (number != 1 && number != 2 && number != 3 && number != 4) {
                logger.warn("number {} - incorrect", number);
                System.out.println("Incorrect number!");
            } else {
                switch (number) {
                    case 1:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive1.\n");
                        break;
                    case 2:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive2.\n");
                        break;
                    case 3:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive1_pp.\n");
                        break;
                    case 4:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive2_pp.\n");
                        break;
                }
            }
        } while (number != 1 && number != 2 && number != 3 && number != 4);

        System.out.println("-----------------------------------------------\n");

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
        switch (number) {
            case 1:
                proccessZIP(impl, AMHLIVE1, dateString, 1);
                break;
            case 2:
                proccessZIP(impl, AMHLIVE2, dateString, 2);
                break;
            case 3:
                proccessZIP(impl, AMHLIVE1_PP, dateString, 3);
                break;
            case 4:
                proccessZIP(impl, AMHLIVE2_PP, dateString, 4);
                break;
        }
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
            decString = readFromConsole().toLowerCase();
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
