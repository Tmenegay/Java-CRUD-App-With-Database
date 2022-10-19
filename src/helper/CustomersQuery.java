package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.*;
import java.time.LocalDateTime;

/**
 *The CustomerQuery class is responsible for being the middleman between the controllers and the database.
 * all customer related sql commands will be found in this class.
 * */
public class CustomersQuery {


    /**
     *The getAllCustomers() method selects data from the appointments table in the database using SQL commands.
     * It joins the First_Level_Divisions table with the Customers table
     * */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> cList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Phone, c.Address, f.Division_ID, f.Division, co.Country, c.Postal_Code From customers as c " +
                    "Inner Join First_Level_Divisions as f ON c.Division_ID = f.Division_ID Right Join Countries as co ON f.Country_ID = co.Country_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int customerCustomerID = rs.getInt("Customer_ID");
                String customerCustomerName = rs.getString("Customer_Name");
                String customerPhone = rs.getString("Phone");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerAddress = rs.getString("Address");
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String customerCountry = rs.getString("Country");

                Customers c = new Customers(customerCustomerID, customerCustomerName, customerPhone, customerPostalCode, customerAddress, divisionID, division, customerCountry);
                cList.add(c);

            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return cList;
    }

    /**
     *The insertCustomer() method inserts data to the customers table in the database using SQL commands.
     * */
    public static void insertCustomer(String customerName, String customerAddress, int customerState, String customerZip, String customerPhone)throws SQLException {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                    " VALUES (?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerZip);
            ps.setString(4, customerPhone);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, "admin");
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "admin");
            ps.setInt(9, customerState);
            ps.execute();



        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *The updateCustomer() method updates data to the customer table in the database using SQL commands.
     * */
    public static void updateCustomer(String customerID, String customerName, String customerAddress, int customerState, String customerZip, String customerPhone) throws SQLException {
        try {
            String sql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                    "Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Integer.parseInt(customerID));
            ps.setString(2, customerName);
            ps.setString(3, customerAddress);
            ps.setString(4, customerZip);
            ps.setString(5, customerPhone);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, "admin");
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, "admin");
            ps.setInt(10, customerState);
            ps.setInt(11, Integer.parseInt(customerID));
            ps.execute();



        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    /**
     *The delete() method deletes data from the customer table in the database using SQL commands.
     * */
    public static void delete(int customer)  {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer);
            ps.execute();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ObservableList<String> getFilterDivisions(String inputCountry) throws SQLException {

        ObservableList<String> filteredDivs = FXCollections.observableArrayList();
        PreparedStatement sql = JDBC.connection.prepareStatement(
                "SELECT c.Country, c.Country_ID,  d.Division_ID, d.Division FROM countries as c RIGHT OUTER JOIN " +
                        "first_level_divisions AS d ON c.Country_ID = d.Country_ID WHERE c.Country = ?");

        sql.setString(1, inputCountry);
        ResultSet results = sql.executeQuery();

        while (results.next()) {
            filteredDivs.add(results.getString("Division"));
        }

        sql.close();
        return filteredDivs;

    }

    public static ObservableList<String> getAllCountries() throws SQLException {

        ObservableList<String> allCountries = FXCollections.observableArrayList();
        PreparedStatement sql = JDBC.connection.prepareStatement("SELECT DISTINCT Country FROM countries");
        ResultSet rs = sql.executeQuery();

        while (rs.next()) {
            allCountries.add(rs.getString("Country"));
        }
        sql.close();
        return allCountries;

    }
    public static Integer getSpecificDivisionID(String division) throws SQLException {
        Integer divID = 0;
        PreparedStatement sqlCommand = JDBC.connection.prepareStatement("SELECT Division, Division_ID FROM " +
                "first_level_divisions WHERE Division = ?");

        sqlCommand.setString(1, division);

        ResultSet result = sqlCommand.executeQuery();

        while ( result.next() ) {
            divID = result.getInt("Division_ID");
        }

        sqlCommand.close();
        return divID;

    }


}
