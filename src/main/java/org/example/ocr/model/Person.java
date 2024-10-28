package org.example.ocr.model;

public class Person {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String cardNumberId;

    public Person(String firstName, String lastName, String dateOfBirth, String cardNumberId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cardNumberId = cardNumberId;
    }

    public Person() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCardNumberId() {
        return cardNumberId;
    }
}
