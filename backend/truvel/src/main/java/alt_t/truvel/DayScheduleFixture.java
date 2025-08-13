package alt_t.truvel;

import alt_t.truvel.routeOptimization.daySchedule.enums.PlaceCategory;
import alt_t.truvel.routeOptimization.daySchedule.enums.PreferTime;
import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset.DayScheduleRequest;
import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset.ScheduleRequest;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Location;
import alt_t.truvel.travelPlan.TravelPlan;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DayScheduleFixture {

    // 미리 생성되어있을 TRAVEL_PLAN 테스트용 객체
    public static TravelPlan TRAVEL_PLAN = new TravelPlan(null, "한국",
            LocalDate.of(2025, 6, 16),
            LocalDate.of(2025, 6, 25),"인천");
    public static Location LOCATION1 = new Location(null, TRAVEL_PLAN,"인천", PlaceCategory.Cafe, 39.20207, 126.40009);
    public static Location LOCATION2 = new Location(null, TRAVEL_PLAN,"서울", PlaceCategory.Restaurant, 30.20207, 121.40009);
    public static Location LOCATION3 = new Location(null, TRAVEL_PLAN,"부산", PlaceCategory.Cafe,32.20207, 120.40009);


    // ScheduleRequest 객체 배열 생성 (개별 일정 요청)
    public static List<ScheduleRequest> SCHEDULE_REQUEST = new ArrayList<>();
    static {
        SCHEDULE_REQUEST.add(new ScheduleRequest("인천", 1, PreferTime.Evening, "test", Duration.ofHours(2L)));
    }
    // DayScheduleRequest 객체 생성 (일별일정 요청)
    public static DayScheduleRequest DAY_SCHEDULE_REQUEST = new DayScheduleRequest(
            "인천",
            "부산",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.of(20,15,0),
            "test",
            SCHEDULE_REQUEST);
    
//    public static DayScheduleResponse DAY_SCHEDULE_RESPONSE = new DayScheduleResponse(
//
//    );

}
