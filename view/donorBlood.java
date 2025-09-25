package com.raksha_kavach.view;

import com.raksha_kavach.Controller.donorBloodController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class donorBlood {
    private Stage primaryStage;
    private Scene previousScene;

    private TextField nameField, emailField, bloodGroupField, phoneField;
    private DatePicker dobPicker;

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public BorderPane createScene(Runnable onBack) {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#4b85b7ff")),
                        new Stop(1, Color.web("#182848"))),
                CornerRadii.EMPTY, Insets.EMPTY)));

        // Initialize fields
        nameField = createTextField("Full Name");
        emailField = createTextField("Email");
        dobPicker = createDatePicker();
        bloodGroupField = createTextField("Blood Group");
        phoneField = createTextField("Phone Number");

        VBox formBox = createFormBox(onBack);
        HBox layout = createContentLayout(formBox);
        root.setCenter(layout);

        return root;

    }

    private VBox createFormBox(Runnable onBack) {
        VBox box = new VBox(20);
        box.setPadding(new Insets(40));
        box.setMaxWidth(600);
        box.setAlignment(Pos.TOP_LEFT);
        box.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 5;");

        Label title = createLabel("Donate Blood", 30, Color.WHITE);

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                + "-fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                + "-fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");

        submitBtn.setOnAction(e -> handleFormSubmission());
        backBtn.setOnAction(e -> onBack.run());

        box.getChildren().addAll(
                title, nameField, emailField, dobPicker,
                bloodGroupField, phoneField, new HBox(20, submitBtn, backBtn));

        return box;
    }

    private HBox createContentLayout(VBox formBox) {
        Label quote = createLabel(
                "Your droplets of blood\nmay create an Ocean of Happiness",
                36, Color.WHITE);
        quote.setWrapText(true);

        ImageView imageView = createImageView("/Assets/Images/donorBlood.png", 400);

        VBox rightPane = new VBox(30, quote, imageView);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(40));

        HBox layout = new HBox(50, formBox, rightPane);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        return layout;
    }

    private void handleFormSubmission() {
        if (nameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                dobPicker.getValue() == null ||
                bloodGroupField.getText().trim().isEmpty() ||
                phoneField.getText().trim().isEmpty()) {

            showAlert("Warning", "Please fill in all required fields.");
            return;
        }

        Alert loading = showLoadingAlert("Submitting your donation...");

        new Thread(() -> {
            try {
                String result = donorBloodController.addUserToFirestore(
                        nameField.getText().trim(),
                        emailField.getText().trim(),
                        dobPicker.getValue().toString(),
                        bloodGroupField.getText().trim(),
                        phoneField.getText().trim());

                Platform.runLater(() -> {
                    loading.close();
                    if (result.contains("Error")) {
                        showAlert("Submission Error", result);
                    } else {
                        showAlert("Success", "Thank you for your donation!");
                        clearFormFields();
                        // 👇 Do NOT redirect to MyForms automatically
                        // You can add a button below to open it manually
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    loading.close();
                    showAlert("Error", "Submission failed: " + e.getMessage());
                });
            }
        }).start();
    }

    private void clearFormFields() {
        nameField.clear();
        emailField.clear();
        bloodGroupField.clear();
        phoneField.clear();
        dobPicker.setValue(null);
    }

    // Create a date picker
    private DatePicker createDatePicker() {
        DatePicker picker = new DatePicker();
        picker.setPromptText("Date of Birth");
        picker.setPrefHeight(45);
        picker.setStyle("-fx-font-size: 16px;");
        return picker;
    }

    // Load image safely
    private ImageView createImageView(String path, double width) {
        ImageView view = new ImageView();
        try {
            Image image = new Image(getClass().getResource(path).toExternalForm());
            view.setImage(image);
            view.setFitWidth(width);
            view.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Image failed: " + e.getMessage());
        }
        return view;
    }

    // Create styled label
    private Label createLabel(String text, int size, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, size));
        label.setTextFill(color);
        return label;
    }

    // Create styled text field
    private TextField createTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefHeight(45);
        tf.setFont(Font.font(16));
        return tf;
    }

    // Create styled button
    private Button createButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPrefSize(130, 45);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btn.setStyle("-fx-background-color:" + color + "; -fx-text-fill: black; -fx-background-radius: 5;");
        return btn;
    }

    // Show non-blocking alert
    private Alert showLoadingAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
        return alert;
    }

    // Show confirmation/error alerts
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
