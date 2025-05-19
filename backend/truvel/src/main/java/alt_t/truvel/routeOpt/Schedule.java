package alt_t.truvel.routeOpt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "dayScheduleId", nullable = false)
    private DaySchedule daySchedule;

    @ManyToOne
    @JoinColumn(name = "locationId", nullable = false)
    private Location location;

    @Column(nullable = false)
    private Integer order;

    private Byte preferTime;
    private Byte field2;
    private Byte field3;
    private String field4;

}
