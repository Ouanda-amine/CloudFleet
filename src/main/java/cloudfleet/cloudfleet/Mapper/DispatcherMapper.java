package cloudfleet.cloudfleet.Mapper;

import cloudfleet.cloudfleet.DTO.CreateDispatcherDTO;
import cloudfleet.cloudfleet.DTO.DispatcherDTO;
import cloudfleet.cloudfleet.DTO.UpdateDispatcherDTO;
import cloudfleet.cloudfleet.Model.Dispatcher;
import cloudfleet.cloudfleet.Model.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DispatcherMapper {

    @Mapping(target = "missionIds", source = "missions")
    DispatcherDTO ToDTO(Dispatcher dispatcher);

    List<DispatcherDTO> ToDTOList(List<Dispatcher> dispatchers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "missions", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Dispatcher ToEntity(CreateDispatcherDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "missions", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void updateEntity(UpdateDispatcherDTO dto, @MappingTarget Dispatcher dispatcher);

    default Long missionToId(Mission mission) {
        return mission == null ? null : mission.getId();
    }
}