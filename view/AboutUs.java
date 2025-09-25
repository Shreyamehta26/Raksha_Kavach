package com.raksha_kavach.view;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AboutUs {
    Scene p2Scene;
    Stage p2Stage;

    public void setP2Scene(Scene p2Scene) {
        this.p2Scene = p2Scene;
    }

    public void setP2Stage(Stage p2Stage) {
        this.p2Stage = p2Stage;
    }

    public VBox createScene(Runnable back) {
        // ScrollPane for the entire content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Main container VBox
        VBox mainContainer = new VBox(30);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("-fx-background-color: #f2f6fc;");

        // Heading Text with animation
        Text heading = new Text("About Us");
        heading.setFont(Font.font("Arial", 28));
        heading.setStyle("-fx-font-weight: bold;");
        heading.setFill(Color.web("#2c3e50"));

        // Add animation to heading
        FadeTransition fadeHeading = new FadeTransition(Duration.seconds(1.5), heading);
        fadeHeading.setFromValue(0);
        fadeHeading.setToValue(1);
        fadeHeading.play();

        // Description
        Text description = new Text(
                "🛡 About Us – Raksha Kavach\n\n" +
                        "Raksha Kavach is a unified health and welfare support app designed to provide timely access to essential services for every citizen. "
                        +
                        "Our mission is to connect people with the help they need, when they need it the most.\n\n" +
                        "Through a simple and user-friendly platform, we offer:\n\n" +
                        "🏥 Hospital Services: Get information about hospital facilities, bed availability, and emergency contacts.\n"
                        +
                        "🩸 Blood & Organ Donation: Connect with donors and contribute to life-saving donations.\n" +
                        "🍼 Milk Bank Support: Learn about and access human milk banks for neonatal care.\n" +
                        "🧾 Government Schemes: Explore health, insurance, and welfare schemes you're eligible for.\n" +
                        "🤝 Support Resources: Find guidance, contacts, and tools for accessing public health assistance.\n\n"
                        +
                        "Built with compassion and care, Raksha Kavach is more than just an app — it's your digital companion in times of health emergencies and a gateway to government support systems.");
        description.setWrappingWidth(700);
        description.setStyle("-fx-font-size: 16px; -fx-fill: #333333;");

        // Teacher Image Section
        VBox teacherSection = new VBox(15);
        teacherSection.setAlignment(Pos.CENTER);
        teacherSection.setPadding(new Insets(20));
        teacherSection.setMaxWidth(700);

        try {

            Image teacherImage = new Image(getClass().getResourceAsStream("/Assets//Images//shashi sir.jpg"));
            ImageView teacherImageView = new ImageView(teacherImage);
            teacherImageView.setFitWidth(200);
            teacherImageView.setFitHeight(200);
            teacherImageView.setPreserveRatio(true);
            teacherImageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

            // Add animation to teacher image
            TranslateTransition slideIn = new TranslateTransition(Duration.seconds(1), teacherImageView);
            slideIn.setFromX(-300);
            slideIn.setToX(0);

            Text thankYou = new Text("Special Thanks to Shashi sir");
            thankYou.setFont(Font.font("Arial", 18));
            thankYou.setStyle("-fx-font-weight: bold; -fx-fill: #6f42c1;");

            teacherSection.getChildren().addAll(thankYou, teacherImageView);
            slideIn.play();
        } catch (Exception e) {
            System.out.println("Teacher image not found");
        }

        // Team Members Section
        VBox teamSection = new VBox(20);
        teamSection.setAlignment(Pos.CENTER);
        teamSection.setPadding(new Insets(20));
        teamSection.setMaxWidth(700);

        Text teamHeading = new Text("Our Team");
        teamHeading.setFont(Font.font("Arial", 22));
        teamHeading.setStyle("-fx-font-weight: bold; -fx-fill: #2c3e50;");

        HBox teamMembers = new HBox(30);
        teamMembers.setAlignment(Pos.CENTER);

        // Team Member 1: Aasavari
        VBox member1 = createTeamMember("/Assets//Images//aasavari.jpeg.jpg", "Aasavari");
        // Team Member 2: Shruti
        VBox member2 = createTeamMember("/Assets/Images/shruti.jpeg.jpg", "Shruti");
        // Team Member 3: Shreya
        VBox member3 = createTeamMember("/Assets/Images/shreya.jpeg.jpg", "Shreya");
        // Team Member 4: Varada
        VBox member4 = createTeamMember("/Assets/Images/varada.jpeg.jpg", "Varada");

        teamMembers.getChildren().addAll(member1, member2, member3, member4);
        teamSection.getChildren().addAll(teamHeading, teamMembers);

        // Add animation to team section
        FadeTransition fadeTeam = new FadeTransition(Duration.seconds(1.5), teamSection);
        fadeTeam.setFromValue(0);
        fadeTeam.setToValue(1);
        fadeTeam.setDelay(Duration.seconds(0.5));
        fadeTeam.play();

        // Inner VBox (Card-like container)
        VBox vb = new VBox(20, heading, description, teacherSection, teamSection);
        vb.setPadding(new Insets(30));
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setMaxWidth(800);
        vb.setStyle("""
                    -fx-background-color: #ffffff;
                    -fx-background-radius: 15;
                    -fx-border-color: #cccccc;
                    -fx-border-radius: 15;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 5, 5);
                """);

        // Back Button
        Button backBtn = new Button("Previous Page");
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");
        backBtn.setOnAction(e -> goBack());

        // Add animation to back button
        FadeTransition fadeBtn = new FadeTransition(Duration.seconds(1), backBtn);
        fadeBtn.setFromValue(0);
        fadeBtn.setToValue(1);
        fadeBtn.setDelay(Duration.seconds(1));
        fadeBtn.play();

        // Outer VBox
        VBox outer = new VBox(30, vb, backBtn);
        outer.setAlignment(Pos.CENTER);
        outer.setPadding(new Insets(50));
        outer.setStyle("-fx-background-color: #f2f6fc;");

        scrollPane.setContent(outer);

        // Main container that holds the scrollable content
        VBox finalLayout = new VBox(scrollPane);
        finalLayout.setStyle("-fx-background-color: #f2f6fc;");

        return finalLayout;
    }

    private VBox createTeamMember(String imagePath, String name) {
        VBox memberBox = new VBox(10);
        memberBox.setAlignment(Pos.CENTER);

        try {
            Image memberImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView memberImageView = new ImageView(memberImage);
            memberImageView.setFitWidth(120);
            memberImageView.setFitHeight(120);
            memberImageView.setPreserveRatio(true);
            memberImageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 0);");

            // Make images circular
            // memberImageView.setClip(new Circle(50, 50, 50));

            Text memberName = new Text(name);
            memberName.setFont(Font.font("Arial", 14));
            memberName.setStyle("-fx-font-weight: bold;");

            memberBox.getChildren().addAll(memberImageView, memberName);

            // Add hover effect
            memberBox.setOnMouseEntered(e -> {
                memberImageView.setScaleX(1.1);
                memberImageView.setScaleY(1.1);
            });
            memberBox.setOnMouseExited(e -> {
                memberImageView.setScaleX(1.0);
                memberImageView.setScaleY(1.0);
            });

        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
            memberBox.getChildren().add(new Text(name));
        }

        return memberBox;
    }

    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.setHomestage(p2Stage);
        Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
        p2Stage.setScene(homeScene);
    }
}