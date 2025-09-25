package com.raksha_kavach.view;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.raksha_kavach.Controller.PatientPageController;
import com.raksha_kavach.view.CurrentUser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class profile1 {

    private final Stage stage;
    private final CurrentUser currentUser;
    private final Runnable onBackAction;
    private static final String BUCKET = "crudop-75477.firebasestorage.app";

    // Updated bucket format

    // Color constants
    private static final String PRIMARY_COLOR = "#3498db";
    private static final String SECONDARY_COLOR = "#2c3e50";
    private static final String BACKGROUND_COLOR = "#f8f9fa";
    private static final String CARD_COLOR = "#ffffff";
    private static final String TEXT_COLOR = "#2c3e50";
    private static final String LABEL_COLOR = "#3498db";

    public profile1(Stage stage, Runnable onBackAction) {
        this.stage = stage;
        this.currentUser = CurrentUser.getInstance();
        this.onBackAction = onBackAction;
    }

    public Scene createScene() {
        VBox root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Header
        Text title = new Text("🩺 Patient Profile");
        title.setStyle("-fx-font-size: 32px; -fx-fill: " + SECONDARY_COLOR + "; " +
                "-fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
        HBox header = new HBox(title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));

        // Scrollable content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox profileBox = new VBox(20);
        profileBox.setPadding(new Insets(30));
        profileBox.setStyle("-fx-background-color: " + CARD_COLOR + "; " +
                "-fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 15, 0, 0, 0);");

        // Profile Image
        ImageView profileImageView = new ImageView();
        profileImageView.setFitWidth(150);
        profileImageView.setFitHeight(150);
        profileImageView.setPreserveRatio(true);
        profileImageView.setStyle("-fx-border-color: " + PRIMARY_COLOR + "; " +
                "-fx-border-width: 3; -fx-border-radius: 75; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Add hover effect to profile image
        profileImageView.setOnMouseEntered(e -> {
            profileImageView.setStyle(profileImageView.getStyle() +
                    "-fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.5), 15, 0, 0, 0);");
        });
        profileImageView.setOnMouseExited(e -> {
            profileImageView.setStyle("-fx-border-color: " + PRIMARY_COLOR + "; " +
                    "-fx-border-width: 3; -fx-border-radius: 75; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        });

        // Load profile image from Firebase Storage
        loadProfileImage(profileImageView);

        HBox imageBox = new HBox(profileImageView);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(0, 0, 20, 0));

        // Profile fields
        Text fullName = new Text();
        Text username = new Text();
        Text email = new Text();
        Text contact = new Text();
        Text aadhar = new Text();
        Text pan = new Text();
        Text disease = new Text();
        Text bloodGroup = new Text();
        Text hemoglobin = new Text();
        Text gender = new Text();
        Text dob = new Text();
        Text age = new Text();
        Text address = new Text();

        // Set font for all text fields
        Font textFont = Font.font("Segoe UI", 14);
        fullName.setFont(textFont);
        username.setFont(textFont);
        email.setFont(textFont);
        contact.setFont(textFont);
        aadhar.setFont(textFont);
        pan.setFont(textFont);
        disease.setFont(textFont);
        bloodGroup.setFont(textFont);
        hemoglobin.setFont(textFont);
        gender.setFont(textFont);
        dob.setFont(textFont);
        age.setFont(textFont);
        address.setFont(textFont);

        profileBox.getChildren().addAll(
                imageBox,
                section("Full Name", fullName),
                section("Username", username),
                section("Email", email),
                section("Contact Number", contact),
                section("Aadhar Card", aadhar),
                section("PAN Card", pan),
                section("Disease", disease),
                section("Blood Group", bloodGroup),
                section("Hemoglobin Level", hemoglobin),
                section("Gender", gender),
                section("Date of Birth", dob),
                section("Age", age),
                section("Address", address));

        scrollPane.setContent(profileBox);

        // Back Button
        Button backButton = new Button("← Back to Dashboard");
        backButton.setStyle(
                "-fx-background-color: linear-gradient(to right, " + PRIMARY_COLOR + ", " + SECONDARY_COLOR + "); " +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; " +
                        "-fx-background-radius: 5; -fx-padding: 10 25 10 25; " +
                        "-fx-font-family: 'Segoe UI';");

        // Button hover effect
        backButton.setOnMouseEntered(e -> backButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #2980b9, #34495e); " +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; " +
                        "-fx-background-radius: 5; -fx-padding: 10 25 10 25; " +
                        "-fx-font-family: 'Segoe UI'; -fx-cursor: hand;"));
        backButton.setOnMouseExited(e -> backButton.setStyle(
                "-fx-background-color: linear-gradient(to right, " + PRIMARY_COLOR + ", " + SECONDARY_COLOR + "); " +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; " +
                        "-fx-background-radius: 5; -fx-padding: 10 25 10 25; " +
                        "-fx-font-family: 'Segoe UI';"));
        // backButton.setOnAction(e -> handleBackAction());
        backButton.setOnAction(e -> goBack());

        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // Add all to root
        root.getChildren().addAll(header, scrollPane, buttonBox);

        // Load data
        loadPatientProfile(fullName, username, email, contact, aadhar, pan, disease,
                bloodGroup, hemoglobin, gender, dob, age, address);

        return new Scene(root, 1540, 940);
    }

    private void loadProfileImage(ImageView imageView) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String imagePath = currentUser.getProfileImagePath();

                if (imagePath == null || imagePath.isEmpty()) {
                    // Try default path if no specific path stored
                    String basePath = "users/" + currentUser.getUsername() + "/profile";
                    String[] extensions = { ".png", ".jpg", ".jpeg", ".gif" };
                    boolean found = false;

                    for (String ext : extensions) {
                        try {
                            String fullPath = basePath + ext;
                            loadImageFromPath(fullPath, imageView);
                            found = true;
                            break;
                        } catch (Exception e) {
                            // Try next extension
                        }
                    }

                    if (!found) {
                        setDefaultImage(imageView);
                    }
                } else {
                    // Use the stored path
                    loadImageFromPath(imagePath, imageView);
                }
            } catch (Exception e) {
                setDefaultImage(imageView);
            }
        });
        executor.shutdown();
    }

    private void loadImageFromPath(String path, ImageView imageView) {
        try {
            String encodedPath = path.replace("/", "%2f");
            String urlstr = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET +
                    "/o/" + encodedPath + "?alt=media&t=" + System.currentTimeMillis();

            Image image = new Image(urlstr, true);
            image.progressProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() == 1.0) {
                    javafx.application.Platform.runLater(() -> {
                        if (!image.isError()) {
                            imageView.setImage(image);
                        } else {
                            setDefaultImage(imageView);
                        }
                    });
                }
            });
        } catch (Exception e) {
            setDefaultImage(imageView);
        }
    }

    private void setDefaultImage(ImageView imageView) {
        javafx.application.Platform.runLater(() -> {
            try {
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/default-profile.png")));
            } catch (Exception e) {
                // If default image not found, set empty
                imageView.setImage(null);
            }
        });
    }

    private void loadPatientProfile(Text fullName, Text username, Text email, Text contact,
            Text aadhar, Text pan, Text disease, Text bloodGroup,
            Text hemoglobin, Text gender, Text dob, Text age, Text address) {

        if (currentUser.getFullName() != null && !currentUser.getFullName().isEmpty()) {
            fullName.setText(currentUser.getFullName());
            username.setText(currentUser.getUsername());
            email.setText(currentUser.getEmail());
            contact.setText(currentUser.getContact());
            aadhar.setText(currentUser.getAadhar());
            pan.setText(currentUser.getPan());
            disease.setText(currentUser.getDisease());
            bloodGroup.setText(currentUser.getBloodGroup());
            hemoglobin.setText(currentUser.getHemoglobin());
            gender.setText(currentUser.getGender());
            dob.setText(currentUser.getDob());
            age.setText(currentUser.getAge());
            address.setText(currentUser.getAddress());
            return;
        }

        String docId = currentUser.getDocId();
        if (docId == null || docId.isEmpty()) {
            fullName.setText("❌ No patient data available.");
            return;
        }

        String response = PatientPageController.getPatientByDocId(docId);
        if (response != null && response.startsWith("Success")) {
            try {
                JsonObject json = JsonParser.parseString(response.replace("Success:\n", "")).getAsJsonObject();
                JsonObject fields = json.getAsJsonObject("fields");

                fullName.setText(getFieldValue(fields, "fullName"));
                username.setText(getFieldValue(fields, "username"));
                email.setText(getFieldValue(fields, "email"));
                contact.setText(getFieldValue(fields, "contact"));
                aadhar.setText(getFieldValue(fields, "aadhar"));
                pan.setText(getFieldValue(fields, "pan"));
                disease.setText(getFieldValue(fields, "disease"));
                bloodGroup.setText(getFieldValue(fields, "bloodGroup"));
                hemoglobin.setText(getFieldValue(fields, "hemoglobin"));
                gender.setText(getFieldValue(fields, "gender"));
                dob.setText(getFieldValue(fields, "dob"));
                age.setText(getFieldValue(fields, "age"));
                address.setText(getFieldValue(fields, "address"));
            } catch (Exception e) {
                fullName.setText("❌ Error parsing patient data");
                e.printStackTrace();
            }
        } else {
            fullName.setText("❌ Error loading profile: " + (response != null ? response : "No response"));
        }
    }

    private VBox section(String labelText, Text valueText) {
        Label label = new Label(labelText + ":");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + LABEL_COLOR + "; " +
                "-fx-font-family: 'Segoe UI';");

        valueText.setStyle("-fx-font-size: 14px; -fx-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Segoe UI';");

        VBox vbox = new VBox(4, label, valueText);
        vbox.setPadding(new Insets(8, 0, 8, 15));
        vbox.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));
        return vbox;
    }

    private String getFieldValue(JsonObject fields, String key) {
        if (!fields.has(key))
            return "";
        JsonObject field = fields.getAsJsonObject(key);
        if (field.has("stringValue")) {
            return field.get("stringValue").getAsString();
        } else if (field.has("integerValue")) {
            return field.get("integerValue").getAsString();
        }
        return "";
    }

    // private void handleBackAction() {
    // if (onBackAction != null) {
    // onBackAction.run();
    // } else {
    // HomePage homePage = new HomePage();
    // homePage.setHomestage(stage);
    // stage.setScene(new Scene(homePage.createHomePage(null), 1540, 940));
    // }
    // }

    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.setHomestage(stage);
        Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
        stage.setScene(homeScene);
    }
}