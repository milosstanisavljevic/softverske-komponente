import java.util.List;

public abstract class Specifikacija {

    private String usr;
    private String psw;
    private List<Korisnik>korisnici; //lista korisnika sa privilegijama

    public abstract void checkDir();
    public abstract void checkUser();
    public abstract void checkPrivileges();


}
