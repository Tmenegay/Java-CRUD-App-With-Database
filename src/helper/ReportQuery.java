package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Reports;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *The ReportQuery class is responsible for being the middleman between the controllers and the database.
 * all report related sql commands will be found in this class.
 * */
public class ReportQuery extends Appointments {


    public ReportQuery(int appointmentsID, String appointmentsTitle, String appointmentsDescription, String appointmentsLocation,
                       String appointmentsType, LocalDateTime appointmentsStart, LocalDateTime appointmentsEnd, int appointmentsCustomerID,
                       int tableContactsID, int tableUsersID) {
        super(appointmentsID, appointmentsTitle, appointmentsDescription, appointmentsLocation, appointmentsType, appointmentsStart,
                appointmentsEnd, appointmentsCustomerID, tableContactsID, tableUsersID);
    }
    /**
     *The getCountries() method selects data from the countries table in the database using SQL commands but gets information
     * specific for the Report form.
     * */
    public static ObservableList<Reports> getCountries() throws SQLException {
        ObservableList<Reports> countriesObservableList = FXCollections.observableArrayList();
        String sql = "select countries.Country, count(*) as countryCount from customers inner join first_level_divisions " +
                "on customers.Division_ID = first_level_divisions.Division_ID inner join countries on countries.Country_ID = first_level_divisions.Country_ID " +
                "where  customers.Division_ID = first_level_divisions.Division_ID group by first_level_divisions.Country_ID order by count(*) desc";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String countryName = rs.getString("Country");
            int countryCount = rs.getInt("countryCount");
            Reports report = new Reports(countryName, countryCount);
            countriesObservableList.add(report);

        }

        return countriesObservableList;
    }

}
