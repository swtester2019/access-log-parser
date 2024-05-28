import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    static int totalTraffic = 0;
    static LocalDateTime minTime = LocalDateTime.now();
    static LocalDateTime maxTime = minTime.minusYears(100);

    static HashSet<String> pathList = new HashSet<>();

    static HashMap<String, Integer> osTypeAppearance = new HashMap<>();

    static HashSet<String> notFoundPathList = new HashSet<>();

    static HashMap<String, Integer> browserAppearance = new HashMap<>();

    //Количество обращений к сайту через обычные браузеры
    static int totalVisits = 0;

    //Количество обращений к сайту через боты
    static int totalBotVisits = 0;

    //Количество ответов с ошибочными кодами
    static int totalErrorResponses = 0;


    //Уникальные IP-адреса реальных пользователей
    static HashSet<String> uniqueIpAddr = new HashSet<>();

    //Количество посещеий за одну секунду
    static HashMap<Integer, Integer> peakSiteVisits = new HashMap<>();

    static int siteVisitsCount = 1;

    //Список сайтов, со страниц которых есть ссылки на текущий сайт
    static HashSet<String> refererList = new HashSet<>();

    //Количество посещений пользователями
    static HashMap<String, Integer> ipAddrSiteVisits = new HashMap<>();

    static int ipAddrSiteVisitsCount = 1;

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

        if (!userAgent.isBot(logEntry.userAgent)) {
            totalVisits++;
            uniqueIpAddr.add(logEntry.ipAddr);
        } else {
            totalBotVisits++;
        }

        if (String.valueOf(logEntry.responseCode).startsWith("4") || String.valueOf(logEntry.responseCode).startsWith("5")) {
            totalErrorResponses++;
        }

        if (!peakSiteVisits.containsKey(logEntry.time.getSecond()) && !userAgent.isBot(logEntry.userAgent)) {
            peakSiteVisits.put(logEntry.time.getSecond(), siteVisitsCount);
        } else if (peakSiteVisits.containsKey(logEntry.time.getSecond()) && !userAgent.isBot(logEntry.userAgent)){
            peakSiteVisits.put(logEntry.time.getSecond(), ++siteVisitsCount);
        }

        String[] parts1 = logEntry.referer.split("https://");

        if (parts1.length >= 2) {
            String str1 = parts1[1];
            String[] parts2 = str1.split("/");
            refererList.add(parts2[0]);
        }

        if (!ipAddrSiteVisits.containsKey(logEntry.ipAddr)&& !userAgent.isBot(logEntry.userAgent)) {
            ipAddrSiteVisits.put(logEntry.ipAddr, ipAddrSiteVisitsCount);
        } else if (ipAddrSiteVisits.containsKey(logEntry.ipAddr)&& !userAgent.isBot(logEntry.userAgent)) {
            ipAddrSiteVisits.put(logEntry.ipAddr, ++ipAddrSiteVisitsCount);
        }

    }

    //Метод подсчёта среднего объёма трафика сайта за час
    public int getTrafficRate() {
        int res = 0;
        res = totalTraffic / (maxTime.getHour() - minTime.getHour());
        return res;
    }

    public HashMap<String, Double> getOsTypeRate() {

        Double osTotalAmount = 0.0;

        for (Integer val : osTypeAppearance.values()) {
            osTotalAmount += val.doubleValue();
        }

        HashMap<String, Double> res = new HashMap<>();

        for (String str : osTypeAppearance.keySet()) {
            res.put(str, osTypeAppearance.get(str) / osTotalAmount);
        }

        return res;

    }

    public HashMap<String, Double> getBrowserRate() {

        Double browserTotalAmount = 0.0;

        for (Integer val : browserAppearance.values()) {
            browserTotalAmount += val.doubleValue();
        }

        HashMap<String, Double> res = new HashMap<>();

        for (String str : browserAppearance.keySet()) {
            res.put(str, browserAppearance.get(str) / browserTotalAmount);
        }

        return res;

    }

    //Метод подсчёта среднего количества посещений сайта за час
    public int getSiteVisitsRate() {
        return totalVisits / (int) (Duration.between(minTime, maxTime).toHours());
    }

    //Метод подсчёта среднего количества ошибочных запросов в час
    public int getErrorResponsesRate() {
        return totalErrorResponses / (int) (Duration.between(minTime, maxTime).toHours());
    }

    //Метод расчёта средней посещаемости одним пользователем
    public int getAverageSiteVisitsRate() {
        return totalVisits / uniqueIpAddr.size();
    }

    //Метод расчёта пиковой посещаемости сайта (в секунду)
    public int getPeakSiteVisitsRate() {
        return Collections.max(peakSiteVisits.values());
    }

    //Метод возвращающий список сайтов, со страниц которых есть ссылки на текущий сайт
    public HashSet<String> getRefererList() {
        return refererList;
    }

    //Метод расчёта максимальной посещаемости одним пользователем
    public int getIpAddrSiteVisits() {
        return Collections.max(ipAddrSiteVisits.values());
    }


    public HashSet<String> getPathList() {
        return pathList;
    }

    public HashSet<String> getNotFoundPathList() {
        return notFoundPathList;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public int getTotalBotVisits() {
        return totalBotVisits;
    }

    @Override
    public String toString() {
        return "Statistics{totalTraffic = " + totalTraffic + ", " + "minTime = " + minTime + ", " + "maxTime = " +
                maxTime + ", " + "pathList = " + pathList + ", " + "osTypeAppearance = " + osTypeAppearance + ", " +
                "notFoundPathList = " + notFoundPathList + "}";
    }
}
