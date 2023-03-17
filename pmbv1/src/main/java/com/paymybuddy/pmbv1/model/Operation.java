package com.paymybuddy.pmbv1.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="operation_id")
    private int operationId;

    @ManyToMany
    @JoinTable( name = "T_Operations_Users_Associations",
            joinColumns = @JoinColumn( name = "operation_id" ),
            inverseJoinColumns = @JoinColumn( name = "user_id" ) )
    private List<User> users = new ArrayList<>();

    @Column(name="sender")
    private String sender;

    @Column(name="receiver")
    private String receiver;

    @Column(name="description")
    private String description;

    @Column(name="amount")
    private int amount;

    public Operation(int operationId, List<User> users, String sender, String receiver, String description, int amount) {
        this.operationId = operationId;
        this.users = users;
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
        this.amount = amount;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
