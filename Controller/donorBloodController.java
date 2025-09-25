package com.raksha_kavach.Controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class donorBloodController {

    static final String PROJECT_ID = "cricketinfo-5b8c4";
    static final String API_KEY = "AIzaSyAK-A5AhmcrIKvu5Fs_CivqtUKCbFfsCxs";

    // Get all forms for a specific user by email
    public static String getUserFormsFromFirestore(String userEmail) {
        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodPatient?key=%s",
                PROJECT_ID, API_KEY);

        // Note: For more precise filtering, you might need to use Firestore queries
        // This will return all forms and you'll filter client-side
        return sendRequest("GET", endpoint, null, null);
    }

    // ADD Patient
    public static String addUserToFirestore(
            String nameField,
            String emailField,
            String dobPicker,
            String bloodGroupField,
            String phoneField) {

        try {
            String endpoint = String.format(
                    "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodPatient?key=%s",
                    PROJECT_ID, API_KEY);

            // Properly formatted JSON payload with all required fields
            String payload = String.format("{\n" +
                    "  \"fields\": {\n" +
                    "    \"name\": {\"stringValue\": \"%s\"},\n" +
                    "    \"email\": {\"stringValue\": \"%s\"},\n" +
                    "    \"dob\": {\"stringValue\": \"%s\"},\n" +
                    "    \"bloodGroup\": {\"stringValue\": \"%s\"},\n" +
                    "    \"phone\": {\"stringValue\": \"%s\"},\n" +
                    "    \"formType\": {\"stringValue\": \"blood_donation\"},\n" +
                    "    \"timestamp\": {\"timestampValue\": \"%s\"}\n" +
                    "  }\n" +
                    "}",
                    escapeJson(nameField),
                    escapeJson(emailField),
                    escapeJson(dobPicker),
                    escapeJson(bloodGroupField),
                    escapeJson(phoneField),
                    new java.util.Date().toInstant().toString());

            return sendRequest("POST", endpoint, payload, null);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // READ All Patients
    public static String readUsersFromFirestore() {
        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodPatient?key=%s",
                PROJECT_ID, API_KEY);
        return sendRequest("GET", endpoint, null, null);
    }

    // UPDATE Patient by docId
    public static String updateUserToFirestore(
            String nameField,
            String emailField,
            String dobPicker,
            String bloodString,
            String phoneField) {

        String docId = nameField;
        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodPatient1/%s?key=%s",
                PROJECT_ID, docId, API_KEY);

        String payload = String.format("""
                {
                  "fields": {
                    "nameField": { "stringValue": "%s" },
                    "emailField": { "stringValue": "%s" },
                    "dobPicker": { "stringValue": "%s" },
                    "bloodString": { "stringValue": "%s" },
                    "phoneField": { "stringValue": "%s" },

                  }
                }
                """, nameField, emailField, dobPicker, bloodString, phoneField);

        return sendRequest("POST", endpoint, payload, "PATCH");
    }

    // DELETE Patient by docId
    public static String deleteUserFromFirestore(String docId) {
        if (docId.isEmpty())
            return "Document ID is required to delete.";

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodPatient/%s?key=%s",
                PROJECT_ID, docId, API_KEY);

        return sendRequest("DELETE", endpoint, null, null);
    }

    // COMMON Request Method
    private static String sendRequest(String method, String endpoint, String payload, String overrideMethod) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");

            if (overrideMethod != null) {
                conn.setRequestProperty("X-HTTP-Method-Override", overrideMethod);
            }

            if (payload != null && !payload.isEmpty()) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.getBytes(StandardCharsets.UTF_8));
                }
            }

            int code = conn.getResponseCode();
            InputStream is = (code < 400) ? conn.getInputStream() : conn.getErrorStream();
            String response = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();

            return response; // Return full response for handling elsewhere

        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

}