package cloudfleet.cloudfleet.DTO;

import jakarta.validation.constraints.NotBlank;

public record UpdateDispatcherDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String phoneNumber,
        @NotBlank String departement
) {
}
