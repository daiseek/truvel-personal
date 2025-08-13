package alt_t.truvel.routeOptimization.daySchedule.domain.entity;

import alt_t.truvel.routeOptimization.daySchedule.enums.PlaceCategory;
import alt_t.truvel.travelPlan.TravelPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "location")
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long location_id;

    @ManyToOne
    @JoinColumn(name = "travel_plan_id")
    private TravelPlan travelPlan;

    @NotNull
    private String name;

    @NotNull
    private PlaceCategory category;

    @Column
    // 위도
    private double latitude;

    @Column
    // 경도
    private double longitude;

}
