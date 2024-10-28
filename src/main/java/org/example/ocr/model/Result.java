package org.example.ocr.model;

public class Result {

    private String nameTrimmed;
    private String lastnameTrimmed;
    private String dateOfBirth;
    private String cardNumberId;


    public Result(String nameTrimmed, String lastnameTrimmed, String dateOfBirth, String cardNumberId) {
        this.nameTrimmed = nameTrimmed;
        this.lastnameTrimmed = lastnameTrimmed;
        this.dateOfBirth = dateOfBirth;
        this.cardNumberId = cardNumberId;
    }
    public Result(){

    }

    public String getNameTrimmed() {
        return nameTrimmed;
    }

    public void setNameTrimmed(String nameTrimmed) {
        this.nameTrimmed = nameTrimmed;
    }

    public String getLastnameTrimmed() {
        return lastnameTrimmed;
    }

    public void setLastnameTrimmed(String lastnameTrimmed) {
        this.lastnameTrimmed = lastnameTrimmed;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCardNumberId() {
        return cardNumberId;
    }

    public void setCardNumberId(String cardNumberId) {
        this.cardNumberId = cardNumberId;
    }
}
