
package com.raksha_kavach.Controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class receiverMilkController {

    static final String PROJECT_ID = "cricketinfo-5b8c4";
    static final String API_KEY = "AIzaSyAK-A5AhmcrIKvu5Fs_CivqtUKCbFfsCxs";

    // ADD Patient
    public static String addUserToFirestore(
            String name,
            String contact,
            String email,
            String address,
            String babyAge,
            String history)

    {
        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty()
                || history.isEmpty() || babyAge.isEmpty()) {
            return "Please fill all the fields.";
        }

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/milkReceiver?key=%s",
                PROJECT_ID, API_KEY);

        String payload = String.format("""
                {
                  "fields": {
                    "name": { "stringValue": "%s" },
                    "contact": { "stringValue": "%s" },
                    "email": { "stringValue": "%s" },
                    "address": { "stringValue": "%s" },
                      "babyAge": { "stringValue": "%s" },
                        "history": { "stringValue": "%s" },

                  }

                }
                """, name, contact, email, address, babyAge, history);

        return sendRequest("POST", endpoint, payload, null);
    }

    // READ All Patients
    public static String readUsersFromFirestore() {
        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/milkReceiver?key=%s",
                PROJECT_ID, API_KEY);
        return sendRequest("GET", endpoint, null, null);
    }

    // UPDATE Patient by docId
    public static String updateUserToFirestore(
            String name,
            String contact,
            String email,
            String address,
            String babyAge,
            String history) {

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/milkReceiver/%s?key=%s",
                PROJECT_ID, API_KEY);

        String payload = String.format("""
                {
                  "fields": {
                   "name": { "stringValue": "%s" },
                    "contact": { "stringValue": "%s" },
                    "email": { "stringValue": "%s" },
                    "address": { "stringValue": "%s" },
                      "babyAge": { "stringValue": "%s" },
                        "history": { "stringValue": "%s" },
                  }
                }
                """, name, contact, email, address, babyAge, history);

        return sendRequest("POST", endpoint, payload, "PATCH");
    }

    // DELETE Patient by docId
    public static String deleteUserFromFirestore(String docId) {
        if (docId.isEmpty())
            return "Document ID is required to delete.";

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/milkReceiver/%s?key=%s",
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
                    ? "Success"
                    : "Failed with HTTP " + responseCode + ":\n" + response;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
