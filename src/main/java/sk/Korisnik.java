package sk;

public class Korisnik {

    private String username;
    private String password;
   // private boolean isAdmin; mozda moze na lepsi nacin sa ovim
    private boolean read;
    private boolean write;
    private boolean edit;
    private boolean delete;

    /**
     * Kreira korisnika skladista sa prosledjenim parametrima
     * @param username - korisnicko ime
     * @param password - lozinka za korisnicki nalog
     */

    public Korisnik(String username, String password){
        this.username = username;
        this.password = password;

    }

    /**
     * Kreira objekat korisnika
     */
    public Korisnik(){

    }

    /**
     * Kreira korisnika skladista sa prosledjenim privilegijama
     * @param username - korisnicko ime
     * @param password - lozinka
     * @param edit - privilegija za menjanje fajlova u skladisty
     * @param write - privilegija za pisanje fajlova u skladistu
     * @param read - privilegija za citanje fajlova u skladistu
     * @param delete - privilegija za brisanje fajlova iz skladista
     */
    public Korisnik(String username, String password, boolean edit, boolean write, boolean read, boolean delete){
        this.username = username;
        this.password = password;
        this.edit = edit;
        this.write = write;
        this.read = read;
        this.delete = delete;

    }

    /**
     * proverava da li je korisnik admin
     * @return true ako je korisnik admin, ima sve privilegije
     * @return false ukoliko nema sve privilegije
     */
    public boolean checkAdmin(){
        if(read && write && delete && edit){
            return true;

        }else
            return false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isEdit() {
        return edit;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
