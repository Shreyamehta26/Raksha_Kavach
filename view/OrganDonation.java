package com.raksha_kavach.view;

import com.raksha_kavach.Controller.organController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrganDonation {

    Scene p2Scene;
    Stage p2Stage;

    public void setP2Scene(Scene p2Scene) {
        this.p2Scene = p2Scene;
    }

    public void setP2Stage(Stage p2Stage) {
        this.p2Stage = p2Stage;
    }

    public BorderPane createScene(Runnable onBack) {
        BorderPane root = new BorderPane();
        root.setPrefSize(750, 800);

        // Gradient background
        BackgroundFill gradientFill = new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#4b85b7ff")),
                        new Stop(1, Color.web("#182848"))),
                CornerRadii.EMPTY, Insets.EMPTY);
        root.setBackground(new Background(gradientFill));

        // Form Panel
        VBox formBox = new VBox(10);
        formBox.setPadding(new Insets(30));
        formBox.setPrefWidth(540);
        formBox.setMaxWidth(540);
        formBox.setPrefHeight(900);
        formBox.setMaxHeight(900);
        formBox.setAlignment(Pos.CENTER);
        formBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 5px;");

        Label title = new Label("Organ Donation Form");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        title.setTextFill(Color.WHITE);

        TextField name = new TextField();
        name.setPromptText("Donor's Name");
        name.setStyle("-fx-prompt-text-fill: grey");

        TextField age = new TextField();
        age.setPromptText("Donor's Age");
        age.setStyle("-fx-prompt-text-fill: grey");

        TextField number = new TextField();
        number.setPromptText("Phone Number");
        number.setStyle("-fx-prompt-text-fill: grey;");

        TextField bldGrp = new TextField();
        bldGrp.setPromptText("Blood Group");
        bldGrp.setStyle("-fx-prompt-text-fill: grey;");

        TextField address = new TextField();
        address.setPromptText("Address");
        address.setStyle("-fx-prompt-text-fill: grey");

        TextField gender = new TextField();
        gender.setPromptText("Gender");
        gender.setStyle("-fx-prompt-text-fill: grey");

        TextField organField = new TextField();
        organField.setPromptText("Organ to Donate");
        organField.setStyle("-fx-prompt-text-fill: grey;");

        Label thankYouLabel = new Label();
        thankYouLabel.setTextFill(Color.WHITE);

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                + "-fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                + "-fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");
        backBtn.setOnAction(e -> onBack.run());

        Label successMessage = new Label();
        successMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        successMessage.setVisible(false); // Initially hidden

        submitBtn.setOnAction(e -> {
            try {
                if (name.getText().isEmpty() || gender.getText().isEmpty() ||
                        age.getText().isEmpty() ||
                        bldGrp.getText().isEmpty() || organField.getText().isEmpty() ||
                        number.getText().isEmpty() ||
                        address.getText().isEmpty()) {

                    showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
                } else {
                    // Submit to Firebase
                    String response = organController.addUserToFirestore(
                            name.getText(),
                            age.getText(),
                            gender.getText(),
                            bldGrp.getText(),
                            organField.getText(),
                            number.getText(),
                            address.getText());

                    if ("Success".equals(response)) {
                        successMessage.setText("Donor registered successfully!");
                        successMessage.setVisible(true); // Show success below button

                        // Clear fields
                        name.clear();
                        age.clear();
                        gender.clear();
                        address.clear();
                        organField.clear();
                        bldGrp.clear();
                        number.clear();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Submission failed.\n" + response);
                    }
                }
            } catch (Exception ex) { // ANONYMOUS CLASS
                successMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                successMessage.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> onBack.run());

        formBox.getChildren().addAll(
                title, name, age, number, bldGrp, address, gender, organField,
                submitBtn, thankYouLabel, backBtn);

        // Right Side Image + Quote
        StackPane rightPane = new StackPane();
        rightPane.setPrefWidth(500);
        rightPane.setMaxHeight(600);
        rightPane.setPrefHeight(600);

        try {
            if (getClass().getResource("/Assets//Images//human2.png") == null) {
                throw new ImageLoadException("Image 'human2.png' not found in Assets/Images.");
            }

            ImageView imageView = new ImageView(
                    new Image(getClass().getResource("/Assets//Images//human2.png").toExternalForm()));
            imageView.setFitWidth(500);
            imageView.setFitHeight(600);
            imageView.setPreserveRatio(true);
            imageView.setEffect(new GaussianBlur(2));
            imageView.setSmooth(true);
            VBox.setMargin(imageView, new Insets(0, 20, 10, 0)); // Adds right margin

            Text quote = new Text(
                    "After I die if I am buried I will rot. If I am burnt I will become ash, but if my body is donated I will live to give life and happiness to many.");
            quote.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            quote.setFill(Color.WHITE);
            quote.setWrappingWidth(600);
            quote.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            VBox.setMargin(quote, new Insets(10, 0, 0, 0)); // Adds top spacing

            VBox quoteBox = new VBox(quote, imageView);
            quoteBox.setAlignment(Pos.CENTER);

            rightPane.getChildren().add(quoteBox);

        } catch (ImageLoadException e) {
            System.out.println("Custom Image Exception: ");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("General Exception :");
            e.printStackTrace();
        }

        HBox content = new HBox(formBox, rightPane);
        content.setAlignment(Pos.CENTER);
        content.setSpacing(200);
        content.setPrefSize(1540, 940);
        content.setMaxSize(1540, 940);

        root.setCenter(content);

        return root;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

// Custom Exception
class ImageLoadException extends Exception {
    public ImageLoadException(String msg) {
        super(msg);
    }
}
