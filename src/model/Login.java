package model;

import helper.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *The Login class is responsible for being the middleman between the controllers and the database.
 * all login related sql commands will be found in this class.
 * */
public class Login {
    private static Users loggedInUser;

    public Login() {}

    /**
     * the attemptLogon() method will verify that the data entered on the login form is a valid combination of username
     * and password.
     */
    public static boolean attemptLogon(String userNameInput, String userPassword) throws SQLException {
        Connection con = JDBC.connection;
        PreparedStatement sqlCommand = con.prepareStatement("SELECT * FROM users WHERE " +
                "User_Name = ? AND Password = ?");
        sqlCommand.setString(1, userNameInput);
        sqlCommand.setString(2, userPassword);
        System.out.println("Executing query...");
        ResultSet result = sqlCommand.executeQuery();
        if (!result.next()) {
            sqlCommand.close();
            return false;

        }
        else {
            loggedInUser = new Users();
            sqlCommand.close();
            return true;

        }

    }
}
