package com.raksha_kavach.Controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class receiverBloodController {
    static final String PROJECT_ID = "cricketinfo-5b8c4";
    static final String API_KEY = "AIzaSyAK-A5AhmcrIKvu5Fs_CivqtUKCbFfsCxs";

    // ADD Patient
    public static String addUserToFirestore(
            String firstName,
            String lastName,
            String dob,
            String email,
            String phone,
            String address1,
            String bloodType

    ) {
        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || email.isEmpty()
                || phone.isEmpty() || bloodType.isEmpty()) {
            return "Please fill all the fields.";
        }

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodReceiver?key=%s",
                PROJECT_ID, API_KEY);

        String payload = String.format("""
                {
                  "fields": {
                    "firstName": { "stringValue": "%s" },
                    "lastName": { "stringValue": "%s" },
                    "dob": { "stringValue": "%s" },
                    "phone": { "stringValue": "%s" },
                    "email": { "stringValue": "%s" },
                     "address1": { "stringValue": "%s" },
                      "bloodType": { "stringValue": "%s" },
                  }
                }
                """, firstName, lastName, email, dob, phone, address1, address1, bloodType);

        return sendRequest("POST", endpoint, payload, null);
    }

    // READ All Patients
    public static String readUsersFromFirestore() {
        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodReceiver?key=%s",
                PROJECT_ID, API_KEY);
        return sendRequest("GET", endpoint, null, null);
    }

    // UPDATE Patient by docId
    public static String updateUserToFirestore(
            String firstName,
            String lastName,
            String dob,
            String email,
            String phone,
            String address1,
            String bloodType) {

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodReceiver/%s?key=%s",
                PROJECT_ID, API_KEY);

        String payload = String.format("""
                {
                  "fields": {
                    "firstName": { "stringValue": "%s" },
                    "lastName": { "stringValue": "%s" },
                    "dob": { "stringValue": "%s" },
                    "phone": { "stringValue": "%s" },
                    "email": { "stringValue": "%s" },
                     "address1": { "stringValue": "%s" },
                      "bloodType": { "stringValue": "%s" },

                  }
                }
                """, firstName, lastName, email, dob, phone, address1, address1, bloodType);

        return sendRequest("POST", endpoint, payload, "PATCH");
    }

    // DELETE Patient by docId
    public static String deleteUserFromFirestore(String docId) {
        if (docId.isEmpty())
            return "Document ID is required to delete.";

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/bloodReceiver/%s?key=%s",
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
                    ? "Success:"+response
                    : "Failed" +response;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}