package alt_t.truvel.daySchedule;

import alt_t.truvel.travelPlan.TravelPlan;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * TravelPlan에 연관관계를 맺기 위한 임시용 엔티티입니다!!
 * 추후에 DaySchedule이 만들어졌을때 제거 해야합니다!!
 *
 */
public class DaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private LocalDateTime startLocationId;

    @ManyToOne
    private LocalDateTime endLocationId;

    @Column
    private LocalDateTime date;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private String dayMemo;


    public void setTravelPlan(TravelPlan travelPlan) {

    }
}
