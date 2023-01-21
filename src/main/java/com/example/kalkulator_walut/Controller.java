package com.example.kalkulator_walut;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private final String[] calculateType = {"Kod waluty", "Nazwa waluty"};
    LocalDate date;
    ArrayList<String> codes = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> mid = new ArrayList<>();
    ArrayList<Currency> currency;
    ApiClient client;
    HashMap<String, String> codeMidMap = new HashMap<>();
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
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label dateLabel;
    @FXML
    private Button calculateButton;


    public void initialize(URL location, ResourceBundle resources) {

        //Send HTTP request
        try {
            client = new ApiClient();
            Parser parser = new Parser();
            currency = parser.parseData(client.response);
            fillArrayLists();
            calculatePicker.getItems().addAll(calculateType);
            calculatePicker.setValue(calculateType[0]);
            fillComboBoxCodeStart(fromPicker, codes);
            fillComboBoxCodeStart(toPicker, codes);
            setLabel(fromPicker, fromLabel);
            setLabel(toPicker, toLabel);
            setHashMap(mid, codes);
            setDatepicker();
            dateLabel.setText("Dane z dnia: " + datePicker.getValue());
        } catch (Exception ex) {
            connectionStatusLabel.setText("Błąd połączenia");
            calculateButton.setDisable(true);
        }
    }

    private void setHashMap(ArrayList<String> mid, ArrayList<String> codes) {
        for (int i = 0; i < mid.size(); i++) {
            codeMidMap.put(codes.get(i), mid.get(i));
        }
    }

    @FXML
    protected void buttonClick() {
        try {
            Float number = calculateValue(fromLabel.getText(), toLabel.getText());
            Float numberToLabel = Math.round(number * 100) / 100f;
            resultLabel.setText(numberToLabel.toString());
        } catch (Exception ex) {
            resultLabel.setText("Błędne dane");
        }
    }

    private void fillArrayLists() {
        for (var curr : currency) {
            codes.add(curr.code);
            names.add(curr.currency);
            mid.add(curr.mid);
        }
        codes.add("PLN");
        names.add("złoty polski");
        mid.add("1");
    }

    //Fill Combobox during start application
    private void fillComboBoxCodeStart(ComboBox box, ArrayList<String> list) {
        box.getItems().addAll(list);
        box.setValue(list.get(list.size() - 1));
    }

    @FXML
    private void changeType() {
            String prev = calculatePicker.getValue();
            if (calculatePicker.getValue().equals("Kod waluty") || calculatePicker.getValue() != prev) {
                fromPicker.getItems().clear();
                fillComboBoxCodeStart(fromPicker, codes);
                toPicker.getItems().clear();
                fillComboBoxCodeStart(toPicker, codes);
            } else if (calculatePicker.getValue().equals("Nazwa waluty") || calculatePicker.getValue() != prev) {
                fromPicker.getItems().clear();
                fillComboBoxCodeStart(fromPicker, names);
                toPicker.getItems().clear();
                fillComboBoxCodeStart(toPicker, names);
            }
    }

    private void setLabel(ComboBox box, Label label) {
        String textToLabel = null;
        String boxValue = (String) box.getValue();

        for (int i = 0; i < codes.size(); i++) {
            if (codes.get(i).equals(boxValue) || names.get(i).equals(boxValue)) {
                textToLabel = codes.get(i);
                break;
            }
        }
        label.setText(textToLabel);
    }

    @FXML
    private void setFromLabel() {
        setLabel(fromPicker, fromLabel);
    }

    @FXML
    private void setToLabel() {
        resultLabel.setText("");
        setLabel(toPicker, toLabel);
    }

    private Float calculateValue(String currencyOne, String currencyTwo) {
        float one;
        float two;
        float datafFromField;
        float result;

        one = Float.parseFloat(codeMidMap.get(currencyOne));
        two = Float.parseFloat(codeMidMap.get(currencyTwo));
        datafFromField = Float.parseFloat(dataField.getText());
        result = (one * datafFromField) / two;
        return result;
    }

    @FXML
    private void setDatepicker() {
        date = LocalDate.now();
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            date = date.minusDays(1);
        }
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.minusDays(2);
        }
        datePicker.setValue(date);
    }

    @FXML
    private void calculateByDate() {
        try {
            client = new ApiClient("http://api.nbp.pl/api/exchangerates/tables/A/" + datePicker.getValue());
            Parser parser = new Parser();
            currency = parser.parseData(client.response);
            codes.clear();
            mid.clear();
            names.clear();
            fillArrayLists();
            setHashMap(mid, codes);
            dateLabel.setText("Dane z dnia: " + datePicker.getValue());
            calculateButton.setDisable(false);
        } catch (Exception ex) {
            dateLabel.setText("Brak danych w podanym dniu");
            calculateButton.setDisable(true);
        }
    }
}
