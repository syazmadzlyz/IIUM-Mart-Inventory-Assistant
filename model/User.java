package model;

/**
 * @author MUHAMMAD ALIF SYAZWAN BIN SHAMSOL MADZLY
 * @matric 2427845
 */
public abstract class User {
    protected String name;
    
    public User(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}