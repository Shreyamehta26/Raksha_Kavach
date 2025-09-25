
package com.raksha_kavach.view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePage {

    boolean sidebarVisible;
    private Stage homestage, primaryStage;
    Scene homeScene, loginScene, diseasScene, c2w_ai_searchPageScene;
    Scene page1Scene, page2Scene, page3Scene, page4Scene, page5Scene, page6Scene;

    HomePage existingHomePage;
    BorderPane root;

    public HomePage getExistingHomePage() {
        return existingHomePage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setExistingHomePage(HomePage existingHomePage) {
        this.existingHomePage = existingHomePage;
    }

    public void setHomestage(Stage homestage) {
        this.homestage = homestage;
    }

    public void setHomeScene(Scene homeScene) {
        this.homeScene = homeScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public Pane createHomePage(Runnable myThread) {
        StackPane mainRoot = new StackPane();
        root = new BorderPane();

        // Main content area
        HBox centerPane = new HBox();
        centerPane.setAlignment(Pos.CENTER);

        // Create and set top navigation bar
        HBox topBar = createTopBar();
        root.setTop(topBar);

        // Create and set sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);
        sidebarVisible = true;

        // Create left content pane
        VBox leftPane = createLeftContentPane();

        // Create right services pane
        VBox rightPane = createServicesPane();

        centerPane.getChildren().addAll(leftPane, rightPane);
        root.setCenter(centerPane);

        // Add fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        mainRoot.getChildren().add(root);
        return mainRoot;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50, #3498db);");
        topBar.setEffect(new DropShadow(10, Color.BLACK));

        // App title with logo
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // try {
        // ImageView logo = new ImageView(new
        // Image(getClass().getResourceAsStream("/images/icons/logo.png")));
        // logo.setFitWidth(30);
        // logo.setFitHeight(30);
        // titleBox.getChildren().add(logo);
        // } catch (Exception e) {
        // System.out.println("Logo not found");
        // }

        Label title = new Label("Raksha Kavach");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);
        titleBox.getChildren().add(title);

        // Toggle button
        Button toggleBtn = new Button("☰");
        toggleBtn.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-text-fill: white;");
        toggleBtn.setOnAction(e -> toggleSidebar());

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button profileButton = new Button("Profile");
        profileButton.setOnAction(e -> {
            CurrentUser currentUser = CurrentUser.getInstance();
            String docId = currentUser.getDocId();

            System.out.println("Current docId: " + docId);

            if (docId != null && !docId.isEmpty()) {
                profile1 profilePage = new profile1(homestage, this::backToHomePage);
                Scene profileScene = profilePage.createScene();
                homestage.setScene(profileScene);
            } else {
                System.out.println("⚠ No patient ID found. Please register first.");
                // Optionally show an alert to the user
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Profile Not Available");
                alert.setHeaderText(null);
                alert.setContentText("Please complete registration first");
                alert.showAndWait();
            }
        });

        topBar.getChildren().addAll(toggleBtn, titleBox, spacer, profileButton);
        return topBar;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #4ca1af);");
        sidebar.setPrefWidth(200);
        sidebar.setEffect(new DropShadow(5, Color.BLACK));

        // Sidebar buttons
        Button btnPatients = createSidebarButton("🧑‍⚕ Patients", this::loadPatients);
        Button btnHelp = createSidebarButton("❓ Help", this::loadHelp);
        Button btnAbout = createSidebarButton("📄 About Us", this::loadAbout);
        Button btnLogout = createSidebarButton("🔒 Logout", this::handleLogout);
        Button btnFaq = createSidebarButton("❓ FAQ", this::handleFaq);
        Button btnMyForms = createSidebarButton("My Forms", this::handleMyForm);

        sidebar.getChildren().addAll(btnPatients, btnHelp, btnAbout, btnLogout, btnFaq, btnMyForms);
        return sidebar;
    }

    private VBox createLeftContentPane() {
        VBox leftPane = new VBox(20);
        leftPane.setPadding(new Insets(20));
        leftPane.setStyle("-fx-background-color: #f5f7fa;");
        leftPane.setPrefWidth(450);

        // Disease section header
        Label diseaseHeader = new Label("Health Categories");
        diseaseHeader.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        diseaseHeader.setTextFill(Color.DARKSLATEBLUE);

        // Disease buttons
        HBox diseaseButtons = createDiseaseButtons();

        // Schemes header
        Label schemesHeader = new Label("Healthcare Schemes");
        schemesHeader.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        schemesHeader.setTextFill(Color.DARKSLATEBLUE);

        // Schemes cards
        ScrollPane schemesScroll = createSchemesScrollPane();

        // AI Chatbot footer
        Button aiChatbotBtn = createAIChatbotButton();

        leftPane.getChildren().addAll(diseaseHeader, diseaseButtons, schemesHeader, schemesScroll, aiChatbotBtn);
        return leftPane;
    }

    private HBox createDiseaseButtons() {
        HBox buttonRow = new HBox(15);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(10));

        Button heartBtn = createDiseaseButton("Heart", "#e74c3c");
        Button eyeBtn = createDiseaseButton("Eye", "#3498db");
        Button brainBtn = createDiseaseButton("Brain", "#2ecc71");
        Button cancerBtn = createDiseaseButton("Cancer", "#f39c12");

        // Add pulse animation to emergency buttons
        addPulseAnimation(heartBtn);
        addPulseAnimation(eyeBtn);
        addPulseAnimation(brainBtn);
        addPulseAnimation(cancerBtn);

        buttonRow.getChildren().addAll(heartBtn, eyeBtn, brainBtn, cancerBtn);
        return buttonRow;
    }

    private Button createDiseaseButton(String text, String color) {
        StackPane buttonPane = new StackPane();
        buttonPane.setPrefSize(150, 100);
        buttonPane.setStyle("-fx-background-radius: 15;");
        buttonPane.setEffect(new DropShadow(5, Color.GRAY));

        // Background with color
        Rectangle bg = new Rectangle(150, 100);
        bg.setArcWidth(15);
        bg.setArcHeight(15);
        bg.setFill(Color.valueOf(color));

        // Icon
        // ImageView icon = new ImageView();
        // try {
        // icon.setImage(new Image(getClass().getResourceAsStream()));
        // icon.setFitWidth(40);
        // icon.setFitHeight(40);
        // } catch (Exception e) {
        // System.out.println("Icon not found: " + iconPath);
        // }

        // Label
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setTextFill(Color.WHITE);
        label.setTranslateY(20);

        buttonPane.getChildren().addAll(bg, label);

        // Animation
        // buttonPane.setOnMouseEntered(e -> {
        // ScaleTransition st = new ScaleTransition(Duration.millis(200), buttonPane);
        // st.setToX(1.05);
        // st.setToY(1.05);

        // RotateTransition rt = new RotateTransition(Duration.millis(200));
        // rt.setByAngle(5);

        // ParallelTransition pt = new ParallelTransition(st, rt);
        // pt.play();
        // });

        // buttonPane.setOnMouseExited(e -> {
        // ScaleTransition st = new ScaleTransition(Duration.millis(200), buttonPane);
        // st.setToX(1.0);
        // st.setToY(1.0);

        // RotateTransition rt = new RotateTransition(Duration.millis(200));
        // rt.setByAngle(-5);

        // ParallelTransition pt = new ParallelTransition(st, rt);
        // pt.play();
        // });

        Button button = new Button();
        button.setGraphic(buttonPane);
        button.setStyle("-fx-background-color: transparent;");
        button.setOnAction(e -> openSchemesPage(text));

        return button;
    }

    private ScrollPane createSchemesScrollPane() {
        GridPane cardGrid = new GridPane();
        cardGrid.setPadding(new Insets(20));
        cardGrid.setHgap(20);
        cardGrid.setVgap(20);

        String[] schemes = {
                "Ayushman Bharat", " Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)",
                "Pradhan Mantri Jan Arogya Yojana(PM-JAY)", "The National Health Mission (NHM)",
                "Chief Minister's Relief Fund (CMRF)", "Rashtriya Arogya Nidhi(RAN)"
        };

        for (int i = 0; i < schemes.length; i++) {
            VBox card = createSchemeCard(schemes[i]);
            cardGrid.add(card, i % 2, i / 2);

            // Add animation to each card
            FadeTransition ft = new FadeTransition(Duration.millis(600), card);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setDelay(Duration.millis(i * 100));
            ft.play();
        }

        ScrollPane scrollPane = new ScrollPane(cardGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefHeight(350);
        return scrollPane;
    }

    private VBox createSchemeCard(String title) {
        VBox card = new VBox();
        card.setStyle("-fx-background-radius: 15;");
        card.setPrefSize(350, 180);
        card.setEffect(new DropShadow(15, Color.LIGHTBLUE));

        // Card background with gradient overlay
        StackPane backgroundPane = new StackPane();
        backgroundPane.setPrefSize(350, 180);

        // Background image
        // ImageView imageView = new ImageView();
        // try {
        // Image image = new
        // Image(getClass().getResourceAsStream("src/main/resources/Assets/Images" +
        // imageName));
        // imageView.setImage(image);
        // imageView.setFitWidth(350);
        // imageView.setFitHeight(180);
        // imageView.setPreserveRatio(false);
        // } catch (Exception e) {
        // //System.out.println("Scheme image not found: " + imageName);
        // // Fallback gradient background
        // imageView.setStyle("-fx-background-color: linear-gradient(to bottom right,
        // #3498db, #2c3e50);");
        // }

        // Gradient overlay
        Rectangle gradientOverlay = new Rectangle(350, 180);
        gradientOverlay.setFill(new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(0, 0, 0, 0.7)),
                new Stop(1, Color.rgb(0, 0, 0, 0.3))));

        // Card content
        VBox content = new VBox();
        content.setAlignment(Pos.BOTTOM_LEFT);
        content.setPadding(new Insets(15));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setEffect(new DropShadow(5, Color.BLACK));

        content.getChildren().add(titleLabel);

        backgroundPane.getChildren().addAll(gradientOverlay, content);
        card.getChildren().add(backgroundPane);

        // Hover animation
        card.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), card);
            st.setToX(1.03);
            st.setToY(1.03);

            FadeTransition ft = new FadeTransition(Duration.millis(200), gradientOverlay);
            ft.setToValue(0.5);

            ParallelTransition pt = new ParallelTransition(st, ft);
            pt.play();
        });

        card.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), card);
            st.setToX(1.0);
            st.setToY(1.0);

            FadeTransition ft = new FadeTransition(Duration.millis(200), gradientOverlay);
            ft.setToValue(0.7);

            ParallelTransition pt = new ParallelTransition(st, ft);
            pt.play();
        });

        card.setOnMouseClicked(e -> openSchemeDetails(title));
        return card;
    }

    private Button createAIChatbotButton() {
        StackPane buttonPane = new StackPane();
        buttonPane.setMaxWidth(Double.MAX_VALUE);
        buttonPane.setStyle("-fx-background-radius: 10;");
        buttonPane.setEffect(new DropShadow(5, Color.GRAY));

        // Background
        Rectangle bg = new Rectangle(300, 50);
        bg.setArcWidth(10);
        bg.setArcHeight(10);
        bg.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#4b85b7ff")),
                new Stop(1, Color.web("#182848"))));

        // Content
        HBox content = new HBox(10);
        content.setAlignment(Pos.CENTER);

        Label label = new Label("AI Health Assistant");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setTextFill(Color.WHITE);

        content.getChildren().addAll(label);
        buttonPane.getChildren().addAll(bg, content);

        // Animation
        buttonPane.setOnMouseEntered(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), buttonPane);
            tt.setByY(-5);

            ScaleTransition st = new ScaleTransition(Duration.millis(200), buttonPane);
            st.setToX(1.02);
            st.setToY(1.02);

            ParallelTransition pt = new ParallelTransition(tt, st);
            pt.play();
        });

        buttonPane.setOnMouseExited(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), buttonPane);
            tt.setByY(5);

            ScaleTransition st = new ScaleTransition(Duration.millis(200), buttonPane);
            st.setToX(1.0);
            st.setToY(1.0);

            ParallelTransition pt = new ParallelTransition(tt, st);
            pt.play();
        });

        Button button = new Button();
        button.setGraphic(buttonPane);
        button.setStyle("-fx-background-color: transparent;");
        button.setOnAction(e -> openSearchPage());
        button.setMaxWidth(Double.MAX_VALUE);

        return button;
    }

    private VBox createServicesPane() {
        VBox rightPane = new VBox(20);
        rightPane.setPadding(new Insets(30));
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPrefWidth(300);

        // Services header with icon
        HBox servicesHeaderBox = new HBox(10);
        servicesHeaderBox.setAlignment(Pos.CENTER);

        Label servicesHeader = new Label("Services");
        servicesHeader.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        servicesHeader.setTextFill(Color.DARKSLATEBLUE);

        servicesHeaderBox.getChildren().addAll(servicesHeader);
        servicesHeaderBox.setPadding(new Insets(0, 0, 20, 0));

        // Emergency service cards
        VBox servicesCards = new VBox(15);
        servicesCards.setAlignment(Pos.CENTER);

        Button bloodBankBtn = createDiseaseButton("Blood Bank", "#e74c3c", this::openBloodBank);
        Button milkBankBtn = createDiseaseButton("Milk Bank", "#3498db", this::openMilkBank);
        Button organDonationBtn = createDiseaseButton("Organ Donation", "#2ecc71", this::openOrganDonation);
        Button ambulanceBtn = createDiseaseButton("Fast Ambulance", "#f39c12", this::openAmbulance);

        // Add pulse animation to emergency buttons
        addPulseAnimation(bloodBankBtn);
        addPulseAnimation(milkBankBtn);
        addPulseAnimation(organDonationBtn);
        addPulseAnimation(ambulanceBtn);

        servicesCards.getChildren().addAll(bloodBankBtn, milkBankBtn, organDonationBtn, ambulanceBtn);
        rightPane.getChildren().addAll(servicesHeaderBox, servicesCards);
        return rightPane;
    }

    private Button createDiseaseButton(String serviceName, String color, Runnable action) {
        StackPane card = new StackPane();
        card.setPrefSize(300, 80);
        card.setStyle("-fx-background-radius: 15; -fx-border-radius: 15;");
        card.setEffect(new DropShadow(10, Color.valueOf(color).darker()));

        // Background with semi-transparent white
        Rectangle bg = new Rectangle(300, 80);
        bg.setArcWidth(15);
        bg.setArcHeight(15);
        bg.setFill(Color.WHITE.deriveColor(0, 1, 1, 0.8));
        bg.setStroke(Color.valueOf(color));
        bg.setStrokeWidth(2);

        // Service icon
        // ImageView icon = new ImageView();
        // try {
        // icon.setImage(new Image(getClass().getResourceAsStream("/images/icons/" +
        // iconName)));
        // icon.setFitWidth(40);
        // icon.setFitHeight(40);
        // } catch (Exception e) {
        // System.out.println("Icon not found: " + iconName);
        // }
        // StackPane.setAlignment( Pos.CENTER_LEFT);
        // StackPane.setMargin( new Insets(0, 0, 0, 20));

        // Service name
        Label nameLabel = new Label(serviceName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.valueOf(color).darker());

        // Glow effect on hover
        InnerShadow glow = new InnerShadow(15, Color.valueOf(color));
        // glow.setInputBlendMode(BlendMode.SCREEN);

        // Animation
        card.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), card);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            bg.setEffect(glow);
        });

        card.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), card);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            bg.setEffect(null);
        });

        card.getChildren().addAll(bg, nameLabel);

        Button button = new Button();
        button.setGraphic(card);
        button.setStyle("-fx-background-color: transparent;");
        button.setOnAction(e -> action.run());

        return button;
    }

    private void addPulseAnimation(Button button) {
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1.5), button);
        pulse.setFromX(1);
        pulse.setFromY(1);
        pulse.setToX(1.03);
        pulse.setToY(1.03);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(ScaleTransition.INDEFINITE);
        pulse.play();
    }

    private Button createSidebarButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;");

        // Animation
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-size: 14px;");
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), button);
            tt.setByX(10);
            tt.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;");
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), button);
            tt.setByX(-10);
            tt.play();
        });

        button.setOnAction(e -> action.run());
        return button;
    }

    private void toggleSidebar() {
        if (sidebarVisible) {
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), root.getLeft());
            slideOut.setByX(-250);
            slideOut.setOnFinished(e -> root.setLeft(null));
            slideOut.play();
        } else {
            root.setLeft(root.getLeft()); // Reset position if needed
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), root.getLeft());
            slideIn.setFromX(-250);
            slideIn.setToX(0);
            slideIn.play();
        }
        sidebarVisible = !sidebarVisible;
    }

    public void openSearchPage() {
        SearchPage c2w_ai_searchPage = new SearchPage();
        c2w_ai_searchPage.setC2w_ai_stage(homestage);

        double width = homestage.getWidth();
        double height = homestage.getHeight();

        c2w_ai_searchPageScene = new Scene(c2w_ai_searchPage.getView(this::backToHomePage), width, height);
        c2w_ai_searchPageScene.setFill(Color.TRANSPARENT);

        homestage.setScene(c2w_ai_searchPageScene);
    }

    private void openSchemesPage(String disease) {
        DiseaseSchemesPage schemePage = new DiseaseSchemesPage(disease, homestage);
        Scene scene = new Scene(schemePage.getLayout(), 1540, 940);
        homestage.setScene(scene);
    }

    private void openSchemeDetails(String schemeName) {
        SchemeDetailsPage schemeDetails = new SchemeDetailsPage(schemeName, homestage);
        Scene scene = new Scene(schemeDetails.getLayout(), 1540, 940);
        homestage.setScene(scene);
    }

    private void openBloodBank() {
        BloodDonation bloodPage = new BloodDonation();
        bloodPage.setPrimaryStage(homestage);
        bloodPage.start(homestage);
    }

    private void openMilkBank() {
        MilkBank milkPage = new MilkBank();
        milkPage.setPrimaryStage(homestage);
        milkPage.start(homestage);
    }

    private void openOrganDonation() {
        organMain organPage = new organMain();
        organPage.start(homestage);
    }

    private void openAmbulance() {
        AmbulanceAvailability ambulance = new AmbulanceAvailability();
        ambulance.showAmbulanceScene(homestage);
    }

    public void loadHome() {
        if (existingHomePage != null) {
            existingHomePage.setPrimaryStage(primaryStage);
            Pane newHomePane = existingHomePage.createHomePage(this::handleBackButton);
            Scene newScene = new Scene(newHomePane, 1540, 940);
            primaryStage.setScene(newScene);
        } else {
            System.out.println("existingHomePage is null");
        }
    }

    private void loadAbout() {
        AboutUs page2 = new AboutUs();
        page2.setP2Stage(homestage);
        page2Scene = new Scene(page2.createScene(this::handleBackButton), 1540, 940);
        page2.setP2Scene(page2Scene);
        homestage.setScene(page2Scene);
    }

    public void loadPatients() {
        PatientPage page3 = new PatientPage(homestage, this::backToHomePage);
        Scene page3Scene = page3.createScene();
        homestage.setScene(page3Scene);

    }

    private void loadHelp() {
        HelpPage page4 = new HelpPage();
        page4.setP2Stage(homestage);
        page4Scene = new Scene(page4.createScene(this::handleBackButton), 1540, 940);
        page4.setP2Scene(page4Scene);
        homestage.setScene(page4Scene);
    }

    private void handleLogout() {
        LogoutPage page5 = new LogoutPage();
        page5.setP2Stage(homestage);
        page5Scene = new Scene(page5.createScene(this::handleBackButton), 1540, 940);
        page5.setP2Scene(page5Scene);
        homestage.setScene(page5Scene);
    }

    private void handleFaq() {
        FAQContainer faqPage = new FAQContainer();
        faqPage.setP2Stage(homestage);
        faqPage.setP2Scene(homeScene); // Set the home scene for back navigation
        Scene faqScene = new Scene(faqPage.createScene(this::handleBackButton), 1540, 940);
        homestage.setScene(faqScene);
    }

    private void handleMyForm() {
        MyForms formsPage = new MyForms();
        formsPage.setP2Stage(homestage);
        formsPage.setP2Scene(homeScene);

        // Create fresh forms scene
        BorderPane formsRoot = formsPage.createScene(() -> {
            homestage.setScene(homeScene);
        });

        Scene formsScene = new Scene(formsRoot, 1540, 940);
        homestage.setScene(formsScene);
    }

    private void handleBackButton() {
        homestage.setScene(homeScene);
    }

    public void backToHomePage() {
        homestage.setScene(homeScene);
    }
}
