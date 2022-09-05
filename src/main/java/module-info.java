module com.example.fractaltrees {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fractaltrees to javafx.fxml;
    exports com.example.fractaltrees;
}