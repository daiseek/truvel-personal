package alt_t.truvel.routeOptimization.daySchedule.service;

import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset.DayScheduleRequest;
import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset.ScheduleRequest;
import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.response.DayScheduleResponse;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.DaySchedule;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Location;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Schedule;
import alt_t.truvel.routeOptimization.daySchedule.domain.repository.DayScheduleRepository;
import alt_t.truvel.routeOptimization.daySchedule.domain.repository.LocationRepository;
import alt_t.truvel.routeOptimization.daySchedule.domain.repository.ScheduleRepository;
import alt_t.truvel.travelPlan.TravelPlan;
import alt_t.truvel.travelPlan.TravelPlanRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional
public class DayScheduleService {
    private final DayScheduleRepository dayScheduleRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final ScheduleService scheduleService;
    private final LocationRepository locationRepository;

    // Response 객체에서 최적화된 일정을 부르기 위한 함수
    public DayScheduleResponse getOptimizationDaySchedule(Long travel_plan_id, DayScheduleRequest dayScheduleRequest){
        // 컨트롤러에서 받은 요청값들 daySchedule 객체로 변환
        TravelPlan travelPlan = travelPlanRepository.findById(travel_plan_id).orElseThrow();

        // 일별 일정 생성
        DaySchedule daySchedule = createDaySchedule(travelPlan, dayScheduleRequest);

        // daySchedule에 대한 일정 최적화
        List<Schedule> schedules = RouteOptimization.optimization(daySchedule.getSchedules(), daySchedule);
        daySchedule.updateSchedules(schedules);
        return new DayScheduleResponse(daySchedule);
    }

    // 실제로 DaySchedule 생성에 사용할 함수
    // 종속되어있는 schedule을 함께 생성함
    public DaySchedule createDaySchedule(TravelPlan travelPlan, DayScheduleRequest dayScheduleRequest){

        // location service에 아래 함수 추가 필요
        Location startLocation = locationRepository.findByName(dayScheduleRequest.getStartLocation()).orElseThrow(
                ()-> new NoSuchElementException(dayScheduleRequest.getStartLocation()+"은 저장되지 않은 장소입니다.")
        );
        Location endLocation = locationRepository.findByName(dayScheduleRequest.getEndLocation()).orElseThrow(
                ()-> new NoSuchElementException(dayScheduleRequest.getStartLocation()+"은 저장되지 않은 장소입니다.")
        );

        DaySchedule daySchedule = saveDaySchedule(DaySchedule.of(travelPlan, dayScheduleRequest,startLocation,endLocation));
        scheduleService.createSchedule(daySchedule, dayScheduleRequest.getSchedules());
        return daySchedule;
    }

    // 기본적인 save 함수
    public DaySchedule saveDaySchedule(DaySchedule daySchedule){
        return dayScheduleRepository.save(daySchedule);
    }
    // 기본적인 update 함수
    public void updateDaySchedule(Long id, DayScheduleRequest dayScheduleRequest){
        DaySchedule daySchedule = dayScheduleRepository.findById(id).orElseThrow();
        Location startLocation = locationRepository.findByName(dayScheduleRequest.getStartLocation()).orElseThrow(
                ()-> new NoSuchElementException(dayScheduleRequest.getStartLocation()+"은 저장되지 않은 장소입니다.")
        );
        Location endLocation = locationRepository.findByName(dayScheduleRequest.getEndLocation()).orElseThrow(
                ()-> new NoSuchElementException(dayScheduleRequest.getStartLocation()+"은 저장되지 않은 장소입니다.")
        );
        daySchedule.update(dayScheduleRequest, startLocation, endLocation);
    }

    // GET 일별 일정
    public DaySchedule getDaySchedule(Long day_schedule_id){
        return dayScheduleRepository.findById(day_schedule_id).orElseThrow(() ->
                new NoSuchElementException("[DayScheduleService] NotFound daySchedule"));
    }
}