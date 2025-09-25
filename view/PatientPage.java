package com.raksha_kavach.view;

import com.raksha_kavach.Controller.PatientPageController;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PatientPage {

    // private static final String STORAGE_PATH = "images/downloads.png";
    private static final String BUCKET = "crudop-75477.firebasestorage.app";
    // Remove these:
    private static final String STORAGE_PATH = "images/downloads.png";
    // private static final String LOCAL_TEMP = "C:/Users/DELL/Downloads/Image.jpg";

    // Add these instead:
    private String getUserImagePath() {
        return "users/" + currentUser.getUsername() + "/profile" +
                (currentUser.getDocId() != null ? "_" + currentUser.getDocId() : "");
    }

    private String getTempFilePath() {
        return System.getProperty("java.io.tmpdir") + "temp_firebase_image.jpg";
    }

    private static final String LOCAL_TEMP = "C:/Users/DELL/Downloads/Image.jpg";;
    private ImageView imageView = new ImageView();
    private final Stage stage;
    private final Runnable onBack;
    private final CurrentUser currentUser = CurrentUser.getInstance();

    public PatientPage(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
    }

    private void backToHomePage() {
        if (onBack != null) {
            onBack.run();
        }
    }

    public Scene createScene() {
        Button uploadBtn = new Button("Upload");
        Button getBtn = new Button("Get");
        Button deleteBtn = new Button("Delete");

        uploadBtn.setOnAction(e -> uploadImage(stage)); // Pass the stage to uploadImage
        getBtn.setOnAction(e -> getImage());
        deleteBtn.setOnAction(e -> deleteImage());
        // Add this after creating the buttons
        uploadBtn.setStyle(
                "-fx-background-color: #4CAF50;" + // green
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 6 14;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-cursor: hand;");

        getBtn.setStyle(
                "-fx-background-color: #2196F3;" + // blue
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 6 14;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-cursor: hand;");

        deleteBtn.setStyle(
                "-fx-background-color: #f44336;" + // red
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 6 14;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-cursor: hand;");

        imageView.setFitWidth(180);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0); " +
                "-fx-border-color: #ccc; -fx-border-width: 1px; -fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; -fx-padding: 10px;");
        StackPane imageBox = new StackPane(imageView);
        imageBox.setPadding(new Insets(10));
        imageBox.setAlignment(Pos.CENTER); // center image inside the box
        imageBox.setMaxWidth(220); // ✅ set maximum width

        imageBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; " +
                "-fx-border-radius: 10px; -fx-background-radius: 10px;");

        HBox imageButtonBox = new HBox(10, uploadBtn, getBtn, deleteBtn);
        imageButtonBox.setAlignment(Pos.BASELINE_LEFT);
        imageButtonBox.setPadding(new Insets(0, 0, 0, 10));

        VBox root = new VBox();
        root.setStyle("-fx-background-color: #e1fafd;");
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);

        VBox formContainer = new VBox(20);
        formContainer.setStyle(
                "-fx-background-color: white; -fx-padding: 40; -fx-border-radius: 10; -fx-background-radius: 10;");
        formContainer.setMaxWidth(800);
        formContainer.setAlignment(Pos.TOP_LEFT);

        Text title = new Text("Patient Registration");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Full Name & Username");
        nameLabel.setStyle("-fx-font-weight: bold;");
        TextField fullName = new TextField(), username = new TextField();
        username.setPromptText("Username");
        fullName.setPromptText("Full Name");

        Label genderLabel = new Label("Gender");
        genderLabel.setStyle("-fx-font-weight: bold;");
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        RadioButton preferNotSay = new RadioButton("Prefer not to say");
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        preferNotSay.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(20, male, female, preferNotSay);

        Label contactLabel = new Label("Email & Contact Number");
        contactLabel.setStyle("-fx-font-weight: bold;");
        TextField email = new TextField(), contact = new TextField();
        email.setPromptText("Email");
        contact.setPromptText("Contact Number");

        Label dobLabel = new Label("DOB & Age");
        dobLabel.setStyle("-fx-font-weight: bold;");
        DatePicker dob = new DatePicker();
        TextField age = new TextField();
        dob.setPromptText("Date of Birth");
        age.setPromptText("Age");

        Label addressLabel = new Label("Address");
        addressLabel.setStyle("-fx-font-weight: bold;");
        TextField address = new TextField();
        address.setPromptText("Address");
        address.setMinHeight(60);

        Label idLabel = new Label("Aadhar Card & PanCard");
        idLabel.setStyle("-fx-font-weight: bold;");
        TextField aadhar = new TextField(), pan = new TextField();
        aadhar.setPromptText("Aadhar Card Number");
        pan.setPromptText("Pan Card Number");

        Label healthLabel = new Label("Disease, Blood Group & Hemoglobin");
        healthLabel.setStyle("-fx-font-weight: bold;");
        TextField disease = new TextField(), bloodGroup = new TextField(), hemoglobin = new TextField();
        disease.setPromptText("Disease");
        bloodGroup.setPromptText("Blood Group");
        hemoglobin.setPromptText("Hemoglobin Level");

        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font-weight: bold;");
        PasswordField password = new PasswordField(), confirm = new PasswordField();
        password.setPromptText("Password");
        confirm.setPromptText("Confirm Password");

        Button submit = new Button("Register");
        Button back = new Button("← Back");
        submit.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        back.setStyle("-fx-background-color: #ccc; -fx-font-weight: bold;");
        HBox buttonBox = new HBox(15, submit, back);
        Text feedback = new Text();
        feedback.setStyle("-fx-fill: red;");

        submit.setOnAction(e -> {
            feedback.setText("");

            try {
                if (fullName.getText().isEmpty())
                    throw new IllegalArgumentException("Full name is required");
                if (username.getText().isEmpty())
                    throw new IllegalArgumentException("Username is required");
                if (email.getText().isEmpty())
                    throw new IllegalArgumentException("Email is required");
                if (contact.getText().isEmpty())
                    throw new IllegalArgumentException("Contact number is required");
                if (aadhar.getText().isEmpty())
                    throw new IllegalArgumentException("Aadhar number is required");
                if (pan.getText().isEmpty())
                    throw new IllegalArgumentException("PAN number is required");
                if (disease.getText().isEmpty())
                    throw new IllegalArgumentException("Disease is required");
                if (bloodGroup.getText().isEmpty())
                    throw new IllegalArgumentException("Blood group is required");
                if (hemoglobin.getText().isEmpty())
                    throw new IllegalArgumentException("Hemoglobin level is required");
                if (password.getText().isEmpty())
                    throw new IllegalArgumentException("Password is required");
                if (!password.getText().equals(confirm.getText()))
                    throw new IllegalArgumentException("Passwords do not match");
                if (dob.getValue() == null)
                    throw new IllegalArgumentException("Date of birth is required");
                if (age.getText().isEmpty())
                    throw new IllegalArgumentException("Age is required");
                if (address.getText().isEmpty())
                    throw new IllegalArgumentException("Address is required");

                // Get gender
                String gender = "Unknown";
                if (male.isSelected())
                    gender = "Male";
                else if (female.isSelected())
                    gender = "Female";
                else if (preferNotSay.isSelected())
                    gender = "Prefer not to say";

                // Save to CurrentUser
                currentUser.setFullName(fullName.getText());
                currentUser.setUsername(username.getText());
                currentUser.setEmail(email.getText());
                currentUser.setContact(contact.getText());
                currentUser.setAadhar(aadhar.getText());
                currentUser.setPan(pan.getText());
                currentUser.setDisease(disease.getText());
                currentUser.setBloodGroup(bloodGroup.getText());
                currentUser.setHemoglobin(hemoglobin.getText());
                currentUser.setPassword(password.getText());
                currentUser.setDob(dob.getValue().toString());
                currentUser.setAge(age.getText());
                currentUser.setAddress(address.getText());
                currentUser.setGender(gender);

                // Call Firestore
                String resp = PatientPageController.addUserToFirestore(
                        currentUser.getFullName(),
                        currentUser.getUsername(),
                        currentUser.getEmail(),
                        currentUser.getContact(),
                        currentUser.getAadhar(),
                        currentUser.getPan(),
                        currentUser.getPassword(),
                        currentUser.getDisease(),
                        currentUser.getBloodGroup(),
                        currentUser.getHemoglobin(),
                        currentUser.getGender(),
                        currentUser.getDob(),
                        currentUser.getAge(),
                        currentUser.getAddress());

                if (resp != null && resp.startsWith("Success:")) {
                    String docId = resp.split(":")[1].trim();
                    currentUser.setDocId(docId);

                    profile1 profilePage = new profile1(stage, this::backToHomePage);
                    stage.setScene(profilePage.createScene());
                } else {
                    feedback.setText("Registration failed: " + (resp != null ? resp : "No response from server"));
                }

            } catch (IllegalArgumentException ex) {
                feedback.setText("Validation error: " + ex.getMessage());
            } catch (Exception ex) {
                feedback.setText("System error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // back.setOnAction(e -> {
        // if (onBack != null) {
        // onBack.run();
        // }
        // });

        back.setOnAction(e -> goBack());

        // Add image controls at the top of the form
        formContainer.getChildren().addAll(
                imageBox,
                imageButtonBox,

                title,
                nameLabel, new HBox(15, fullName, username),
                genderLabel, genderBox,
                contactLabel, new HBox(15, email, contact),
                dobLabel, new HBox(15, dob, age),
                addressLabel, address,
                idLabel, new HBox(15, aadhar, pan),
                healthLabel, new HBox(15, disease, bloodGroup, hemoglobin),
                passLabel, new HBox(15, password, confirm),
                buttonBox,
                feedback);

        root.getChildren().add(formContainer);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #e1fafd;");

        return new Scene(scrollPane, 1540, 940);
    }

    private void uploadImage(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select image");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = chooser.showOpenDialog(stage);
        if (file == null)
            return;

        try {
            String extension = file.getName().substring(file.getName().lastIndexOf("."));
            String storagePath = "users/" + currentUser.getUsername() + "/profile" + extension;

            String contentType = determineContentType(file);
            String urlstr = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET +
                    "/o?uploadType=media&name=" + URLEncoder.encode(storagePath, "UTF-8");

            HttpURLConnection conn = (HttpURLConnection) new URL(urlstr).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", contentType);

            try (OutputStream os = conn.getOutputStream();
                    FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Upload successful to: " + storagePath);
                imageView.setImage(new Image(file.toURI().toString()));
                currentUser.setProfileImagePath(storagePath); // Store the path
            } else {
                System.out.println("Upload failed with code: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Upload Error", "Failed to upload image", ex.getMessage());
        }
    }

    private void getImage() {
        try {
            // Get user-specific path (without extension as we don't know it)
            String basePath = getUserImagePath();

            // Try common extensions
            String[] extensions = { ".png", ".jpg", ".jpeg", ".gif" };
            boolean found = false;

            for (String ext : extensions) {
                try {
                    String fullPath = basePath + ext;
                    String encodedPath = fullPath.replace("/", "%2f");

                    String urlstr = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET +
                            "/o/" + encodedPath + "?alt=media";

                    URL url = new URL(urlstr);
                    HttpURLConnection testConn = (HttpURLConnection) url.openConnection();
                    testConn.setRequestMethod("HEAD");
                    int responseCode = testConn.getResponseCode();
                    testConn.disconnect();

                    if (responseCode == 200) {
                        // Found the image
                        String tempPath = getTempFilePath();
                        try (InputStream in = url.openStream();
                                FileOutputStream out = new FileOutputStream(tempPath)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = in.read(buffer)) != -1) {
                                out.write(buffer, 0, bytesRead);
                            }
                        }

                        imageView.setImage(new Image(new FileInputStream(tempPath)));
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    // Try next extension
                }
            }

            if (!found) {
                showAlert("Image Not Found", "No profile image found",
                        "Please upload an image first");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load image", ex.getMessage());
        }
    }

    private String determineContentType(File file) {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".png"))
            return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg"))
            return "image/jpeg";
        if (name.endsWith(".gif"))
            return "image/gif";
        return "application/octet-stream";
    }

    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.setHomestage(stage);
        Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
        stage.setScene(homeScene);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void deleteImage() {
        try {
            String encodedPath = STORAGE_PATH.replace("/", "%2f");
            String urlstr = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET + "/o/" + encodedPath;
            HttpURLConnection conn = (HttpURLConnection) new URL(urlstr).openConnection();
            conn.setRequestMethod("DELETE");
            System.out.println("Delete response" + conn.getResponseCode());
            conn.disconnect();
            imageView.setImage(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}