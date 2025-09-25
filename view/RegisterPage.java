package com.raksha_kavach.view;

import com.raksha_kavach.Controller.Registration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterPage {

    Stage registerStage;
    Scene registerScene;

    public void setRegisterStage(Stage registerStage) {
        this.registerStage = registerStage;
    }

    public void setRegisterScene(Scene registerScene) {
        this.registerScene = registerScene;
    }

    public StackPane createRegisterPage(Runnable myThread) {
        StackPane sp = new StackPane();

        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(940);
        card.setMaxHeight(600);
        card.setStyle("-fx-background-color: rgba(240, 236, 236, 0.34); -fx-background-radius: 20;");
        card.setEffect(new BoxBlur(15, 15, 0));

        Text title = new Text("Register Here");
        title.setFill(Color.BLACK);
        title.setStyle("-fx-font-size : 35 ;");

        Text emailLabel = new Text("Email");
        emailLabel.setStyle("-fx-font-size : 20");
        emailLabel.setFill(Color.BLACK);
        TextField emailField = new TextField();
        emailField.setStyle("-fx-max-width : 300;");
        emailField.setPromptText("Enter your email");

        Text passwordLabel = new Text("Password");
        passwordLabel.setStyle("-fx-font-size : 20");
        passwordLabel.setFill(Color.BLACK);
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-max-width : 300;");
        passwordField.setPromptText("Enter your password");

        Text messageText = new Text();

        Button submit = new Button("Submit");
        // submit.setStyle("-fx-background-color: linear-gradient(to bottom right,
        // #1D976C, #93F9B9);");
        submit.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");
        submit.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                messageText.setText("Please fill all the fields.");
                messageText.setFill(Color.RED);
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                messageText.setText("Please enter a valid email address.");
                messageText.setFill(Color.RED);
                return;
            }

            Registration reg = new Registration();
            boolean registered = reg.signUpWithEmailAndPassword(email, password);

            if (registered) {
                messageText.setText("Registration Successful!");
                messageText.setFill(Color.GREEN);
                myThread.run();
            } else {
                messageText.setText("Registration Failed.");
                messageText.setFill(Color.RED);
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
        backBtn.setOnAction(e -> goBack());
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");

        // Image backgroundImage = new Image("Assets/Images/BG.jpg");
        // BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true,
        // true, false);
        // BackgroundImage background = new BackgroundImage(backgroundImage,
        // BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        // BackgroundPosition.CENTER, backgroundSize);
        StackPane root = new StackPane();
        // root.setBackground(new Background(background));
        // root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%,
        // #0070BB, #00FF7F);");
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #2C3E50, #4CA1AF);");
        card.getChildren().addAll(title, emailLabel, emailField,
                passwordLabel, passwordField,
                submit, backBtn, messageText);

        root.getChildren().add(card);
        return root;
    }

    private void goBack() {
        LoginPage loginPage = new LoginPage();
        loginPage.setLoginStage(registerStage);
        Scene loginScene = new Scene(loginPage.createloginPage(null), 1540, 940);
        registerStage.setScene(loginScene);
    }

    // private void handleBack() {
    // System.out.println("Back button pressed in home page");
    // registerStage.setScene(registerScene);
    // }
}
