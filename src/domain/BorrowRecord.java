package src.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowRecord {
    private String recordId;
    private String readerId;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private ArrayList<String> borrowedISBNs;
    private String status;

    public BorrowRecord(String recordId, String readerId, LocalDate borrowDate,
            ArrayList<String> borrowedISBNs) {
        this.recordId = recordId;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = borrowDate.plusDays(7);
        this.actualReturnDate = null;
        this.borrowedISBNs = new ArrayList<>(borrowedISBNs);
        this.status = "BORROWING";
    }

    public BorrowRecord(String recordId, String readerId, LocalDate borrowDate,
            LocalDate expectedReturnDate, LocalDate actualReturnDate,
            ArrayList<String> borrowedISBNs, String status) {
        this.recordId = recordId;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.borrowedISBNs = new ArrayList<>(borrowedISBNs);
        this.status = status;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getReaderId() {
        return readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public ArrayList<String> getBorrowedISBNs() {
        return new ArrayList<>(borrowedISBNs);
    }

    public String getStatus() {
        return status;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
        if (actualReturnDate != null) {
            this.status = "RETURNED";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long calculateOverduePenalty() {
        if (status.equals("BORROWING")) {
            LocalDate today = LocalDate.now();
            if (today.isAfter(expectedReturnDate)) {
                long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(expectedReturnDate, today);
                return daysOverdue * 5000;
            }
        } else if (actualReturnDate != null && actualReturnDate.isAfter(expectedReturnDate)) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(expectedReturnDate, actualReturnDate);
            return daysOverdue * 5000;
        }
        return 0;
    }

    public boolean isOverdue() {
        if (status.equals("BORROWING")) {
            return LocalDate.now().isAfter(expectedReturnDate);
        }
        return false;
    }

    @Override
    public String toString() {
        String isbnList = String.join(",", borrowedISBNs);
        String actualReturnStr = actualReturnDate != null ? actualReturnDate.toString() : "null";
        return recordId + "|" + readerId + "|" + borrowDate + "|" + expectedReturnDate + "|" +
                actualReturnStr + "|" + isbnList + "|" + status;
    }
}
