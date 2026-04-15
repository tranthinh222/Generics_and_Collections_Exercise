import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BookManagement {
    private ArrayList<Book> books;
    private final String FILE_NAME = "booksFile.txt";

    public BookManagement() {
        this.books = new ArrayList<>();
        loadBooksFromFile();
    }

    public void loadBooksFromFile() {
        books.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    String isbn = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String publisher = parts[3];
                    int publicationYear = Integer.parseInt(parts[4]);
                    String category = parts[5];
                    double price = Double.parseDouble(parts[6]);
                    int quantity = Integer.parseInt(parts[7]);

                    Book book = new Book(isbn, title, author, publisher, publicationYear, category, price, quantity);
                    books.add(book);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty book list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean isbnExists(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
