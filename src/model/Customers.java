package model;

/**
 *The Customers class creates the constructor along with the getters and setters for customers data.
 * */
public class Customers {

    private int customerCustomerID;
    private String customerCustomerName;
    private String customerPhone;
    private String customerPostalCode;
    private String customerAddress;
    private int divisionID;
    private String division;
    private String customerCountry;

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    /**
     *The Customers() constructor.
     * */
    public Customers(int customerCustomerID, String customerCustomerName, String customerPhone, String customerPostalCode, String customerAddress, int divisionID, String division, String customerCountry) {
        this.customerCustomerID = customerCustomerID;
        this.customerCustomerName = customerCustomerName;
        this.customerPhone = customerPhone;
        this.customerPostalCode = customerPostalCode;
        this.customerAddress = customerAddress;
        this.divisionID = divisionID;
        this.division = division;
        this.customerCountry = customerCountry;
    }
    /**
     *The getCustomerCustomerID() getter.
     * */
    public int getCustomerCustomerID() {
        return customerCustomerID;
    }
    /**
     *The getCustomerCustomerName() getter.
     * */
    public String getCustomerCustomerName() {
        return customerCustomerName;
    }
    /**
     *The getCustomerPhone() getter.
     * */
    public String getCustomerPhone() {
        return customerPhone;
    }
    /**
     *The getCustomerPostalCode() getter.
     * */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }
    /**
     *The getCustomerAddress() getter.
     * */
    public String getCustomerAddress() {
        return customerAddress;
    }
    /**
     *The getDivisionID() getter.
     * */
    public int getDivisionID() {
        return divisionID;
    }
}
