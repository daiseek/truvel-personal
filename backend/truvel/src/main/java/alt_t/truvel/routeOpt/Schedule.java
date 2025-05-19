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
    private Long schedule_id;

    @ManyToOne
    @JoinColumn(name = "day_schedule_id", nullable = false)
    private DaySchedule daySchedule;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // 우선순위 숫자가 작을수록 높은 우선순위
    @Column(nullable = false)
    private Integer order;

    // 가고싶은 시간대를 아침,점심,저녁,랜덤으로 나누고 입력받음
    private PreferTime preferTime;

    private String memo;

}
