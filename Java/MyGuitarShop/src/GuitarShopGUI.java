import BusinessLogic.DAO;
import BusinessLogic.Product;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;


public class GuitarShopGUI extends Application {

    public GuitarShopGUI() throws ClassNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }


    //Data source specified as DataBaseProductDataSource().
    private DAO<Product> productDAO = new DataBaseProductDataSource();

    private ObservableList<Product> productList;

    private TextField idField = new TextField();
    private TextField codeField = new TextField();
    private TextField nameField = new TextField();
    private TextField descriptionField = new TextField();
    private TextField priceField = new TextField();
    private TextField discountField = new TextField();

    private ComboBox categoryComboBox = new ComboBox();

    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;

    private ToggleGroup group;

    private static final String ACTIVE_TEXT_FIELD_STYLE = "-fx-background-color: white;";
    private static final String INACTIVE_TEXT_FIELD_STYLE = "-fx-background-color: #ededed";

    private static final int TABLE_WIDTH = 700;
    private static final int TABLE_HEIGHT = 350;

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 475;

    private static final int ID_FIELD_WIDTH = 50;
    private static final int PRODUCT_CODE_FIELD_WIDTH = 150;
    private static final int CATEGORY_NAME_FIELD_WIDTH = 150;
    private static final int PRODUCT_NAME_FIELD_WIDTH = 200;
    private static final int DESCRIPTION_FIELD_WIDTH = 350;
    private static final int LIST_PRICE_FIELD_WIDTH = 100;
    private static final int DISCOUNT_FIELD_WIDTH = 100;

    private static final double DESCRIPTION_COLUMN_WIDTH_RATIO = 2.84;




    /**
     * Method implements GUI for Guitar Shop Application.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        //menu
        MenuBar menuBar = createMenuBar(primaryStage);

        //table field
        TableView<Product> tableView = createTableView();

        //Text fields
        createTextFields();

        // Category choice combo box
        categoryComboBox.getItems().addAll("Guitars", "Basses", "Drums", "Keyboards");
        categoryComboBox.setPromptText("Category");
        categoryComboBox.prefWidth(CATEGORY_NAME_FIELD_WIDTH);

        //Radio buttons
        createRadioButtons();

        //Submit button
        Button doIt = new Button("Submit");
        doIt.setOnAction(e -> submit());

        DropShadow shadow = new DropShadow();
        doIt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            doIt.setEffect(shadow);
        });
        doIt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            doIt.setEffect(null);
        });

        //Clear Text Fields button
        Button clearText = new Button("Clear");
        clearText.setOnAction(event -> clearAllFields());
//        clearText.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                clearAllFields();
//            }
//        });

        clearText.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            clearText.setEffect(shadow);
        });
        clearText.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            clearText.setEffect(null);
        });

        //H Boxes
        HBox textBox = new HBox();
        textBox.getChildren().addAll(idField, codeField, categoryComboBox, nameField, descriptionField, priceField, discountField, doIt);
        textBox.setSpacing(10);
        textBox.setPadding(new Insets(5, 0, 0, 10));

        HBox radioBox = new HBox();
        radioBox.getChildren().addAll(rb1, rb2, rb3, clearText);
        radioBox.setSpacing(10);
        radioBox.setPadding(new Insets(15, 0, 10, 10));

        // Layout
        GridPane layout = new GridPane();
        layout.add(menuBar, 1, 1);
        layout.add(tableView, 1, 2);
        layout.add(radioBox, 1, 3);
        layout.add(textBox, 1, 100);

        Scene scene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("My Guitar Shop");
        primaryStage.setScene(scene);
        primaryStage.show();

    }





    /**
     * Method creates a menu bar.
     *
     * @param primaryStage
     * @return
     */
    private MenuBar createMenuBar(Stage primaryStage) {
        Menu fileMenu = new Menu("File");
        Menu productMenu = new Menu("Products");

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> Platform.exit());

        MenuItem viewRefresh = new MenuItem("View/Refresh");
        viewRefresh.setOnAction(e -> refreshView());

        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exit);

        productMenu.getItems().add(viewRefresh);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        menuBar.getMenus().addAll(fileMenu, productMenu);

        return menuBar;
    }




    /**
     * Method creates a table view (a space where a table retrieved from database will be displayed).
     *
     * @return
     */
    private TableView<Product> createTableView() {
        productList = FXCollections.observableList(productDAO.getAll());

        TableView<Product> table = new TableView<>(productList);

        TableColumn<Product, Integer> proId = new TableColumn<>("ProductID");
        proId.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().add(proId);

        TableColumn<Product, String> proCode = new TableColumn<>("Product Code");
        proCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        table.getColumns().add(proCode);

        TableColumn<Product, String> proCategory = new TableColumn<>("Category Name");
        proCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        table.getColumns().add(proCategory);

        TableColumn<Product, String> proName = new TableColumn<>("Product Name");
        proName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        table.getColumns().add(proName);

        TableColumn<Product, String> proDescription = new TableColumn<>("Description");
        proDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        table.getColumns().add(proDescription);
        proDescription.prefWidthProperty().bind(table.widthProperty().divide(DESCRIPTION_COLUMN_WIDTH_RATIO));

        TableColumn<Product, Double> proListPrice = new TableColumn<>("List Price");
        proListPrice.setCellValueFactory(new PropertyValueFactory<>("listPrice"));
        table.getColumns().add(proListPrice);

        TableColumn<Product, Double> proDiscount = new TableColumn<>("Discount");
        proDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        table.getColumns().add(proDiscount);

        TableColumn<Product, String> proDate = new TableColumn<>("Date Added");
        proDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        table.getColumns().add(proDate);

        table.setPrefWidth(TABLE_WIDTH);
        table.setPrefHeight(TABLE_HEIGHT);

        return table;
    }




    /**
     * Method creates a set of text fields and sets applicable restrictions.
     */
    private void createTextFields() {
        idField.setPromptText("ID");
        idField.setPrefWidth(ID_FIELD_WIDTH);
        //Entry restriction for ID field
        idField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!"0123456789".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        });
        codeField.setPromptText("Product Code");
        codeField.setPrefWidth(PRODUCT_CODE_FIELD_WIDTH);
        nameField.setPromptText("Product Name");
        nameField.setPrefWidth(PRODUCT_NAME_FIELD_WIDTH);
        descriptionField.setPromptText("Description");
        descriptionField.setPrefWidth(DESCRIPTION_FIELD_WIDTH);
        priceField.setPromptText("Price");
        priceField.setPrefWidth(LIST_PRICE_FIELD_WIDTH);
        //Entry restriction for Price field
        priceField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!"0123456789.".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        });
        discountField.setPromptText("Discount$");
        discountField.setPrefWidth(DISCOUNT_FIELD_WIDTH);
        //Entry restriction for Discount field
        discountField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!"0123456789.".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        });
    }




    /**
     * Method creates a set of radio buttons, bind them in a toggle group
     * and sets them on action.
     */
    private void createRadioButtons() {
        rb1 = new RadioButton("Add");
        rb2 = new RadioButton("Update");
        rb3 = new RadioButton("Delete");

        group = new ToggleGroup();
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);

        toggleListener();

        rb1.setOnAction(e -> toggleListener());
        rb2.setOnAction(e -> toggleListener());
        rb3.setOnAction(e -> toggleListener());
    }




    /**
     * Method provides actions for submit button depending on position of radio button (add, update, delete).
     * It also provides basic data consistency check and calls to refresh table view at the end of operation.
     */
    private void submit() {
        //Null pointer exception handling in combo box
        String categorySelection;
        if (categoryComboBox.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Category selection is empty!");
            categorySelection = "";
        } else {
            categorySelection = categoryComboBox.getSelectionModel().getSelectedItem().toString();
        }
        //Add action
        if (group.getSelectedToggle() == rb1) {
            String code = codeField.getText();
            String name = nameField.getText();
            String description = descriptionField.getText();
            if (code.isEmpty() || categorySelection.isEmpty() || name.isEmpty() || description.isEmpty() || priceField.getText().isEmpty() || discountField.getText().isEmpty()) {
                System.out.println("One of the fields is empty!");
                return;
            }
            double price = Double.parseDouble(priceField.getText());
            double discount = Double.parseDouble(discountField.getText());
            Product newProduct = new Product(categorySelection, code, name, description, price, discount);
            productDAO.add(newProduct);
            refreshView();
            clearAllFields();
        }
        //Update action
        if (group.getSelectedToggle() == rb2) {
            if (idField.getText().isEmpty()) {
                System.out.println("Did you enter PRODUCT ID?");
                return;
            }
            if (codeField.getText().isEmpty() && categorySelection.isEmpty() && nameField.getText().isEmpty() && descriptionField.getText().isEmpty() &&
                    priceField.getText().isEmpty() && discountField.getText().isEmpty()) {
                System.out.println("Did you fill ALL fields?!");
                return;
            }

            long id = Long.parseLong(idField.getText());
            Product productToUpdate = productDAO.get(id);

            String code = codeField.getText();
            if (!code.isEmpty()) {
                productToUpdate.setProductCode(code);
            }
            if (!categorySelection.isEmpty()) {
                productToUpdate.setCategoryName(categorySelection);
            }
            String name = nameField.getText();
            if (!name.isEmpty()) {
                productToUpdate.setProductName(name);
            }
            String description = descriptionField.getText();
            if (!description.isEmpty()) {
                productToUpdate.setDescription(description);
            }
            if (!priceField.getText().isEmpty()) {
                productToUpdate.setListPrice(Double.parseDouble(priceField.getText()));
            }
            if (!discountField.getText().isEmpty()) {
                productToUpdate.setDiscount(Double.parseDouble(discountField.getText()));
            }
            productDAO.update(productToUpdate);
            refreshView();
            clearAllFields();
        }
        //Delete action
        if (group.getSelectedToggle() == rb3) {
            long id = Long.parseLong(idField.getText());
            productDAO.delete(id);
            refreshView();
            clearAllFields();
        }
    }




    /**
     * Method refreshes view of table after introduced changes(add, update, delete)
     * by re-downloading data from database, clearing table view and inserting new data.
     */
    private void refreshView() {
        List<Product> backUpList = productDAO.getAll();
        productList.clear();
        for (Product product : backUpList) {
            productList.add(product);
        }
    }




    /**
     * Method sets relevant fields active/inactive depending on radio button position.
     */
    private void toggleListener() {
        //Add style
        if (group.getSelectedToggle() == rb1) {
            idField.setEditable(false);
            codeField.setEditable(true);
            categoryComboBox.setDisable(false);
            nameField.setEditable(true);
            descriptionField.setEditable(true);
            priceField.setEditable(true);
            discountField.setEditable(true);

            idField.setStyle(INACTIVE_TEXT_FIELD_STYLE);
            codeField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            categoryComboBox.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            nameField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            descriptionField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            priceField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            discountField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
        }
        // Update style
        if (group.getSelectedToggle() == rb2) {
            idField.setEditable(true);
            codeField.setEditable(true);
            categoryComboBox.setDisable(false);
            nameField.setEditable(true);
            descriptionField.setEditable(true);
            priceField.setEditable(true);
            discountField.setEditable(true);

            idField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            codeField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            categoryComboBox.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            nameField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            descriptionField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            priceField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            discountField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
        }
        //Delete style
        if (group.getSelectedToggle() == rb3) {
            idField.setEditable(true);
            codeField.setEditable(false);
            categoryComboBox.setDisable(true);
            nameField.setEditable(false);
            descriptionField.setEditable(false);
            priceField.setEditable(false);
            discountField.setEditable(false);

            idField.setStyle(ACTIVE_TEXT_FIELD_STYLE);
            codeField.setStyle(INACTIVE_TEXT_FIELD_STYLE);
            categoryComboBox.setStyle(INACTIVE_TEXT_FIELD_STYLE);
            nameField.setStyle(INACTIVE_TEXT_FIELD_STYLE);
            descriptionField.setStyle(INACTIVE_TEXT_FIELD_STYLE);
            priceField.setStyle(INACTIVE_TEXT_FIELD_STYLE);
            discountField.setStyle(INACTIVE_TEXT_FIELD_STYLE);
        }
    }




    /**
     * Method clears text fields and combo box.
     */
    private void clearAllFields() {
        idField.clear();
        codeField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        categoryComboBox.setAccessibleText("Category");
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        discountField.clear();
    }
}
