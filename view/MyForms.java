package com.raksha_kavach.view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.raksha_kavach.Controller.donorBloodController;
import com.raksha_kavach.Controller.organController;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Map;

public class MyForms {
    private Scene previousScene;
    private Stage primaryStage;

    // Setter methods for stage and scene
    public void setP2Stage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setP2Scene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    /**
     * Creates the main My Forms scene
     */
    public BorderPane createScene(Runnable backAction) {
        BorderPane root = new BorderPane();

        // Create gradient background
        javafx.scene.paint.Stop[] stops = {
                new javafx.scene.paint.Stop(0, Color.web("#2C3E50")),
                new javafx.scene.paint.Stop(1, Color.web("#4CA1AF"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        root.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Show loading state initially
        VBox loadingBox = new VBox(20);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setPadding(new Insets(40));

        Label loadingLabel = new Label("Loading your forms...");
        loadingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        loadingLabel.setTextFill(Color.WHITE);

        ProgressIndicator progress = new ProgressIndicator();
        loadingBox.getChildren().addAll(loadingLabel, progress);
        root.setCenter(loadingBox);

        // Load forms in background
        loadUserForms(root, backAction);

        return root;
    }

    /**
     * Loads user forms from Firestore
     */
    private void loadUserForms(BorderPane root, Runnable backAction) {
        new Thread(() -> {
            String userEmail = getCurrentUserEmail();
            System.out.println("Fetching forms for: " + userEmail);

            String bloodResponse = donorBloodController.getUserFormsFromFirestore(userEmail);

            // Merge documents from both responses
            JsonArray combinedForms = new JsonArray();
            combinedForms.addAll(extractDocuments(bloodResponse));

            JsonObject finalResponse = new JsonObject();
            finalResponse.add("documents", combinedForms);
            String response = finalResponse.toString();
            System.out.println("Combined Firestore response:\n" + response);

            Platform.runLater(() -> {
                if (response == null || response.trim().equalsIgnoreCase("Success")) {
                    showErrorScreen(root, "No form data found for this user.", backAction);
                } else if (response.contains("Error")) {
                    showErrorScreen(root, "Failed to load forms: " + response, backAction);
                } else {
                    try {
                        VBox formsContainer = createFormsContainer(response, backAction);
                        root.setCenter(formsContainer);
                    } catch (Exception e) {
                        showErrorScreen(root, "Error parsing form data", backAction);
                        e.printStackTrace(); // helpful for debugging
                    }
                }

            });
        }).start();
    }

    private JsonArray extractDocuments(String jsonResponse) {
        try {
            if (jsonResponse == null || jsonResponse.trim().equalsIgnoreCase("Success")) {
                return new JsonArray(); // treat 'Success' as empty response
            }
            JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
            return root.getAsJsonArray("documents");
        } catch (Exception e) {
            System.out.println("Error extracting documents: " + e.getMessage());
            return new JsonArray(); // fallback to empty list
        }
    }

    /**
     * Creates the forms list container
     */
    private VBox createFormsContainer(String jsonResponse, Runnable backAction) {
        VBox container = new VBox(20);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.TOP_CENTER);

        // Title
        Label title = new Label("MY SUBMITTED FORMS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.BLACK);
        container.getChildren().add(title);

        // Parse JSON response
        JsonObject responseObj = JsonParser.parseString(jsonResponse).getAsJsonObject();

        JsonArray documents = responseObj.getAsJsonArray("documents");

        if (documents.size() == 0) {
            Label noFormsLabel = new Label("You haven't submitted any forms yet");
            noFormsLabel.setFont(Font.font(16));
            noFormsLabel.setTextFill(Color.WHITE);
            container.getChildren().add(noFormsLabel);
        } else {
            // Create scrollable list of forms
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

            VBox formsList = new VBox(15);
            formsList.setPadding(new Insets(10));

            for (JsonElement doc : documents) {
                JsonObject document = doc.getAsJsonObject();
                JsonObject fields = document.getAsJsonObject("fields");

                String formType = getFieldValue(fields, "formType");
                String name = getFieldValue(fields, "nameField");
                String date = getFieldValue(fields, "timestamp");
                String shortDate = date != null ? date.substring(0, 10) : "Unknown date";

                Button formBtn = createFormButton(formType, name, shortDate, document);
                if (formBtn != null) {
                    formsList.getChildren().add(formBtn); // Only add if it's valid
                }

            }

            scrollPane.setContent(formsList);
            container.getChildren().add(scrollPane);
        }

        // Back button
        Button backBtn = new Button("Back to Home");
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");
        backBtn.setOnAction(e -> goBack()); // ✅ uses actual method
        container.getChildren().add(backBtn);

        return container;
    }

    /**
     * Creates a styled form button
     */
    private Button createFormButton(String formType, String name, String date, JsonObject formData) {
        Button button = new Button();
        button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 5;");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);

        if ((formType == null || formType.trim().isEmpty()) &&
                (name == null || name.trim().isEmpty()) &&
                (date == null || date.trim().isEmpty())) {
            return null; // Skip rendering for empty forms
        }

        // Create form info layout
        HBox content = new HBox(20);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(15));

        // Form details
        VBox details = new VBox(5);
        Label typeLabel = new Label(formType != null ? formType.replace("_", " ").toUpperCase() : "FORM");
        typeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Set color based on form type
        if (formType != null && formType.equalsIgnoreCase("BLOOD DONOR")) {
            typeLabel.setTextFill(Color.web("#00108bff")); // Dark blue for BLOOD DONOR
        } else {
            typeLabel.setTextFill(Color.BLACK);
        }

        Label nameLabel = new Label("Name: " + (name != null ? name : "N/A"));
        nameLabel.setFont(Font.font(14));
        nameLabel.setTextFill(Color.BLACK);

        Label dateLabel = new Label("Submitted: " + date);
        dateLabel.setFont(Font.font(12));
        dateLabel.setTextFill(Color.BLACK); // Changed from #DDDDDD to BLACK

        details.getChildren().addAll(typeLabel, nameLabel, dateLabel);
        content.getChildren().addAll(details);

        button.setGraphic(content);
        button.setOnAction(e -> showFormDetails(formData));

        return button;
    }

    /**
     * Shows detailed view of a single form
     */
    private void showFormDetails(JsonObject formData) {
        BorderPane detailsRoot = new BorderPane();

        // Background gradient
        javafx.scene.paint.Stop[] stops = {
                new javafx.scene.paint.Stop(0, Color.web("#2C3E50")),
                new javafx.scene.paint.Stop(1, Color.web("#4CA1AF"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        detailsRoot.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Back button
        Button backBtn = new Button("Back to My Forms");
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");
        backBtn.setOnAction(e -> {
            MyForms formsPage = new MyForms();
            formsPage.setP2Stage(primaryStage);
            formsPage.setP2Scene(previousScene);

            BorderPane formsRoot = formsPage.createScene(() -> {
                primaryStage.setScene(previousScene);
            });

            Scene formsScene = new Scene(formsRoot, 1540, 940);
            primaryStage.setScene(formsScene);
        });

        HBox topBar = new HBox(backBtn);
        topBar.setPadding(new Insets(15));
        detailsRoot.setTop(topBar);

        // Form details content
        JsonObject fields = formData.getAsJsonObject("fields");
        String formType = getFieldValue(fields, "formType");

        VBox detailsContainer = new VBox(20);
        detailsContainer.setPadding(new Insets(30));
        detailsContainer.setAlignment(Pos.TOP_CENTER);
        detailsContainer.setMaxWidth(800);

        // Form title - apply dark blue for BLOOD DONOR

        // Add this to your scene and verify it appears dark blue
        Label title = new Label(formType != null ? formType.replace("_", " ").toUpperCase() : "FORM DETAILS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        if (formType != null && formType.equalsIgnoreCase("BLOOD DONOR")) {
            title.setTextFill(Color.WHITE);
        } else {
            title.setTextFill(Color.WHITE);
        }
        detailsContainer.getChildren().add(title);

        // Form fields
        GridPane fieldsGrid = new GridPane();
        fieldsGrid.setHgap(20);
        fieldsGrid.setVgap(15);
        fieldsGrid.setPadding(new Insets(20));
        fieldsGrid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 10;");

        int row = 0;
        for (Map.Entry<String, JsonElement> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            if (fieldName.equals("formType"))
                continue;

            String fieldValue = entry.getValue().getAsJsonObject().entrySet().iterator().next().getValue()
                    .getAsString();

            Label nameLabel = new Label(fieldName + ":");
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            nameLabel.setTextFill(Color.WHITE);

            Label valueLabel = new Label(fieldValue);
            valueLabel.setFont(Font.font(14));
            valueLabel.setTextFill(Color.BLACK); // All other text in black
            valueLabel.setWrapText(true);

            fieldsGrid.add(nameLabel, 0, row);
            fieldsGrid.add(valueLabel, 1, row);
            row++;
        }

        detailsContainer.getChildren().add(fieldsGrid);
        detailsRoot.setCenter(detailsContainer);

        Scene detailsScene = new Scene(detailsRoot, 1540, 940);
        primaryStage.setScene(detailsScene);
    }

    /**
     * Shows error screen when forms can't be loaded
     */
    private void showErrorScreen(BorderPane root, String errorMessage, Runnable backAction) {
        VBox errorBox = new VBox(30);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(40));

        Label errorLabel = new Label(errorMessage);
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        errorLabel.setTextFill(Color.BLACK);

        Button retryBtn = createStyledButton("Retry", Color.web("#e31515ff"));
        retryBtn.setOnAction(e -> loadUserForms(root, backAction));

        Button backBtn = createStyledButton("Back to Home", Color.web("#FFC107"));
        backBtn.setOnAction(e -> backAction.run());

        errorBox.getChildren().addAll(errorLabel, retryBtn, backBtn);
        root.setCenter(errorBox);
    }

    // Helper methods
    private String getFieldValue(JsonObject fields, String fieldName) {
        try {
            if (!fields.has(fieldName))
                return "BLOOD DONOR";

            JsonObject valueObj = fields.getAsJsonObject(fieldName);
            if (valueObj.has("stringValue")) {
                return valueObj.get("stringValue").getAsString();
            } else if (valueObj.has("timestampValue")) {
                return valueObj.get("timestampValue").getAsString().substring(0, 10);
            } else {
                return "Unsupported format";
            }
        } catch (Exception e) {
            return "Parsing error";
        }
    }

    private Button createStyledButton(String text, Color color) {
        Button button = new Button(text);
        button.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20;",
                color.toString().replace("0x", "#")));
        button.setEffect(new javafx.scene.effect.DropShadow(5, Color.gray(0.5)));
        return button;
    }

    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.setHomestage(primaryStage);
        Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
        primaryStage.setScene(homeScene);
    }

    // Implement this method to get current user's email
    private String getCurrentUserEmail() {
        // TODO: Replace with your actual user identification logic
        // This could come from login session, preferences, etc.
        return "user@example.com";
    }
}