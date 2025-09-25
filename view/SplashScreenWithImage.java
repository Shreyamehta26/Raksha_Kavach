package com.raksha_kavach.view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class SplashScreenWithImage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Splash screen UI
        Label titleLabel = new Label("RAKSHA KAVACH");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Load the image - using resource stream for better reliability
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream("/Assets/Images/HelpingHand.jpeg"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            VBox splashLayout = new VBox(20, titleLabel, imageView);
            splashLayout.setAlignment(Pos.CENTER);
            Scene splashScene = new Scene(splashLayout, 1540, 940);

            // Set the splash scene initially
            primaryStage.setScene(splashScene);
            primaryStage.setTitle("Splash Screen");
            primaryStage.show();

            // Wait for 3 seconds, then switch to login screen
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
                // Create and show the login page
                LoginPage loginPage = new LoginPage();
                try {
                    loginPage.start(new Stage());
                    primaryStage.close(); // Close the splash screen
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            delay.play();
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            // Fallback if image fails to load
            Label errorLabel = new Label("RAKSHA KAVACH");
            VBox errorLayout = new VBox(errorLabel);
            errorLayout.setAlignment(Pos.CENTER);
            primaryStage.setScene(new Scene(errorLayout, 300, 200));
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch this class, not LoginPage
    }
}