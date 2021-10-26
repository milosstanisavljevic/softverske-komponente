import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class SpecifikacijaSkladista {

    private File configFile;
    private List<Korisnik>korisnici;

    public abstract void createRoot(String path, String name);
    public abstract void checkIfRootExists(String path);
    public abstract void checkPrivileges();

    public void makeConfig() throws IOException {
        //pravljanje config fajla
        BufferedWriter bf = new BufferedWriter(new FileWriter(configFile));
        bf.write("MaxFileCount" + "=" + "5" + "\n");
        bf.write("MaxSizeInBytes:"+ "=" + "10000000" + "\n");
        bf.write("not supported extension" + ".exe");
    }
    public void updateConfig() throws IOException {
        // fajl koji sluzi za update config fajla
    }


}
