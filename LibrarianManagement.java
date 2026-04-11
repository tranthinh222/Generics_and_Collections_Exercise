import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class LibrarianManagement {
    private static final String binaryFile = "librarian.dat";
    private HashMap<String, String> librarians;

    public void saveLibrarianListToFile(){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(binaryFile))){
            output.writeObject(librarians);

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadLibrarianListFromFile(){
        File file = new File(binaryFile);
        if (!file.exists())
            return;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(binaryFile))){
            this.librarians = (HashMap<String, String>) input.readObject();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addLibrarian(String username, String password){
        if (this.librarians == null)
        {
            this.librarians = new HashMap<>();
        }
        this.librarians.put(username, password);
        this.saveLibrarianListToFile();
    }

    public boolean checkValidAccount(String username, String password){
        if (this.librarians == null)
            return false;
        String tmp = this.librarians.get(username);
        if (tmp != null && tmp.equals(password))
            return true;
        return false;
    }
    
    public boolean isDuplicatedAccount(String username, String password){
        if (this.librarians == null)
            return false;

        if (this.librarians.get(username) != null)
            return true;
        return false;
    }
}
