package cloudfleet.cloudfleet.DTO;

import java.util.List;

public record DispatcherDTO(

        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String department,
        boolean enabled,
        List<Long> missionIds
) {
}
