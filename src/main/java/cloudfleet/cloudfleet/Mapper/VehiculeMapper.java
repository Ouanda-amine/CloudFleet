package cloudfleet.cloudfleet.Mapper;

import cloudfleet.cloudfleet.DTO.CreateVehiculeDTO;
import cloudfleet.cloudfleet.DTO.UpdateVehiculePositionDTO;
import cloudfleet.cloudfleet.DTO.VehiculeDTO;
import cloudfleet.cloudfleet.Model.Mission;
import cloudfleet.cloudfleet.Model.Vehicule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(target = "missionIds", source = "missions")
    VehiculeDTO toDto(Vehicule vehicule);

    List<VehiculeDTO> toDtoList(List<Vehicule> vehicules);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "currentLatitude", ignore = true)
    @Mapping(target = "currentLongitude", ignore = true)
    @Mapping(target = "missions", ignore = true)
    Vehicule toEntity(CreateVehiculeDTO dto);

    @Mapping(target = "currentLatitude", source = "latitude")
    @Mapping(target = "currentLongitude", source = "longitude")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "matricule", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "missions", ignore = true)
    void updateLocation(UpdateVehiculePositionDTO dto, @MappingTarget Vehicule vehicule);

    default Long missionToId(Mission mission) {
        return mission == null ? null : mission.getId();
    }
}
