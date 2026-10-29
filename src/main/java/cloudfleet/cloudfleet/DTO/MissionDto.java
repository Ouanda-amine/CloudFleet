package cloudfleet.cloudfleet.DTO;

import cloudfleet.cloudfleet.Model.MissionStatus;

import java.time.LocalDateTime;

public record MissionDto(

        Long id,
        LocalDateTime dateDebut,
        LocalDateTime dateFin,
        LocalDateTime actualDateDebut,
        LocalDateTime actualDateFin,
        String departAdresse,
        String destinationAdresse,
        MissionStatus status,
        Long vehiculeId,
        Long driverId,
        String driverFullName,
        Long dispatcherId

) {
}
