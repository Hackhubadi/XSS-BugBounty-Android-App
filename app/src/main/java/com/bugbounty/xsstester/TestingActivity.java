package com.bugbounty.xsstester;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bugbounty.xsstester.models.TestResult;
import com.bugbounty.xsstester.utils.NetworkHelper;
import com.bugbounty.xsstester.utils.PayloadEncoder;
import java.util.ArrayList;
import java.util.List;

public class TestingActivity extends AppCompatActivity {

    private EditText etTargetUrl, etPayload, etParameter;
    private Spinner spinnerMethod, spinnerEncoding;
    private Button btnTest, btnClear, btnUseFromLibrary;
    private TextView tvResponse, tvStatus, tvResponseCode;
    private ProgressBar progressBar;
    private ScrollView scrollViewResponse;
    private List<TestResult> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        initViews();
        setupSpinners();
        setupClickListeners();
        results = new ArrayList<>();
    }

    private void initViews() {
        etTargetUrl = findViewById(R.id.etTargetUrl);
        etPayload = findViewById(R.id.etPayload);
        etParameter = findViewById(R.id.etParameter);
        spinnerMethod = findViewById(R.id.spinnerMethod);
        spinnerEncoding = findViewById(R.id.spinnerEncoding);
        btnTest = findViewById(R.id.btnTest);
        btnClear = findViewById(R.id.btnClear);
        btnUseFromLibrary = findViewById(R.id.btnUseFromLibrary);
        tvResponse = findViewById(R.id.tvResponse);
        tvStatus = findViewById(R.id.tvStatus);
        tvResponseCode = findViewById(R.id.tvResponseCode);
        progressBar = findViewById(R.id.progressBar);
        scrollViewResponse = findViewById(R.id.scrollViewResponse);
    }

    private void setupSpinners() {
        // HTTP Methods
        String[] methods = {"GET", "POST", "PUT", "DELETE", "PATCH"};
        ArrayAdapter<String> methodAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, methods);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMethod.setAdapter(methodAdapter);

        // Encoding Types
        String[] encodings = {"None", "URL Encode", "HTML Encode", "Base64", "Double URL Encode", "Hex Encode"};
        ArrayAdapter<String> encodingAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, encodings);
        encodingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEncoding.setAdapter(encodingAdapter);
    }

    private void setupClickListeners() {
        btnTest.setOnClickListener(v -> performTest());
        btnClear.setOnClickListener(v -> clearFields());
        btnUseFromLibrary.setOnClickListener(v -> {
            // Open payload library to select
            Toast.makeText(this, "Opening Payload Library...", Toast.LENGTH_SHORT).show();
            finish(); // Go back to main, user can navigate to library
        });
    }

    private void performTest() {
        String url = etTargetUrl.getText().toString().trim();
        String payload = etPayload.getText().toString().trim();
        String parameter = etParameter.getText().toString().trim();
        String method = spinnerMethod.getSelectedItem().toString();
        String encoding = spinnerEncoding.getSelectedItem().toString();

        // Validation
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter target URL", Toast.LENGTH_SHORT).show();
            etTargetUrl.requestFocus();
            return;
        }

        if (payload.isEmpty()) {
            Toast.makeText(this, "Please enter XSS payload", Toast.LENGTH_SHORT).show();
            etPayload.requestFocus();
            return;
        }

        // Encode payload if needed
        String encodedPayload = payload;
        if (!encoding.equals("None")) {
            encodedPayload = PayloadEncoder.encode(payload, encoding);
            Toast.makeText(this, "Payload encoded: " + encoding, Toast.LENGTH_SHORT).show();
        }

        // Show loading state
        progressBar.setVisibility(View.VISIBLE);
        tvStatus.setText("🔄 Testing in progress...");
        tvStatus.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tvResponse.setText("");
        tvResponseCode.setText("");
        btnTest.setEnabled(false);

        // Perform network request
        final String finalEncodedPayload = encodedPayload;
        NetworkHelper.sendRequest(url, encodedPayload, parameter, method, new NetworkHelper.ResponseCallback() {
            @Override
            public void onSuccess(String response, int statusCode) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnTest.setEnabled(true);
                    
                    // Display response
                    tvResponse.setText(response);
                    tvResponseCode.setText("HTTP " + statusCode);
                    
                    // Analyze response for XSS
                    AnalysisResult analysis = analyzeResponse(response, payload, finalEncodedPayload);
                    
                    if (analysis.isVulnerable) {
                        tvStatus.setText("⚠️ POTENTIAL XSS DETECTED!");
                        tvStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        
                        // Show detailed analysis
                        showAnalysisDialog(analysis);
                    } else {
                        tvStatus.setText("✓ No XSS Detected");
                        tvStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    }

                    // Save result
                    TestResult result = new TestResult(url, payload, analysis.isVulnerable, response, statusCode);
                    results.add(result);
                    
                    Toast.makeText(TestingActivity.this, 
                        "Test completed. Results saved.", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnTest.setEnabled(true);
                    tvStatus.setText("❌ Error: " + error);
                    tvStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    tvResponse.setText("Request failed. Check URL and network connection.");
                    tvResponseCode.setText("");
                });
            }
        });
    }

    private AnalysisResult analyzeResponse(String response, String originalPayload, String encodedPayload) {
        AnalysisResult result = new AnalysisResult();
        result.details = new ArrayList<>();
        
        // Check 1: Direct payload reflection
        if (response.contains(originalPayload)) {
            result.isVulnerable = true;
            result.details.add("✓ Original payload reflected in response");
        }
        
        // Check 2: Encoded payload reflection
        if (!originalPayload.equals(encodedPayload) && response.contains(encodedPayload)) {
            result.isVulnerable = true;
            result.details.add("✓ Encoded payload reflected in response");
        }
        
        // Check 3: Common XSS indicators
        String[] indicators = {
            "<script", "onerror=", "onload=", "javascript:", 
            "onclick=", "onmouseover=", "<svg", "<img", 
            "eval(", "alert(", "prompt(", "confirm("
        };
        
        for (String indicator : indicators) {
            if (response.toLowerCase().contains(indicator.toLowerCase())) {
                result.isVulnerable = true;
                result.details.add("✓ XSS indicator found: " + indicator);
                break;
            }
        }
        
        // Check 4: HTML context analysis
        if (response.contains("<") && response.contains(">")) {
            result.details.add("• Response contains HTML tags");
        }
        
        // Check 5: Script context
        if (response.contains("<script>") || response.contains("</script>")) {
            result.details.add("• Response contains script tags");
        }
        
        if (!result.isVulnerable) {
            result.details.add("✗ No XSS indicators found");
            result.details.add("✗ Payload not reflected or sanitized");
        }
        
        return result;
    }

    private void showAnalysisDialog(AnalysisResult analysis) {
        StringBuilder message = new StringBuilder("Vulnerability Analysis:\n\n");
        for (String detail : analysis.details) {
            message.append(detail).append("\n");
        }
        message.append("\n⚠️ Verify manually before reporting!");
        
        new android.app.AlertDialog.Builder(this)
            .setTitle("XSS Detection Details")
            .setMessage(message.toString())
            .setPositiveButton("OK", null)
            .setNegativeButton("Copy Report", (dialog, which) -> {
                // Copy analysis to clipboard
                android.content.ClipboardManager clipboard = 
                    (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(
                    "XSS Analysis", message.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Analysis copied to clipboard", Toast.LENGTH_SHORT).show();
            })
            .show();
    }

    private void clearFields() {
        etTargetUrl.setText("");
        etPayload.setText("");
        etParameter.setText("");
        tvResponse.setText("");
        tvStatus.setText("");
        tvResponseCode.setText("");
        spinnerMethod.setSelection(0);
        spinnerEncoding.setSelection(0);
    }

    // Inner class for analysis results
    private static class AnalysisResult {
        boolean isVulnerable = false;
        List<String> details;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Save results to persistent storage if needed
    }
}
