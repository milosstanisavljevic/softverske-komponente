import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class SpecifikacijaSkladista {

    private File configFile;
    private List<Korisnik>korisnici = new ArrayList<>();
    private int files = 0;
    private int memory = 0;
    private int brojac = 0;
    private int br = 0;

    public abstract boolean createRoot(String path, String username, String password);
    public abstract boolean checkIfRootExists(String path);
    public abstract boolean checkPrivileges(String privilege);
    public abstract boolean createFile(String path, String fileName);

    public void createMoreFiles(String path, int n, String filetype){
        for (int i = 0; i < n; i++) {
            createFile(path, "myFile" + br++ + filetype);
        }
    }

    public void createMoreFolders(String path, int n){
        for (int i = 0; i < n; i++) {
            createFolder(path, "Folder" + brojac++);
        }
    }
    public abstract boolean createFolder(String path, String folderName);
    public abstract boolean deleteFile(String path, String name);
    public abstract boolean deleteFolder(String path, String name);
    public abstract boolean moveFromTo(String fromFolder, String toFolder, String file);
    public abstract boolean downloadFile(String path, String filename);
    public abstract boolean copyPasteFiles(String fromFolder, String toFolder, String filename);
    public abstract int countFiles();
    public abstract long countFilesMemory();

    public String[] listFiles(String path) {

        String[] files;
        File file = new File(path);
        files = file.list();

        return files;
    }

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

    public abstract boolean updateConfig(String path, int size, String filetype, int maxNumber);

    public List<Korisnik> loadUsers(String username, String password, boolean edit, boolean write, boolean read, boolean delete){
        //korisnici = new ArrayList<>();
        Korisnik user = new Korisnik(username, password, edit, write, read, delete);
        korisnici.add(user);
        return korisnici;
    }

    public void loadUsers(String path) throws Exception{
        path = path + "\\" + "users.json";
        Type type = new TypeToken<Korisnik[]>() {}.getType();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path));
        Korisnik[] data = gson.fromJson(reader,type);
        for(Korisnik k : data){
            korisnici.add(k);
            System.out.println(k.getUsername());
        }
        System.out.println(korisnici);
//        try {
//            String username2 = "";
//            boolean s1 = false;
//            String password2 = "";
//            boolean s2 = false;
//
//            setRootDirectoryPath(path);
//            path = path + "\\" + "users.json";
//            Gson gson = new Gson();
//            JsonReader reader = new JsonReader(new FileReader(path));
//            Korisnik[] data = gson.fromJson(reader, type);
    }

    public abstract void makeUser(String path, List<Korisnik> korisnici);

    public abstract void makeDefaultUser(String path, String username, String password);

    public abstract boolean addUser(String path, String name, String password, String privilege);

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

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
}
