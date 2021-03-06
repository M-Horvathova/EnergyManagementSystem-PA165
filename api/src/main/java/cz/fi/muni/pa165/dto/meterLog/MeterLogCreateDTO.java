package cz.fi.muni.pa165.dto.meterLog;
import java.util.Objects;

/**
 * @author Michaela Hrováthová
 */
public class MeterLogCreateDTO {
    private Long measure;
    private String logDate;
    private Long smartMeterId;
    private String logTime;

    public Long getSmartMeterId() {
        return smartMeterId;
    }

    public void setSmartMeterId(Long smartMeterId) {
        this.smartMeterId = smartMeterId;
    }

    public Long getMeasure() {
        return measure;
    }

    public void setMeasure(Long measure) {
        this.measure = measure;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeterLogCreateDTO)) return false;
        MeterLogCreateDTO that = (MeterLogCreateDTO) o;
        return getMeasure().equals(that.getMeasure()) &&
                getSmartMeterId().equals(that.getSmartMeterId())
                && Objects.equals(getLogDate(), that.getLogDate()) &&
                Objects.equals(getLogTime(), that.getLogTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMeasure(), getSmartMeterId(), getLogDate(), getLogTime());
    }

    @Override
    public String toString() {
        return "MeterLogCreateDTO{" +
                "measure=" + measure +
                ", smartMeter=" + smartMeterId +
                ", logDate=" + logDate +
                ", logTime=" + logTime +
                '}';
    }
}
