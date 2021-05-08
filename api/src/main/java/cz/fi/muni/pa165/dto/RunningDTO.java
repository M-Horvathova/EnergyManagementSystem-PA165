package cz.fi.muni.pa165.dto;

import java.util.Objects;

public class RunningDTO {

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

        RunningDTO that = (RunningDTO) o;

        return Objects.equals(running, that.running);
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
