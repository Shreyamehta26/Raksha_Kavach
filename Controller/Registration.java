package com.raksha_kavach.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registration {

    private static final String API_KEY = "AIzaSyDAjZo-VlwU5_S4yxDZ8VIEUyO7a9jfp0A";

    public boolean signUpWithEmailAndPassword(String email, String password) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // ✅ Only send email and password (NOT confirm password)
            String payload = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                email, password
            );

            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return true; // ✅ Success
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line); // 🔴 Firebase error shown here
                    }
                }
                return false;
            }

        } catch (Exception ae) {
            ae.printStackTrace();
            return false;
        }
    }
}
