package cloudfleet.cloudfleet.Security;

public record AuthResponseDTO(
        String token,
        Long id,
        String email,
        String role
) {
}
