package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class WorkingData {
    
    private static WorkingData workingData = new WorkingData();

    private String AMHLIVE1;
    private String AMHLIVE2;
    private String AMHLIVE1_PP;
    private String AMHLIVE2_PP;
    private String AMHLIVE1_GPI;
    private String AMHLIVE2_GPI;
    private String AMHLIVE1_GPI_PP;
    private String AMHLIVE2_GPI_PP;
    private String AMHLIVE1_ZIP;
    private String AMHLIVE2_ZIP;
    private String AMHLIVE1_PP_ZIP;
    private String AMHLIVE2_PP_ZIP;
    private String AMHLIVE1_GPI_ZIP;
    private String AMHLIVE2_GPI_ZIP;
    private String AMHLIVE1_GPI_PP_ZIP;
    private String AMHLIVE2_GPI_PP_ZIP;

    private LocalDate inputDate;

    private WorkingData() {
        init();
    }

    private void init() {
        Properties properties = new Properties();
        
        try {
            properties.load(new FileInputStream("resource.properties"));
        } catch (IOException e) {
            try {
                properties.load(new FileInputStream(this.getClass().getClassLoader().getResource("resource.properties").getFile()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        
        AMHLIVE1 = properties.getProperty("amhlive1.path");
        AMHLIVE2 = properties.getProperty("amhlive2.path");
        AMHLIVE1_PP = properties.getProperty("amhlive1_pp.path");
        AMHLIVE2_PP = properties.getProperty("amhlive2_pp.path");
        AMHLIVE1_GPI = properties.getProperty("amhlive1_gpi.path");
        AMHLIVE2_GPI = properties.getProperty("amhlive2_gpi.path");
        AMHLIVE1_GPI_PP = properties.getProperty("amhlive1_gpi_pp.path");
        AMHLIVE2_GPI_PP = properties.getProperty("amhlive2_gpi_pp.path");
        AMHLIVE1_ZIP = properties.getProperty("zip.amhlive1.path");
        AMHLIVE2_ZIP = properties.getProperty("zip.amhlive2.path");
        AMHLIVE1_PP_ZIP = properties.getProperty("zip.amhlive1_pp.path");
        AMHLIVE2_PP_ZIP = properties.getProperty("zip.amhlive2_pp.path");
        AMHLIVE1_GPI_ZIP = properties.getProperty("zip.amhlive1_gpi.path");
        AMHLIVE2_GPI_ZIP = properties.getProperty("zip.amhlive2_gpi.path");
        AMHLIVE1_GPI_PP_ZIP = properties.getProperty("zip.amhlive1_gpi_pp.path");
        AMHLIVE2_GPI_PP_ZIP = properties.getProperty("zip.amhlive2_gpi_pp.path");
    }

    public static WorkingData getWorkingData() {
        return workingData;
    }

    public String AMHLIVE1() {
        return AMHLIVE1;
    }

    public String AMHLIVE2() {
        return AMHLIVE2;
    }

    public String AMHLIVE1_PP() {
        return AMHLIVE1_PP;
    }

    public String AMHLIVE2_PP() {
        return AMHLIVE2_PP;
    }

    public String AMHLIVE1_GPI() {
        return AMHLIVE1_GPI;
    }

    public String AMHLIVE2_GPI() {
        return AMHLIVE2_GPI;
    }

    public String AMHLIVE1_GPI_PP() {
        return AMHLIVE1_GPI_PP;
    }

    public String AMHLIVE2_GPI_PP() {
        return AMHLIVE2_GPI_PP;
    }

    public String AMHLIVE1_ZIP() {
        return AMHLIVE1_ZIP;
    }

    public String AMHLIVE2_ZIP() {
        return AMHLIVE2_ZIP;
    }

    public String AMHLIVE1_PP_ZIP() {
        return AMHLIVE1_PP_ZIP;
    }

    public String AMHLIVE2_PP_ZIP() {
        return AMHLIVE2_PP_ZIP;
    }

    public String AMHLIVE1_GPI_ZIP() {
        return AMHLIVE1_GPI_ZIP;
    }

    public String AMHLIVE2_GPI_ZIP() {
        return AMHLIVE2_GPI_ZIP;
    }

    public String AMHLIVE1_GPI_PP_ZIP() {
        return AMHLIVE1_GPI_PP_ZIP;
    }

    public String AMHLIVE2_GPI_PP_ZIP() {
        return AMHLIVE2_GPI_PP_ZIP;
    }

    public LocalDate inputDate() {
        return inputDate;
    }

    public void setInputDate(LocalDate inputDate) {
        this.inputDate = inputDate;
    }
}