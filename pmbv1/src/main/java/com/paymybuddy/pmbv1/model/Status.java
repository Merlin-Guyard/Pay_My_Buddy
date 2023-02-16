package com.paymybuddy.pmbv1.model;

public enum Status {

    //User Errors
    DUPLICATE_USER(100, "This user already exists."),
    UNKNOWN_USER(101, "This user doesn't exist."),

    //Contact Errors
    DUPLICATE_CONTACT(201, "This contact is already registered."),
    UNKNOWN_CONTACT(202, "This contact doesn't exist."),
    CONTACT_IS_USER(203, "You cannot add yourself."),
    CONTACT_UNRELATED(204, "This contact isn't in your friend list."),
    CONTACT_REMOVED(298, "This contact isn't in your friend list."),
    CONTACT_ADDED(299, "Contact added successfully."),

    //Operation Errors
    BALANCE_INSUFFICIENT(300, "You don't have enough funds."),
    INADMISSIBLE_AMOUNT(301, "Amount is not admissible."),

    //DB Errors
    DATABASE(400, "A database error has occurred."),

    //TODO
    TODO(9999, "TODO");

    private final int code;
    private final String description;

    private Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
