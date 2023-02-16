package com.mg.paymybuddy.model;

import javax.persistence.*;


@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @Column(name="user_id")
    private int userId;

    @Column(name="contact_id")
    private int contactId;

    public Contact() {
    }

    public Contact(int userId, int contactId) {
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}