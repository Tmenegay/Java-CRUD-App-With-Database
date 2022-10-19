package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *The DivisionQuery class is responsible for being the middleman between the controllers and the database.
 * all State/Province related sql commands will be found in this class.
 * */
public class DivisionQuery extends FirstLevelDivisions {

    public DivisionQuery(int divisionID, String divisionName, int country_ID) {
        super(divisionID, divisionName, country_ID);
    }
    /**
     *The getAllProvinces() method selects data from the First_Level_Divisions table in the database using SQL commands.
     * */
    public static ObservableList<DivisionQuery> getAllProvinces() throws SQLException {
        ObservableList<DivisionQuery> dList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int country_ID = rs.getInt("Country_ID");
                DivisionQuery divisionQuery = new DivisionQuery(divisionID, divisionName, country_ID);
                dList.add(divisionQuery);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return dList;
    }


}
