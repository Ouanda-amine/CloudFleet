package cloudfleet.cloudfleet.Mapper;

import cloudfleet.cloudfleet.DTO.CreateDriverDTO;
import cloudfleet.cloudfleet.DTO.DriverDTO;
import cloudfleet.cloudfleet.Model.Driver;
import cloudfleet.cloudfleet.Model.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DriverMapper {

    DriverDTO ToDTO(Driver driver);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "missions", ignore = true)
    Driver ToEntity(CreateDriverDTO createDriverDTO);

    default Long missionToId(Mission mission) {
        return mission == null ? null : mission.getId();
    }

}
