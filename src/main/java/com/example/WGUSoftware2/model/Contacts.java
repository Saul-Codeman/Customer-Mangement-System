package com.example.WGUSoftware2.model;

/**
 * Contacts class that contains functions for getting, setting, and constructing contacts
 */
public class Contacts {
    private Integer contactID;
    private String contactName;
    private String email;

    /**
     * Constructor for contacts
     * @param contactID contact ID
     * @param contactName contact name
     * @param email contact email
     */
    public Contacts(Integer contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Getter - contact ID.
     * @return ID of the contact.
     */
    public Integer getContactID() {
        return contactID;
    }
    /**
     * Setter - contact ID.
     * @param contactID the contact ID to set.
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter - contact name.
     * @return name of the contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter - contact name.
     * @param contactName the contact name to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter - contact email.
     * @return email of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter - contact email.
     * @param email the contact email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
