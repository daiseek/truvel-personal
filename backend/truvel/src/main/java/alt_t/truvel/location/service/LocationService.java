package alt_t.truvel.location.service;

import java.util.List;

import alt_t.truvel.location.domain.entity.Location;
import alt_t.truvel.location.domain.repository.LocationRepository;
import alt_t.truvel.location.locationDto.response.GooglePlaceResultDto;
import alt_t.truvel.location.locationDto.response.LocationResponseDto;
import alt_t.truvel.location.locationDto.request.LocationSaveRequestDto;
import alt_t.truvel.travelPlan.TravelPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LocationService {

    private final GooglePlaceClient googlePlaceClient;
    private final LocationRepository locationRepository;
    private final TravelPlanRepository travelPlanRepository;
    // 장소 후보 검색
    public List<GooglePlaceResultDto> searchPlaces(String query) {
        return googlePlaceClient.search(query);
    }

    // 장소 저장
    public List<LocationResponseDto> saveSelectedPlaces(Long travel_plan_id , List<LocationSaveRequestDto> dtos) {
        return dtos.stream().map(dto -> {
            Location location = Location.builder()
                    .travelPlan(travelPlanRepository.getReferenceById(travel_plan_id))
                    .name(dto.getName())
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .address(dto.getAddress())
                    .category(dto.getCategory())
                    .build();
            System.out.println("[LocationService] 저장할 장소 정보: " + location.getCategory());
            Location saved = locationRepository.save(location);

            return LocationResponseDto.builder()
                    .locationId(saved.getLocation_id()) // 이제 null 아님
                    .place(saved.getName())
                    .latitude((float) saved.getLatitude())
                    .longitude((float) saved.getLongitude())
                    .address(saved.getAddress())
                    .category(String.valueOf(saved.getCategory()))
                    .build();
        }).toList();
    }
}
