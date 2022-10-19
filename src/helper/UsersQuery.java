package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *The UsersQuery class is responsible for being the middleman between the controllers and the database.
 * all User related sql commands will be found in this class.
 * */
public class UsersQuery {

    /**
     *The getAllUsers() method selects data from the users table in the database using SQL commands.
     * */
    public static ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> usersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            Users users = new Users(userID, userName, userPassword);
            usersObservableList.add(users);
        }
        return usersObservableList;
    }

}
