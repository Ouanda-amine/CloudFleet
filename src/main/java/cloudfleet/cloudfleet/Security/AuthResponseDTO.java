package cloudfleet.cloudfleet.Security;

public record AuthResponseDTO(
        String token,
        String email,
        String role
) {
}
