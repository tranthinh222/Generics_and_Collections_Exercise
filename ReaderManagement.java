import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReaderManagement {
    private static final String readersFile = "readersFile.txt";
    private ArrayList<Reader> readers;

    ReaderManagement() {
        this.readers = new ArrayList<>();
    }

    public void saveReaderListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(readersFile))) {
            for (Reader reader : readers) {
                writer.write(reader.getReaderId() + "," + reader.getName() + ","
                        + reader.getIdCard() + "," + reader.getDateOfBirth().toString() + "," + reader.getGender() + ","
                        + reader.getEmail() + "," + reader.getAddress() + ","
                        + reader.getCardCreationDate().toString() + "," + reader.getExpiryDate().toString());
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadReadersFromFile() {
        this.readers.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(readersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    String readerId = data[0];
                    String name = data[1];
                    String idCard = data[2];
                    LocalDate dateOfBirth = LocalDate.parse(data[3]);
                    String gender = data[4];
                    String email = data[5];
                    String address = data[6];
                    LocalDate cardCreationDate = LocalDate.parse(data[7]);

                    Reader readerObj = new Reader(readerId, name, idCard, dateOfBirth, gender, email, address,
                            cardCreationDate);
                    if (data.length == 9) {
                        LocalDate expiryDate = LocalDate.parse(data[8]);
                        readerObj.setExpiryDate(expiryDate);
                    }

                    this.readers.add(readerObj);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addReader(Reader reader) {
        if (this.readers == null)
            this.readers = new ArrayList<>();
        this.readers.add(reader);
    }

    public void changeReaderInfo(Reader reader, String ISNB) {
        for (int i = 0; i < this.readers.size(); i++) {
            if (this.readers.get(i).getReaderId().equals(ISNB)) {
                this.readers.get(i).setReaderId(reader.getReaderId());
                this.readers.get(i).setAddress(reader.getAddress());
                this.readers.get(i).setEmail(reader.getEmail());
                this.readers.get(i).setDateOfBirth(reader.getDateOfBirth());
                this.readers.get(i).setExpiryDate(reader.getExpiryDate());
                this.readers.get(i).setName(reader.getName());
                this.readers.get(i).setIdCard(reader.getIdCard());
                this.readers.get(i).setCardCreationDate(reader.getCardCreationDate());
                this.readers.get(i).setGender(reader.getGender());
            }
        }
    }

    public ArrayList<Reader> getReaders() {
        return this.readers;
    }

}
