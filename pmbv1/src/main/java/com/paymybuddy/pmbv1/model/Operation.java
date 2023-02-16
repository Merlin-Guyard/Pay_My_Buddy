package com.paymybuddy.pmbv1.model;

import javax.persistence.*;

@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private int operationId;

    @Column(name="contact")
    private String contact;

    @Column(name="description")
    private String description;

    @Column(name="date")
    private String date;

    @Column(name="amount")
    private int amount;
}
