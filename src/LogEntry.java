import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    final String ipAddr;
    final LocalDateTime time;
    final HttpMethod httpMethod;
    final String path;
    final int responseCode;
    final int responseSize;
    final String referer;
    final String userAgent;

    public LogEntry(String str1) {
        String[] parts1 = str1.split(" ", 2);
        ipAddr = parts1[0];

        String str2 = parts1[1];
        String[] parts2 =str2.split("\\[");
        String str3 = parts2[1];
        String[] parts3 =str3.split("] \"");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        time =  LocalDateTime.parse(parts3[0], formatter);

        String str4 = parts3[1];
        String[] parts4 = str4.split(" ", 2);
        httpMethod = HttpMethod.valueOf(parts4[0]);

        String str5 = parts4[1];
        String[] parts5 = str5.split(" ", 5);
        path = parts5[0];
        responseCode = Integer.parseInt(parts5[2]);
        responseSize = Integer.parseInt(parts5[3]);

        String str6 = parts5[4];
        String[] parts6 = str6.split("\"");
        referer = parts6[1];
        userAgent = parts6[3];

    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddr='" + ipAddr + '\'' +
                ", time=" + time +
                ", httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referer='" + referer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

}
