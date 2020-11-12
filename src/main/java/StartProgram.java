import archive.ArchiveImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static utils.ConsoleHelper.checkInstance;
import static utils.ConsoleHelper.readFromConsole;
import static utils.RegexDate.dateMatch;
import static utils.RegexDate.parseDate;
import static utils.WorkingData.getWorkingData;

public class StartProgram {
    private static final Logger logger = LoggerFactory.getLogger(StartProgram.class);

    /*
    * Before start program with IDE-Runner copy resource.properties
    * from test/resources to main/resources
    * */
    public static void main(String[] args) throws Exception {

        System.out.println("Choose your instanse to archive:" + "\n" +
                "1. amhlive1;" + "\n" +
                "2. amhlive2;" + "\n" +
                "3. amhlive1_pp;" + "\n" +
                "4. amhlive2_pp;" + "\n" +
                "5. amhlive1_gpi;" + "\n" +
                "6. amhlive2_gpi;" + "\n" +
                "7. amhlive1_gpi_pp;" + "\n" +
                "8. amhlive2_gpi_pp;" + "\n" +
                "Press 1-8 -> Enter...");

        int number = 0;
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        String numberString = ""; //use it for working with input text from console.

        do {
            numberString = readFromConsole();
            number = checkInstance(numberString);

            if (!nums.contains(number)) {
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
                    case 5:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive1_gpi.\n");
                        break;
                    case 6:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive2_gpi.\n");
                        break;
                    case 7:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive1_gpi_pp.\n");
                        break;
                    case 8:
                        logger.info("number {} - correct", number);
                        System.out.println("Correct! You chose - amhlive2_gpi_pp.\n");
                        break;
                }
            }
        } while (!nums.contains(number));

        System.out.println("-----------------------------------------------\n");

        System.out.println("Input data of archive in format dd/mm/yyyy:");
        String dateString = "";

        do {
            dateString = readFromConsole();
        } while (!dateMatch(dateString));

        System.out.println(dateString + " - succeed! Date is correct.\n");

        getWorkingData().setInputDate(parseDate(dateString));
        logger.info("date {} - correct", dateString);
        System.out.println("-----------------------------------------------\n");

        System.out.print("Proccessing...");

        ArchiveImpl impl = new ArchiveImpl();

        switch (number) {
            case 1:
                processZIP(impl, getWorkingData().AMHLIVE1(), dateString, 1);
                break;
            case 2:
                processZIP(impl, getWorkingData().AMHLIVE2(), dateString, 2);
                break;
            case 3:
                processZIP(impl, getWorkingData().AMHLIVE1_PP(), dateString, 3);
                break;
            case 4:
                processZIP(impl, getWorkingData().AMHLIVE2_PP(), dateString, 4);
                break;
            case 5:
                processZIP(impl, getWorkingData().AMHLIVE1_GPI(), dateString, 5);
                break;
            case 6:
                processZIP(impl, getWorkingData().AMHLIVE2_GPI(), dateString, 6);
                break;
            case 7:
                processZIP(impl, getWorkingData().AMHLIVE1_GPI_PP(), dateString, 7);
                break;
            case 8:
                processZIP(impl, getWorkingData().AMHLIVE2_GPI_PP(), dateString, 8);
                break;
        }
    }

    private static void processZIP(ArchiveImpl impl, String pathStr, String dateString, int instance) throws Exception {
        logger.info("start proccessZIP for {}", pathStr);
        Path path = Paths.get(pathStr);
        File processDir = path.toFile();

        if (!processDir.exists()) {
            throw new FileNotFoundException(path.toString().concat("(No such file or directory)"));
        }

        int count = impl.makeZIP(path, dateString, instance);
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
