package com.bugbounty.xsstester.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkHelper {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final int CONNECT_TIMEOUT = 15000; // 15 seconds
    private static final int READ_TIMEOUT = 15000; // 15 seconds

    public interface ResponseCallback {
        void onSuccess(String response, int statusCode);
        void onError(String error);
    }

    public static void sendRequest(String urlString, String payload, String parameter, 
                                   String method, ResponseCallback callback) {
        executor.execute(() -> {
            HttpURLConnection connection = null;
            try {
                // Build URL with parameters for GET requests
                if (method.equals("GET") && parameter != null && !parameter.isEmpty()) {
                    String separator = urlString.contains("?") ? "&" : "?";
                    urlString += separator + parameter + "=" + payload;
                }

                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setConnectTimeout(CONNECT_TIMEOUT);
                connection.setReadTimeout(READ_TIMEOUT);
                
                // Set headers
                connection.setRequestProperty("User-Agent", "XSS-Tester/1.0 (Bug Bounty Tool)");
                connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                connection.setRequestProperty("Connection", "close");

                // For POST/PUT/PATCH, send payload in body
                if (!method.equals("GET") && !method.equals("DELETE")) {
                    connection.setDoOutput(true);
                    
                    if (parameter != null && !parameter.isEmpty()) {
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        String postData = parameter + "=" + payload;
                        
                        OutputStream os = connection.getOutputStream();
                        os.write(postData.getBytes("UTF-8"));
                        os.flush();
                        os.close();
                    }
                }

                // Get response code
                int statusCode = connection.getResponseCode();
                
                // Read response
                BufferedReader reader;
                if (statusCode >= 200 && statusCode < 400) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                } else {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
                }
                
                StringBuilder response = new StringBuilder();
                String line;
                int lineCount = 0;
                int maxLines = 1000; // Limit response size
                
                while ((line = reader.readLine()) != null && lineCount < maxLines) {
                    response.append(line).append("\n");
                    lineCount++;
                }
                
                if (lineCount >= maxLines) {
                    response.append("\n... [Response truncated - too large]");
                }
                
                reader.close();

                // Success callback
                callback.onSuccess(response.toString(), statusCode);

            } catch (java.net.SocketTimeoutException e) {
                callback.onError("Connection timeout - Server took too long to respond");
            } catch (java.net.UnknownHostException e) {
                callback.onError("Unknown host - Check URL and internet connection");
            } catch (java.net.MalformedURLException e) {
                callback.onError("Invalid URL format");
            } catch (javax.net.ssl.SSLException e) {
                callback.onError("SSL/TLS error - Certificate issue");
            } catch (java.io.IOException e) {
                callback.onError("Network error: " + e.getMessage());
            } catch (Exception e) {
                callback.onError("Unexpected error: " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    // Shutdown executor when app closes
    public static void shutdown() {
        executor.shutdown();
    }
}
