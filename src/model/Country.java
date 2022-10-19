package model;
/**
 *The Country class creates the constructor along with the getters and setters for country data.
 * */
public class Country {

    private String countryName;
    private int countryID;

    /**
     *The Country() constructor.
     * */
    public Country(String countryName, int countryID) {
        this.countryName = countryName;
        this.countryID = countryID;
    }
    /**
     *The getCountryName() getter.
     * */
    public String getCountryName() {
        return countryName;
    }

    /**
     *The getCountryID() getter.
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     *The toString() method is used to set the Country name in a ComboBox.
     * */
    @Override
    public String toString(){
        return(countryName);
    }
}