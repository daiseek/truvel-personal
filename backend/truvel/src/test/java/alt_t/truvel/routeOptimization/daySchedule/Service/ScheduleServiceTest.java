package alt_t.truvel.routeOptimization.daySchedule.Service;

import alt_t.truvel.location.domain.entity.Location;
import alt_t.truvel.location.domain.repository.LocationRepository;
import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset.DayScheduleRequest;
import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset.ScheduleRequest;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.DaySchedule;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Schedule;
import alt_t.truvel.routeOptimization.daySchedule.domain.repository.DayScheduleRepository;
import alt_t.truvel.location.PlaceCategory;
import alt_t.truvel.routeOptimization.daySchedule.service.ScheduleService;
import alt_t.truvel.travelPlan.TravelPlan;
import alt_t.truvel.travelPlan.TravelPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static alt_t.truvel.routeOptimization.daySchedule.DayScheduleFixture.DAY_SCHEDULE_REQUEST;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Autowired
    private DayScheduleRepository dayScheduleRepository;

    TravelPlan TRAVEL_PLAN;
    Location LOCATION1;
    Location LOCATION2;
    Location LOCATION3;

    @BeforeEach
    public void beforeEach(){
        TRAVEL_PLAN = new TravelPlan(null, "한국",
                LocalDate.of(2025, 6, 16),
                LocalDate.of(2025, 6, 25),"인천");
        LOCATION1 = new Location(null, TRAVEL_PLAN,"인천", "address" ,PlaceCategory.CAFE, 39.20207, 126.40009);
        LOCATION2 = new Location(null, TRAVEL_PLAN,"서울", "address", PlaceCategory.RESTAURANT, 30.20207, 121.40009);
        LOCATION3 = new Location(null, TRAVEL_PLAN,"부산", "address", PlaceCategory.CAFE,32.20207, 120.40009);
    }

    @Test
    void createGet() {
        TravelPlan TRAVEL_PLAN = new TravelPlan(null, "한국",
                LocalDate.of(2025, 6, 16),
                LocalDate.of(2025, 6, 25),"인천");
        //given
        DayScheduleRequest dayScheduleRequest = DAY_SCHEDULE_REQUEST;
        travelPlanRepository.save(TRAVEL_PLAN);
        locationRepository.save(LOCATION1);
        locationRepository.save(LOCATION2);
        locationRepository.save(LOCATION3);
        DaySchedule daySchedule = dayScheduleRepository.save(DaySchedule.of(TRAVEL_PLAN, DAY_SCHEDULE_REQUEST,LOCATION1,LOCATION3));

        // when
        List<ScheduleRequest> schedules1 = DAY_SCHEDULE_REQUEST.getSchedules();
        // create
        List<Schedule> schedules2 = scheduleService.createSchedule(daySchedule, dayScheduleRequest.getSchedules());
        // get
        List<Schedule> schedules3 = daySchedule.getSchedules();

        // then
        System.out.println(schedules2.getFirst().getLocation().getName());
        assertEquals(schedules1.getFirst().getLocationName(), schedules2.getFirst().getLocation().getName());
        assertEquals(schedules1.getFirst().getLocationName(),schedules3.getFirst().getLocation().getName());
    }
}