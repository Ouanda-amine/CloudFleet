package cloudfleet.cloudfleet.DTO;

import cloudfleet.cloudfleet.Model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVehiculeDTO(

        @NotBlank String matricule,
        @NotNull VehicleType type

) {
}
