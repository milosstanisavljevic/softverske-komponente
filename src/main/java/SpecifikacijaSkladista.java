import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class SpecifikacijaSkladista {

    private File configFile;
    private Korisnik connectedUser;
    private List<Korisnik>korisnici;

    public abstract boolean createRoot(String path, String username, String password);
    public abstract boolean checkIfRootExists(String path);
   // public abstract void checkPrivileges();
    public abstract void createFile(String fileName);
    public abstract void createMoreFiles(int n);
    public abstract void createMoreFolders(int n);
    public abstract void createFolder(String folderName);
    public abstract void deleteFile(String name);
    public abstract void deleteFolder(String name);

    public Map<String, Object> mapConfig(Object size,String filetype, int maxNumber, String admin ){
        Map<String, Object> map = new HashMap<>();
        map.put("size of root directory", size);
        map.put("file type", filetype);
        map.put("max number of files", maxNumber);
        map.put("admin", admin);

        return map;
    }

    public abstract void makeConfig(String path, Map<String, Object> mapa);

    public abstract void makeDefaultConfig(String path, String username);

    public abstract void updateConfig(String path, int size, String filetype, int maxNumber);

    public List<Korisnik> loadUsers(String username, String password, boolean edit, boolean write, boolean read, boolean delete){
        korisnici = new ArrayList<>();
        Korisnik user = new Korisnik(username, password, edit, write, read, delete);
        korisnici.add(user);
        return korisnici;
    }

    public abstract void makeUser(String path, List<Korisnik> korisnici);

    public abstract void makeDefaultUser(String path, String username, String password);

    public abstract void addUser(String path, String name, String password, String privilege);

    public abstract String checkAdmin(String path);

    public abstract boolean checkUser(String path, String username1, String password1);


}
