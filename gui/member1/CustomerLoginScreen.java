package gui.member1;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import model.Student;
import app.MainApp; 

/**
 * @author MUHAMMAD ALIF SYAZWAN BIN SHAMSOL MADZLY
 * @matric 2427845
 */
public class CustomerLoginScreen {
    private MainApp app;
    private VBox root;
    
    public CustomerLoginScreen(MainApp app) {
        this.app = app;
        createUI();
    }
    
    private void createUI() {
        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setMaxWidth(400);
        
        Label title = new Label("Customer Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        
        Label matricLabel = new Label("Matric Number:");
        TextField matricField = new TextField();
        matricField.setPromptText("e.g., 2210001");
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button startBtn = new Button("Start Shopping");
        startBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        startBtn.setPrefWidth(150);
        startBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String matric = matricField.getText().trim();
            
            if (name.isEmpty() || matric.isEmpty()) {
                showAlert("Validation Error", "Please fill in all fields!");
                return;
            }
            
            Student student = new Student(name, matric);
            app.showCustomerDashboard(student);
        });
        
        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(100);
        backBtn.setOnAction(e -> app.showMainScreen());
        
        buttonBox.getChildren().addAll(backBtn, startBtn);
        
        root.getChildren().addAll(title, nameLabel, nameField, matricLabel, matricField, buttonBox);
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