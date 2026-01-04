package gui.member3;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.collections.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.*;
import app.MainApp; 

/**
 * @author MOHAMMAD JOHAN HAKIMI BIN ARSAD
 * @matric 2425961
 */
public class StaffDashboard {
    private MainApp app;
    private Staff staff;
    private BorderPane root;
    private Mart mart;
    
    public StaffDashboard(MainApp app, Staff staff) {
        this.app = app;
        this.staff = staff;
        this.mart = staff.getMart();
        createUI();
    }
    
    private void createUI() {
        root = new BorderPane();
        
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #2196F3;");
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        Label welcomeLabel = new Label("Staff Dashboard - " + mart.getName() + " Mart");
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> app.showMainScreen());
        
        topBar.getChildren().addAll(welcomeLabel, spacer, logoutBtn);
        root.setTop(topBar);
        
        TabPane tabPane = new TabPane();
        
        Tab inventoryTab = new Tab("Inventory", createInventoryView());
        inventoryTab.setClosable(false);
        
        Tab ordersTab = new Tab("Orders", createOrdersView());
        ordersTab.setClosable(false);
        
        tabPane.getTabs().addAll(inventoryTab, ordersTab);
        root.setCenter(tabPane);
    }
    
    private VBox createInventoryView() {
        VBox view = new VBox(10);
        view.setPadding(new Insets(15));
        
        TableView<StockItem> table = new TableView<>();
        ObservableList<StockItem> items = FXCollections.observableArrayList(mart.getStock());
        table.setItems(items);
        
        TableColumn<StockItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        nameCol.setPrefWidth(250);
        
        TableColumn<StockItem, Number> priceCol = new TableColumn<>("Price (RM)");
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty());
        priceCol.setPrefWidth(100);
        
        TableColumn<StockItem, Number> qtyCol = new TableColumn<>("Stock Quantity");
        qtyCol.setCellValueFactory(data -> data.getValue().quantityProperty());
        qtyCol.setPrefWidth(150);
        
        table.getColumns().addAll(nameCol, priceCol, qtyCol);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        
        Button addBtn = new Button("Add New Item");
        addBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addBtn.setOnAction(e -> showAddItemDialog(table));
        
        Button updateBtn = new Button("Update Quantity");
        updateBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        updateBtn.setOnAction(e -> {
            StockItem selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Selection Error", "Please select an item to update!");
                return;
            }
            showUpdateQuantityDialog(selected);
        });
        
        buttonBox.getChildren().addAll(addBtn, updateBtn);
        
        view.getChildren().addAll(table, buttonBox);
        VBox.setVgrow(table, Priority.ALWAYS);
        
        return view;
    }
    
    private void showAddItemDialog(TableView<StockItem> table) {
        Stage dialog = new Stage();
        dialog.setTitle("Add New Item");
        
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(20));
        
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();
        
        Label priceLabel = new Label("Price (RM):");
        TextField priceField = new TextField();
        
        Label qtyLabel = new Label("Initial Quantity:");
        TextField qtyField = new TextField();
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button saveBtn = new Button("Add Item");
        saveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveBtn.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                
                if (name.isEmpty()) {
                    showAlert("Validation Error", "Item name cannot be empty!");
                    return;
                }
                
                String itemId = "I" + System.currentTimeMillis();
                mart.addStockItem(itemId, name, price, qty);
                
                ObservableList<StockItem> items = FXCollections.observableArrayList(mart.getStock());
                table.setItems(items);
                
                dialog.close();
                showAlert("Success", "Item added successfully!");
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Please enter valid numbers for price and quantity!");
            }
        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> dialog.close());
        
        buttonBox.getChildren().addAll(cancelBtn, saveBtn);
        
        dialogVBox.getChildren().addAll(nameLabel, nameField, priceLabel, priceField, qtyLabel, qtyField, buttonBox);
        
        Scene dialogScene = new Scene(dialogVBox, 350, 250);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    private void showUpdateQuantityDialog(StockItem item) {
        Stage dialog = new Stage();
        dialog.setTitle("Update Quantity");
        
        VBox dialogVBox = new VBox(15);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setAlignment(Pos.CENTER);
        
        Label itemLabel = new Label("Item: " + item.getName());
        itemLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Label currentLabel = new Label("Current Quantity: " + item.getQuantity());
        
        Label newLabel = new Label("New Quantity:");
        TextField qtyField = new TextField();
        qtyField.setText(String.valueOf(item.getQuantity()));
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button updateBtn = new Button("Update");
        updateBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        updateBtn.setOnAction(e -> {
            try {
                int newQty = Integer.parseInt(qtyField.getText());
                if (newQty < 0) {
                    showAlert("Invalid Input", "Quantity cannot be negative!");
                    return;
                }
                
                item.setQuantity(newQty);
                dialog.close();
                showAlert("Success", "Quantity updated successfully!");
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Please enter a valid number!");
            }
        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> dialog.close());
        
        buttonBox.getChildren().addAll(cancelBtn, updateBtn);
        
        dialogVBox.getChildren().addAll(itemLabel, currentLabel, newLabel, qtyField, buttonBox);
        
        Scene dialogScene = new Scene(dialogVBox, 300, 220);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    private VBox createOrdersView() {
        VBox view = new VBox(10);
        view.setPadding(new Insets(15));
        
        Label titleLabel = new Label("Orders for " + mart.getName() + " Mart");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        ListView<Order> ordersList = new ListView<>();
        ObservableList<Order> orders = FXCollections.observableArrayList(DataManager.getInstance().getOrdersForMart(mart.getName()));
        ordersList.setItems(orders);
        
        TextArea detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setPrefHeight(200);
        detailsArea.setPromptText("Select an order to view details...");
        
        ordersList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                StringBuilder details = new StringBuilder();
                details.append("Order ID: ").append(newVal.getOrderId()).append("\n");
                details.append("Customer: ").append(newVal.getStudent().getName()).append("\n");
                details.append("Student ID: ").append(newVal.getStudent().getStudentId()).append("\n");
                details.append("Total: RM ").append(String.format("%.2f", newVal.getTotal())).append("\n\n");
                details.append("Items:\n");
                
                for (Cart.CartItem item : newVal.getItems()) {
                    if (item.getItem().getMartName().equalsIgnoreCase(mart.getName())) {
                        details.append("- ").append(item.getItem().getName()).append(" x").append(item.getQuantity()).append(" @ RM").append(String.format("%.2f", item.getItem().getPrice())).append(" = RM").append(String.format("%.2f", item.getSubtotal())).append("\n");
                    }
                }
                
                detailsArea.setText(details.toString());
            }
        });
        
        Label detailsLabel = new Label("Order Details:");
        detailsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        view.getChildren().addAll(titleLabel, ordersList, detailsLabel, detailsArea);
        VBox.setVgrow(ordersList, Priority.ALWAYS);
        
        return view;
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