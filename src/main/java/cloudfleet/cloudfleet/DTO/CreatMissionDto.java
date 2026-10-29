package cloudfleet.cloudfleet.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreatMissionDto(

        @NotNull LocalDateTime dateDebut,
        @NotNull LocalDateTime dateFin,
        @NotBlank String departAdresse,
        @NotBlank String destinationAdresse,
        @NotNull Long vehiculeId,
        @NotNull Long driverId


) {
}
