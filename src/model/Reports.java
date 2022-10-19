package model;
/**
 *The Reports class creates the constructor along with the getters and setters for report data.
 * */
public class Reports {
    private int countryCount;
    private String countryName;
    public String appointmentMonth;
    public int appointmentTotal;

    /**
     * The Reports() constructor.
     */
    public Reports(String countryName, int countryCount) {
        this.countryCount = countryCount;
        this.countryName = countryName;

    }

    /**
     * the getCountryName getter
     */
    public String getCountryName() {

        return countryName;
    }

    /**
     * the getCountryCount getter
     */
    public int getCountryCount() {

        return countryCount;
    }

}
