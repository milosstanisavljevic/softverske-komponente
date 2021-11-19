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

    public abstract boolean createRoot(String path, String name, String username, String password);
    public abstract boolean checkIfRootExists(String path, String name);
    public abstract boolean createFile(String path, String fileName);
    public abstract String getPath();

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

    public boolean checkPrivileges(String privilege, String path, String connectedUser) {

        try {
            path = path + "\\" + "users.json";
            Type type = new TypeToken<Korisnik[]>() {
            }.getType();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(path));
            Korisnik[] data = gson.fromJson(reader, type);

            for (Korisnik k : data) {
                if(k.getUsername().equalsIgnoreCase(connectedUser)) {
                    switch (privilege) {

                        case ("read"):
                            System.out.println(k.isRead());
                            return k.isRead();

                        case ("write"):
                            System.out.println(k.isWrite());
                            return k.isWrite();

                        case ("delete"):
                            System.out.println(k.isDelete());
                            return k.isDelete();

                        case ("edit"):
                            System.out.println(k.isEdit());
                            return k.isEdit();

                    }
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Object checkConfigType(String path, String key) {
        try {
            Object value = null;
            path = path + "\\config.json";

            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));
            Map<String, Object> map = gson.fromJson(reader, Map.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(key)) {
                    value = entry.getValue();
                }
            }
            reader.close();
            return value;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String checkAdmin(String path) {
        try {
            String admin = "";

            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));
            Map<String, Object> map = gson.fromJson(reader, Map.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("admin")) {
                    admin += entry.getValue().toString();
                }
            }
            reader.close();
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract void makeUser(String path, List<Korisnik> korisnici);

    public abstract void makeDefaultUser(String path, String username, String password);

    public abstract boolean addUser(String path, String name, String password, String privilege);

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
