package alt_t.truvel.routeOpt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class TravelPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPlanId;

    @Column(nullable = false)
    private Long budgetTrackerId;

    @Column(nullable = false)
    private String nation;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate finishDate;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL)
    private List<DaySchedule> daySchedules = new ArrayList<>();

}
