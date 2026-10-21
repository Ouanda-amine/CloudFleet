package cloudfleet.cloudfleet.Model;

import jakarta.persistence.Entity;

@Entity
public class Driver extends User{

    private String licenseNumber;
    private String PhoneNumber;
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




}
