package com.raksha_kavach.view;

import com.raksha_kavach.Controller.receiverMilkController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class receiverMilk {

    Scene p3Scene;

    public void setp3Scene(Scene scene) {
        this.p3Scene = scene;
    }

    public BorderPane createScene(Runnable onBack) {
        BorderPane root = new BorderPane();
        root.setPrefSize(1540, 940);

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
        formBox.setAlignment(Pos.CENTER);
        formBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 15px;");
        formBox.setPrefWidth(900);
        formBox.setMaxWidth(400);

        Label title = new Label("Milk Receiver Request");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        title.setTextFill(Color.WHITE);
        title.setAlignment(Pos.TOP_CENTER);

        TextField name = new TextField();
        name.setPromptText("Receiver's Name");
        name.setStyle("-fx-prompt-text-fill: grey");

        TextField contact = new TextField();
        contact.setPromptText("Contact Number");
        contact.setStyle("-fx-prompt-text-fill: grey;");

        TextField email = new TextField();
        email.setPromptText("Email Address");
        email.setStyle("-fx-prompt-text-fill: grey");

        TextField address = new TextField();
        address.setPromptText("Residential Address");
        address.setStyle("-fx-prompt-text-fill: grey");

        TextField babyAge = new TextField();
        babyAge.setPromptText("Baby's Age (in months)");
        babyAge.setStyle("-fx-prompt-text-fill: grey");

        TextArea history = new TextArea();
        history.setPromptText("Reason for Requesting Donated Milk");
        history.setPrefRowCount(2);
        history.setStyle("-fx-prompt-text-fill: grey");

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

        submitBtn.setOnAction(e -> {
            try {
                if (name.getText().isEmpty() ||
                        email.getText().isEmpty() ||
                        contact.getText().isEmpty() ||
                        address.getText().isEmpty() ||
                        babyAge.getText().isEmpty() ||
                        history.getText().isEmpty()) {

                    showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
                } else {
                    String response = receiverMilkController.addUserToFirestore(
                            name.getText(),
                            contact.getText(),
                            email.getText(),
                            address.getText(),
                            babyAge.getText(),
                            history.getText());

                    if (response != null && response.toLowerCase().contains("success")) {
                        showAlert(Alert.AlertType.INFORMATION, "Submitted Successfully!");

                        // Clear fields
                        name.clear();
                        email.clear();
                        contact.clear();
                        address.clear();
                        babyAge.clear();
                        history.clear();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Submission failed.\n" + response);
                    }
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> onBack.run());

        formBox.getChildren().addAll(title, name, contact, email, address, babyAge, history, submitBtn, backBtn);

        // Right Side Image + Quote
        StackPane rightPane = new StackPane();
        rightPane.setPrefWidth(1100);
        rightPane.setMaxWidth(800);

        try {
            ImageView imageView = new ImageView(
                    new Image(getClass().getResource("/Assets//Images//child.png").toExternalForm()));
            imageView.setFitWidth(640);
            imageView.setFitHeight(400);
            imageView.setPreserveRatio(true);
            imageView.setEffect(new GaussianBlur(2));

            Text quote = new Text(
                    "We're here to support you and your little one.\nRegister now to see if you qualify.");
            quote.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
            quote.setFill(Color.WHITE);
            quote.setWrappingWidth(400);
            quote.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

            VBox quoteBox = new VBox(quote);
            quoteBox.setAlignment(Pos.TOP_CENTER);
            VBox.setMargin(quote, new Insets(106));

            rightPane.getChildren().addAll(imageView, quoteBox);
        } catch (Exception e) {
            System.out.println("Could not load right image: " + e.getMessage());
        }

        // Split content horizontally
        HBox content = new HBox(formBox, rightPane);
        content.setAlignment(Pos.CENTER);
        content.setSpacing(80);
        HBox.setHgrow(formBox, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

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