package alt_t.truvel.routeOpt.daySchedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class DayScheduleService {
    private final DayScheduleRepository dayScheduleRepository;


}
