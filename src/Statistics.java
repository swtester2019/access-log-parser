import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    static int totalTraffic= 0;;
    static LocalDateTime minTime= LocalDateTime.now();
    static LocalDateTime maxTime = minTime.minusYears(100);

    static HashSet<String> pathList = new HashSet<>();

    static HashMap<String, Integer> osTypeAppearance = new HashMap<>();

    static HashSet<String> notFoundPathList = new HashSet<>();

    static HashMap<String, Integer> browserAppearance = new HashMap<>();

    public Statistics() {

    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.responseSize;

        if (logEntry.time.isBefore(minTime)) {
            minTime = logEntry.time;
        } else if (logEntry.time.isAfter(maxTime)) {
            maxTime = logEntry.time;
        }

        if (logEntry.responseCode == 200) {
            pathList.add(logEntry.path);
        } else if (logEntry.responseCode == 404) {
            notFoundPathList.add(logEntry.path);
        }

        UserAgent userAgent = new UserAgent(logEntry.userAgent);

        int osTypeCount = 1;
        if (!osTypeAppearance.containsKey(userAgent.osType)) {
            osTypeAppearance.put(userAgent.osType, osTypeCount);
        } else {
            osTypeAppearance.put(userAgent.osType, ++osTypeCount);
        }

        int browserCount = 1;
        if (!browserAppearance.containsKey(userAgent.browser)) {
            browserAppearance.put(userAgent.browser, browserCount);
        } else {
            browserAppearance.put(userAgent.browser, ++browserCount);
        }

    }

    public int getTrafficRate() {
        int res = 0;
        res = totalTraffic / (maxTime.getHour() - minTime.getHour());
        return res;
    }

    public HashMap<String, Double> getOsTypeRate() {

        Double osTotalAmount = 0.0;

        for (Integer val: osTypeAppearance.values()) {
            osTotalAmount += val.doubleValue();
        }

        HashMap<String, Double> res = new HashMap<>();

        for (String str: osTypeAppearance.keySet()) {
            res.put(str, osTypeAppearance.get(str) / osTotalAmount);
        }

        return res;

    }

    public HashMap<String, Double> getBrowserRate() {

        Double browserTotalAmount = 0.0;

        for (Integer val: browserAppearance.values()) {
            browserTotalAmount += val.doubleValue();
        }

        HashMap<String, Double> res = new HashMap<>();

        for (String str: browserAppearance.keySet()) {
            res.put(str, browserAppearance.get(str) / browserTotalAmount);
        }

        return res;

    }

    public HashSet<String> getPathList() {
        return pathList;
    }

    public HashSet<String> getNotFoundPathList() {
        return notFoundPathList;
    }

    @Override
    public String toString() {
        return "Statistics{totalTraffic = " + totalTraffic + ", " + "minTime = " + minTime + ", " + "maxTime = " +
                maxTime + ", " + "pathList = " + pathList + ", " + "osTypeAppearance = " + osTypeAppearance + ", " +
                "notFoundPathList = " + notFoundPathList + "}";
    }
}
