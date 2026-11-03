package cloudfleet.cloudfleet.Security;

import cloudfleet.cloudfleet.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = (User) auth.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, user.getEmail(), user.getRole().name());
    }
}
