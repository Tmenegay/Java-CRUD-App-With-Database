package model;

/**
 *The Users class creates the constructor along with the getters and setters for users data.
 * */
public class Users {

    private static int userID;
    private static String username;
    private static String password;
    

    public Users() {
        userID = 0;
        username = null;
        password = null;

    }


    /**
     *The Users() constructor.
     * */
    public Users(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }
    /**
     *The getUserID() getter.
     * */
    public int getUserID() {
        return userID;
    }
    /**
     *The getUsername() getter.
     * */
    public static String  getUsername() {
        return username;
    }
    /**
     *The getPassword() getter.
     * */
    public String getPassword() {
        return this.password;
    }

}
