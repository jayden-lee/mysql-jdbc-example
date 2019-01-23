# Connection

## 1. Common Connection

### 1.1 Typical flow of using JDBC
1. Load the JDBC driver
2. Create a Connection
3. Create a Statement and Get a ResultSet
4. Traverse and Process the ResultSet
5. Close ResultSet, Statement and Connection

## 2. Encrypted Connection

### 2.1 Prerequisites
- Certificate and Key File (Certain SSL mode requires certificates and key files)

### 2.2 SSL Mode and Legacy properties
| SSL Mode        | Description                                                                                                                       | Legacy Properties                                                    |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------|
| DISABLED        | Establish unencrypted connections                                                                                                 | useSSL = false                                                       |
| PREFERRED       | Establish encrypted connections if the server enabled them, otherwise fall back to unencrypted connections                        | useSSL = true<br/> requireSSL = false<br/> verifyServerCertificate = false |
| REQUIRED        | Establish secure connections if the server enabled them, fail otherwise                                                           | useSSL = true<br/> requireSSL = true<br/> verifyServerCertificate = false      |
| VERIFY_CA       | Like "REQUIRED" but additionally verify the server TLS certificate against the configured Certificate Authority (CA) certificates | useSSL = true<br/> verifyServerCertificate = true                         |
| VERIFY_IDENTITY | Like "VERIFY_CA", but additionally verify that the server certificate matches the host to which the connection is attempted       | There is no equivalent legacy settings for "sslMode=VERIFY_IDENTITY" |

> Note that, for ALL server versions, the default setting of "sslMode" is "PREFERRED", and it is equivalent to the legacy settings of "useSSL=true", "requireSSL=false", and "verifyServerCertificate=false".
The legacy properties are ignored if "sslMode" is set explicitly. If none of "sslMode" or "useSSL" is set explicitly, the default setting of "sslMode=PREFERRED" applies.

## 3. Connecting to remote MySQL through SSH using Java

### 3.1 Prerequisites
- [JSCH Library](http://www.jcraft.com/jsch/)