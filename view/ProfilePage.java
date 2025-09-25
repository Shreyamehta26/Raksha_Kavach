package com.raksha_kavach.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProfilePage {

    private Stage profileStage;

    public void setProfileStage(Stage stage) {
        this.profileStage = stage;
    }

    public void showProfilePage(HomePage homePage) {
        BorderPane root = new BorderPane();

        // Top Bar
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: linear-gradient(to right, #00b4db, #0083b0);");
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("👤 Profile Page");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.WHITE);

        Button backButton = new Button("← Back");
        backButton.setFont(new Font(16));
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #0083b0; -fx-font-weight: bold;");
        backButton.setOnAction(e -> {
            profileStage.setScene(homePage.homeScene); // Return to home
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(backButton, spacer, title);
        root.setTop(topBar);

        // Center Content
        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label nameLabel = new Label("Name: Aasavari Mahadik");
        Label emailLabel = new Label("Email: aasavari@example.com");
        Label roleLabel = new Label("Role: User");

        for (Label label : new Label[]{nameLabel, emailLabel, roleLabel}) {
            label.setFont(new Font(18));
            label.setTextFill(Color.BLACK);
        }

        content.getChildren().addAll(nameLabel, emailLabel, roleLabel);
        root.setCenter(content);

        Scene profileScene = new Scene(root, profileStage.getWidth(), profileStage.getHeight());
        profileStage.setScene(profileScene);
    }
}
