package com.bugbounty.xsstester.utils;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PayloadEncoder {

    /**
     * Encode payload based on encoding type
     */
    public static String encode(String payload, String encodingType) {
        if (payload == null || payload.isEmpty()) {
            return payload;
        }

        try {
            switch (encodingType) {
                case "URL Encode":
                    return urlEncode(payload);
                    
                case "HTML Encode":
                    return htmlEncode(payload);
                    
                case "Base64":
                    return base64Encode(payload);
                    
                case "Double URL Encode":
                    return doubleUrlEncode(payload);
                    
                case "Hex Encode":
                    return hexEncode(payload);
                    
                case "None":
                default:
                    return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return payload;
        }
    }

    /**
     * URL Encoding
     */
    private static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return input;
        }
    }

    /**
     * Double URL Encoding
     */
    private static String doubleUrlEncode(String input) {
        try {
            String firstEncode = URLEncoder.encode(input, "UTF-8");
            return URLEncoder.encode(firstEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return input;
        }
    }

    /**
     * HTML Entity Encoding
     */
    private static String htmlEncode(String input) {
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '<':
                    encoded.append("&lt;");
                    break;
                case '>':
                    encoded.append("&gt;");
                    break;
                case '&':
                    encoded.append("&amp;");
                    break;
                case '"':
                    encoded.append("&quot;");
                    break;
                case '\'':
                    encoded.append("&#x27;");
                    break;
                case '/':
                    encoded.append("&#x2F;");
                    break;
                default:
                    encoded.append(c);
            }
        }
        return encoded.toString();
    }

    /**
     * Base64 Encoding
     */
    private static String base64Encode(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.NO_WRAP);
    }

    /**
     * Hexadecimal Encoding
     */
    private static String hexEncode(String input) {
        StringBuilder hex = new StringBuilder();
        for (char c : input.toCharArray()) {
            hex.append("\\x").append(String.format("%02x", (int) c));
        }
        return hex.toString();
    }

    /**
     * Decode payload based on encoding type
     */
    public static String decode(String payload, String encodingType) {
        if (payload == null || payload.isEmpty()) {
            return payload;
        }

        try {
            switch (encodingType) {
                case "URL Encode":
                    return java.net.URLDecoder.decode(payload, "UTF-8");
                    
                case "Base64":
                    return new String(Base64.decode(payload, Base64.NO_WRAP));
                    
                case "Double URL Encode":
                    String firstDecode = java.net.URLDecoder.decode(payload, "UTF-8");
                    return java.net.URLDecoder.decode(firstDecode, "UTF-8");
                    
                default:
                    return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return payload;
        }
    }

    /**
     * Get encoding description
     */
    public static String getEncodingDescription(String encodingType) {
        switch (encodingType) {
            case "URL Encode":
                return "Converts special characters to %XX format";
            case "HTML Encode":
                return "Converts to HTML entities (&lt; &gt; etc)";
            case "Base64":
                return "Encodes to Base64 format";
            case "Double URL Encode":
                return "Applies URL encoding twice";
            case "Hex Encode":
                return "Converts to hexadecimal \\xXX format";
            default:
                return "No encoding applied";
        }
    }
}
