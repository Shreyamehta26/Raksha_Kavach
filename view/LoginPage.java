
package com.raksha_kavach.view;

import com.raksha_kavach.Controller.LoginAuthController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends Application {

    public static String userNameText;
    Stage loginStage;

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    Scene loginScene, homeScene, registerScene;
    private HomePage homePage; // 👈 Global variable

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();

        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #2C3E50, #4CA1AF);");

        // Load and set background image
        // Image backgroundImage = new
        // Image(getClass().getResource("/Assets/Images/BG.jpg").toExternalForm());
        // BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true,
        // true, false);
        // BackgroundImage background = new BackgroundImage(backgroundImage,
        // BackgroundRepeat.NO_REPEAT,
        // BackgroundRepeat.NO_REPEAT,
        // BackgroundPosition.CENTER,
        // backgroundSize);
        // root.setBackground(new Background(background));

        // Card container
        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(940);
        card.setMaxHeight(600);
        card.setStyle("-fx-background-color: rgba(240, 236, 236, 0.66); -fx-background-radius: 20;");
        card.setEffect(new BoxBlur(15, 15, 0));

        // Logo
        Image logoImage = new Image(getClass().getResource("/Assets/Images/heart.png").toExternalForm());
        ImageView logo = new ImageView(logoImage);
        logo.setFitWidth(200);
        logo.setFitHeight(200);
        logo.setPreserveRatio(true);

        // Title
        Text title = new Text("Welcome to Raksha Kawach");
        title.setFont(Font.font("Arial", 35));
        title.setFill(Color.BLACK);

        // Email Field
        Text userNameTitle = new Text("Email");
        userNameTitle.setFill(Color.BLACK);

        userNameTitle.setStyle("-fx-font-size: 20;");
        TextField userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter your email");
        userNameTextField.setMaxWidth(400);

        VBox userNameVBox = new VBox(10, userNameTitle, userNameTextField);
        userNameVBox.setAlignment(Pos.CENTER);

        // Password Field
        Text passwordTitle = new Text("Password");
        passwordTitle.setFill(Color.BLACK);
        passwordTitle.setStyle("-fx-max-width:300");
        passwordTitle.setStyle("-fx-font-size: 20;");
        PasswordField passwordPasswordField = new PasswordField();
        passwordPasswordField.setPromptText("Enter your password");
        passwordPasswordField.setMaxWidth(400);
        VBox passwordVBox = new VBox(10, passwordTitle, passwordPasswordField);
        passwordVBox.setAlignment(Pos.CENTER);

        // Output text
        Text output = new Text();
        output.setStyle("-fx-font-size: 20;");
        output.setFill(Color.RED);

        // Login Button
        Button loginBtn = new Button("Login");
        loginBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");
        loginBtn.setOnAction(event -> {
            String email = userNameTextField.getText().trim();
            String password = passwordPasswordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                output.setFill(Color.RED);
                output.setText("Please enter all details.");
                return;
            }

            LoginAuthController auth = new LoginAuthController();
            boolean success = auth.signInWithEmailAndPassword(email, password);

            if (success) {
                userNameText = email;
                output.setFill(Color.GREEN);
                output.setText("Login successful!");
                initializeHomePage(loginStage);
                loginStage.setScene(homeScene);
            } else {
                output.setFill(Color.RED);
                output.setText("Invalid email or password.");
            }
        });

        // Register Button
        Button registerBtn = new Button("Register");
        registerBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");
        registerBtn.setOnAction(e -> {
            initializRegisterPage();
            loginStage.setScene(registerScene);
        });

        // Layout
        HBox buttonBox = new HBox(30, loginBtn, registerBtn);
        buttonBox.setAlignment(Pos.CENTER);

        card.getChildren().addAll(logo, title, userNameVBox, passwordVBox, output, buttonBox);
        root.getChildren().add(card);

        loginScene = new Scene(root, 1540, 940);
        loginStage = stage;
        stage.setScene(loginScene);
        stage.setTitle("Raksha Kawach - Login");
        stage.show();
    }

    public void initializeHomePage(Stage primaryStage) {
        this.homePage = new HomePage();
        homePage.setHomestage(primaryStage); // or primaryStage
        homePage.setPrimaryStage(primaryStage); // ✅ Also set primaryStage
        homeScene = new Scene(homePage.createHomePage(this::handleBack), 1540, 940);
        homePage.setHomeScene(homeScene);
        // homePage.page1Scene = homeScene;
        homePage.setExistingHomePage(homePage);
        // ✅ Required for back button to work
        primaryStage.setScene(homeScene);
        primaryStage.show();

    }

    private void initializRegisterPage() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterStage(loginStage);
        registerScene = new Scene(registerPage.createRegisterPage(this::handleBack), 1540, 940);
        registerPage.setRegisterScene(registerScene);
    }

    private void handleBack() {
        loginStage.setScene(loginScene);
    }

    public void getLoginScene() {

        throw new UnsupportedOperationException("Unimplemented method 'getLoginScene'");
    }

    public Parent createloginPage(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createloginPage'");
    }

}