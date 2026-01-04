package gui.member1;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import model.*;
import app.MainApp;

/**
 * @author MUHAMMAD ALIF SYAZWAN BIN SHAMSOL MADZLY
 * @matric 2427845
 */
public class StaffLoginScreen {
    private MainApp app;
    private VBox root;
    
    public StaffLoginScreen(MainApp app) {
        this.app = app;
        createUI();
    }
    
    private void createUI() {
        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setMaxWidth(400);
        
        Label title = new Label("Staff Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        Label martLabel = new Label("Select Your Mart:");
        ComboBox<String> martCombo = new ComboBox<>();
        martCombo.getItems().addAll("Bilal", "Ali", "Koop");
        martCombo.setPromptText("Choose a mart");
        martCombo.setPrefWidth(300);
        
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter password");
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");
        loginBtn.setPrefWidth(150);
        loginBtn.setOnAction(e -> {
            String selectedMart = martCombo.getValue();
            String password = passField.getText();
            
            if (selectedMart == null || password.isEmpty()) {
                showAlert("Validation Error", "Please select a mart and enter password!");
                return;
            }
            
            Staff staff = DataManager.getInstance().getStaffByMartName(selectedMart);
            if (staff != null && staff.checkPassword(password)) {
                app.showStaffDashboard(staff);
            } else {
                showAlert("Login Failed", "Invalid password!");
            }
        });
        
        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(100);
        backBtn.setOnAction(e -> app.showMainScreen());
        
        buttonBox.getChildren().addAll(backBtn, loginBtn);
        
        root.getChildren().addAll(title, martLabel, martCombo, passLabel, passField, buttonBox);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public VBox getView() {
        return root;
    }
}