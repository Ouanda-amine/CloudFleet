package cloudfleet.cloudfleet.DTO;

import cloudfleet.cloudfleet.Model.DriverStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateDriverDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String phoneNumber,
        @NotBlank String licenseNumber,
        @NotNull DriverStatus status
) {
}
