package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.*;
import java.time.LocalDateTime;


/**
 *The AppointmentsQuery class is responsible for being the middleman between the controllers and the database.
 * all appointment related sql commands will be found in this class.
 * */
public class AppointmentsQuery {


    /**
     *The getAllAppointments method selects data from the appointments table in the database using SQL commands.
     * */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> aList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int appointmentsID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentsStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentsEnd = rs.getTimestamp("End").toLocalDateTime();
                int appointmentsCustomerID = rs.getInt("Customer_ID");
                int tableContactsID = rs.getInt("Contact_ID");
                int tableUsersID = rs.getInt("User_ID");
                Appointments a = new Appointments(appointmentsID, appointmentTitle, appointmentDescription, appointmentLocation,
                        appointmentType, appointmentsStart, appointmentsEnd, appointmentsCustomerID, tableContactsID, tableUsersID);
                aList.add(a);

            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return aList;
    }


    /**
     *The insertAppointment() method inserts data to the appointments table in the database using SQL commands.
     * */
    public static void insertAppointment(String aptTitle, String aptDesc,
                                         String aptLocation, String aptType, String startUTC,
                                         String endUTC, String aptCustID,
                                         String aptContID, String aptUserID)throws SQLException {



        try{
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ps.setString(1, aptTitle);
            ps.setString(2, aptDesc);
            ps.setString(3, aptLocation);
            ps.setString(4, aptType);
            ps.setString(5, startUTC);
            ps.setString(6, endUTC);
            ps.setString(7, aptCustID);
            ps.setString(8, aptContID);
            ps.setString(9, aptUserID);
            ps.execute();


        } catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    /**
     *The update() method updates data to the appointments table in the database using SQL commands.
     * */
    public static void update(String aptId, String aptTitle, String aptDesc,
                             String aptLocation, String aptType, String startUTC,
                             String endUTC, String aptCustID,
                             String aptContID, String aptUserID){

        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, Contact_ID = ?, User_ID = ? WHERE Appointment_ID = ?;";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, aptTitle);
            ps.setString(2, aptDesc);
            ps.setString(3, aptLocation);
            ps.setString(4, aptType);
            ps.setString(5, startUTC);
            ps.setString(6, endUTC);
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "admin");
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10, "admin");
            ps.setString(11, aptCustID);
            ps.setString(12, aptContID);
            ps.setString(13, aptUserID);
            ps.setString(14, aptId);

            ps.execute();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     *The delete() method deletes data from the appointments table in the database using SQL commands.
     * */
    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     *The reportTotalsByTypeAndMonth method selects data from the appointments table in the database using SQL commands.
     * */
    public static ObservableList<String> reportTotalsByTypeAndMonth() throws SQLException {
        ObservableList<String> reportStrings = FXCollections.observableArrayList();


        PreparedStatement typeSqlCommand = JDBC.getConnection().prepareStatement(
                "SELECT Type, COUNT(Type) as \"Total\" FROM appointments GROUP BY Type");

        PreparedStatement monthSqlCommand = JDBC.getConnection().prepareStatement(
                "SELECT MONTHNAME(Start) as \"Month\", COUNT(MONTH(Start)) as \"Total\" from appointments GROUP BY Month");

        ResultSet typeResults = typeSqlCommand.executeQuery();
        ResultSet monthResults = monthSqlCommand.executeQuery();

        while (typeResults.next()) {
            String typeStr = "Type: " + typeResults.getString("Type") + " Count: " +
                    typeResults.getString("Total") + "\n";
            reportStrings.add(typeStr);

        }

        while (monthResults.next()) {
            String monthStr = "Month: " + monthResults.getString("Month") + " Count: " +
                    monthResults.getString("Total") + "\n";
            reportStrings.add(monthStr);

        }

        monthSqlCommand.close();
        typeSqlCommand.close();

        return reportStrings;

    }


}
