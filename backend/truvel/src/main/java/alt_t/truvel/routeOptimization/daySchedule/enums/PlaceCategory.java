package alt_t.truvel.routeOptimization.daySchedule.enums;

import lombok.Getter;

import java.time.Duration;

@Getter
public enum PlaceCategory {
    Cafe(Duration.ofHours(2L)),
    Restaurant(Duration.ofHours(2L));

    PlaceCategory(Duration stayTime){
        this.stayTime = stayTime;
    }
    private final Duration stayTime;
}
