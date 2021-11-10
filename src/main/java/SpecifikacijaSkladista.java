import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class SpecifikacijaSkladista {

    private File configFile;
    private Korisnik connectedUser;
    private List<Korisnik>korisnici = new ArrayList<>();
    private int files = 0;
    private int memory = 0;

    public abstract boolean createRoot(String path, String username, String password);
    public abstract boolean checkIfRootExists(String path);
   // public abstract void checkPrivileges();
    public abstract void createFile(String path, String fileName);
    public abstract void createMoreFiles(String path, int n, String filetype);
    public abstract void createMoreFolders(String path, int n);
    public abstract void createFolder(String path, String folderName);
    public abstract void deleteFile(String path, String name);
    public abstract void deleteFolder(String path, String name);
    public abstract void moveFromTo(String fromFolder, String toFolder, String file);
    public abstract void downloadFile(String path, String filename);
    public abstract void copyPasteFiles(String fromFolder, String toFolder, String filename);
    public abstract int countFiles();

    public Map<String, Object> mapConfig(Object size,String filetype, int maxNumber, String admin ){
        Map<String, Object> map = new HashMap<>();
        map.put("sizeOfDir", size);
        map.put("fileType", filetype);
        map.put("maxNumber", maxNumber);
        map.put("admin", admin);

        return map;
    }

    public abstract void makeConfig(String path, Map<String, Object> mapa);

    public abstract void makeDefaultConfig(String path, String username);

    public abstract void updateConfig(String path, int size, String filetype, int maxNumber);

    public List<Korisnik> loadUsers(String username, String password, boolean edit, boolean write, boolean read, boolean delete){
        //korisnici = new ArrayList<>();
        Korisnik user = new Korisnik(username, password, edit, write, read, delete);
        korisnici.add(user);
        return korisnici;
    }

    public abstract void makeUser(String path, List<Korisnik> korisnici);

    public abstract void makeDefaultUser(String path, String username, String password);

    public abstract void addUser(String path, String name, String password, String privilege);

    public abstract String checkAdmin(String path);
    public abstract Object checkConfigType(String path, String key);

    public abstract boolean checkUser(String path, String username1, String password1);

    public void setFiles(int files) {
        this.files = files;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }
}
