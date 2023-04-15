package com.paymybuddy.pmbv1.model;

import javax.persistence.*;

@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="operation_id")
    private int operationId;

//    @ManyToOne @JoinColumn(name = "user_id")
    @Column(name="sender")
    private String sender;

//    @ManyToOne @JoinColumn(name = "user_id")
    @Column(name="receiver")
    private String receiver;

    @Column(name="description")
    private String description;

    @Column(name="amount")
    private int amount;

    public Operation() {
    }

    public Operation(String sender, String receiver, String description, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
        this.amount = amount;
    }

    public Operation(int operationId, String sender, String receiver, String description, int amount) {
        this.operationId = operationId;
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
