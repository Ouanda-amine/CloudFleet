package cloudfleet.cloudfleet.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDriverDTO(

        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String phoneNumber,
        @NotBlank String licenseNumber

) {
}
