package com.raksha_kavach.view;

import com.raksha_kavach.Controller.receiverBloodController;

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

public class receiverBlood {

    Scene p3Scene;
    Stage p3Stage;

    public void setp3Scene(Scene scene) {
        this.p3Scene = scene;
    }

    public void setp3Stage(Stage stage) {
        this.p3Stage = stage;
    }

    public BorderPane createScene(Runnable onBack) {
        // ===== Full Gradient Background =====
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#4b85b7ff")),
                new Stop(1, Color.web("#182848")),
        };

        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // ====== LEFT FORM PANEL ======
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(60));
        formBox.setAlignment(Pos.TOP_LEFT);
        formBox.setMaxWidth(600);
        formBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 20;");

        Label title = new Label("Blood Request Form");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        title.setTextFill(Color.WHITE);

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        firstName.setPrefHeight(45);
        firstName.setFont(Font.font(16));

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        lastName.setPrefHeight(45);
        lastName.setFont(Font.font(16));

        DatePicker dob = new DatePicker();
        dob.setPromptText("Date of Birth");
        dob.setPrefHeight(45);
        dob.setStyle("-fx-font-size: 16px;");

        TextField email = new TextField();
        email.setPromptText("Email");
        email.setPrefHeight(45);
        email.setFont(Font.font(16));

        TextField phone = new TextField();
        phone.setPromptText("Phone");
        phone.setPrefHeight(45);
        phone.setFont(Font.font(16));

        TextField address1 = new TextField();
        address1.setPromptText("Street Address");
        address1.setPrefHeight(45);
        address1.setFont(Font.font(16));

        ComboBox<String> bloodType = new ComboBox<>();
        bloodType.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodType.setPromptText("Required Blood Type");
        bloodType.setPrefHeight(45);
        bloodType.setStyle("-fx-font-size: 16px;");

        Button submitBtn = new Button("Submit");
        submitBtn.setPrefSize(130, 45);
        submitBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                + "-fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");

        Button backBtn = new Button("Back");
        backBtn.setPrefSize(130, 45);
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                + "-fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");

        Label successMessage = new Label();
        successMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        successMessage.setVisible(false); // Initially hidden

        submitBtn.setOnAction(e -> {
            try {
                if (firstName.getText().isEmpty() ||
                        lastName.getText().isEmpty() ||
                        dob.getValue() == null ||
                        dob.getValue() == null ||
                        bloodType.getValue().isEmpty() ||
                        address1.getText().isEmpty() ||
                        phone.getText().isEmpty()) {

                    showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
                } else {
                    // Submit to Firebase
                    String response = receiverBloodController.addUserToFirestore(
                            firstName.getText(),
                            lastName.getText(),
                            dob.getValue().toString(),
                            email.getText(),
                            // dob.getValue().toString(),
                            bloodType.getValue(),

                            address1.getText(),
                            phone.getText());

                    if (response.startsWith("Success")) {
                        showAlert(Alert.AlertType.INFORMATION, "Submission successful!");

                        // Clear fields
                        firstName.clear();
                        lastName.clear();
                        dob.setValue(null);
                        email.clear();
                        phone.clear();
                        address1.clear();
                        bloodType.setValue(null);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Submission failed.\n" + response);
                    }

                }
            } catch (Exception ex) {
                successMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                successMessage.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> onBack.run());

        HBox buttonBox = new HBox(20, submitBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        formBox.getChildren().addAll(
                title,
                firstName, lastName,
                dob,
                email, phone,
                address1,
                bloodType,
                buttonBox);

        // ====== RIGHT IMAGE ======
        Label quote = new Label("A timely donation of blood can save a life");
        quote.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        quote.setTextFill(Color.WHITE);
        quote.setWrapText(true);
        quote.setAlignment(Pos.TOP_RIGHT);

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResource("/Assets//Images//blood.png").toExternalForm());
            imageView.setImage(image);
            imageView.setFitWidth(500);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
        } catch (Exception e) {
            System.out.println("Could not load image: " + e.getMessage());
        }

        VBox rightPane = new VBox(30, quote, imageView);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(40));

        // ====== MAIN LAYOUT ======
        HBox contentLayout = new HBox(50, formBox, rightPane);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.setPadding(new Insets(40));

        root.setCenter(contentLayout);
        return root;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}