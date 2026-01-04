package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HARIZ ARIFFIN BIN ABD MALEK
 * @matric 2429835
 */
public class Mart {
    private String martId;
    private String name;
    private List<StockItem> stock;
    
    public Mart(String martId, String name) {
        this.martId = martId;
        this.name = name;
        this.stock = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public List<StockItem> getStock() {
        return stock;
    }
    
    public void addStockItem(String itemId, String name, double price, int quantity) {
        StockItem item = new StockItem(itemId, name, price, quantity, this.name);
        stock.add(item);
    }
    
    public List<StockItem> findItemByName(String keyword) {
        List<StockItem> results = new ArrayList<>();
        for (StockItem item : stock) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }
    
    public StockItem findItemById(String id) {
        for (StockItem item : stock) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }
}