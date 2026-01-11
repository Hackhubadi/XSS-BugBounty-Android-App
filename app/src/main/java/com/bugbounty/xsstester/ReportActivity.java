package com.bugbounty.xsstester;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    private TextView tvReportContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        tvReportContent = findViewById(R.id.tvReportContent);
        
        // Display placeholder message
        tvReportContent.setText("📊 Test Reports\n\n" +
                "This feature will display:\n" +
                "• Test history\n" +
                "• Vulnerability findings\n" +
                "• Export options (PDF/HTML)\n" +
                "• Statistics\n\n" +
                "Coming soon in next update!");
    }
}
