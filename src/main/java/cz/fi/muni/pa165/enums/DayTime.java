package cz.fi.muni.pa165.enums;

import java.time.LocalTime;
/**
 * @author Michaela Horváthová
 */
public enum DayTime {
    Night(LocalTime.of(21, 0), LocalTime.of(7, 59)),
    Day(LocalTime.of(8, 0), LocalTime.of(20, 59));

    private final LocalTime start;
    private final LocalTime end;


    DayTime(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
