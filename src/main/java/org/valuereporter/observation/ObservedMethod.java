package org.valuereporter.observation;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ObservedMethod {
    private String prefix = "";
    private final String name;
    private final long startTime;
    private final long endTime;
    private final long duration;


    public ObservedMethod(String name, long startTime, long endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        duration = endTime - startTime;
    }
    public ObservedMethod(String prefix,String name, long startTime, long endTime) {
        this.prefix = prefix;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        duration = endTime - startTime;
    }

    public ObservedMethod(String prefix, String name, long startTime, long endTime, long duration) {
        this.prefix = prefix;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return duration;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "ObservedMethod{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", prefix='" + prefix + '\'' +
                '}';
    }

    public String toCsv() {
        return new String(getPrefix() +"," + getName() +"," + getStartTime() +"," + getEndTime() + "," + getDuration());

    }
}
