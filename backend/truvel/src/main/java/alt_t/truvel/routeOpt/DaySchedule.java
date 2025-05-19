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
    private Long dayScheduleId;

    @Column(nullable = false)
    private Long travelPlanId;

    @ManyToOne
    @JoinColumn(name = "locationId", nullable = false)
    private Location startLocation;

    @ManyToOne
    @JoinColumn(name = "locationId", nullable = false)
    private Location endLocation;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime finishTime;

    @Column(columnDefinition = "TEXT")
    private String dayScheduleMemo;  // 일정 메모

    @OneToMany(mappedBy = "daySchedule", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();
}
