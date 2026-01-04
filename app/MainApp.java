package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import gui.member1.*;
import gui.member2.*;
import gui.member3.*;
import model.*;

public class MainApp extends Application {
    private Stage primaryStage;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("IIUM Mart Inventory Assistant (MIA)");
        
        showMainScreen();
        
        primaryStage.show();
    }
    
    public void showMainScreen() {
        MainScreen mainScreen = new MainScreen(this);
        Scene scene = new Scene(mainScreen.getView(), 600, 400);
        primaryStage.setScene(scene);
    }
    
    public void showCustomerDashboard(Student student) {
        CustomerDashboard dashboard = new CustomerDashboard(this, student);
        Scene scene = new Scene(dashboard.getView(), 900, 600);
        primaryStage.setScene(scene);
    }
    
    public void showStaffDashboard(Staff staff) {
        StaffDashboard dashboard = new StaffDashboard(this, staff);
        Scene scene = new Scene(dashboard.getView(), 900, 600);
        primaryStage.setScene(scene);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}