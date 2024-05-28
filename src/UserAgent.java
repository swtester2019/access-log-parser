public class UserAgent {
    final String osType;
    final String browser;

    public UserAgent(String userAgent) {
        String[] parts1 = userAgent.split("\\(");
        if (parts1.length >= 2) {

            String str1 = parts1[1];
            String[] parts2 = str1.split(";", 2);
            osType = parts2[0];

            String str2 = parts1[parts1.length - 1];
            String[] parts3 = str2.split(" ");
            String str3 = parts3[parts3.length - 1];
            String[] parts4 = str3.split("/");
            browser = parts4[0];

        } else {
            osType = parts1[0];
            browser = parts1[0];
        }

    }

    public String getOsType() {
        return osType;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isBot(String userAgent) {
        return userAgent.contains("bot");
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "osType='" + osType + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }
}
