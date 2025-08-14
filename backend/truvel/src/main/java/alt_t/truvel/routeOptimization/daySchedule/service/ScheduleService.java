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

    public List<Schedule> createSchedule(DaySchedule daySchedule, List<ScheduleRequest> scheduleRequests){
        List<Schedule> schedules = new ArrayList<>();
        scheduleRequests.forEach(scheduleRequest -> {
            Location location = locationRepository.findByName(scheduleRequest.getLocationName())
                    .orElseThrow(() -> new NoSuchElementException(scheduleRequest.getLocationName() + "를 찾을 수 없습니다."));

            // 중복 체크: 이미 같은 daySchedule, location 조합이 있는지 확인
            boolean exists = scheduleRepository.existsByDayScheduleAndLocation(daySchedule, location);
            if (exists) {
                // 필요에 따라 예외 발생 또는 건너뛰기
                throw new IllegalArgumentException("이미 등록된 일정입니다: " + location.getName());
                // 또는 return; 으로 건너뛰기
            }

            schedules.add(Schedule.of(daySchedule, scheduleRequest, location));
        });

        setStayTime(schedules);
        daySchedule.updateSchedules(schedules);

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
