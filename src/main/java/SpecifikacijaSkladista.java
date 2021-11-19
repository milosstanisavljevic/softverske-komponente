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

    /**
     * Kreira Skladiste
     * @param path - putanja ka skladistu
     * @param name - ime skladiste
     * @param username - korisnicko ime korisnika skladista
     * @param password - lozinka za korsnika skladsita
     * @return da li je skladiste uspesno napravljeno ili ne
     */
    public abstract boolean createRoot(String path, String name, String username, String password);

    /**
     * Proverava da li skladiste vec postoji
     * @param path - putanja ka skladistu
     * @return true ako skladsite postoji
     */
    public abstract boolean checkIfRootExists(String path);

    /**
     * kreira fajl na zadatoj putanji
     * @param path - putanja na kojoj se fajl kreira
     * @param fileName ime fajla
     * @return true ukoliko je uspesno napravljen
     */
    public abstract boolean createFile(String path, String fileName);

    /**
     * @return putanja skladista
     */
    public abstract String getPath();

    /**
     * Kreira vise fajlova na zadatoj putanji
     * @param path - putanja na kojoj se fajlovi prave
     * @param n - broj fajlova koji se pravi
     * @param filetype - tip fajla koji se pravi
     */
    public void createMoreFiles(String path, int n, String filetype){
        for (int i = 0; i < n; i++) {
            createFile(path, "myFile" + br++ + filetype);
        }
    }

    /**
     * Kreira vise foldera na zadatoj putanji
     * @param path - putanja na kojoj se folderi prave
     * @param n - broj foldera koji se prave
     */
    public void createMoreFolders(String path, int n){
        for (int i = 0; i < n; i++) {
            createFolder(path, "Folder" + brojac++);
        }
    }
    /**
     * Kreira folder
     * @param path - putanja na kojoj se pravi folder
     * @param folderName - ime foldera
     * @return true ako je uspesno napravljen folder
     */
    public abstract boolean createFolder(String path, String folderName);

    /**
     * Brise fajl
     * @param path - putanja gde se nalazi fajl koji se brise
     * @param name - ime fajla koji se brise
     * @return true ukoliko je uspesno obrisan
     */
    public abstract boolean deleteFile(String path, String name);

    /**
     * Brise folder
     * @param path - putanja na kojoj se folder nalazi
     * @param name - ime foldera koji se nalazi
     * @return true ukoliko je uspesno obrisan
     */
    public abstract boolean deleteFolder(String path, String name);

    /**
     * Menja folder u kom se fajl nalazi
     * @param fromFolder - putanja foldera gde se trenutno nalazi
     * @param toFolder - putanja novog foldera gde ce se nalaziti
     * @param file - fajl nad kojim se manipulise
     * @return true ukoliko je operacija uspesna
     */
    public abstract boolean moveFromTo(String fromFolder, String toFolder, String file);

    /**
     * Download-uje fajl
     * @param path - putanja na kojoj se nalazi
     * @param filename - ime fajla koji se download-uje
     * @return true ukoliko je operacija uspesna
     */
    public abstract boolean downloadFile(String path, String filename);

    /**
     * Kopiranje fajla
     * @param fromFolder - putanja fajla koji se kopira
     * @param toFolder - putanja foldera gde se fajl "pastuje"
     * @param filename - ime fajla koji se kopira
     * @return true ukoliko je operacija uspesna
     */
    public abstract boolean copyPasteFiles(String fromFolder, String toFolder, String filename);

    /**
     * @return broj fajlova u skladistu
     */
    public abstract int countFiles();

    /**
     * @return ukupnu velicinu fajlova u skladistu
     */
    public abstract long countFilesMemory();

    /**
     *
     * @param path
     * @return listu fajlova koji se nalaze u skladistu
     */
    public String[] listFiles(String path) {

        String[] files;
        File file = new File(path);
        files = file.list();

        return files;
    }

    /**
     * mapira vrednosti konfiguracije
     * @param size - velicina skladista
     * @param filetype - tipovi fajlova koji su podrzani
     * @param maxNumber - maksimalni dozvoljen broj fajlova u skladistu
     * @param admin - admin skladista
     * @return mapa sa podacima konfiguracije skladista
     */
    public Map<String, Object> mapConfig(Object size,String filetype, int maxNumber, String admin ){
        Map<String, Object> map = new HashMap<>();
        map.put("sizeOfDir", size);
        map.put("fileType", filetype);
        map.put("maxNumber", maxNumber);
        map.put("admin", admin);

        return map;
    }

    /**
     * pravi konfiguracioni fajl
     * @param path putanja do skladista
     * @param mapa mapa konfiguracije
     */
    public abstract void makeConfig(String path, Map<String, Object> mapa);

    /**
     * kreira standardan konfiguracioni fajl
     * @param path - putanja do skladista
     * @param username - username admina
     */
    public abstract void makeDefaultConfig(String path, String username);

    /**
     * updatuje vrednosti konfiguarcionog fajla
     * @param path - putanja gde se skladiste nalazi
     * @param size - nova maksimalna dozvoljena velicina
     * @param filetype - novi dozvoljeni tipovi fajlova u skladistu
     * @param maxNumber - novi maksimalni broj fajlova u skladistu
     * @return true, ukoliko je manipulacija uspesna
     */
    public abstract boolean updateConfig(String path, int size, String filetype, int maxNumber);
    /**
     *Ucitava listu korisnika iz fajla
     * @return listu korisnika
     */
    public List<Korisnik> loadUsers(String username, String password, boolean edit, boolean write, boolean read, boolean delete){
        Korisnik user = new Korisnik(username, password, edit, write, read, delete);
        korisnici.add(user);
        return korisnici;
    }

    /**
     * ucitava listu korisnika iz fajla sa zadate putanje
     * @param path - putanja na kojoj se skladiste nalazi
     * @throws Exception ukoliko fajl ne postoji
     */
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

    /**
     * Proverava privilegije korisnika
     * @param privilege - koji tip privilegija je korisniku potreban
     * @param path - putanja do skladista
     * @param connectedUser - konektovani korisnik
     * @return true za odredjene privilegije
     */
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

    /**
     * proverava parametre konfiguracije
     * @param path putanja do fajla
     * @param key tip konfiguracije
     * @return vrednost trazene konfiguracije
     */
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

    /**
     * Proverava da li je korisnik admin
     * @param path - putanja do skladista
     * @return ime admina
     */
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

    /**
     * Pravi novog korisnika
     * @param path - putanja do skladista
     * @param korisnici - lista korisnika
     */
    public abstract void makeUser(String path, List<Korisnik> korisnici);

    /**
     * pravi standardnog korisnika
     * @param path - putanja do skladista
     * @param username - ime korisnika
     * @param password - lozinka za zadatog korisnika
     */
    public abstract void makeDefaultUser(String path, String username, String password);

    /**
     * dodaje novog korisnika u skladiste
     * @param path - putanja na kojoj se skladiste nalazi
     * @param name - ime korisnika
     * @param password - lozinka korisika
     * @param privilege - korisnikove privilegije
     * @return true, ukoliko je uspesna operacija
     */
    public abstract boolean addUser(String path, String name, String password, String privilege);

    /**
     * proverava da li korisnik postoji
     * @param path putanja do skladista
     * @param username1 - ime korisnika
     * @param password1 - lozinka
     * @return true ukoliko postoji takav korisnik
     */
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
