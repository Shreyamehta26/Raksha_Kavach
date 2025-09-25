package com.raksha_kavach.Controller;
//import com.raksha_kavach.Controller.PatientPageController;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PatientPageController {

    static final String PROJECT_ID = "crudop-75477";
    static final String API_KEY = "AIzaSyDAjZo-VlwU5_S4yxDZ8VIEUyO7a9jfp0A";

    // ADD Patient
    public static String addUserToFirestore(
            String fullName,
            String username,
            String email,
            String contact,
            String aadhar,
            String pan,
            String password,
            String disease,
            String bloodGroup,
            String hemoglobin,
            String gender,
            String dob,
            String age,
            String address) {
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || contact.isEmpty()
                || aadhar.isEmpty() || pan.isEmpty() || password.isEmpty()
                || disease.isEmpty() || bloodGroup.isEmpty() || hemoglobin.isEmpty()
                || gender.isEmpty() || dob.isEmpty() || age.isEmpty() || address.isEmpty()) {
            return "Please fill all the fields.";
        }

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/Patients?key=%s",
                PROJECT_ID, API_KEY);

        String payload = String.format("""
                {
                  "fields": {
                    "fullName": { "stringValue": "%s" },
                    "username": { "stringValue": "%s" },
                    "email": { "stringValue": "%s" },
                    "contact": { "stringValue": "%s" },
                    "aadhar": { "stringValue": "%s" },
                    "pan": { "stringValue": "%s" },
                    "password": { "stringValue": "%s" },
                    "disease": { "stringValue": "%s" },
                    "bloodGroup": { "stringValue": "%s" },
                    "hemoglobin": { "stringValue": "%s" },
                    "gender": { "stringValue": "%s" },
                    "dob": { "stringValue": "%s" },
                    "age": { "integerValue": %s },
                    "address": { "stringValue": "%s" }
                  }
                }
                """, fullName, username, email, contact, aadhar, pan, password,
                disease, bloodGroup, hemoglobin, gender, dob, age, address);

        return sendRequest("POST", endpoint, payload, null);
    }

    // READ All Patients
    public static String readUsersFromFirestore() {
        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/Patients?key=%s",
                PROJECT_ID, API_KEY);
        return sendRequest("GET", endpoint, null, null);
    }

    // UPDATE Patient by docId
    public static String updateUserToFirestore(
            String docId,
            String fullName,
            String username,
            String email,
            String contact,
            String aadhar,
            String pan,
            String password,
            String disease,
            String bloodGroup,
            String hemoglobin,
            String gender,
            String dob,
            String age,
            String address) {
        if (docId.isEmpty())
            return "Document ID is required to update.";

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/Patients/%s?key=%s",
                PROJECT_ID, docId, API_KEY);

        String payload = String.format("""
                {
                  "fields": {
                    "fullName": { "stringValue": "%s" },
                    "username": { "stringValue": "%s" },
                    "email": { "stringValue": "%s" },
                    "contact": { "stringValue": "%s" },
                    "aadhar": { "stringValue": "%s" },
                    "pan": { "stringValue": "%s" },
                    "password": { "stringValue": "%s" },
                    "disease": { "stringValue": "%s" },
                    "bloodGroup": { "stringValue": "%s" },
                    "hemoglobin": { "stringValue": "%s" },
                    "gender": { "stringValue": "%s" },
                    "dob": { "stringValue": "%s" },
                    "age": { "integerValue": %s },
                    "address": { "stringValue": "%s" }
                  }
                }
                """, fullName, username, email, contact, aadhar, pan, password,
                disease, bloodGroup, hemoglobin, gender, dob, age, address);

        return sendRequest("POST", endpoint, payload, "PATCH");
    }

    // DELETE Patient by docId
    public static String deleteUserFromFirestore(String docId) {
        if (docId.isEmpty())
            return "Document ID is required to delete.";

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/Patients/%s?key=%s",
                PROJECT_ID, docId, API_KEY);

        return sendRequest("DELETE", endpoint, null, null);
    }

    // COMMON Request Method
    private static String sendRequest(String method, String endpoint, String payload, String overrideMethod) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            if (overrideMethod != null) {
                conn.setRequestProperty("X-HTTP-Method-Override", overrideMethod);
            }

            if (payload != null) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.getBytes(StandardCharsets.UTF_8));
                }
            }

            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode < 400) ? conn.getInputStream() : conn.getErrorStream();
            byte[] responseBytes = is.readAllBytes();
            String response = new String(responseBytes, StandardCharsets.UTF_8);
            is.close();
            conn.disconnect();

            return responseCode == 200 || responseCode == 201
                    ? "Success:\n" + response
                    : "Failed with HTTP " + responseCode + ":\n" + response;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Get patient by document ID
    public static String getPatientByDocId(String docId) {
        if (docId == null || docId.isEmpty()) {
            return "Error: docId is null or empty";
        }

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/Patients/%s?key=%s",
                PROJECT_ID, docId, API_KEY);

        return sendRequest("GET", endpoint, null, null);
    }

}