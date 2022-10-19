package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *The CountryQuery class is responsible for being the middleman between the controllers and the database.
 * all country related sql commands will be found in this class.
 * */
public class CountryQuery extends Country{

    public CountryQuery(String country, int countryID) {
        super(country, countryID);
    }

    /**
     *The getAllCountries() method selects data from the countries table in the database using SQL commands.
     * */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> cList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                CountryQuery c = new CountryQuery(country, countryID);
                cList.add(c);

            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return cList;
    }




}
