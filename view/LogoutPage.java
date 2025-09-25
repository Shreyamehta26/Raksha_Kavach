package com.raksha_kavach.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogoutPage {
    Scene p2Scene;
    Stage p2Stage;

    public void setP2Scene(Scene p2Scene) {
        this.p2Scene = p2Scene;
    }

    public void setP2Stage(Stage p2Stage) {
        this.p2Stage = p2Stage;
    }

    public VBox createScene(Runnable backAction) {
        // Title
        Text title = new Text("🔒 Logout");
        title.setFont(Font.font("Arial", 22));
        title.setFill(Color.DARKBLUE);

        // Confirmation label
        Label confirmLabel = new Label("Are you sure you want to logout?");
        confirmLabel.setFont(Font.font("Arial", 14));
        confirmLabel.setTextFill(Color.web("#2E7D32"));

        // Confirm button
        Button confirmBtn = new Button("✅ Yes, Logout");
        confirmBtn.setStyle(
                "-fx-background-color: #388E3C;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 6px 14px;" +
                        "-fx-background-radius: 10;");
        confirmBtn.setOnAction(e -> System.exit(0));

        // Cancel button
        Button cancelBtn = new Button("❌ Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: #9E9E9E;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 6px 14px;" +
                        "-fx-background-radius: 10;");
        cancelBtn.setOnAction(e -> {
            if (backAction != null)
                backAction.run();
        });

        // Back button
        // Button backButton = new Button("⬅️ Previous Page");
        // backButton.setStyle(
        // "-fx-background-color: #1976D2;" +
        // "-fx-text-fill: white;" +
        // "-fx-font-size: 13px;" +
        // "-fx-padding: 6px 14px;" +
        // "-fx-background-radius: 10;"
        // );

        Button backBtn = new Button("Previous Page");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
        backBtn.setOnAction(e -> goBack());
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");

        // Layout setup
        VBox vb = new VBox(15, title, confirmLabel, confirmBtn, cancelBtn, backBtn);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        vb.setBackground(new Background(new BackgroundFill(Color.web("#E0F2F1"), CornerRadii.EMPTY, Insets.EMPTY)));

        return vb;
    }

    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.setHomestage(p2Stage);
        Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
        p2Stage.setScene(homeScene);
    }

}
