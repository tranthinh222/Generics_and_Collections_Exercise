import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowRecordManagement {
    private ArrayList<BorrowRecord> records;
    private String filePath;

    public BorrowRecordManagement() {
        this.records = new ArrayList<>();
        this.filePath = "borrowRecordsFile.txt";
        loadRecordsFromFile();
    }

    // Load records from file
    public void loadRecordsFromFile() {
        records.clear();
        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    String recordId = parts[0];
                    String readerId = parts[1];
                    LocalDate borrowDate = LocalDate.parse(parts[2]);
                    LocalDate expectedReturnDate = LocalDate.parse(parts[3]);
                    LocalDate actualReturnDate = "null".equals(parts[4]) ? null : LocalDate.parse(parts[4]);

                    ArrayList<String> isbns = new ArrayList<>();
                    String[] isbnArray = parts[5].split(",");
                    for (String isbn : isbnArray) {
                        isbns.add(isbn.trim());
                    }

                    String status = parts[6];

                    BorrowRecord record = new BorrowRecord(recordId, readerId, borrowDate,
                            expectedReturnDate, actualReturnDate, isbns, status);
                    records.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save records to file
    public void saveRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (BorrowRecord record : records) {
                writer.write(record.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add new borrow record
    public void addRecord(BorrowRecord record) {
        records.add(record);
        saveRecordsToFile();
    }

    // Get all records
    public ArrayList<BorrowRecord> getRecords() {
        return records;
    }

    // Get records by reader ID
    public ArrayList<BorrowRecord> getRecordsByReaderId(String readerId) {
        ArrayList<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord record : records) {
            if (record.getReaderId().equals(readerId)) {
                result.add(record);
            }
        }
        return result;
    }

    // Get record by record ID
    public BorrowRecord getRecordById(String recordId) {
        for (BorrowRecord record : records) {
            if (record.getRecordId().equals(recordId)) {
                return record;
            }
        }
        return null;
    }

    // Mark as returned
    public boolean markAsReturned(String recordId, LocalDate returnDate) {
        BorrowRecord record = getRecordById(recordId);
        if (record != null && record.getStatus().equals("BORROWING")) {
            record.setActualReturnDate(returnDate);
            saveRecordsToFile();
            return true;
        }
        return false;
    }

    // Generate record ID (format: REC_timestamp)
    public String generateRecordId() {
        return "REC_" + System.currentTimeMillis();
    }

    // Get borrowing records (status = "BORROWING")
    public ArrayList<BorrowRecord> getBorrowingRecords() {
        ArrayList<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord record : records) {
            if (record.getStatus().equals("BORROWING")) {
                result.add(record);
            }
        }
        return result;
    }

    // Get returned records (status = "RETURNED")
    public ArrayList<BorrowRecord> getReturnedRecords() {
        ArrayList<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord record : records) {
            if (record.getStatus().equals("RETURNED")) {
                result.add(record);
            }
        }
        return result;
    }

    // Get overdue records
    public ArrayList<BorrowRecord> getOverdueRecords() {
        ArrayList<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord record : records) {
            if (record.isOverdue()) {
                result.add(record);
            }
        }
        return result;
    }

    // Delete record (update to CANCELLED status)
    public boolean deleteRecord(String recordId) {
        BorrowRecord record = getRecordById(recordId);
        if (record != null) {
            records.remove(record);
            saveRecordsToFile();
            return true;
        }
        return false;
    }

    // Update record
    public boolean updateRecord(String recordId, BorrowRecord updatedRecord) {
        BorrowRecord record = getRecordById(recordId);
        if (record != null) {
            int index = records.indexOf(record);
            records.set(index, updatedRecord);
            saveRecordsToFile();
            return true;
        }
        return false;
    }
}
