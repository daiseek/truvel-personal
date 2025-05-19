package alt_t.truvel.routeOpt.daySchedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Controller
@Getter
@Setter
@RequiredArgsConstructor
public class DayScheduleController {
    private final DayScheduleService dayScheduleService;
}
