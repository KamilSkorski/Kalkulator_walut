package com.example.kalkulator_walut;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    @FXML
    private TextField dataField;

    //Tables of currencies codes and names
    //private String[] currencyCode = {};
    //Table of calculate types for currencies
    //private String[] currencyName = {};


    ArrayList<String> codes = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> mid = new ArrayList<>();
    ArrayList<Currency> currency;
    ApiClient client;

    HashMap<String, String> codeMidMap = new HashMap<>();


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
        setLabel(fromPicker,fromLabel);
        setLabel(toPicker, toLabel);
        setHashMap(mid, codes);
    } catch (Exception ex) {
        connectionStatusLabel.setText("Błąd połączenia");
    }
}

private void setHashMap(ArrayList<String> mid, ArrayList<String> codes)
{
    for(int i = 0 ;i<mid.size(); i++)
    {
        codeMidMap.put(codes.get(i),mid.get(i));
    }
}

    @FXML
    protected void buttonClick() throws IOException, InterruptedException
    {
        try
        {
            Float number =calculateValue(fromLabel.getText(), toLabel.getText());
            Float numberToLabel = Math.round(number*100)/100f;
            resultLabel.setText(numberToLabel.toString());
        }catch (Exception ex)
        {
            resultLabel.setText("Błąd danych");
        }



    }
    private void fillArrayLists()
    {
        for (var curr:currency)
        {
            codes.add(curr.code);
            names.add(curr.currency);
            mid.add(curr.mid);
        }
        codes.add("PLN");
        names.add("złoty polski");
        mid.add("1");
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

    private void setLabel(ComboBox box, Label label)
    {
        String textToLabel = null;
        for(int i =0 ; i<codes.size(); i++)
        {
            if(codes.get(i) == (String)box.getValue() || names.get(i) == (String)box.getValue() )
            {
                textToLabel = codes.get(i);
                break;
            }
        }
        label.setText(textToLabel);
    }

    @FXML
    private void setFromLabel()
    {
        setLabel(fromPicker, fromLabel);
    }
    @FXML
    private void setToLabel()
    {
        resultLabel.setText("");
        setLabel(toPicker, toLabel);
    }

    private Float calculateValue(String currencyOne, String currencyTwo)
    {
        Float one;
        Float two;
        Float datafFromField;
        Float result;

        one = Float.parseFloat(codeMidMap.get(currencyOne));
        two = Float.parseFloat(codeMidMap.get(currencyTwo));
        datafFromField = Float.parseFloat(dataField.getText());
        System.out.println(one);
        System.out.println(two);
        System.out.println(datafFromField);
        result = (one*datafFromField)/two;

        return result;
    }

}
