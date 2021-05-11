package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Michaela Horváthová
 */
public class MeterLogDTO {
    private String logDate;
    private String logTime;
    private Long measure;
    private Long smartMeterId;
    private String createStamp;


    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(String createStamp) {
        this.createStamp = createStamp;
    }

    public Long getMeasure() {
        return measure;
    }

    public void setMeasure(Long measure) {
        this.measure = measure;
    }

    public Long getSmartMeterId() {
        return smartMeterId;
    }

    public void setSmartMeterId(Long smartMeterId) {
        this.smartMeterId = smartMeterId;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeterLogDTO)) return false;
        MeterLogDTO that = (MeterLogDTO) o;
        return getLogDate().equals(that.getLogDate())
                && getLogTime().equals(that.getLogTime())
                && getMeasure().equals(that.getMeasure())
                && getSmartMeterId().equals(that.getSmartMeterId())
                && Objects.equals(getCreateStamp(), that.getCreateStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogDate(), getLogTime(), getMeasure(), getSmartMeterId(), getCreateStamp());
    }

    @Override
    public String toString() {
        return "MeterLogDTO{" +
                "logDate=" + logDate +
                ", logTime=" + logTime +
                ", measure=" + measure +
                ", smartMeter=" + smartMeterId +
                '}';
    }
}
