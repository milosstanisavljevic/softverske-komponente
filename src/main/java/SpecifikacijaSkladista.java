import com.google.gson.Gson;

import java.io.*;
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

    public void makeConfig(String path) throws Exception {
        try {

            String ime = "proba1";
            path = path + "\\" + ime + ".json";

            Map<String, Object> map = new HashMap<>();
            map.put("size of root directory", 10000000);
            map.put("file type", ".txt");
            map.put("max number of files", 10);
            map.put("admin", "Bica Bakic");

            Writer writer = new FileWriter(path);
            new Gson().toJson(map, writer);

            writer.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void makeUser(String path) throws Exception{
         try {
             String ime = "proba2";
             path = path + "\\" + ime + ".json";

             Korisnik user = new Korisnik("Bica", "Bakic", true, true, true, true);

             Writer writer = new FileWriter(path);
             new Gson().toJson(user, writer);

             writer.close();

         }catch (Exception e){
             e.printStackTrace();
         }
    }

    public void updateConfig() throws IOException {
        // fajl koji sluzi za update config fajla
    }



}
