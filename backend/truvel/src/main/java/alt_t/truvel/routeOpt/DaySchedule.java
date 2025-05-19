package alt_t.truvel.routeOpt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class DaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long day_schedule_id;

    // 여행 일정 id 외래키
    @ManyToOne
    @JoinColumn(name = "travel_plan_id")
    private TravelPlan travelPlan;

    @ManyToOne
    @JoinColumn(name = "start_location_id", nullable = false)
    private Location startLocation;

    @ManyToOne
    @JoinColumn(name = "end_location_id", nullable = false)
    private Location endLocation;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime finishTime;

    private String dayScheduleMemo;  // 일정 메모

    @OneToMany(mappedBy = "daySchedule", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();
}
