package src.domain;

import java.time.LocalDate;

public class Reader {
    private String readerID;
    private String name;
    private String idCard;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String address;
    private LocalDate cardCreationDate;
    private LocalDate expiryDate;

    public String getReaderId() {
        return readerID;
    }

    public String getName() {
        return name;
    }

    public String getIdCard() {
        return idCard;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getCardCreationDate() {
        return cardCreationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setReaderId(String readerID) {
        this.readerID = readerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCardCreationDate(LocalDate cardCreationDate) {
        this.cardCreationDate = cardCreationDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Reader(String readerID, String name, String idCard, LocalDate dateOfBirth, String gender, String email,
            String address, LocalDate issueDate) {
        this.readerID = readerID;
        this.name = name;
        this.idCard = idCard;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.cardCreationDate = issueDate;
        this.expiryDate = this.cardCreationDate.plusMonths(48);
    }

}
