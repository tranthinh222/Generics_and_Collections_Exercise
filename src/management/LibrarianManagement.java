package src.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class LibrarianManagement {
    private static final String textFile = "data/librarianFile.txt";
    private HashMap<String, String> librarians;

    public void saveLibrarianListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
            if (librarians != null) {
                for (String username : librarians.keySet()) {
                    String password = librarians.get(username);
                    writer.write(username + "|" + password);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLibrarianListFromFile() {
        File file = new File(textFile);
        if (!file.exists()) {
            this.librarians = new HashMap<>();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            this.librarians = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    this.librarians.put(username, password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLibrarian(String username, String password) {
        if (this.librarians == null) {
            this.librarians = new HashMap<>();
        }
        this.librarians.put(username, password);
        this.saveLibrarianListToFile();
    }

    public boolean checkValidAccount(String username, String password) {
        if (this.librarians == null)
            return false;
        String tmp = this.librarians.get(username);
        if (tmp != null && tmp.equals(password))
            return true;
        return false;
    }

    public boolean isDuplicatedAccount(String username, String password) {
        if (this.librarians == null)
            return false;

        if (this.librarians.get(username) != null)
            return true;
        return false;
    }
}
