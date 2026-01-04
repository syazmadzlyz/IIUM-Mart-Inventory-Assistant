package model;

/**
 * @author MOHAMMAD JOHAN HAKIMI BIN ARSAD
 * @matric 2425961
 */
public class Student extends User {
    private String studentId;
    private Cart cart;
    
    public Student(String name, String studentId) {
        super(name);
        this.studentId = "S_" + studentId;
        this.cart = new Cart(this);
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public Cart getCart() {
        return cart;
    }
}