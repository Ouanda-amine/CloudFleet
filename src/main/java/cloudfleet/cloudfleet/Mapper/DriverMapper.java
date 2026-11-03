package cloudfleet.cloudfleet.Mapper;

import cloudfleet.cloudfleet.DTO.CreateDriverDTO;
import cloudfleet.cloudfleet.DTO.DriverDTO;
import cloudfleet.cloudfleet.DTO.UpdateDriverDTO;
import cloudfleet.cloudfleet.Model.Driver;
import cloudfleet.cloudfleet.Model.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DriverMapper {

    @Mapping(target = "missionIds", source = "missions")
    DriverDTO ToDTO(Driver driver);

    List<DriverDTO> ToDTOList(List<Driver> drivers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "missions", ignore = true)
    Driver ToEntity(CreateDriverDTO createDriverDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "missions", ignore = true)
    void updateEntity(UpdateDriverDTO dto, @MappingTarget Driver driver);

    default Long missionToId(Mission mission) {
        return mission == null ? null : mission.getId();
    }

}
