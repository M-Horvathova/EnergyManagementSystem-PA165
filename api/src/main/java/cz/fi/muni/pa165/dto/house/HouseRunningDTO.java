package cz.fi.muni.pa165.dto.house;

import java.util.Objects;

public class HouseRunningDTO {

    private Boolean running;

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseRunningDTO that = (HouseRunningDTO) o;

        return Objects.equals(running, that.getRunning());
    }

    @Override
    public int hashCode() {
        return running != null ? running.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RunningDTO{" +
                "running=" + running +
                '}';
    }
}
