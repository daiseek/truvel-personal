package alt_t.truvel.routeOptimization.daySchedule.enums;

import java.time.LocalTime;

public enum PreferTime {
    Morning,
    Afternoon,
    Evening,
    // 비지니스 로직에서 아침, 점심, 저녁 중 하나로 랜덤하게 바뀜
    Random;
    public static final LocalTime MAX_MORNING_TIME = LocalTime.of(12,0);
    public static final LocalTime MAX_AFTERNOON_TIME = LocalTime.of(12,0);
    public static final LocalTime MAX_EVENING_TIME = LocalTime.of(12,0);
}
