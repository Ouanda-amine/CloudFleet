package cloudfleet.cloudfleet.DTO;

import cloudfleet.cloudfleet.Model.VehicleStatus;
import cloudfleet.cloudfleet.Model.VehicleType;

import java.util.List;

public record VehiculeDTO(
        Long id,
        String matricule,
        VehicleType type,
        VehicleStatus status,
        Double currentLatitude,
        Double currentLongitude,
        List<Long> missionIds
) {
}
