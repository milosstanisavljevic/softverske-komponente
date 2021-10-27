import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SpecifikacijaSkladista {

    private File configFile;
    private List<Korisnik>korisnici;

    public abstract void createRoot(String path, String name);
    public abstract void checkIfRootExists(String path);
    public abstract void checkPrivileges();

    public void makeConfig(String path, Object size, Object filetype, Object maxNumber, Object admin) throws Exception {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("size of root directory", size);
            map.put("file type", filetype);
            map.put("max number of files", maxNumber);
            map.put("admin", admin);

            Writer writer = new FileWriter(path);
            new Gson().toJson(map, writer);

            writer.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void makeDefaultConfig(String path){ //POZIVATI OVO U IMPLEMENTACIJI

        path = path + "\\" + "config.json";
        try {
            makeConfig(path, 1000000, ".txt", 10, "user1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeUser(String path, String username, String password, boolean edit, boolean write, boolean read, boolean delete) throws Exception{
         try {

             Korisnik user = new Korisnik(username, password, edit, write, read, delete);
             //user.getUsername(), user.getPassword()
             Writer writer = new FileWriter(path);
             new Gson().toJson(user, writer);

             writer.close();

         }catch (Exception e){
             e.printStackTrace();
         }
    }

    public void makeDefaultUser(String path){ // POZIVATI OVO U IMPLEMENTACIJI

        path = path + "\\" + "users.json";

        try {
            makeUser(path, "user1", "user1", true, true, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(String path, String name, String password, int privilege) throws Exception{
        //nije testirana
        try {
            if(privilege == 1){
                path = path + "\\" + "users.json";
                makeUser(path,name, password, false, false, true, false);

            }
            if(privilege == 2){
                path = path + "\\" + "users.json";
                makeUser(path, name, password, false, true, true, false);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateConfig(String path, Object size, Object filetype, Object maxNumber) throws Exception{
        try {

            String admin = "";

            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));
            Map<String, Object> map = gson.fromJson(reader, Map.class);

            for (Map.Entry<String, Object> entry: map.entrySet()) {
                if(entry.getKey() == "admin"){
                    admin = entry.getValue().toString();
                }
            }
            reader.close();

            //izbrisati sve iz fajla

            makeConfig(path, size, filetype, maxNumber, admin);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
