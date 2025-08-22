package alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class DayScheduleRequest {
    // daySchedule DTO Request
    private String startLocation;
    private String endLocation;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime finishTime;
    private String dayScheduleMemo;
    private List<ScheduleRequest> schedules;
}
