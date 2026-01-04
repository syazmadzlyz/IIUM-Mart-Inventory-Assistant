package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HARIZ ARIFFIN BIN ABD MALEK
 * @matric 2429835
 */
public class Cart {
    public class CartItem {
        private StockItem item;
        private int quantity;
        
        public CartItem(StockItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }
        
        public StockItem getItem() { 
            return item; 
        }
        
        public int getQuantity() { 
            return quantity; 
        }
        
        public double getSubtotal() { 
            return item.getPrice() * quantity; 
        }
    }
    
    private Student student;
    private List<CartItem> items;
    private String currentMartName;
    
    public Cart(Student student) {
        this.student = student;
        this.items = new ArrayList<>();
        this.currentMartName = null;
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public String getCurrentMartName() {
        return currentMartName;
    }
    
    public void addItem(StockItem item, int quantity) {
        if (currentMartName == null) {
            currentMartName = item.getMartName();
        }
        items.add(new CartItem(item, quantity));
    }
    
    public void clearCart() {
        items.clear();
        currentMartName = null;
    }
}