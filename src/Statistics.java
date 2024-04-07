import java.time.LocalDateTime;

public class Statistics {
    static int totalTraffic= 0;;
    static LocalDateTime minTime= LocalDateTime.now();
    static LocalDateTime maxTime = minTime.minusYears(100);

    public Statistics() {

    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.responseSize;

        if (logEntry.time.isBefore(minTime)) {
            minTime = logEntry.time;
        } else if (logEntry.time.isAfter(maxTime)) {
            maxTime = logEntry.time;
        }
    }

    public int getTrafficRate() {
        int res = 0;
        res = totalTraffic / (maxTime.getHour() - minTime.getHour());
        return res;
    }

    @Override
    public String toString() {
        return "Statistics{totalTraffic = " + totalTraffic + ", " + "minTime = " + minTime + ", " + "maxTime = " + maxTime + "}";
    }
}
