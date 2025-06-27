package alt_t.truvel.location.service;

import java.util.List;

import alt_t.truvel.location.domain.entity.Location;
import alt_t.truvel.location.domain.repository.LocationRepository;
import alt_t.truvel.location.locationDto.response.GooglePlaceResultDto;
import alt_t.truvel.location.locationDto.response.LocationResponseDto;
import alt_t.truvel.location.locationDto.request.LocationSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LocationService {

    private final GooglePlaceClient googlePlaceClient;
    private final LocationRepository locationRepository;

    // 장소 후보 검색
    public List<GooglePlaceResultDto> searchPlaces(String query) {
        return googlePlaceClient.search(query);
    }

    // 장소 저장
    public List<LocationResponseDto> saveSelectedPlaces(List<LocationSaveRequestDto> dtos) {
        return dtos.stream().map(dto -> {
            Location location = Location.builder()
                    .place(dto.getPlace())
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .address(dto.getAddress())
                    .build();
            locationRepository.save(location);

            return LocationResponseDto.builder()
                    .locationId(location.getId())
                    .place(location.getPlace())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .address(location.getAddress())
                    .build();
        }).toList();
    }
}