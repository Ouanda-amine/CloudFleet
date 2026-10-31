package cloudfleet.cloudfleet.Security;

import cloudfleet.cloudfleet.Model.Admin;
import cloudfleet.cloudfleet.Repository.AdminRepository;
import cloudfleet.cloudfleet.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;




    @Override
    public void run(String... args) throws Exception {

        if (adminPassword == null || adminPassword.isBlank()) {
            System.out.println("ADMIN_PASSWORD non défini : aucun admin créé.");
            return;
        }
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        Admin admin = new Admin();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setEnabled(true);
        adminRepository.save(admin);

    }
}
