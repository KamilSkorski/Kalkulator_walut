package com.example.kalkulator_walut;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class Controller implements Initializable {
    private String[] calculateType = {"Kod waluty", "Nazwa waluty"};
    @FXML
    private Label resultLabel;
    @FXML
    private ComboBox<String> calculatePicker;

    @FXML
    private Label connectionStatusLabel;
    @FXML
    private ComboBox<String> fromPicker;
    @FXML
    private ComboBox<String> toPicker;
    @FXML
    private Label fromLabel;
    @FXML
            private Label toLabel;

    //Tables of currencies codes and names
    //private String[] currencyCode = {};
    //Table of calculate types for currencies
    //private String[] currencyName = {};


    ArrayList<String> codes = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Currency> currency;
    ApiClient client;
public void initialize(URL location, ResourceBundle resources) {
    //Initialize CalculatePicker
    calculatePicker.getItems().addAll(calculateType);
    calculatePicker.setValue(calculateType[0]);
    //Send HTTP request
    try {
         client = new ApiClient();
        Parser parser = new Parser();
        currency= parser.parseData(client.response);
        fillArrayLists();
        fillComboBoxCodeStart(fromPicker, codes);
        fillComboBoxCodeStart(toPicker, codes);
    } catch (Exception ex) {
        connectionStatusLabel.setText("Błąd połączenia");
    }

}
    @FXML
    protected void buttonClick() throws IOException, InterruptedException {

    resultLabel.setText(":)");

    }
    private void fillArrayLists()
    {
        for (var curr:currency)
        {
            codes.add(curr.code);
            names.add(curr.currency);
        }
        codes.add("PLN");
        names.add("złoty polski");
    }
    //Fill Combobox during start application
    private void fillComboBoxCodeStart(ComboBox box, ArrayList<String> list)
    {
        box.getItems().addAll(list);
        box.setValue(list.get(list.size()-1));
    }
    /*
    private void fillComboBoxNameStart(ComboBox box)
    {
        box.getItems().addAll(names);
        box.setValue(names.get(names.size()-1));
    }
    */

@FXML
    private void changeType()
    {
        String prev = calculatePicker.getValue();
        if(calculatePicker.getValue().equals("Kod waluty") || calculatePicker.getValue()!=(prev))
        {
            fromPicker.getItems().clear();
            fillComboBoxCodeStart(fromPicker, codes);
            toPicker.getItems().clear();
            fillComboBoxCodeStart(toPicker, codes);
        }else if(calculatePicker.getValue().equals("Nazwa waluty") || calculatePicker.getValue()!=(prev))
        {
            fromPicker.getItems().clear();
            fillComboBoxCodeStart(fromPicker, names);
            toPicker.getItems().clear();
            fillComboBoxCodeStart(toPicker,names);
        }
        

    }


}
