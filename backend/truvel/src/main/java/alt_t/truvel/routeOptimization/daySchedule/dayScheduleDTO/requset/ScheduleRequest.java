package alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset;

import alt_t.truvel.routeOptimization.daySchedule.enums.PreferTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class ScheduleRequest {
    private String locationName;
    private Integer scheduleOrder;
    private PreferTime preferTime;
    private String memo;
    private Duration stayTime;
}
