Course Project I
================

Simple signup web-application with security flaws

##Four views
/form
/done
/signup/{id}
/register

##Two database tables

SIGNUP
id BIGINT(19) NOT NULL
address VARCHAR(255)
name VARCHAR(255)

FEEDBACK
id BIGINT(19) NOT NULL
adm BOOLEAN(1)
audience BOOLEAN(1)
content VARCHAR(255)

##Two authenties
ADMIN (ted,ted)
USER

##5 Security Issues
A1-Injection
A3-Cross-siteScripting (XSS)
A4-Insecure Direct Object References
A5-Secure Misconfiguration
A7-Missing Function Level Access Control
(and some more: A9-Using Components with Known Vulnerabilities, ...)

