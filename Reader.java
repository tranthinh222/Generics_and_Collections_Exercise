import java.time.LocalDate;

public class Reader {
    private String readerId;
    private String name;
    private String idCard;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String address;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    
    public String getReaderId() {
        return readerId;
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
    public LocalDate getIssueDate() {
        return issueDate;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public void setReaderId(String readerId) {
        this.readerId = readerId;
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
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    public Reader(String readerId, String name, String idCard, LocalDate dateOfBirth, String gender, String email,
            String address, LocalDate issueDate) {
        this.readerId = readerId;
        this.name = name;
        this.idCard = idCard;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.issueDate = issueDate;
        this.expiryDate = this.issueDate.plusMonths(48);
    }


}
