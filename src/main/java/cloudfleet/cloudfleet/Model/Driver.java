package cloudfleet.cloudfleet.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter

public class Driver extends User{

    private String licenseNumber;
    private String PhoneNumber;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    public Driver() {
        this.setRole(Role.DRIVER);
    }

    public Driver(Long id, String nom, String prenom, String email, String password,boolean isEnabled, String licenseNumber, String phoneNumber, DriverStatus status) {
        super(id, nom, prenom, email, password,isEnabled, Role.DRIVER);
        this.licenseNumber = licenseNumber;
        this.PhoneNumber = phoneNumber;
        this.status = status;
    }

    @OneToMany
    private List<Mission> missions;





}
