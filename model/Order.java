package model;

import java.util.ArrayList; 
import java.util.List; 

/**
 * @author MOHAMMAD JOHAN HAKIMI BIN ARSAD
 * @matric 2425961
 */
public class Order {
    private static int counter = 1;
    private String orderId;
    private Student student;
    private Mart mart;
    private List<Cart.CartItem> items;
    private double total;
    
    public Order(Student student, List<Cart.CartItem> items, Mart mart) {
        this.orderId = "ORD" + counter++;
        this.student = student;
        this.items = new ArrayList<>(items);
        this.mart = mart;
        for (Cart.CartItem ci : items) {
            total += ci.getSubtotal();
            StockItem item = ci.getItem();
            item.setQuantity(item.getQuantity() - ci.getQuantity());
        }
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public double getTotal() {
        return total;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public Mart getMart() {
        return mart;
    }
    
    public List<Cart.CartItem> getItems() {
        return items;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s - RM%.2f (%d items)", 
            orderId, student.getName(), total, items.size());
    }
}