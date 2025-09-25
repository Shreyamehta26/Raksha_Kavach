package com.raksha_kavach.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class demoScheme extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Plain text content
        String infoText = """
                Scheme: Ayushman Bharat

                Description:
                Ayushman Bharat Pradhan Mantri Jan Arogya Yojana (PM-JAY) is a flagship health insurance scheme launched in 2018.
                It provides health coverage to poor and vulnerable families across India.

                Documents Required:
                - Aadhaar Card
                - Ration Card / BPL Card
                - Voter ID / PAN / Other Govt. ID
                - Family Photograph
                - Mobile Number

                Benefits:
                - Free treatment up to ₹5 lakhs per family per year
                - No premium or payment is required from the beneficiary
                - Coverage is family floater type (₹5 lakh shared by all members)
                - Cashless and paperless treatment in public and empaneled private hospitals
                - Nationwide portability

                 Description:
                Ayushman Bharat Pradhan Mantri Jan Arogya Yojana (PM-JAY) is a flagship health insurance scheme launched in 2018.
                It provides health coverage to poor and vulnerable families across India.

                Documents Required:
                - Aadhaar Card
                - Ration Card / BPL Card
                - Voter ID / PAN / Other Govt. ID
                - Family Photograph
                - Mobile Number

                Benefits:
                - Free treatment up to ₹5 lakhs per family per year
                - No premium or payment is required from the beneficiary
                - Coverage is family floater type (₹5 lakh shared by all members)
                - Cashless and paperless treatment in public and empaneled private hospitals
                - Nationwide portability

                 Description:
                Ayushman Bharat Pradhan Mantri Jan Arogya Yojana (PM-JAY) is a flagship health insurance scheme launched in 2018.
                It provides health coverage to poor and vulnerable families across India.

                Documents Required:
                - Aadhaar Card
                - Ration Card / BPL Card
                - Voter ID / PAN / Other Govt. ID
                - Family Photograph
                - Mobile Number

                Benefits:
                - Free treatment up to ₹5 lakhs per family per year
                - No premium or payment is required from the beneficiary
                - Coverage is family floater type (₹5 lakh shared by all members)
                - Cashless and paperless treatment in public and empaneled private hospitals
                - Nationwide portability

                 Description:
                Ayushman Bharat Pradhan Mantri Jan Arogya Yojana (PM-JAY) is a flagship health insurance scheme launched in 2018.
                It provides health coverage to poor and vulnerable families across India.

                Documents Required:
                - Aadhaar Card
                - Ration Card / BPL Card
                - Voter ID / PAN / Other Govt. ID
                - Family Photograph
                - Mobile Number

                Benefits:
                - Free treatment up to ₹5 lakhs per family per year
                - No premium or payment is required from the beneficiary
                - Coverage is family floater type (₹5 lakh shared by all members)
                - Cashless and paperless treatment in public and empaneled private hospitals
                - Nationwide portability

                This scheme is implemented by the National Health Authority (NHA).
                """;

        // Label for multiline text
        Label contentLabel = new Label(infoText);
        contentLabel.setFont(Font.font("Arial", 16));
        contentLabel.setWrapText(true);

        // ScrollPane to make content scrollable vertically
        ScrollPane scrollPane = new ScrollPane(contentLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Card-like VBox
        VBox card = new VBox(scrollPane);
        card.setPadding(new Insets(20));
        card.setSpacing(10);
        card.setMaxWidth(750);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        // Add drop shadow for card effect
        card.setEffect(new DropShadow(10, Color.GRAY));
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));

        // Center the card in the scene
        StackPane root = new StackPane(card);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f0f0f0;");

        Scene scene = new Scene(root, 1540, 940);

        primaryStage.setTitle("Ayushman Bharat Information");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
