module com.example.oyun {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oyun to javafx.fxml;
    exports com.example.oyun;
}