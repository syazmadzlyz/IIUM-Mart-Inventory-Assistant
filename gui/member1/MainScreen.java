package gui.member1;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import app.MainApp; 

/**
 * @author MUHAMMAD ALIF SYAZWAN BIN SHAMSOL MADZLY
 * @matric 2427845
 */
public class MainScreen {
    private MainApp app;
    private BorderPane root;
    
    public MainScreen(MainApp app) {
        this.app = app;
        createUI();
    }
    
    private void createUI() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");
        
        VBox titleBox = new VBox(10);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(30));
        
        Label title = new Label("IIUM Mart Inventory Assistant");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        
        Label subtitle = new Label("Campus Mart Inventory & Reservation System");
        subtitle.setFont(Font.font("Arial", 18));
        subtitle.setStyle("-fx-text-fill: #666;");
        
        titleBox.getChildren().addAll(title, subtitle);
        root.setTop(titleBox);
        
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));
        
        Label prompt = new Label("Select Your Role:");
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        Button customerBtn = new Button("I'm a Customer/Student");
        customerBtn.setPrefSize(250, 50);
        customerBtn.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        customerBtn.setOnAction(e -> showCustomerLogin());
        
        Button staffBtn = new Button("I'm a Staff Member");
        staffBtn.setPrefSize(250, 50);
        staffBtn.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        staffBtn.setOnAction(e -> showStaffLogin());
        
        centerBox.getChildren().addAll(prompt, customerBtn, staffBtn);
        root.setCenter(centerBox);
    }
    
    private void showCustomerLogin() {
        CustomerLoginScreen loginScreen = new CustomerLoginScreen(app);
        root.setCenter(loginScreen.getView());
    }
    
    private void showStaffLogin() {
        StaffLoginScreen loginScreen = new StaffLoginScreen(app);
        root.setCenter(loginScreen.getView());
    }
    
    public BorderPane getView() {
        return root;
    }
}