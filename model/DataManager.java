package model;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    
    private List<Mart> marts;
    private List<Staff> staffAccounts;
    private List<Order> allOrders;
    
    private DataManager() {
        marts = new ArrayList<>();
        staffAccounts = new ArrayList<>();
        allOrders = new ArrayList<>();
        initializeData();
    }
    
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    private void initializeData() {
        Mart bilal = new Mart("M001", "Bilal");
        Mart ali = new Mart("M002", "Ali");
        Mart koop = new Mart("M003", "Koop");
        
        bilal.addStockItem("I001", "Notebook A4", 5.00, 50);
        bilal.addStockItem("I002", "Pen (Blue)", 2.00, 100);
        bilal.addStockItem("I003", "Pencil", 1.50, 80);
        
        ali.addStockItem("I004", "Instant Noodles", 3.50, 60);
        ali.addStockItem("I005", "Mineral Water", 1.50, 120);
        ali.addStockItem("I006", "Biscuits", 4.00, 45);
        
        koop.addStockItem("I007", "Highlighter Set", 8.00, 30);
        koop.addStockItem("I008", "Calculator", 25.00, 20);
        koop.addStockItem("I009", "USB Drive 16GB", 15.00, 25);
        
        marts.add(bilal);
        marts.add(ali);
        marts.add(koop);
        
        staffAccounts.add(new Staff("Bilal Staff", "ST001", "bilal_123", bilal));
        staffAccounts.add(new Staff("Ali Staff", "ST002", "ali_123", ali));
        staffAccounts.add(new Staff("Koop Staff", "ST003", "koop_123", koop));
    }
    
    public Mart getMartByName(String name) {
        for (Mart mart : marts) {
            if (mart.getName().equalsIgnoreCase(name)) {
                return mart;
            }
        }
        return null;
    }
    
    public List<Mart> getAllMarts() {
        return marts;
    }
    
    public List<StockItem> getAllItems() {
        List<StockItem> allItems = new ArrayList<>();
        for (Mart mart : marts) {
            allItems.addAll(mart.getStock());
        }
        return allItems;
    }
    
    public Staff getStaffByMartName(String martName) {
        for (Staff staff : staffAccounts) {
            if (staff.getMart().getName().equalsIgnoreCase(martName)) {
                return staff;
            }
        }
        return null;
    }
    
    public void addOrder(Order order) {
        allOrders.add(order);
    }
    
    public List<Order> getAllOrders() {
        return allOrders;
    }
    
    public List<Order> getOrdersForMart(String martName) {
        List<Order> martOrders = new ArrayList<>();
        for (Order order : allOrders) {
            for (Cart.CartItem item : order.getItems()) {
                if (item.getItem().getMartName().equalsIgnoreCase(martName)) {
                    martOrders.add(order);
                    break;
                }
            }
        }
        return martOrders;
    }
}