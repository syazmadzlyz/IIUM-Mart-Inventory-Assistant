package model;

import javafx.beans.property.*;

/**
 * @author HARIZ ARIFFIN BIN ABD MALEK
 * @matric 2429835
 */
public class StockItem {
    private String itemId;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty quantity;
    private String martName;
    
    public StockItem(String itemId, String name, double price, int quantity, String martName) {
        this.itemId = itemId;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.martName = martName;
    }
    
    public String getItemId() { 
        return itemId; 
    }
    
    public String getName() { 
        return name.get(); 
    }
    
    public void setName(String name) { 
        this.name.set(name); 
    }
    
    public StringProperty nameProperty() { 
        return name; 
    }
    
    public double getPrice() { 
        return price.get(); 
    }
    
    public void setPrice(double price) { 
        this.price.set(price); 
    }
    
    public DoubleProperty priceProperty() { 
        return price; 
    }
    
    public int getQuantity() { 
        return quantity.get(); 
    }
    
    public void setQuantity(int quantity) { 
        this.quantity.set(quantity); 
    }
    
    public IntegerProperty quantityProperty() { 
        return quantity; 
    }
    
    public String getMartName() { 
        return martName; 
    }
}