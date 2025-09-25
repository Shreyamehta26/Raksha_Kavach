package com.raksha_kavach.Controller;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.OutputStream; 
import java.net.HttpURLConnection; 
import java.net.URL; 
import java.nio.charset.StandardCharsets; 
import java.util.List; 
import java.util.Map;
import org.json.JSONObject;

//import com.raksha_kavach.model.Notes;


public class AiApiController {
    public String callGeminiAPI(String c2w_ai_prompt) {
        String c2w_ai_apiKey = "AIzaSyDMwsMAtgdbq5lI0pC_J_sJzj0Ci89bAxg";
        // String c2w_ai_url =
        //"https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
        // JSONObject c2w_ai_json = new JSONObject()
        // .put("contents",
        // List.of(Map.of("parts", List.of(Map.of("text", c2w_ai_prompt+ " Give this in
        // 3 to 4 lines")))));
        // HttpResponse<String> c2w_ai_response =Unirest.post(c2w_ai_url +
        // c2w_ai_apiKey)
        // .header("Content-Type", "application/json")
        // .body(c2w_ai_json.toString())
        // .asString();
        StringBuilder c2w_ai_response = new StringBuilder();
        try {

            URL c2w_ai_url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="+ c2w_ai_apiKey);
            HttpURLConnection c2w_ai_conn = (HttpURLConnection) 
            c2w_ai_url.openConnection();
            c2w_ai_conn.setRequestMethod("POST");
            c2w_ai_conn.setRequestProperty("Content-Type", 
            "application/json; charset=UTF-8");
            c2w_ai_conn.setDoOutput(true);
            // Build the JSON request body
            JSONObject c2w_ai_jsonRequest = new JSONObject()
            .put("contents", List.of(
                Map.of("parts", 
                List.of(
                    Map.of("text", c2w_ai_prompt+ " Give this in 3 to 4 lines")))));
                    // Send the JSON request
                    OutputStream c2w_ai_os = c2w_ai_conn.getOutputStream();
                    byte[] input = 
                    c2w_ai_jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                    c2w_ai_os.write(input, 0, input.length);
                    // Read the response
                    int responseCode = c2w_ai_conn.getResponseCode();
                    InputStream c2w_ai_is = (responseCode >= 200 && responseCode < 300)
                    ? c2w_ai_conn.getInputStream()
                    : c2w_ai_conn.getErrorStream();
                    BufferedReader reader = new BufferedReader(new 
                    InputStreamReader(c2w_ai_is, StandardCharsets.UTF_8));
                    c2w_ai_response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        c2w_ai_response.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject c2w_ai_jsonResponse = new 
                JSONObject(c2w_ai_response.toString());
                return 
                c2w_ai_jsonResponse.getJSONArray("candidates").getJSONObject(0).getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0).getString("text");
            }

    
    }
