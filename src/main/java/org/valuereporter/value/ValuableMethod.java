package org.valuereporter.value;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ValuableMethod {

    private String prefix = "";
    private String name;
    private long usageCount;
    private long startTime;
    private long endTime;

    public ValuableMethod(String name, long usageCount) {
        this.name = name;
        this.usageCount = usageCount;
    }
    public ValuableMethod(String prefix,String name, long usageCount) {
        this.prefix = prefix;
        this.name = name;
        this.usageCount = usageCount;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public long getUsageCount() {
        return usageCount;
    }

    public void addUsageCount(long usageCount) {
        this.usageCount += usageCount;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
