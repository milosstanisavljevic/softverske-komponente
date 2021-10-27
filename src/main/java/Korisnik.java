public class Korisnik {

    private String username;
    private String password;
   // private boolean isAdmin; mozda moze na lepsi nacin sa ovim
    private boolean read;
    private boolean write;
    private boolean edit;
    private boolean delete;



    public Korisnik(String username, String password){
        this.username = username;
        this.password = password;

    }

    public Korisnik(String username, String password, boolean edit, boolean write, boolean read, boolean delete){
        this.username = username;
        this.password = password;
        this.edit = edit;
        this.write = write;
        this.read = read;
        this.delete = delete;

    }
    public boolean checkAdmin(){
        if(read && write && delete && edit){
            return true;

        }else
            return false;
    }

}
