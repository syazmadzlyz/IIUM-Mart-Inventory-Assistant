package model;

/**
 * @author MOHAMMAD JOHAN HAKIMI BIN ARSAD
 * @matric 2425961
 */
public class Staff extends User {
    private String staffId;
    private String password;
    private Mart worksAt;
    
    public Staff(String name, String staffId, String password, Mart worksAt) {
        super(name);
        this.staffId = staffId;
        this.password = password;
        this.worksAt = worksAt;
    }
    
    public String getStaffId() {
        return staffId;
    }
    
    public Mart getMart() {
        return worksAt;
    }
    
    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }
}