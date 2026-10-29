package cloudfleet.cloudfleet.Mapper;

import cloudfleet.cloudfleet.DTO.CreatMissionDto;
import cloudfleet.cloudfleet.DTO.MissionDto;
import cloudfleet.cloudfleet.Model.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MissionMapper {




    @Mapping(target = "vehiculeId", source = "vehicule.id")
    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "dispatcherId", source = "dispatcher.id")
    @Mapping(target = "driverFullName",
            expression = "java(mission.getDriver() == null ? null : mission.getDriver().getFirstName() + \" \" + mission.getDriver().getLastName())")
    MissionDto toDto(Mission mission);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "actualDateDebut", ignore = true)
    @Mapping(target = "actualDateFin", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "dispatcher", ignore = true)
    Mission toEntity(CreatMissionDto dto);
}
