package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class DashboardController implements Initializable {

    @FXML
    private Button availableFD_addBtn;

    @FXML
    private Button availableFD_btn;

    @FXML
    private Button availableFD_clearBtn;

    @FXML
    private TableColumn<?, ?> availableFD_col_price;

    @FXML
    private TableColumn<Kategorie, String> availableFD_col_productID;

    @FXML
    private TableColumn<Kategorie, String> availableFD_col_productName;

    @FXML
    private TableColumn<Kategorie, String> availableFD_col_status;

    @FXML
    private TableColumn<Kategorie, String> availableFD_col_type;

    @FXML
    private VBox availableFD_form1;

    @FXML
    private VBox availableFD_form2;

    @FXML
    private TableView<Kategorie> availableFD_tableView;

    @FXML
    private TextField availableFD_productID;

    @FXML
    private TextField availableFD_productName;

    @FXML
    private TextField availableFD_price;

    @FXML
    private ComboBox<?> availableFD_productStatus;

    @FXML
    private ComboBox<?> availableFD_type;

    @FXML
    private Button availableFD_removeBtn;

    @FXML
    private TextField availableFD_search;

    @FXML
    private Button availableFD_updateBtn;

    @FXML
    private AreaChart<?, ?> dashboard_ICChart;


    @FXML
    private Label dashboard_NC;

    @FXML
    private BarChart<?, ?> dashboard_NOCChart;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TIncome;

    @FXML
    private Button dashboard_btn;

    @FXML
    private GridPane dashboard_form1;

    @FXML
    private GridPane dashboard_form2;

    @FXML
    private Button logout;

    @FXML
    private Button order_addBtn;

    @FXML
    private TextField order_amount;

    @FXML
    private Button order_btn;

    @FXML
    private TableColumn<Product, String> order_col_price;

    @FXML
    private TableColumn<Product, String> order_col_productID;

    @FXML
    private TableColumn<Product, String> order_col_productName;

    @FXML
    private TableColumn<Product, String> order_col_quantity;

    @FXML
    private TableColumn<Product, String> order_col_type;

    @FXML
    private VBox order_form1;

    @FXML
    private VBox order_form2;

    @FXML
    private Button order_payBtn;

    @FXML
    private ComboBox<?> order_productID;

    @FXML
    private ComboBox<?> order_productName;

    @FXML
    private Spinner<Integer> order_productQuantity;

    @FXML
    private Button order_removeBtn;

    @FXML
    private Label order_balance;

    @FXML
    private TableView<Product> order_tableView;

    @FXML
    private Label order_total;

    @FXML
    private Label username;

    @FXML
    private GridPane main_form;




    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    /**
     * Wyświetla liczbę dostępnych produktów (COUNT(id)) w tabeli `product_info`.
     */

    public void dashboardNC() {

        String sql = "SELECT COUNT(id) FROM product_info";

        int nc = 0;

        connect = BazaDanych.connectDb();

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }

            dashboard_NC.setText(String.valueOf(nc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Wyświetla dzisiejszy przychód (SUM(total)) z tabeli `product_info`, bazując na bieżącej dacie.
     */

    public void dashboardTI() {

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM product_info WHERE date = '" + sqlDate + "'";

        connect = BazaDanych.connectDb();

        double ti = 0;

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText(String.valueOf(ti) + "zł");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Wyświetla całkowity przychód (SUM(total)) z tabeli `product_info`.
     */

    public void dashboardTIncome() {

        String sql = "SELECT SUM(total) FROM product_info";

        connect = BazaDanych.connectDb();

        double ti = 0;

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            dashboard_TIncome.setText(String.valueOf(ti) + "zł");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Generuje wykres liczby klientów na podstawie ilości rekordów (COUNT(id)) w tabeli `product_info`,
     * grupowanych według daty i posortowanych rosnąco, ograniczając wynik do 5 rekordów.
     */

    public void dashboardNOCCChart() {

        try {

            dashboard_NOCChart.getData().clear();

            String sql = "SELECT date, COUNT(id) FROM product_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

            connect = BazaDanych.connectDb();

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_NOCChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Generuje wykres przychodów na podstawie sum (SUM(total)) w tabeli `product_info`,
     * grupowanych według daty i posortowanych rosnąco, ograniczając wynik do 7 rekordów.
     */

    public void dashboardICC() {

        dashboard_ICChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM product_info GROUP BY date ORDER BY TIMESTAMP(total) ASC LIMIT 7";

        connect = BazaDanych.connectDb();

        try {

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));

            }

            dashboard_ICChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Dodaje nowy produkt do tabeli `kategoria` na podstawie wprowadzonych danych (ID produktu, nazwa, typ, cena, status).
     * Sprawdza, czy ID produktu już istnieje. Jeśli istnieje, wyświetla odpowiedni komunikat błędu.
     */

    public void availableFDAdd() {

        String sql = "INSERT INTO kategoria (product_id, product_name, type, price, status) "
                + "VALUES(?,?,?,?,?)";

        connect = BazaDanych.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableFD_productID.getText());
            prepare.setString(2, availableFD_productName.getText());
            prepare.setString(3, (String) availableFD_type.getSelectionModel().getSelectedItem());
            prepare.setString(4, availableFD_price.getText());
            prepare.setString(5, (String) availableFD_productStatus.getSelectionModel().getSelectedItem());

            Alert alert;

            if (availableFD_productID.getText().isEmpty()
                    || availableFD_productName.getText().isEmpty()
                    || availableFD_type.getSelectionModel() == null
                    || availableFD_price.getText().isEmpty()
                    || availableFD_productStatus.getSelectionModel() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Bład");
                alert.setHeaderText(null);
                alert.setContentText("Wypełnij wszystkie puste pola");
                alert.showAndWait();

            } else {

                String checkData = "SELECT product_id FROM kategoria WHERE product_id = '"
                        + availableFD_productID.getText() + "'";

                connect = BazaDanych.connectDb();

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText(null);
                    alert.setContentText("ID Produktu: " + availableFD_productID.getText() + " już istnieje!");
                    alert.showAndWait();
                } else {
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacja");
                    alert.setHeaderText(null);
                    alert.setContentText("Pomyślnie dodano!");
                    alert.showAndWait();

                    // WYŚWIETLENIE DANYCH
                    availableFDShowData();
                    // WYCZYSZCZENIE PÓL TEKSTOWYCH
                    availableFDClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera listę wszystkich danych z tabeli `kategoria` i zwraca ją w postaci listy obserwowalnej.
     *
     * @return Lista obserwowalna zawierająca obiekty klasy `Kategorie`.
     */

    public ObservableList<Kategorie> availableFDListData() {

        ObservableList<Kategorie> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM kategoria";

        connect = BazaDanych.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Kategorie cat;

            while (result.next()) {
                cat = new Kategorie(result.getString("product_id"),
                        result.getString("product_name"), result.getString("type"),
                        result.getDouble("price"), result.getString("status"));

                listData.add(cat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Kategorie> availableFDList;

    /**
     * Wyświetla dane z tabeli `kategoria` w widoku tabelarycznym, przypisując kolumnom odpowiednie właściwości obiektów.
     */

    public void availableFDShowData() {
        availableFDList = availableFDListData();

        availableFD_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        availableFD_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableFD_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableFD_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableFD_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        availableFD_tableView.setItems(availableFDList);

    }

    /**
     * Aktualizuje istniejący rekord w tabeli `kategoria` na podstawie wprowadzonych danych.
     * Wyświetla potwierdzenie przed wprowadzeniem zmian.
     */

    public void availableFDUpdate() {

        String sql = "UPDATE kategoria SET product_name = '"
                + availableFD_productName.getText() + "', type = '"
                + availableFD_type.getSelectionModel().getSelectedItem() + "', price = '"
                + availableFD_price.getText() + "', status = '"
                + availableFD_productStatus.getSelectionModel().getSelectedItem()
                + "' WHERE product_id = '" + availableFD_productID.getText() + "'";

        connect = BazaDanych.connectDb();

        try {

            Alert alert;

            if (availableFD_productID.getText().isEmpty()
                    || availableFD_productName.getText().isEmpty()
                    || availableFD_type.getSelectionModel().getSelectedItem() == null
                    || availableFD_price.getText().isEmpty()
                    || availableFD_productStatus.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Wypełnij wszystkie puste pola");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText(null);
                alert.setContentText("Jesteś pewien, że chcesz zaktualizować ID Produktu: "
                        + availableFD_productID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Wiadomość informacyjna");
                    alert.setHeaderText(null);
                    alert.setContentText("Pomyślnie zaktualizowano!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    // WYŚWIETLENIE DANYCH
                    availableFDShowData();
                    // WYCZYSZCZENIE PÓL TEKSTOWYCH
                    availableFDClear();

                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Wiadomość informacyjna");
                    alert.setHeaderText(null);
                    alert.setContentText("Anulowano.");
                    alert.showAndWait();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Usuwa istniejący rekord z tabeli `kategoria` na podstawie ID produktu.
     * Wyświetla potwierdzenie przed usunięciem.
     */

    public void availableFDDelete() {

        String sql = "DELETE FROM kategoria WHERE product_id = '"
                + availableFD_productID.getText() + "'";

        connect = BazaDanych.connectDb();

        try {

            Alert alert;

            if (availableFD_productID.getText().isEmpty()
                    || availableFD_productName.getText().isEmpty()
                    || availableFD_type.getSelectionModel().getSelectedItem() == null
                    || availableFD_price.getText().isEmpty()
                    || availableFD_productStatus.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Wypełnij wszystkie puste pola");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText(null);
                alert.setContentText("Jesteś pewien, że chcesz usunąć ID Produktu: "
                        + availableFD_productID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Wiadomość informacyjna");
                    alert.setHeaderText(null);
                    alert.setContentText("Pomyślnie usunięto!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    // WYŚWIETLENIE DANYCH
                    availableFDShowData();
                    // WYCZYSZCZENIE PÓL TEKSTOWYCH
                    availableFDClear();

                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Czyści pola tekstowe oraz selektory w formularzu edycji/dodawania produktów.
     */

    public void availableFDClear() {

        availableFD_productID.setText("");
        availableFD_productName.setText("");
        availableFD_type.getSelectionModel().clearSelection();
        availableFD_price.setText("");
        availableFD_productStatus.getSelectionModel().clearSelection();

    }

    /**
     * Przypisuje dane z wybranego wiersza tabeli do pól tekstowych formularza, umożliwiając edycję.
     */

    public void availableFDSelect() {

        Kategorie catData = availableFD_tableView.getSelectionModel().getSelectedItem();

        int num = availableFD_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableFD_productID.setText(catData.getProductId());
        availableFD_productName.setText(catData.getName());
        availableFD_price.setText(String.valueOf(catData.getPrice()));

    }

    /**
     * Filtruje dane w tabeli `kategoria` na podstawie tekstu wpisanego w pole wyszukiwania.
     * Obsługuje wyszukiwanie według ID, nazwy, typu, ceny i statusu produktu.
     */

    public void availableFDSearch() {
        // Sprawdź, czy lista nie jest null
        if (availableFDList == null) {
            System.err.println("Error: availableFDList is null.");
            return;
        }

        // Utwórz FilteredList z dostępną listą
        FilteredList<Kategorie> filter = new FilteredList<>(availableFDList, e -> true);

        // Debug: wyświetl liczbę elementów w liście
        System.out.println("Initial list size: " + availableFDList.size());

        // Dodaj listener do pola wyszukiwania
        availableFD_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateCategories -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Wyświetl wszystko, jeśli pole wyszukiwania jest puste
                }

                String searchKey = newValue.toLowerCase();

                // Sprawdź każdą właściwość obiektu Kategorie
                if (predicateCategories.getProductId() != null && predicateCategories.getProductId().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getName() != null && predicateCategories.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getType() != null && predicateCategories.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getPrice() != null && String.valueOf(predicateCategories.getPrice()).contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getStatus() != null && predicateCategories.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                }

                return false; // Jeśli nie pasuje, ukryj element
            });

            // Debug: wyświetl rozmiar przefiltrowanej listy
            System.out.println("Filtered list size: " + filter.size());
        });

        // Stwórz SortedList i powiąż z tabelą
        SortedList<Kategorie> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(availableFD_tableView.comparatorProperty());
        availableFD_tableView.setItems(sortList);

        // Debug: sprawdź, czy tabela ma dane
        System.out.println("Table initialized with items.");
    }

    private String[] categories = {"Jedzenie", "Napoje"};

    /**
     * Inicjalizuje selektor typu produktu, wypełniając go predefiniowanymi kategoriami (`Jedzenie`, `Napoje`).
     */

    public void availableFDType() {
        List<String> listCat = new ArrayList<>();

        for (String data: categories) {
            listCat.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listCat);
        availableFD_type.setItems(listData);
    }

    private String[] status = {"Dostępne", "Niedostępne"};

    /**
     * Inicjalizuje selektor statusu produktu, wypełniając go predefiniowanymi wartościami (`Dostępne`, `Niedostępne`).
     */

    public void availableFDStatus() {
        List<String> listStatus = new ArrayList<>();

        for(String data: status) {
            listStatus.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listStatus);
        availableFD_productStatus.setItems(listData);

    }

    /**
     * Pobiera dostępne identyfikatory produktów z bazy danych i wyświetla je w komponencie interfejsu użytkownika.
     */

    public void orderProductId() {

        String sql = "SELECT product_id FROM kategoria WHERE status = 'Dostępne'";

        connect = BazaDanych.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_id"));
            }

            order_productID.setItems(listData);

            orderProductName();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Pobiera nazwę produktu na podstawie wybranego identyfikatora produktu i wyświetla ją w komponencie interfejsu użytkownika.
     */

    public void orderProductName() {

        String sql = "SELECT product_name FROM kategoria WHERE product_id = '"
                + order_productID.getSelectionModel().getSelectedItem() + "'";

        connect = BazaDanych.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_name"));
            }

            order_productName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ObservableList<Product> orderData;

    /**
     * Wyświetla dane zamówień w tabeli, w tym identyfikatory produktów, nazwy, typy, ceny i ilości.
     */

    public void orderDisplayData() {
        orderData = orderListData();

        order_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        order_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        order_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        order_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        order_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        order_tableView.setItems(orderData);

    }

    private int customerId;

    /**
     * Pobiera unikalny identyfikator klienta na podstawie danych w bazie.
     */

    public void orderCustomerId() {

        String sql = "SELECT customer_id FROM product";

        connect = BazaDanych.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("customer_id");
            }

            String checkData = "SELECT customer_id FROM product_info";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            int customerInfoId = 0;

            while (result.next()) {
                customerInfoId = result.getInt("customer_id");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (customerId == customerInfoId) {
                customerId += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Pobiera listę produktów zamówionych przez danego klienta.
     *
     * @return Lista produktów zamówionych przez klienta.
     */

    public ObservableList<Product> orderListData() {

        orderCustomerId();

        ObservableList<Product> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product WHERE customer_id = " + customerId;

        connect = BazaDanych.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Product prod;

            while (result.next()) {
                prod = new Product(result.getInt("id"),
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getInt("quantity"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private double totalP = 0;

    /**
     * Realizuje płatność za złożone zamówienie, zapisując dane w bazie danych.
     */

    public void orderPay() {
        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES(?,?,?)";

        connect = BazaDanych.connectDb();

        try {

            Alert alert;

            if (balance == 0 || String.valueOf(balance) == "0.0zł" || String.valueOf(balance) == null
                    || totalP == 0 || String.valueOf(totalP) == "0.0zł" || String.valueOf(totalP) == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Nie udało się wykonać");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText(null);
                alert.setContentText("Jesteś pewien?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Wiadomość informacyjno");
                    alert.setHeaderText(null);
                    alert.setContentText("Zaakceptowano!");
                    alert.showAndWait();

                    order_total.setText("0.0zł");
                    order_balance.setText("0.0zł");
                    order_amount.setText("");

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wiadomość informacyjna");
                    alert.setHeaderText(null);
                    alert.setContentText("Anulowano!");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Oblicza całkowitą wartość zamówienia dla danego klienta.
     */

    public void orderTotal() {
        orderCustomerId();

        String sql = "SELECT SUM(price) FROM product WHERE customer_id = " + customerId;

        connect = BazaDanych.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double amount;
    private double balance;

    /**
     * Obsługuje wprowadzenie kwoty płatności i oblicza saldo pozostałe po zapłacie za zamówienie.
     */

    public void orderAmount() {
        orderTotal();

        Alert alert;

        if (order_amount.getText().isEmpty() || order_amount.getText() == null
                || order_amount.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wpisano niewłaściwą kwotę!");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(order_amount.getText());

            if (amount < totalP) {
                order_amount.setText("");
            } else {
                balance = (amount - totalP);
                order_balance.setText(String.valueOf(balance) + "zł");
            }
        }
    }

    /**
     * Usuwa wybrany produkt z zamówienia.
     */

    public void orderRemove() {

        String sql = "DELETE FROM product WHERE id = " + item;

        connect = BazaDanych.connectDb();

        try {
            Alert alert;

            if (item == 0 || String.valueOf(item) == null || String.valueOf(item) == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Najpierw wybierz pozycję z listy");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText(null);
                alert.setContentText("Jesteś pewien, że chcesz usunąć: " + item + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Wiadomość informacyjna");
                    alert.setHeaderText(null);
                    alert.setContentText("Pomyślnie usunięto!");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();

                    order_amount.setText("");
                    order_balance.setText("0.0zł");

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wiadomość informacyjna");
                    alert.setHeaderText(null);
                    alert.setContentText("Anulowano!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int item;

    /**
     * Pobiera dane wybranego produktu z tabeli zamówień.
     */

    public void orderSelectData() {

        Product prod = order_tableView.getSelectionModel().getSelectedItem();
        int num = order_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        item = prod.getId();
    }

    /**
     * Wyświetla całkowitą wartość zamówienia w interfejsie użytkownika.
     */

    public void orderDisplayTotal() {
        orderTotal();
        order_total.setText(String.valueOf(totalP) + "zł");

    }

    /**
     * Dodaje produkt do zamówienia i zapisuje dane w bazie.
     */

    public void orderAdd() {

        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product "
                + "(customer_id, product_id, product_name, type, price, quantity, date) "
                + "VALUES(?,?,?,?,?,?,?)";

        connect = BazaDanych.connectDb();

        try {
            String orderType = "";
            double orderPrice = 0;

            String checkData = "SELECT * FROM kategoria WHERE product_id = '"
                    + order_productID.getSelectionModel().getSelectedItem() + "'";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                orderType = result.getString("type");
                orderPrice = result.getDouble("price");
            }

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(customerId));
            prepare.setString(2, (String) order_productID.getSelectionModel().getSelectedItem());
            prepare.setString(3, (String) order_productName.getSelectionModel().getSelectedItem());
            prepare.setString(4, orderType);

            double totalPrice = orderPrice * qty;

            prepare.setString(5, String.valueOf(totalPrice));

            prepare.setString(6, String.valueOf(qty));

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            prepare.setString(7, String.valueOf(sqlDate));

            prepare.executeUpdate();

            orderDisplayTotal();
            orderDisplayData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SpinnerValueFactory<Integer> spinner;

    /**
     * Konfiguruje spinner do wybierania ilości produktów w zamówieniu (maksymalnie 50).
     */

    public void orderSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);

        order_productQuantity.setValueFactory(spinner);
    }

    private int qty;

    /**
     * Pobiera ilość produktów do zamówienia z komponentu spinner.
     */

    public void orderQuantity() {
        qty = order_productQuantity.getValue();
    }

    /**
     * Przełącza widok między różnymi sekcjami aplikacji w zależności od wybranego przycisku.
     *
     * @param event Zdarzenie wywołane kliknięciem przycisku.
     */

    public void switchForm(ActionEvent event) {

        if(event.getSource() == dashboard_btn) {
            dashboard_form1.setVisible(true);
            dashboard_form2.setVisible(true);
            availableFD_form1.setVisible(false);
            availableFD_form2.setVisible(false);
            order_form1.setVisible(false);
            order_form2.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #14757c;  -fx-text-fill: #fff;  -fx-border-width: 0px;");
            availableFD_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            dashboardNC();
            dashboardTI();
            dashboardTIncome();
            dashboardTIncome();
            dashboardNOCCChart();
            dashboardICC();

        } else if(event.getSource() == availableFD_btn) {
            dashboard_form1.setVisible(false);
            dashboard_form2.setVisible(false);
            availableFD_form1.setVisible(true);
            availableFD_form2.setVisible(true);
            order_form1.setVisible(false);
            order_form2.setVisible(false);

            availableFD_btn.setStyle("-fx-background-color: #14757c;  -fx-text-fill: #fff;  -fx-border-width: 0px;");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            availableFDShowData();
            availableFDSearch();


        } else if (event.getSource() == order_btn) {
            dashboard_form1.setVisible(false);
            dashboard_form2.setVisible(false);
            availableFD_form1.setVisible(false);
            availableFD_form2.setVisible(false);
            order_form1.setVisible(true);
            order_form2.setVisible(true);

            order_btn.setStyle("-fx-background-color: #14757c;  -fx-text-fill: #fff;  -fx-border-width: 0px;");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            availableFD_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            orderProductId();
            orderProductName();
            orderSpinner();
            orderDisplayData();
            orderDisplayTotal();


        }
    }

    private double x = 0;
    private double y = 0;

    /**
     * Wylogowuje użytkownika z aplikacji i przełącza widok na ekran logowania.
     */

    public void logout() {
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Wiadomość potwierdzająca");
            alert.setHeaderText(null);
            alert.setContentText("Jesteś pewien, że chcesz wylogować?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/Wyglad.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8f);

                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        }catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Wyświetla nazwę użytkownika w interfejsie użytkownika na podstawie danych logowania.
     */

    public void displayUsername() {
        String user = Data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user);
    }

    /**
     * Zamknięcie aplikacji.
     */

    public void close() {
        System.exit(0);
    }

    /**
     * Minimalizuje okno aplikacji.
     */

    public void minimize() {
        Stage stage = (Stage)main_form.getScene().getWindow();
    stage.setIconified(true);
    }

    /**
     * Inicjalizuje dane i komponenty interfejsu użytkownika podczas uruchamiania aplikacji.
     *
     * @param location Lokalizacja zasobów.
     * @param resources Zasoby wymagane do inicjalizacji.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dashboardNC();
        dashboardTI();
        dashboardTIncome();
        dashboardTIncome();
        dashboardNOCCChart();
        dashboardICC();

        displayUsername();
        availableFDStatus();
        availableFDType();

        availableFDShowData();

        orderProductId();
        orderProductName();
        orderSpinner();
        orderDisplayData();
        orderDisplayTotal();


    }
}
