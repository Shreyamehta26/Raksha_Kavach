package com.raksha_kavach.Controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.scene.control.Alert;

public class UserController {
    public boolean authenticateUser(String c2w_ai_userName, String c2w_ai_password) {
        try {
            // Firebase Web API key (replace with your own if needed)
            String c2w_ai_apiKey = "AIzaSyAfavdNAnc8xal1nozwK_vmCAVO0Dh_xD8";
            // String c2w_ai_apiKey = "<YOUR_FIREBASE_WEB_API_KEY>";
            URL c2w_ai_url = new URL(
                    "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + c2w_ai_apiKey);
            HttpURLConnection c2w_ai_conn = (HttpURLConnection) c2w_ai_url.openConnection();
            c2w_ai_conn.setRequestMethod("POST");
            c2w_ai_conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            c2w_ai_conn.setDoOutput(true);
            // Build the JSON request body
            JSONObject c2w_ai_jsonRequest = new JSONObject();
            c2w_ai_jsonRequest.put("email", c2w_ai_userName);
            c2w_ai_jsonRequest.put("password", c2w_ai_password);
            c2w_ai_jsonRequest.put("returnSecureToken", true);
            System.out.println(c2w_ai_jsonRequest.toString());
            // Send the JSON request to the server

            try (OutputStream c2w_ai_os = c2w_ai_conn.getOutputStream()) {
                byte[] input = c2w_ai_jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                c2w_ai_os.write(input, 0, input.length);
            }
            // Check the response code to determine if login was successful
            if (c2w_ai_conn.getResponseCode() == 200) {
                // Login successful
                return true;
            } else {
                // Login failed, show error alert
                showAlert("Error", "Failed to log in user.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Show alert if an exception occurs
            showAlert("Error", "Failed to log in user: " + e.getMessage());
            return false;
        }
    }

    public boolean handleSignup(String userName, String password) {
        if (userName.isEmpty() || password.isEmpty()) {
            showAlert("Error", "UserName and password cannot be empty.");
            return false; // Return false if email or password is empty
        }
        try {
            // Create a new user request with the provided email and password
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(userName)
                    .setPassword(password)
                    .setDisabled(false); // Enable the user after creation
            // Create the user in Firebase Authentication
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created user: " +
                    userRecord.getUid());
            showAlert("Success", "User created successfully.");
            return true; // Return true if signup was successful
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create user: " + e.getMessage());
            return false; // Return false if signup failed
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
