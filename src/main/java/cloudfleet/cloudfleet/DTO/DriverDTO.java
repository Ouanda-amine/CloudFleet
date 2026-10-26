package cloudfleet.cloudfleet.DTO;

import cloudfleet.cloudfleet.Model.DriverStatus;

import java.util.List;

public record DriverDTO(

        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String licenseNumber,
        DriverStatus status,
        boolean enabled,
        List<Long> missionIds
) {
}
