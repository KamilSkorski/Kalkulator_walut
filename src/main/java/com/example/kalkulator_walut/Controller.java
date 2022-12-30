package com.example.kalkulator_walut;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private String[] calculateType = {"Kod waluty", "Nazwa waluty"};
    @FXML
    private Label resultLabel;
    @FXML
    private ComboBox<String> calculatePicker = new ComboBox<>();
    //Tables of currencies codes and names
    private String[] currencyCode = {};
    private String[] currencyName = {};

    //Table of calculate types for currencies


    //Initialize CalculatePicker
public void initialize(URL location, ResourceBundle resources)
{
calculatePicker.getItems().addAll(calculateType);
calculatePicker.setValue(calculateType[0]);
}


    @FXML
    protected void buttonClick() {
        resultLabel.setText(":)");
    }
}