package com.bugbounty.xsstester;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private CardView cardPayloadLibrary, cardTesting, cardReports, cardSettings;
    private TextView tvAppVersion, tvDisclaimer;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("XSSTesterPrefs", MODE_PRIVATE);
        
        initViews();
        setupClickListeners();
        
        // Show disclaimer on first launch
        if (!prefs.getBoolean("disclaimer_accepted", false)) {
            showDisclaimerDialog();
        }
    }

    private void initViews() {
        cardPayloadLibrary = findViewById(R.id.cardPayloadLibrary);
        cardTesting = findViewById(R.id.cardTesting);
        cardReports = findViewById(R.id.cardReports);
        cardSettings = findViewById(R.id.cardSettings);
        tvAppVersion = findViewById(R.id.tvAppVersion);
        tvDisclaimer = findViewById(R.id.tvDisclaimer);
        
        tvAppVersion.setText("Version 1.0.0");
        tvDisclaimer.setText("⚠️ For authorized testing only");
    }

    private void setupClickListeners() {
        cardPayloadLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PayloadLibraryActivity.class));
            }
        });

        cardTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestingActivity.class));
            }
        });

        cardReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReportActivity.class));
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsDialog();
            }
        });
    }

    private void showDisclaimerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("⚠️ Legal Disclaimer");
        builder.setMessage("This tool is for AUTHORIZED security testing only.\n\n" +
                "• Only test applications you have permission to test\n" +
                "• Follow bug bounty program rules and scope\n" +
                "• Respect responsible disclosure guidelines\n" +
                "• Unauthorized testing is ILLEGAL\n\n" +
                "By using this app, you agree to use it ethically and legally.");
        builder.setPositiveButton("I Understand & Agree", (dialog, which) -> {
            prefs.edit().putBoolean("disclaimer_accepted", true).apply();
            dialog.dismiss();
        });
        builder.setNegativeButton("Exit", (dialog, which) -> {
            finish();
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settings");
        builder.setMessage("Settings feature coming soon!\n\n" +
                "Planned features:\n" +
                "• Custom payload management\n" +
                "• Export/Import settings\n" +
                "• Theme customization\n" +
                "• Network timeout configuration");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update stats or refresh data if needed
    }
}
