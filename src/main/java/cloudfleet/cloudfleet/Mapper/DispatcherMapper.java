package cloudfleet.cloudfleet.Mapper;

import cloudfleet.cloudfleet.DTO.CreateDispatcherDTO;
import cloudfleet.cloudfleet.DTO.DispatcherDTO;
import cloudfleet.cloudfleet.Model.Dispatcher;
import cloudfleet.cloudfleet.Model.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DispatcherMapper {

    @Mapping(target = "missionIds", source = "missions")
    DispatcherDTO toDto(Dispatcher dispatcher);

    List<DispatcherDTO> toDtoList(List<Dispatcher> dispatchers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "missions", ignore = true)
    Dispatcher toEntity(CreateDispatcherDTO dto);

    default Long missionToId(Mission mission) {
        return mission == null ? null : mission.getId();
    }
}