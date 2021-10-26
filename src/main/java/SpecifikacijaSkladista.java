import java.util.List;

public abstract class SpecifikacijaSkladista {


    private List<Korisnik>korisnici;

    public abstract void createRoot(String path, String name);
    public abstract void checkIfRootExists(String path);
    public abstract void checkPrivileges();
}
