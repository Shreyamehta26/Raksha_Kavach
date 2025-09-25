package com.raksha_kavach.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelpPage {
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
        Text tx = new Text("Help & Support");
        tx.setFont(Font.font("Arial", 24));
        tx.setFill(Color.DARKBLUE);

        // Details
        Text email = new Text("📧 Email: xyz@gmail.com");
        Text phone = new Text("📞 Phone: +91-123456789");
        Text address = new Text("📍 Address: xyz");
        Text rights = new Text("© 2025 YourApp. All rights reserved.");
        Text privacy = new Text("🔒 We value your privacy. Your data is encrypted and secure.");

        Font infoFont = Font.font("Arial", 16);
        email.setFont(infoFont);
        phone.setFont(infoFont);
        address.setFont(infoFont);
        rights.setFont(infoFont);
        privacy.setFont(infoFont);

        // Back button
        Button backBtn = new Button("Previous Page");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
        backBtn.setOnAction(e -> goBack());
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");

        // VBox layout
        VBox vb = new VBox(15, tx, email, phone, address, rights, privacy, backBtn);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        vb.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        return vb;
    }

    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.setHomestage(p2Stage);
        Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
        p2Stage.setScene(homeScene);
    }
}
