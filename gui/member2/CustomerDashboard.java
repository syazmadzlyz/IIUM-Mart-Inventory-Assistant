package gui.member2;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.collections.*;
import model.*;
import java.util.ArrayList;
import java.util.List;
import app.MainApp; 

/**
 * @author HARIZ ARIFFIN BIN ABD MALEK
 * @matric 2429835
 */
public class CustomerDashboard {
    private MainApp app;
    private Student student;
    private BorderPane root;
    private Label totalLabel;
    private TableView<StockItem> searchTable;
    private TableView<Cart.CartItem> cartTable;
    
    public CustomerDashboard(MainApp app, Student student) {
        this.app = app;
        this.student = student;
        createUI();
    }
    
    private void createUI() {
        root = new BorderPane();
        
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #4CAF50;");
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        Label welcomeLabel = new Label("Welcome, " + student.getName() + " (" + student.getStudentId() + ")");
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> app.showMainScreen());
        
        topBar.getChildren().addAll(welcomeLabel, spacer, logoutBtn);
        root.setTop(topBar);
        
        TabPane tabPane = new TabPane();
        
        Tab searchTab = new Tab("Search Items", createSearchView());
        searchTab.setClosable(false);
        
        Tab cartTab = new Tab("My Cart", createCartView());
        cartTab.setClosable(false);
        
        tabPane.getTabs().addAll(searchTab, cartTab);
        root.setCenter(tabPane);
    }
    
    private VBox createSearchView() {
        VBox view = new VBox(10);
        view.setPadding(new Insets(15));
        
        HBox searchBar = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Search for items...");
        searchField.setPrefWidth(300);
        
        Button searchBtn = new Button("Search");
        searchBar.getChildren().addAll(new Label("Search:"), searchField, searchBtn);
        
        searchTable = new TableView<>();
        ObservableList<StockItem> allItems = FXCollections.observableArrayList(DataManager.getInstance().getAllItems());
        searchTable.setItems(allItems);
        
        TableColumn<StockItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        nameCol.setPrefWidth(200);
        
        TableColumn<StockItem, Number> priceCol = new TableColumn<>("Price (RM)");
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty());
        priceCol.setPrefWidth(100);
        
        TableColumn<StockItem, String> martCol = new TableColumn<>("Mart");
        martCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMartName()));
        martCol.setPrefWidth(100);
        
        TableColumn<StockItem, Number> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(data -> data.getValue().quantityProperty());
        stockCol.setPrefWidth(100);
        
        searchTable.getColumns().addAll(nameCol, priceCol, martCol, stockCol);
        
        searchBtn.setOnAction(e -> {
            String query = searchField.getText().toLowerCase().trim();
            if (query.isEmpty()) {
                searchTable.setItems(FXCollections.observableArrayList(DataManager.getInstance().getAllItems()));
            } else {
                ObservableList<StockItem> filtered = FXCollections.observableArrayList();
                for (StockItem item : DataManager.getInstance().getAllItems()) {
                    if (item.getName().toLowerCase().contains(query)) {
                        filtered.add(item);
                    }
                }
                searchTable.setItems(filtered);
            }
        });
        
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        addToCartBtn.setOnAction(e -> {
            StockItem selected = searchTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Selection Error", "Please select an item to add to cart!");
                return;
            }
            
            if (selected.getQuantity() <= 0) {
                showAlert("Out of Stock", "This item is currently out of stock!");
                return;
            }
            
            student.getCart().addItem(selected, 1);
            updateTotal();
            refreshCartTable();
            showAlert("Success", "Item added to cart!");
        });
        
        view.getChildren().addAll(searchBar, searchTable, addToCartBtn);
        VBox.setVgrow(searchTable, Priority.ALWAYS);
        
        return view;
    }
    
    private VBox createCartView() {
        VBox view = new VBox(10);
        view.setPadding(new Insets(15));
        
        cartTable = new TableView<>();
        ObservableList<Cart.CartItem> cartItems = FXCollections.observableArrayList(student.getCart().getItems());
        cartTable.setItems(cartItems);
        
        TableColumn<Cart.CartItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(data -> data.getValue().getItem().nameProperty());
        nameCol.setPrefWidth(250);
        
        TableColumn<Cart.CartItem, Number> priceCol = new TableColumn<>("Unit Price");
        priceCol.setCellValueFactory(data -> data.getValue().getItem().priceProperty());
        priceCol.setPrefWidth(100);
        
        TableColumn<Cart.CartItem, String> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getQuantity())));
        qtyCol.setPrefWidth(100);
        
        TableColumn<Cart.CartItem, String> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(data -> {
            Cart.CartItem item = data.getValue();
            return new javafx.beans.property.SimpleStringProperty(String.format("RM %.2f", item.getSubtotal()));
        });
        subtotalCol.setPrefWidth(120);
        
        cartTable.getColumns().addAll(nameCol, priceCol, qtyCol, subtotalCol);
        
        HBox bottomBox = new HBox(15);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        
        totalLabel = new Label("Total: RM 0.00");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        updateTotal();
        
        Button removeBtn = new Button("Remove Item");
        removeBtn.setOnAction(e -> {
            Cart.CartItem selected = cartTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                student.getCart().getItems().remove(selected);
                refreshCartTable();
                updateTotal();
            }
        });
        
        Button checkoutBtn = new Button("Checkout");
        checkoutBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        checkoutBtn.setPrefWidth(120);
        checkoutBtn.setOnAction(e -> processCheckout());
        
        bottomBox.getChildren().addAll(removeBtn, totalLabel, checkoutBtn);
        
        view.getChildren().addAll(cartTable, bottomBox);
        VBox.setVgrow(cartTable, Priority.ALWAYS);
        
        return view;
    }
    
    private void refreshCartTable() {
        ObservableList<Cart.CartItem> cartItems = FXCollections.observableArrayList(student.getCart().getItems());
        cartTable.setItems(cartItems);
    }
    
    private void updateTotal() {
        double total = 0;
        for (Cart.CartItem item : student.getCart().getItems()) {
            total += item.getSubtotal();
        }
        totalLabel.setText(String.format("Total: RM %.2f", total));
    }
    
    private void processCheckout() {
        if (student.getCart().getItems().isEmpty()) {
            showAlert("Cart Empty", "Your cart is empty!");
            return;
        }
        
        for (Cart.CartItem cartItem : student.getCart().getItems()) {
            if (cartItem.getItem().getQuantity() < cartItem.getQuantity()) {
                showAlert("Insufficient Stock", "Not enough stock for: " + cartItem.getItem().getName());
                return;
            }
        }
        
        String martName = student.getCart().getCurrentMartName();
        Mart mart = DataManager.getInstance().getMartByName(martName);
        
        List<Cart.CartItem> itemsCopy = new ArrayList<>(student.getCart().getItems());
        Order order = new Order(student, itemsCopy, mart);
        DataManager.getInstance().addOrder(order);
        
        double total = order.getTotal();
        student.getCart().clearCart();
        refreshCartTable();
        updateTotal();
        
        showAlert("Order Successful", String.format("Your order has been placed!\nOrder ID: %s\nTotal: RM %.2f", order.getOrderId(), total));
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public BorderPane getView() {
        return root;
    }
}