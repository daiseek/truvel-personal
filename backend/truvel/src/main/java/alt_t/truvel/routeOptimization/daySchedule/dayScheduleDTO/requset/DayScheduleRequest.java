package alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset;

import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Location;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Schedule;
import alt_t.truvel.travelPlan.TravelPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
