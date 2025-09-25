package com.raksha_kavach.view;

import com.raksha_kavach.Controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class SignupPage {
    private UserController c2w_ai_userController = new UserController();

    /**
     * Creates and returns the signup scene.
     *
     * @param c2w_ai_backHandler A Runnable to handle the action of going back
     *                           to the previous scene.
     * @return The signup scene.
     */
    public Parent createSignupScene(Runnable c2w_ai_backHandler) {
        System.out.println("inside signup method");
        // ImageView c2w_ai_logo = new ImageView("image/logo.png");
        // ImageView c2w_ai_logo = new ImageView(new Image("image\\cat.png")); // String(getClass().getResource("image\\cat.png").toExternalForm())
        // c2w_ai_logo.setFitWidth(120);
        // c2w_ai_logo.setPreserveRatio(true);
         Label c2w_ai_title = new Label("Sign up");
         c2w_ai_title.setStyle(
                 "-fx-font-size:25 ;-fx-font-weight: bold;-fx-pref-width: 600; -fx-pref-height: 30; -fx-alignment : CENTER;-fx-text-fill:#FFFFFFF");
        VBox c2w_ai_header = new VBox(20,  c2w_ai_title);
        c2w_ai_header.setAlignment(Pos.CENTER);
        Label c2w_ai_userLabel = new Label("Username:");
        TextField c2w_ai_userTextField = new TextField();
        c2w_ai_userTextField.setPromptText("Enter Username");
        c2w_ai_userTextField.setFocusTraversable(false);
        c2w_ai_userTextField.setStyle("-fx-max-width: 270; -fx-min-height:30;-fx-backgound-radius: 15;");
        // Label and PasswordField for password
        Label c2w_ai_passLabel = new Label("Password:");
        PasswordField c2w_ai_passField = new PasswordField();
        c2w_ai_passField.setFocusTraversable(false);
        c2w_ai_passField.setPromptText("Enter Password");
        c2w_ai_passField.setStyle("-fx-pref-width: 270; -fx-min-height:30;-fx-background-radius: 15;");
        // Button to trigger signup action
        Button c2w_ai_signupButton = new Button("Signup");
        c2w_ai_signupButton.setStyle(
                "-fx-pref-width: 70;-fx-min-height:30;-fx-background-radius: 15; -fx-background-color : #2196F3;-fx-text-fill:#FFFFFF");
        // Label to navigate to login scene
        Label c2w_ai_loginButton = new Label("Login");
        c2w_ai_loginButton.setStyle(" -fx-background-radius: 15; -fx-text-fill: white");
        // Label for output messages
        Label c2w_ai_output = new Label();
        c2w_ai_output.setStyle("-fx-text-fill: white;");
        // Style the labels
        c2w_ai_userLabel.setStyle("-fx-text-fill: white;");
        c2w_ai_passLabel.setStyle("-fx-text-fill: white;");
        // VBox layouts for the fields and buttons
        VBox c2w_ai_fieldBox1 = new VBox(10, c2w_ai_userLabel,
                c2w_ai_userTextField);
        c2w_ai_fieldBox1.setMaxSize(300, 30);
        VBox c2w_ai_fieldBox2 = new VBox(10, c2w_ai_passLabel,
                c2w_ai_passField);
        c2w_ai_fieldBox2.setMaxSize(300, 30);
        // Set action for the signup button
        c2w_ai_signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent c2w_ai_event) {
                if (!c2w_ai_userTextField.getText().isEmpty() &&
                        !c2w_ai_passField.getText().isEmpty()) {
                    if (c2w_ai_userController.handleSignup(c2w_ai_userTextField.getText(),
                            c2w_ai_passField.getText())) {
                        LoginPage c2w_ai_loginPage = new LoginPage();
                        c2w_ai_loginPage.getLoginScene();
                    } else {
                        c2w_ai_output.setText("User not Registered");
                    }
                } else {
                    c2w_ai_output.setText("Please Enter Username andPassword");
                }
            }
        });
        // Set action for the login button
        c2w_ai_loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent c2w_ai_event) {
                c2w_ai_backHandler.run();
            }
        });
        // Main VBox layout for the signup page
        VBox c2w_ai_loginBox = new VBox(20, c2w_ai_header, c2w_ai_fieldBox1,
                c2w_ai_fieldBox2, c2w_ai_signupButton, c2w_ai_loginButton, c2w_ai_output);
        c2w_ai_loginBox.setStyle(
                "-fx-pref-height : 200 ; -fx-alignment :CENTER ; -fx-padding : 30 ;-fx-background-color : rgba(0, 0, 0);");
        c2w_ai_loginBox.setAlignment(Pos.CENTER);
        c2w_ai_loginBox.setMaxSize(300, 200);
        Rectangle clip = new Rectangle(300, 600);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        c2w_ai_loginBox.setClip(clip);
        return c2w_ai_loginBox;
    }
}