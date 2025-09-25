package com.raksha_kavach.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FAQContainer {
        Scene p2Scene;
        Stage p2Stage;

        public void setP2Scene(Scene p2Scene) {
                this.p2Scene = p2Scene;
        }

        public void setP2Stage(Stage p2Stage) {
                this.p2Stage = p2Stage;
        }

        public VBox createScene(Runnable back) {
                // Title Label
                Label title = new Label("Frequently Asked Questions");
                title.setFont(Font.font("Arial", 34));
                title.setStyle("-fx-font-weight: bold;");
                title.setTextFill(Color.web("#333333"));

                // Search Field
                TextField searchField = new TextField();
                searchField.setPromptText("Search...");
                searchField.setMaxWidth(300);
                searchField.setStyle("""
                                    -fx-background-radius: 20;
                                    -fx-padding: 10 15;
                                    -fx-border-color: #cccccc;
                                    -fx-border-radius: 20;
                                """);

                // Top Bar
                HBox topBar = new HBox(20);
                topBar.setAlignment(Pos.CENTER_LEFT);
                topBar.getChildren().addAll(title, searchField);

                // Accordion Container
                VBox accordionContainer = new VBox(20);
                accordionContainer.setStyle("""
                                    -fx-background-color: #ffffff;
                                    -fx-background-radius: 15;
                                """);
                accordionContainer.setPadding(new Insets(20));
                accordionContainer.setEffect(new DropShadow(10, Color.rgb(200, 200, 200, 0.5)));

                // Add FAQs
                accordionContainer.getChildren().addAll(
                                styledTitledPane("How to register as a donor?",
                                                "You can register by filling out the donor form under the Blood/Organ Donation section."),
                                styledTitledPane("Can I book an ambulance through Raksha Kavach?",
                                                "Yes, you can view ambulance availability and contact providers directly from the app."),
                                styledTitledPane("What is the Milk Bank feature?",
                                                "It connects mothers who want to donate or request breast milk in emergencies."),
                                styledTitledPane("Does the app support local languages?",
                                                "Yes, Raksha Kavach supports multiple regional languages for easy access."),
                                styledTitledPane("Is my data safe?",
                                                "Yes, user data is stored securely and used only for healthcare purposes."),
                                styledTitledPane("How to check hospital bed availability?",
                                                "Go to Hospital section to check real-time availability of beds."),
                                styledTitledPane("What if I need help using the app?",
                                                "You can visit the Help section or contact support for assistance."));

                // ScrollPane for accordionContainer
                ScrollPane scrollPane = new ScrollPane(accordionContainer);
                scrollPane.setFitToWidth(true);
                scrollPane.setPrefHeight(600);
                scrollPane.setStyle("-fx-background: transparent; -fx-border-color: transparent;");

                // Back Button
                Button backBtn = new Button("Previous Page");
                backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
                backBtn.setOnAction(e -> goBack());
                backBtn.setStyle(
                                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");

                // Main Layout
                VBox mainLayout = new VBox(40);
                mainLayout.setPadding(new Insets(50));
                mainLayout.setStyle("-fx-background-color: #f2f6fc;");
                mainLayout.setAlignment(Pos.TOP_CENTER);
                mainLayout.getChildren().addAll(topBar, scrollPane, backBtn);

                return mainLayout;
        }

        private TitledPane styledTitledPane(String title, String content) {
                Label contentLabel = new Label(content);
                contentLabel.setWrapText(true);
                contentLabel.setPadding(new Insets(10));
                contentLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #444444;");

                TitledPane tp = new TitledPane(title, contentLabel);
                tp.setStyle("""
                                    -fx-background-color: #ffffff;
                                    -fx-border-color: #cccccc;
                                    -fx-border-radius: 10;
                                    -fx-font-size: 16;
                                """);
                tp.setExpanded(false);

                VBox.setMargin(tp, new Insets(8));
                return tp;
        }

        private void goBack() {
                HomePage homePage = new HomePage();
                homePage.setHomestage(p2Stage);
                Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
                p2Stage.setScene(homeScene);
        }

        public Parent createScene(Object back) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'createScene'");
        }
}