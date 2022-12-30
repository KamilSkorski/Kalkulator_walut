module com.example.kalkulator_walut {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.net.http;
    requires com.google.gson;

    opens com.example.kalkulator_walut to javafx.fxml;
    exports com.example.kalkulator_walut;
}