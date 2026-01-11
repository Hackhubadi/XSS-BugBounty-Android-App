package com.bugbounty.xsstester.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestResult {
    private String url;
    private String payload;
    private boolean vulnerable;
    private String response;
    private int statusCode;
    private Date timestamp;
    private String method;
    private String encoding;

    public TestResult(String url, String payload, boolean vulnerable, String response, int statusCode) {
        this.url = url;
        this.payload = payload;
        this.vulnerable = vulnerable;
        this.response = response;
        this.statusCode = statusCode;
        this.timestamp = new Date();
        this.method = "GET";
        this.encoding = "None";
    }

    public TestResult(String url, String payload, boolean vulnerable, String response, 
                     int statusCode, String method, String encoding) {
        this.url = url;
        this.payload = payload;
        this.vulnerable = vulnerable;
        this.response = response;
        this.statusCode = statusCode;
        this.timestamp = new Date();
        this.method = method;
        this.encoding = encoding;
    }

    // Getters
    public String getUrl() {
        return url;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public String getResponse() {
        return response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMethod() {
        return method;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(timestamp);
    }

    public String getStatusText() {
        if (statusCode >= 200 && statusCode < 300) {
            return "Success";
        } else if (statusCode >= 300 && statusCode < 400) {
            return "Redirect";
        } else if (statusCode >= 400 && statusCode < 500) {
            return "Client Error";
        } else if (statusCode >= 500) {
            return "Server Error";
        }
        return "Unknown";
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "url='" + url + '\'' +
                ", vulnerable=" + vulnerable +
                ", statusCode=" + statusCode +
                ", timestamp=" + getFormattedTimestamp() +
                '}';
    }
}
