package model;

/**
 *The FirstLevelDivisions class creates the constructor along with the getters and setters for first level division data.
 * */
public class FirstLevelDivisions {

    private int divisionID;
    private String divisionName;
    public int country_ID;
    /**
     *The FirstLevelDivisions() constructor.
     * */
    public FirstLevelDivisions(int divisionID, String divisionName, int country_ID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }
    /**
     *The getDivisionID() getter.
     * */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     *The getDivisionName() getter.
     * */
    public String getDivisionName() {
        return divisionName;
    }
    /**
     *The getCountry_ID() getter.
     * */
    public int getCountry_ID() {
        return country_ID;
    }
    /**
     *The toString() method is used to set the Province/State name in a ComboBox.
     * */
    @Override
    public String toString(){
        return(divisionName);
    }
}
