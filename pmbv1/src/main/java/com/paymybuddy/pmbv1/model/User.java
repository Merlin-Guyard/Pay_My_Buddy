package com.paymybuddy.pmbv1.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Column(name="first_name")
    @Size(min=2, max=20)
    private String firstName;

    @Column(name="last_name")
    @Size(min=2, max=20)
    private String lastName;

    @Column(name="email")
    @Email
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="balance")
    private double balance;

    @Column(name="card_code")
    private int card_code;

    @Column(name="card_date_limit")
    private int card_date_limit;

    @Column(name="card_secret_number")
    private int card_secret_number;

    @Column(name="card_user_firstname")
    private int card_user_firstname;

    @Column(name="card_user_lastname")
    private int card_user_lastname;

    @Column(name="enabled")
    private int enabled;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "contact",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<User> friendList = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(int userId, String firstName, String lastName, String email, String password, int balance, int card_code, int card_date_limit, int card_secret_number, int card_user_firstname, int card_user_lastname, int enabled) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.card_code = card_code;
        this.card_date_limit = card_date_limit;
        this.card_secret_number = card_secret_number;
        this.card_user_firstname = card_user_firstname;
        this.card_user_lastname = card_user_lastname;
        this.enabled = enabled;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCard_code() {
        return card_code;
    }

    public void setCard_code(int card_code) {
        this.card_code = card_code;
    }

    public int getCard_date_limit() {
        return card_date_limit;
    }

    public void setCard_date_limit(int card_date_limit) {
        this.card_date_limit = card_date_limit;
    }

    public int getCard_secret_number() {
        return card_secret_number;
    }

    public void setCard_secret_number(int card_secret_number) {
        this.card_secret_number = card_secret_number;
    }

    public int getCard_user_firstname() {
        return card_user_firstname;
    }

    public void setCard_user_firstname(int card_user_firstname) {
        this.card_user_firstname = card_user_firstname;
    }

    public int getCard_user_lastname() {
        return card_user_lastname;
    }

    public void setCard_user_lastname(int card_user_lastname) {
        this.card_user_lastname = card_user_lastname;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

}
