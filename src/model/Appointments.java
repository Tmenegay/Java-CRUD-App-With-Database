package model;
import java.time.LocalDateTime;

/**
 *The Appointments class creates the constructor along with the getters and setters for appointment data.
 * */
public class Appointments {
    private int appointmentsID;
    private String appointmentsTitle;
    private String appointmentsDescription;
    private String appointmentsLocation;
    private String appointmentsType;
    private LocalDateTime appointmentsStart;
    private LocalDateTime appointmentsEnd;
    private int appointmentsCustomerID;
    private int contactID;
    private int tableUsersID;

    /**
     *The Appointments() constructor.
     * */
    public Appointments(int appointmentsID, String appointmentsTitle, String appointmentsDescription,
                        String appointmentsLocation, String appointmentsType, LocalDateTime appointmentsStart, LocalDateTime appointmentsEnd, int appointmentsCustomerID,
                        int contactID, int tableUsersID) {
        this.appointmentsID = appointmentsID;
        this.appointmentsTitle = appointmentsTitle;
        this.appointmentsDescription = appointmentsDescription;
        this.appointmentsLocation = appointmentsLocation;
        this.appointmentsType = appointmentsType;
        this.appointmentsStart = appointmentsStart;
        this.appointmentsEnd = appointmentsEnd;
        this.appointmentsCustomerID = appointmentsCustomerID;
        this.contactID = contactID;
        this.tableUsersID = tableUsersID;
    }
    /**
     *The getAppointmentsID() getter.
     * */
    public int getAppointmentsID() {
        return appointmentsID;
    }
    /**
     *The getAppointmentsTitle() getter.
     * */
    public String getAppointmentsTitle() {
        return appointmentsTitle;
    }
    /**
     *The getAppointmentsDescription() getter.
     * */
    public String getAppointmentsDescription() {
        return appointmentsDescription;
    }
    /**
     *The getAppointmentsLocation() getter.
     * */
    public String getAppointmentsLocation() {
        return appointmentsLocation;
    }
    /**
     *The getAppointmentsType() getter.
     * */
    public String getAppointmentsType() {
        return appointmentsType;
    }
    /**
     *The getAppointmentsStart() getter.
     * */
    public LocalDateTime getAppointmentsStart() {
        return appointmentsStart;
    }
    /**
     *The getAppointmentsEnd() getter.
     * */
    public LocalDateTime getAppointmentsEnd() {
        return appointmentsEnd;
    }
    /**
     *The getAppointmentsCustomerID() getter.
     * */
    public int getAppointmentsCustomerID() {
        return appointmentsCustomerID;
    }
    /**
     *The getContactID() getter.
     * */
    public int getContactID() {
        return contactID;
    }
    /**
     *The getTableUsersID() getter.
     * */
    public int getTableUsersID() {
        return tableUsersID;
    }


}
