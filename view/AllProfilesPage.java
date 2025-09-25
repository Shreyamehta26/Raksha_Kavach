package com.raksha_kavach.view;

import com.google.gson.*;
import com.raksha_kavach.Controller.PatientPageController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AllProfilesPage {

    private Stage stage;

    public AllProfilesPage(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #e3f2fd;");

        String response = PatientPageController.readUsersFromFirestore();

        if (response.startsWith("Success")) {
            JsonObject json = JsonParser.parseString(response.replace("Success:\n", "")).getAsJsonObject();
            JsonArray documents = json.getAsJsonArray("documents");

            for (JsonElement docElem : documents) {
                JsonObject doc = docElem.getAsJsonObject();
                JsonObject fields = doc.getAsJsonObject("fields");

                VBox profileBox = new VBox(5);
                profileBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 15px; -fx-border-color: #90caf9;");
                profileBox.setMaxWidth(800);

                profileBox.getChildren().add(new Text("Full Name: " + getField(fields, "fullName")));
                profileBox.getChildren().add(new Text("Username: " + getField(fields, "username")));
                profileBox.getChildren().add(new Text("Email: " + getField(fields, "email")));
                profileBox.getChildren().add(new Text("Contact: " + getField(fields, "contact")));
                profileBox.getChildren().add(new Text("Disease: " + getField(fields, "disease")));
                profileBox.getChildren().add(new Text("Blood Group: " + getField(fields, "bloodGroup")));
                profileBox.getChildren().add(new Text("Hemoglobin: " + getField(fields, "hemoglobin")));
                profileBox.getChildren().add(new Text("Gender: " + getField(fields, "gender")));
                profileBox.getChildren().add(new Text("DOB: " + getField(fields, "dob")));
                profileBox.getChildren().add(new Text("Age: " + getField(fields, "age")));
                profileBox.getChildren().add(new Text("Address: " + getField(fields, "address")));

                layout.getChildren().add(profileBox);
            }
        } else {
            layout.getChildren().add(new Text("Error fetching profiles: " + response));
        }

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        return new Scene(scrollPane, 1540, 940);
    }

    private String getField(JsonObject fields, String key) {
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
}
