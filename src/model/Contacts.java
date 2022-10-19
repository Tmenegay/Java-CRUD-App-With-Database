package model;
/**
 *The contacts class creates the constructor along with the getters and setters for contact data.
 * */
public class Contacts {

    public int contactID ;
    public String contactName;
    public String contactEmail;
    /**
     *The getContactID() getter.
     * */
    public int getContactID() {
        return contactID;
    }
    /**
     *The getContactID() getter.
     * */
    public String getContactName() {
        return contactName;
    }

    /**
     *The Contacts() constructor.
     * */
    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
}
