package alt_t.truvel.routeOptimization.daySchedule.service;

import alt_t.truvel.routeOptimization.daySchedule.dayScheduleDTO.requset.ScheduleRequest;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.DaySchedule;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Location;
import alt_t.truvel.routeOptimization.daySchedule.domain.entity.Schedule;
import alt_t.truvel.routeOptimization.daySchedule.domain.repository.LocationRepository;
import alt_t.truvel.routeOptimization.daySchedule.domain.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final LocationRepository locationRepository;

    public List<Schedule> createSchedule(DaySchedule daySchedule, List<ScheduleRequest> scheduleRequest){
        List<Schedule> schedules = new ArrayList<>();
        scheduleRequest.forEach(scheduleRequest1 -> {
            // 장소 추가에서 저장되어 있는 location을 이름으로 검색해서 가져옴
            Location location = locationRepository.findByName(scheduleRequest1.getLocationName())
                    .orElseThrow(() -> new NoSuchElementException(scheduleRequest1.getLocationName()+"를 찾을 수 없습니다."));

            schedules.add(Schedule.of(daySchedule, scheduleRequest1,location));
        });
        // schedules의 stayTime 초기화
        setStayTime(schedules);
        // 매핑되어있는 daySchedule 에도 update
        daySchedule.updateSchedules(schedules);
        // 생성한 객체 db에 저장
        return scheduleRepository.saveAll(schedules);
    }

    // stayTime이 비어있으면 category에 맞춰 자동으로 stayTime을 설정해주는 함수
    private void setStayTime(List<Schedule> schedules){
        schedules.forEach(schedule -> {
            if (schedule.getStayTime() == null){
                schedule.updateStayTime(schedule.getLocation().getCategory().getStayTime());
            }
        });
    }

}
